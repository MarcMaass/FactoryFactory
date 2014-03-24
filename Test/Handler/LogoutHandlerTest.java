package de.hsrm.mi.swt.Test.Handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LogoutException;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.UnsavedUserOperationsException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Dispatcher.Handler.DefaultHandler;
import de.hsrm.mi.swt.Dispatcher.Handler.LogoutHandler;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

public class LogoutHandlerTest {

	private IHandler handler;
	private ICommand cmd;
	private IUser user;
	@Mock
	private IController controller;
	@Mock
	IUserManager userManager;

	@Before
	public void setup() {
		controller = mock(IController.class);
		userManager = mock(IUserManager.class);
		handler = new LogoutHandler();
		handler.setNext(new DefaultHandler());
		when(controller.getUserManager()).thenReturn(userManager);
		when(userManager.logout(123456)).thenReturn(true);

		user = new User("miri", "2de5ceb0d2e95c67f76ec47f1ebfdb51", 123456,
				null);
		cmd = new Command(CommandType.LOGOUT, Targets.USER, user);
	}

	@Test(expected = LogoutException.class)
	public void testLogoutFailedSID() throws Exception {
		handler.handle(cmd, user, controller);
	}

	@Test(expected = UnsavedUserOperationsException.class)
	public void testLogoutFailedUnsavedOps() throws Exception {
		user.addCommand(new Command(CommandType.CREATE_FACTORY,
				Targets.FACTORY, null));
		handler.handle(cmd, user, controller);
	}

	@Test
	public void testLogoutWithForceFlag() throws Exception {
		user.addCommand(new Command(CommandType.CREATE_FACTORY,
				Targets.FACTORY, null));
		user.setForceFlag(true);
		cmd.setSessionID(123456);
		handler.handle(cmd, user, controller);
	}

	@Test
	public void testLogout() throws Exception {
		cmd.setSessionID(123456);
		handler.handle(cmd, user, controller);
	}

}
