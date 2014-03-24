package de.hsrm.mi.swt.Network.Server;

import java.net.URISyntaxException;

import org.apache.activemq.broker.BrokerService;

/**
 * BrokerClass
 * @author mario, justin
 * 
 */
public class ActiveMQBroker {
	/** the URL of the Broker */
	private String brokerUrl;
	/** how the broker should be called */
	private String brokerName;
	/** instance of the activemq broker */
	private BrokerService myBroker;

	/**
	 * Constructor for the Broker
	 * @param brokerURL 
	 * @param brokerName 
	 */
	public ActiveMQBroker(String brokerURL, String brokerName) {
		this.brokerName = brokerName;
		this.brokerUrl = brokerURL;
		setup();
	}

	/**
	 * this is the setup method for the broker
	 * this method initialize the broker with the brokerUrl and sets the brokername
	 */
	private void setup() {
		System.out.println("... Setting Broker Properties: " + brokerName + " "
				+ brokerUrl);
		try {
			myBroker = new BrokerService();
			myBroker.addConnector(this.brokerUrl);
			// Setting Name of Broker for virtual machine access vm://brokerName
			myBroker.setBrokerName(brokerName);
			// Setting Plugins
			myBroker.setPersistent(false);
			myBroker.setUseJmx(false);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * starts the broker
	 */
	public void start() {
		try {
			if (!myBroker.isStarted())
				myBroker.start();
				System.out.println("Broker has been started...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * stops the broker
	 */
	public void stop() {
		try {
			if (myBroker.isStarted())
				myBroker.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * getter for the BrokerName
	 *	
	 * @return String name of the Broker
	 */
	public String getBrokerName() {
		return brokerName;
	}

	/**
	 * getter for the BrokerURL
	 * @return String MessageBrokerURL
	 */
	public String getMessageBrokerUrl() {
		return brokerUrl;
	}
	
	/**
	 * getter for MyBroker
	 * @return BrokerService instance of myBroker
	 */
	public BrokerService getMyBroker() {
		return myBroker;
	}
}
