package de.hsrm.mi.swt.Network.Controller;

import java.io.File;

import javax.jms.Message;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryManager;
import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.UserManager;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Dispatcher.Dispatcher;
import de.hsrm.mi.swt.Dispatcher.intf.IDispatcher;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;
import de.hsrm.mi.swt.Network.Exceptions.UnknownServerException;
import de.hsrm.mi.swt.Network.Listener.ServerEvent;
import de.hsrm.mi.swt.Network.Listener.ServerListener;
import de.hsrm.mi.swt.Network.Server.ActiveMQBroker;
import de.hsrm.mi.swt.Network.Server.ActiveMQServerManager;
import de.hsrm.mi.swt.Network.Server.intf.IServer;
import de.hsrm.mi.swt.Network.Server.intf.IServerManager;
import de.hsrm.mi.swt.Test.DummyData.CreateDummyData;

/**
 * Controller Classes manages his Servers an Managers
 * @author Justin Albert, Mario Wandpflug
 * 
 */
public class Controller implements IController, ServerListener<Message> {

	/**
	 * Dispatcher for Messages
	 */
	private IDispatcher dispatcher;
	/**
	 * Instance of the serverManager
	 */
	private IServerManager serverManager;
	/**
	 * instance to persistance-Layer to handle User-Objects
	 */
	private IUserManager userManager;
	/**
	 * instance to persistance-Layer to handle things around the factory
	 */
	private IFactoryManager factoryManager;
	/**
	 * instance to fire Events to Listeners
	 */
	private ServerEvent<Message> source;
	/**
	 * Name of the chossen Server
	 */
	private String reqResServerName;

	/**
	 * Constructor for the Controller
	 * It's the MasterObject to startup the Program
	 * @param serverManager
	 * @throws UnknownServerException
	 */
	public Controller(IServerManager serverManager)
			throws UnknownServerException {
		reqResServerName = "FactoryQueue";
		dispatcher = new Dispatcher();
		userManager = new UserManager();
		factoryManager = new FactoryManager();
		this.serverManager = serverManager;
		IServer<Message> requestQueue = serverManager.createServer(ActiveMQServerManager.TYPE_QUEUE,
				reqResServerName);
		requestQueue.addServerListener(this);
	}

	/**
	 * Listener-method when server registered an Message
	 */
	@Override
	public void fireServerEvent(ServerEvent<Message> se) {
		ICommand command = se.getMyCommand();
		source = se;
		// dispatch
		dispatcher.dispatch(command, this);
	}

	/**
	 * Method to send an repsonse to a request
	 */
	public void respond(ICommand reply) {
		try {
			serverManager.send(reqResServerName, source.getRequester(), reply);
		} catch (UnknownServerException use) {
			use.printStackTrace();
		}
//		((IServer<Message>) source.getSource()).send(source.getRequester(),	reply);
	}
	
	/**
	 * Method to send to all Listeners in one Topic, 
	 * mostly used when a new Object is added to one Factory
	 * @param factoryID to identify the choosen Factory
	 * @param broadcastCommand Command-Object wich should send to all Listeners in that Topic
	 */
	public void broadcast(String factoryID, ICommand broadcastCommand) {
		try {
			if (serverManager.hasServer(factoryID))
				serverManager.createServer(ActiveMQServerManager.TYPE_TOPIC, factoryID);
			serverManager.send(factoryID, null, broadcastCommand);
		} catch (UnknownServerException use) {
			use.printStackTrace();
		}
	}

	/**
	 * Main-Procedure
	 * 
	 * @param args
	 * @throws UnknownServerException
	 */
	public static void main(String[] args) throws UnknownServerException {
		String brokerURL = "tcp://0.0.0.0:61616";
		String brokerName = "localhost";
		String embeddedBrokerURI = "vm://" + brokerName;
		createUserData();
		// 1. Step... ActiveMQBroker has to be started
		ActiveMQBroker broker = new ActiveMQBroker(brokerURL, brokerName);
		broker.start();
		IServerManager serverManager = new ActiveMQServerManager(embeddedBrokerURI);
		// 2. Step... Establish

		new Controller(serverManager);
		// 3. Step create User -> only for jar file...
		
		broker.getMyBroker().waitUntilStopped(); // has to be set, because the
													// main thread would kill
													// the broker!!!
		// TODO: Maybe it is better to write a REAL Server Controller...
	}
	
	/**
	 * Just for Testserver....
	 * TODO: Remove, when not needed anymore 
	 */
	private static void createUserData() {
		File f = new File("save");
		if (!f.exists()) {
			System.out.println("Creating user data... ");
			try {
				CreateDummyData.generateDummyUser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IUserManager getUserManager() {
		return userManager;
	}

	@Override
	public IFactoryManager getFactoryManager() {
		return factoryManager;
	}

}
