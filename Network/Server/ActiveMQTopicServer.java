package de.hsrm.mi.swt.Network.Server;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.swing.event.EventListenerList;

import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Keys;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Listener.ServerEvent;
import de.hsrm.mi.swt.Network.Listener.ServerListener;
import de.hsrm.mi.swt.Network.Server.intf.IServer;
import de.hsrm.mi.swt.Network.Utilities.JSONParser;

/**
 * This Class is responsible for sending Messages in one Topic
 * @author Justin Albert, Mario Wandpflug
 *
 */
public class ActiveMQTopicServer implements MessageListener, IServer<Message> {
	/** instances of the listener, who listens to this class */
	private EventListenerList myServerListeners;
	/** name of the topic Server */
	private String topicName;
	/** Destination. Where should all the Messages send */
	private Destination topicDestination;
	/** instance of an ActiveMQ Session */
	private Session serverManagerSession;

	private MessageProducer topicMessageProducer;
	private MessageConsumer topicConsumer;

	/* model und Observer (Instanz der Run-Klasse - spaeter Controller!) */
	/** instance of the JSON parser, to de-serialize Objects with GSON */
	private JSONParser parser;

	/**
	 * Constructor for the Topic Server
	 * @param topicName
	 * @param serverManagerSession
	 * @param serverMessageProducer
	 */
	public ActiveMQTopicServer(String topicName, Session serverManagerSession,
			MessageProducer serverMessageProducer) {
		this.topicName = topicName;
		this.serverManagerSession = serverManagerSession;
		topicMessageProducer = serverMessageProducer;
		setup();
	}

	/**
	 * setup method initialize all instances of Objects
	 */
	private void setup() {
		try {
			topicDestination = serverManagerSession.createTopic(topicName);
			topicConsumer = serverManagerSession.createConsumer(topicDestination);
			topicConsumer.setMessageListener(this);
			this.parser = new JSONParser();
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	@Override
	public void send(Message receiver, ICommand command) {
		try {
			MapMessage response = serverManagerSession.createMapMessage();
			response.setStringProperty(Keys.ACTION.name(), command.getAction()
					.name());
			if (command.getAction() == CommandType.EXCEPTION) {
				response.setStringProperty(Keys.DATA.name(),
						command.getErrorMessage());
			} else {
				response.setStringProperty(Keys.DATA.name(),
						parser.serialize(command.getData()));
			}
			// response.setJMSCorrelationID(origMsg.getJMSCorrelationID());
			// Send Message to Topic
			topicMessageProducer.send(topicDestination, response);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addServerListener(ServerListener sl) {
		myServerListeners.add(ServerListener.class, sl);
	}

	@Override
	public void removeServerListener(ServerListener sl) {
		myServerListeners.remove(ServerListener.class, sl);
	}

	/**
	 * Notify Listeners when there is an Event of this Server
	 * 
	 * @param myCommand
	 *            Certain Command of this stuff
	 * @param requester
	 */
	private void notifyServerListeners(ICommand myCommand, Message requester) {
		for (ServerListener<Message> sl : myServerListeners
				.getListeners(ServerListener.class)) {
			sl.fireServerEvent(new ServerEvent<Message>(this, myCommand,
					requester));
		}
	}

}
