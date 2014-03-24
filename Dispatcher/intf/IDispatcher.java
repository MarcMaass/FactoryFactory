package de.hsrm.mi.swt.Dispatcher.intf;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

public interface IDispatcher {
	
	/**
	 * Dispatches the command object to different managers, sorted by targets.
	 * Permissions will be checked.
	 * @param c
	 */
	public void dispatch(ICommand c, IController controller);
	
}
