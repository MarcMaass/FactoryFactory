package de.hsrm.mi.swt.Test.Handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.UserManager;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LoginException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Dispatcher.Dispatcher;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;
import de.hsrm.mi.swt.Network.Exceptions.CommandNotFoundException;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

public class DefaultHandlerAndConnectionTest {

	IController controller;
	Dispatcher d;
	IUser user;
	ICommand command;
	IUserManager um;

	@Before
	public void setup() {
		controller = mock(IController.class);
		d = new Dispatcher();
		um = new UserManager();
		user = new User("miri", "2de5ceb0d2e95c67f76ec47f1ebfdb51", 0, null);
		command = new Command(CommandType.EXCEPTION, Targets.EXCEPTION, user);
		try {
			user = um.login(user);
			command.setSessionID(user.getSessionId());
		} catch (LoginException e) {
			System.out.println("Login nicht möglich");
		} catch (PersistenceException e) {
			System.out.println("Persistence Fehler");
		}
		when(controller.getUserManager()).thenReturn(um);
	}

	@Test
	public void testWholeChain() {
		d.dispatch(command, controller);
	}

	@Test(expected = CommandNotFoundException.class)
	public void testExceptionThrowing() throws Exception {
		d.getHandler().handle(command, user, controller);
	}

}
