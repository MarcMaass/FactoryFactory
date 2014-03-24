package de.hsrm.mi.swt.Test.Network;

import org.apache.activemq.command.Message;
import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Listener.ServerEvent;
import de.hsrm.mi.swt.Network.Listener.ServerListener;


public class NetworkTest {
	ActiveMQTestClient myClient;
	
	@Before
	public void init() {		
		myClient = new ActiveMQTestClient("allRoundQueue", "tcp://localhost:61616");
		
	}
	
	@Test
	public void getBinaryString() {
		myClient.send(new Command(CommandType.GET, Targets.FACTORY, "hello to server"));
	}
	
//	@Test
//	public void getLogin() {
//		myClient.send(new Command(CommandType.LOGIN, Targets.USER, "{ \"session\": }"))
//	}
}
