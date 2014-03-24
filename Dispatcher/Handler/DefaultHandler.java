package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;
import de.hsrm.mi.swt.Network.Exceptions.CommandNotFoundException;

/**
 * The DefaultHandler handles all Actions which haven't been handled yet.
 * 
 */
public class DefaultHandler implements IHandler {

	@Override
	public void handle(ICommand command, IUser user, IController controller) throws Exception {
		throw new CommandNotFoundException("unsupported command");
	}

	@Override
	public void setNext(IHandler handler) {
	}

	@Override
	public IHandler getNext() {
		return null;
	}

}
