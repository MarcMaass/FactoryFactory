package de.hsrm.mi.swt.Network.Controller.intf;

import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;

public interface IController {

	/**
	 * Getter for UserManager
	 * 
	 * @return
	 */
	public IUserManager getUserManager();

	/**
	 * Getter for FactoryManager
	 * 
	 * @return
	 */
	public IFactoryManager getFactoryManager();

	/**
	 * sends a command back to source
	 * 
	 * @param command
	 */
	public void respond(ICommand command);

}
