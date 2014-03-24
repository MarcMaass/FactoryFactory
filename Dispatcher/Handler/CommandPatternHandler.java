package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * Handler um die Command-Pattern Operationen UNDO/REDO zu unterstützen
 * 
 * @author martin
 * 
 */
public class CommandPatternHandler implements IHandler {

	private IHandler next;

	@Override
	public void handle(ICommand command, IUser user, IController controller)
			throws Exception {

		if (command.getTarget() == Targets.USER) {
			IUserManager manager = controller.getUserManager();
			IUser u = manager.getUser(user.getSessionId());
			ICommand c2;

			switch (command.getAction()) {
			case UNDO:
				c2 = u.undo(); // get command to undo
				if (c2 == null) {
					break;
				}

				switch (c2.getAction()) { // determine countermeasure
				case CREATE_MACHINE:
					command = new Command(CommandType.DELETE_MACHINE, c2.getTarget(), c2.getData());
					break;

				case DELETE_MACHINE:
					command = new Command(CommandType.CREATE_MACHINE, c2.getTarget(), c2.getData());
					break;

				default:
					break;
				}
				// feedback to client whether further undo/redo is possible:
				controller.respond(new Command(CommandType.UNDO, Targets.USER, new Boolean(user.isUndoPossible())));
				break;

			case REDO:
				c2 = u.redo();
				if (c2 != null) {
					command = c2;
				}
				// feedback to client whether further undo/redo is possible:
				controller.respond(new Command(CommandType.UNDO, Targets.USER, new Boolean(user.isRedoPossible())));
				break;

			default:
				break;
			}
		} else if (command.getTarget().equals(Targets.FACTORY) || command.getTarget().equals(Targets.MACHINE)) {
			// insert command into user commandstack:
			controller.getUserManager().getUser(command.getSessionID()).addCommand(command);
		}
		next.handle(command, user, controller);

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
