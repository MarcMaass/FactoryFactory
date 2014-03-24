package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LogoutException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * The LogoutHandler manages all operations on the assigned user to log him out.
 * Exceptions will be thrown if there are unexpected unsaved operations made by
 * the user. User data will be reset after the logout.
 * 
 */
public class LogoutHandler implements IHandler {
	private IHandler next;

	@Override
	public void handle(ICommand command, IUser user, IController controller) throws Exception {
		// check if undo/redo list is empty or list isn't empty but the force
		// flag is set
		if (command.getTarget().equals(Targets.USER) && command.getAction().equals(CommandType.LOGOUT)) {
			//if (!user.hasUnsavedOperations() || (user.hasUnsavedOperations() && user.forceFlagIsSet())) {
				// logout from user management
				boolean isLoggedOut = controller.getUserManager().logout(command.getSessionID());
				// reset User
				if (isLoggedOut) {
					user.setForceFlag(false);
					user.clearOperations();
					user.setSessionId(0);
					// send ICommand back with User Data and sessionID = 0
					controller.respond(new Command(CommandType.LOGOUT, Targets.USER, user));
				} else {
					throw new LogoutException("Logout impossible, session id unknown");
				}
			//} else {
			//	throw new UnsavedUserOperationsException("Unsaved User Operations");
			//}
		} else {
			next.handle(command, user, controller);
		}

	}

	@Override
	public void setNext(IHandler handler) {
		this.next = handler;
	}

	@Override
	public IHandler getNext() {
		return next;
	}

}
