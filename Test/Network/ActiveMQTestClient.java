package de.hsrm.mi.swt.Test.Network;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.event.EventListenerList;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import com.google.gson.JsonParseException;

import de.hsrm.mi.swt.Network.Communication.enums.Keys;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Exceptions.CommandNotFoundException;
import de.hsrm.mi.swt.Network.Exceptions.DataNotFoundException;

public class ActiveMQTestClient implements MessageListener {
	private EventListenerList myServerListeners;
	private String requestServerName;
	private String brokerURL;
	private int ackMode = Session.AUTO_ACKNOWLEDGE;

	private Session mySession;
	private Destination ownDest;
	private MessageProducer requestProducer;
	private MessageConsumer responseConsumer;

	@SuppressWarnings("unused")
	private ActiveMQSession session;

	public ActiveMQTestClient(String requestServerName,
			String brokerURL) {
		myServerListeners = new EventListenerList();
		this.requestServerName = requestServerName;
		this.brokerURL = brokerURL;
		setup();
	}

	private void setup() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerURL);
		Connection con;
		Queue factoryQueue;
		try {
			connectionFactory.setBrokerURL(this.brokerURL);
			con = connectionFactory.createConnection();
			
			System.out.println("Test Client started..." + " " + connectionFactory.getBrokerURL());
			mySession = con.createSession(false, ackMode);
			factoryQueue = mySession.createQueue(requestServerName);
			requestProducer = mySession.createProducer(factoryQueue);
			requestProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			ownDest = mySession.createTemporaryQueue();
			responseConsumer = mySession.createConsumer(ownDest);
			responseConsumer.setMessageListener(this);
			con.start();
			System.out.println("Test client started!");
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
				System.out.println("Not defined message!");
			}
			/*
			 * response.setJMSCorrelationID(msg.getJMSCorrelationID());
			 * response.setJMSReplyTo(msg.getJMSReplyTo());
			 * System.out.println("-- Sende Nachricht!");
			 * this.replyProducer.send(response);
			 */
		} catch (JMSException jmse) {
			jmse.printStackTrace();
			// TODO Log Msg should be created here
			// TODO Build Message for Client
		}
	}

	/**
	 * MapMessage-Handler Method
	 * 
	 * @param mapMsg
	 * @throws JMSException
	 * @throws CommandNotFoundException
	 * @throws DataNotFoundException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void handleMapMessage(MapMessage mapMsg) throws JMSException {
		return ;
	}

	/**
	 * BytesMessage Handler-Method
	 * 
	 * @param bytesMsg
	 * @throws JMSException
	 */
	private void handleBytesMessage(BytesMessage bytesMsg) throws JMSException {
		byte[] rawData;
		System.out.println(bytesMsg.getBodyLength());
		rawData = new byte[(int)bytesMsg.getBodyLength()];
		bytesMsg.readBytes(rawData);
		for (int i = 0 ; i < rawData.length ; i++) {
			System.out.print((char)rawData[i]);
		}
		System.out.println();
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

	public void send(ICommand command) {
		try {
				MapMessage request = this.mySession.createMapMessage();
				request.setStringProperty(Keys.ACTION.name(), command
						.getAction().name());
				request.setStringProperty(Keys.TARGET.name(), command.getTarget().name());
				request.setStringProperty(Keys.DATA.name(), command.getData().toString());
				request.setJMSReplyTo(ownDest);
				// response.setJMSCorrelationID(origMsg.getJMSCorrelationID());
				this.requestProducer.send(request);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
