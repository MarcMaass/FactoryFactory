package de.hsrm.mi.swt.Dispatcher.Handler;

import java.util.ArrayList;

import de.hsrm.mi.swt.Business.FactoryManagement.Exceptions.FactoryNotFoundException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * Handler für alle Maschinen-Operationen
 * 
 * @author martin
 *
 */
public class MachineHandler implements IHandler {
	private IHandler next;
	
	@Override
	public void handle(ICommand command, IUser user, IController controller) throws Exception {
		if (command.getTarget() == Targets.FACTORY) {
			IFactoryManager manager = controller.getFactoryManager();
			IMachineUnit machine = (MachineUnit)command.getData();
			
			switch (command.getAction()){
			case CREATE_MACHINE:
				if(user.getActiveFactoryID() != 0){
					machine = new MachineUnit(machine.getTypeID(), machine.getWidth(), machine.getHeight(), machine.getNodes(), new ArrayList<Integer>(), new ArrayList<Integer>());
					manager.placeMachineUnit(user, user.getActiveFactoryID(), machine, machine.getPosX(), machine.getPosY());
					// TODO: broadcast to topic?
					controller.respond(new Command(command.getAction(), command.getTarget(), machine));
				} else {
					throw new FactoryNotFoundException("no factory selected");
					//controller.respond(new Command(CommandType.EXCEPTION, command.getTarget(), "no factory selected", true));
				}
				break;
				
			case DELETE_MACHINE:
				if(user.getActiveFactoryID() != 0){
					manager.deleteMachineUnit(user, user.getActiveFactoryID(), machine.getID());
					// TODO: broadcast to topic?
					controller.respond(new Command(command.getAction(), command.getTarget(), machine));
				} else {
					throw new FactoryNotFoundException("no factory selected");
					//controller.respond(new Command(CommandType.EXCEPTION, command.getTarget(), "no factory selected", true));
				}
				break;
				
			case MODIFY_MACHINE:
				// TODO simple MODIFY actions aren't easily undoable (no reference value)
				break;
				
			case DUPLICATE_MACHINE:
				// TODO
				break;
				
			default:
				next.handle(command, user, controller);
			}
		} else {
			next.handle(command, user, controller);
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
