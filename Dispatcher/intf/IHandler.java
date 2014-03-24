package de.hsrm.mi.swt.Dispatcher.intf;

import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * Interface for Chain of Reponsibility (CommandHandler)
 * 
 */
public interface IHandler {

	/**
	 * Handles an ICommand
	 * 
	 * @param command
	 * @param user
	 * @param controller
	 * @throws Exception
	 */
	public void handle(ICommand command, IUser user, IController controller)
			throws Exception;

	/**
	 * Setter
	 * @param handler
	 */
	public void setNext(IHandler handler);

	/**
	 * Getter for next-handler
	 * @return
	 */
	public IHandler getNext();

}
