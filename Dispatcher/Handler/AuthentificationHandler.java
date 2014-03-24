package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LoginException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * The AuthentificationHandler is responsible for the login process if the given
 * user hasen't been logged in yet. Otherwise the belonging user object to the
 * session id will be fetched from the user manager and send to the next handler
 * 
 */
public class AuthentificationHandler implements IHandler {
	private IHandler next;

	@Override
	public void handle(ICommand command, IUser user, IController controller)
			throws Exception {
		if (command.getTarget() == Targets.USER && command.getAction() == CommandType.LOGIN && command.getSessionID() == 0) {
			// get Userdata from command
			IUser frontendUser = (User) command.getData();
			// login
			IUser backendUser = controller.getUserManager().login(frontendUser);
			// send respond
			controller.respond(new Command(command.getAction(), command.getTarget(), backendUser));
		} else {
			if (command.getAction() == CommandType.LOGIN && command.getSessionID() != 0) {
				throw new LoginException("Session ID bereits gesetzt, obwohl Login gefordert war");
			} else if (command.getAction() != CommandType.LOGIN && command.getSessionID() == 0) {
				throw new LoginException("Keine Session ID gesetzt");
			} else {
				// get User from Management by current sessionID
				IUser myUser = controller.getUserManager().getUser(command.getSessionID());
				next.handle(command, myUser, controller);
			}
		}

	}

	@Override
	public void setNext(IHandler handler) {
		next = handler;

	}

	@Override
	public IHandler getNext() {
		return next;
	}

}
