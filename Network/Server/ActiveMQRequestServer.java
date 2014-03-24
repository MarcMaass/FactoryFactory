package de.hsrm.mi.swt.Network.Server;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.event.EventListenerList;

import com.google.gson.JsonParseException;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.FactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Keys;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Exceptions.ActionNotFoundException;
import de.hsrm.mi.swt.Network.Exceptions.CommandNotFoundException;
import de.hsrm.mi.swt.Network.Exceptions.DataNotFoundException;
import de.hsrm.mi.swt.Network.Exceptions.TargetNotFoundException;
import de.hsrm.mi.swt.Network.Listener.ServerEvent;
import de.hsrm.mi.swt.Network.Listener.ServerListener;
import de.hsrm.mi.swt.Network.Server.intf.IServer;
import de.hsrm.mi.swt.Network.Utilities.JSONParser;

/**
 * 
 * @author mwand001
 * 
 */
public class ActiveMQRequestServer implements MessageListener, IServer<Message> {
	private EventListenerList myServerListeners;
	private String queueName;
	private Session serverManagerSession;
	private Destination requestQueue;
	private MessageConsumer queueConsumer;
	private MessageProducer replyProducer;

	/* model und Observer (Instanz der Run-Klasse - spaeter Controller!) */
	private JSONParser parser;

	public ActiveMQRequestServer(String queueName,
			Session serverManagerSession, MessageProducer serverMessageProducer) {
		myServerListeners = new EventListenerList();
		this.queueName = queueName;
		this.serverManagerSession = serverManagerSession;
		this.replyProducer = serverMessageProducer;
		this.parser = new JSONParser();
		setup();
		System.out
				.println(queueName + " initialized...! Waiting for Messages!");
	}

	private void setup() {
		try {
			requestQueue = serverManagerSession.createQueue(queueName);
			queueConsumer = serverManagerSession.createConsumer(requestQueue); // Create
																				// Queue
																				// Consumer
			queueConsumer.setMessageListener(this);
			replyProducer = serverManagerSession.createProducer(null);
			replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	public void onMessage(Message msg) {
		try {
			if (msg instanceof MapMessage) {
				handleMapMessage((MapMessage) msg);
			} else if (msg instanceof BytesMessage) {
				handleBytesMessage((BytesMessage) msg);
			} else if (msg instanceof TextMessage) {
				handleTextMessage((TextMessage) msg);
			} else {
				send(msg, new Command(CommandType.EXCEPTION, Targets.EXCEPTION,
						"epischer Fehlschlag"));
			}
			/*
			 * response.setJMSCorrelationID(msg.getJMSCorrelationID());
			 * response.setJMSReplyTo(msg.getJMSReplyTo());
			 * System.out.println("-- Sende Nachricht!");
			 * this.replyProducer.send(response);
			 */
		} catch (Exception e) {
			// TODO: Nice Test Case :-)
			send(msg, new Command(CommandType.EXCEPTION, Targets.FACTORY, e.getMessage(), true));
		}
	}

	/**
	 * 
	 * @param mapMsg
	 * @throws JMSException
	 * @throws CommandNotFoundException
	 * @throws DataNotFoundException
	 * @throws JsonParseException
	 * @throws ClassNotFoundException
	 */
	private void handleMapMessage(MapMessage mapMsg) throws JMSException,
			ActionNotFoundException, TargetNotFoundException, DataNotFoundException,
			JsonParseException, ClassNotFoundException {
		ICommand dispatchingCommand;
		long sessionID;
		String action, target, data;
		String objectType;
		if (mapMsg.getStringProperty(Keys.ACTION.name()) == null) {
			throw new ActionNotFoundException("StringProperty: ACTION is missing!");
		}
		if (mapMsg.getStringProperty(Keys.TARGET.name()) == null) {
			throw new TargetNotFoundException("StringProperty: TARGET is missing!");
		}
		if (mapMsg.getStringProperty(Keys.DATA.name()) == null) {
			throw new DataNotFoundException("StringProperty: DATA is missing!");
		}
		action = mapMsg.getStringProperty(Keys.ACTION.name());
		data = mapMsg.getStringProperty(Keys.DATA.name());
		target = mapMsg.getStringProperty(Keys.TARGET.name());
		// Check the Target for object types :-)
		if (target.equals(Targets.USER.name())) {
			objectType = User.class.getName();
		} else if (target.equals(Targets.FACTORY.name())) {
			if (action.equals(CommandType.CREATE_MACHINE.name()))
				objectType = MachineUnit.class.getName();
			else
				objectType = FactoryHeader.class.getName();
		} else if (target.equals(Targets.MACHINE.name())) {
			objectType = MachineUnit.class.getName();
		} else {
			objectType = null;
		}
		System.out.println("INCOMING ACTION: " + action);
		System.out.println("INCOMING TARGET: " + target);
		System.out.println("INCOMING DATA: " + data);
		dispatchingCommand = new Command(CommandType.valueOf(action), Targets.valueOf(target), parser.deserializeUser(data, objectType));
		if (mapMsg.getStringProperty(Keys.SID.name()) != null) {
			sessionID = Long.parseLong(mapMsg.getStringProperty(Keys.SID.name()));
			System.out.println("INCOMING SID: " + sessionID);
			dispatchingCommand.setSessionID(sessionID);
		}
		notifyServerListeners(dispatchingCommand, mapMsg);
	}

	/**
	 * BytesMessage Handler-Method
	 * 
	 * @param bytesMsg
	 * @throws JMSException
	 */
	private void handleBytesMessage(BytesMessage bytesMsg) throws JMSException {
		// NOT IMPLEMENTED!!!! TODO!!!
		throw new JMSException("Not implemented yet!!");
	}

	/**
	 * TextMessage Handler-Method
	 * 
	 * @param bytesMsg
	 * @throws JMSException
	 */
	private void handleTextMessage(TextMessage txtMsg) throws JMSException {
		// NOT IMPLEMENTED!!!! TODO!!!
		throw new JMSException("Not implemented yet!!");
	}

	private byte[] createRawDataString(char[] charChain) {
		byte[] rawData = new byte[charChain.length];
		for (int i = 0; i < rawData.length; i++)
			rawData[i] = (byte) charChain[i];
		return rawData;
	}

	/**
	 * Reads an Image Object and converts it to an array
	 * 
	 * @param i
	 * @return
	 */
	private byte[] readImageToByteArray(String path) {
		ByteArrayOutputStream baos;
		BufferedImage bufImage;
		baos = new ByteArrayOutputStream();
		try {
			bufImage = ImageIO.read(new File(path));
			ImageIO.write(bufImage, "JPEG", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	@Override
	public void send(Message receiver, ICommand command) {
		try {
			if (command.getAction() == CommandType.GET) {
				BytesMessage response = this.serverManagerSession
						.createBytesMessage();
				response.setStringProperty(Keys.ACTION.name(), command
						.getAction().name());
				response.setStringProperty(Keys.TYPE.name(), command
						.getTarget().name());
				response.writeBytes(command.getBinaryData());
				// response.setJMSCorrelationID(origMsg.getJMSCorrelationID());
				// } else if (command.getAction() == CommandType.LOGIN) {
				response.setStringProperty(Keys.CID.name() , receiver.getStringProperty(Keys.CID.name()));
				this.replyProducer.send(receiver.getJMSReplyTo(), response);
			} else {
				MapMessage response = this.serverManagerSession
						.createMapMessage();
				response.setStringProperty(Keys.ACTION.name(), command
						.getAction().name());
				response.setStringProperty(Keys.TARGET.name(), command
						.getTarget().name());
				if (command.getAction() == CommandType.EXCEPTION || command.getTarget() == Targets.EXCEPTION) {
					System.out.println("EXCEPTION SENDING");
					System.out.println(command.getErrorMessage());
					response.setStringProperty(Keys.DATA.name(),
							command.getErrorMessage());
				} else {
					System.out.println("DATA SENDING");
					System.out.println(parser.serialize(command.getData()));
					response.setStringProperty(Keys.DATA.name(),
							parser.serialize(command.getData()));
				}
				
				response.setStringProperty(Keys.CID.name(), receiver.getStringProperty(Keys.CID.name()));
				// response.setJMSCorrelationID(origMsg.getJMSCorrelationID());
				this.replyProducer.send(receiver.getJMSReplyTo(), response);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addServerListener(ServerListener sl) {
		myServerListeners.add(ServerListener.class, sl);

	}

	@Override
	public void removeServerListener(ServerListener sl) {
		myServerListeners.remove(ServerListener.class, sl);
	}

	private void notifyServerListeners(ICommand myCommand, Message requester) {
		for (ServerListener<Message> sl : myServerListeners
				.getListeners(ServerListener.class)) {
			sl.fireServerEvent(new ServerEvent<Message>(this, myCommand,
					requester));
		}
	}

	public String getQueueName() {
		return queueName;
	}

}