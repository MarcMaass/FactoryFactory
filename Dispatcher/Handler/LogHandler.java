package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * A Handler to log all Commands
 * 
 * @author martin
 *
 */
public class LogHandler implements IHandler {

	private IHandler next;
	
	@Override
	public void handle(ICommand command, IUser user, IController controller) throws Exception {
		// TODO: proper logging (file?)
		System.out.println("received Command: "+command.getTarget()+" "+command.getAction());
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
