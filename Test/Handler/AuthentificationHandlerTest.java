package de.hsrm.mi.swt.Test.Handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.UserManager;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.AuthentificationException;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LoginException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Dispatcher.Handler.AuthentificationHandler;
import de.hsrm.mi.swt.Dispatcher.Handler.FactoryHandler;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class AuthentificationHandlerTest {

	IHandler handler;
	@Mock
	IUserManager userManager;
	ICommand loginCommandTry;
	IController controller;
	IUser loginUser;
	@Mock
	FactoryHandler fh;

	@Before
	public void start() {
		controller = mock(IController.class);
		userManager = new UserManager();
		loginUser = new User("mario", "de2f15d014d40b93578d255e6221fd60", 0,
				null);
		loginCommandTry = new Command(CommandType.LOGIN, Targets.USER,
				loginUser);
		when(controller.getUserManager()).thenReturn(userManager);
		handler = new AuthentificationHandler();
		handler.setNext(new FactoryHandler());
	}

	@Test
	public void testSuccessfulLoginHandle() throws Exception {
		loginCommandTry.setSessionID(0);
		handler.handle(loginCommandTry, null, controller);
	}

	@Test(expected = LoginException.class)
	public void testFailedSessionIdHandle() throws Exception {
		loginCommandTry.setSessionID(123);
		handler.handle(loginCommandTry, null, controller);
	}

	@Test(expected = PersistenceException.class)
	public void testUnkownUsernameTryToLogin() throws Exception {
		IUser wrongUser = new User("unkownUser", "whatever", 0, null);
		loginCommandTry = new Command(CommandType.LOGIN, Targets.USER,
				wrongUser);
		loginCommandTry.setSessionID(0);
		handler.handle(loginCommandTry, null, controller);
	}

	@Test(expected = LoginException.class)
	public void testWrongPasswordTryToLogin() throws Exception {
		IUser wrongUser = new User("miri", "whatever", 0, null);
		loginCommandTry = new Command(CommandType.LOGIN, Targets.USER,
				wrongUser);
		loginCommandTry.setSessionID(0);
		handler.handle(loginCommandTry, null, controller);
	}

	@Test(expected = LoginException.class)
	public void wrongTargetTest() throws Exception {
		loginCommandTry = new Command(CommandType.CREATE_FACTORY, Targets.USER,
				loginUser);
		handler.handle(loginCommandTry, null, controller);
	}

	@Test(expected = AuthentificationException.class)
	public void testNotSuccessfulPassToNextHandler() throws Exception {
		// prepare Userdata
		IUser loggedInUser = new User("miri",
				"2de5ceb0d2e95c67f76ec47f1ebfdb51", 123456, null);
		ICommand notLogin = new Command(CommandType.CREATE_FACTORY,
				Targets.FACTORY, null);
		notLogin.setSessionID(123456);
		// handle some Command with unknown Session ID
		handler.handle(notLogin, loggedInUser, controller);
	}

	//TODO check method invocation
//	@Test
//	public void testSuccessfulPassToNextHandler() throws Exception {
//		// prepare Userdata
//		IUser user = new User("miri", "2de5ceb0d2e95c67f76ec47f1ebfdb51", 0,
//				null);
//		IUser loggedInUser = userManager.login(user);
//		Long sid = loggedInUser.getSessionId();
//		System.out.println(sid);
//		IFactory factory = new Factory("123", 5, 5, null, null);
//		ICommand notLogin = new Command(CommandType.CREATE_FACTORY,
//				Targets.FACTORY, factory);
//		notLogin.setSessionID(sid);
//
//		// handle some Command with Session ID
//		handler.handle(notLogin, loggedInUser, controller);
//	}

}