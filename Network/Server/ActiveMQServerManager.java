package de.hsrm.mi.swt.Network.Server;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Exceptions.UnknownServerException;
import de.hsrm.mi.swt.Network.Server.intf.IServer;
import de.hsrm.mi.swt.Network.Server.intf.IServerManager;

public class ActiveMQServerManager implements IServerManager<Message> {
	/** String if a queue queue is wanted */
	public static final String TYPE_QUEUE = "queue";
	/** String if a topic server is wanted */
	public static final String TYPE_TOPIC = "topic";
	/** dictonary of all Servers */
	private Map<String, IServer<Message>> allServers;
	/** instance of the current Session object */
	private Session mySession;
	/** message broker URL */
	private String messageBrokerUrl;
	/** instance of the MessageProducer */
	private MessageProducer messageProducer;
	private static int ackMode = Session.AUTO_ACKNOWLEDGE;

	/**
	 * Constructor for the ServerManager
	 * @param messageBrokerUrl
	 */
	public ActiveMQServerManager(String messageBrokerUrl) {
		allServers = new HashMap<String, IServer<Message>>();
		this.messageBrokerUrl = messageBrokerUrl;
		setup();
	}

	/**
	 * setup-method initialize all necessary Data and Objects
	 */
	private void setup() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				this.messageBrokerUrl);
		Connection con;
		try {
			con = connectionFactory.createConnection();
			mySession = con.createSession(false, ackMode);
			messageProducer = mySession.createProducer(null); // Create anonymous Producer... every Server handles the destination itself...
			con.start();
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	@Override
	public IServer<Message> createServer(String type, String serverName)
			throws UnknownServerException {
		IServer<javax.jms.Message> tempServer;
		if (type.equals(TYPE_QUEUE)) {
			tempServer = new ActiveMQRequestServer(serverName, mySession, messageProducer);
			allServers.put(serverName, tempServer);
			return tempServer;
		} else if (type.equals(TYPE_TOPIC)) {
			tempServer = new ActiveMQTopicServer(serverName, mySession, messageProducer);
			allServers.put(serverName, tempServer);
			return tempServer;
		}
		throw new UnknownServerException("Command: " + type + " is not valid!");
	}

	@Override
	public IServer<Message> getServer(String serverName)
			throws UnknownServerException {
		IServer<Message> ret = allServers.get(serverName);
		if (ret != null) {
			return ret;
		}
		throw new UnknownServerException("server was not found");
	}

	@Override
	public void removeServer(String serverName) throws UnknownServerException {
		if (allServers.remove(serverName) == null) {
			throw new UnknownServerException("server is not in serverpool");
		}
	}

	@Override
	public void send(String serverName, Message receiver, ICommand command)
			throws UnknownServerException {
		IServer<Message> akt = allServers.get(serverName);
		if (akt != null) {
			akt.send(receiver, command);
		} else {
			throw new UnknownServerException("Server ist not registered!");
		}

	}

	@Override
	public boolean hasServer(String serverName) {
		return allServers.containsKey(serverName);
	}

}
