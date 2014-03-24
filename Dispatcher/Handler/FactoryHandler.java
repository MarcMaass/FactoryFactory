package de.hsrm.mi.swt.Dispatcher.Handler;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.FactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.Targets;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * Handler für alle Factory-Operationen
 * 
 * @author martin
 *
 */
public class FactoryHandler implements IHandler {
	private IHandler next;
	
	@Override
	public void handle(ICommand command, IUser user, IController controller) throws Exception {
		if (command.getTarget() == Targets.FACTORY) {
			IFactoryManager manager = controller.getFactoryManager();
			FactoryHeader header;
			IFactory factory;
			
			switch (command.getAction()){
			case CREATE_FACTORY:
				header = (FactoryHeader)command.getData();
				factory = manager.createFactory(user, header.getName(), header.getSizeX(), header.getSizeY(), header.getReadGroup(), header.getWriteGroup());
				user.setActiveFactoryID(factory.getId());
				controller.respond(new Command(command.getAction(), command.getTarget(), manager.loadFactory(user, factory.getId())));
				break;
			
			case LOAD_FACTORY:
				header = (FactoryHeader)command.getData();
				factory = manager.loadFactory(user, header.getID()); // fetch requested factory
				user.setActiveFactoryID(header.getID());
				controller.respond(new Command(command.getAction(), command.getTarget(), factory));
				break;
				
			case LISTFAC:
				controller.respond(new Command(command.getAction(), command.getTarget(), manager.getFactoryList(user)));
				break;
				
			default:
				next.handle(command, user, controller);
			}
		} else {
			next.handle(command, user, controller);
		}
	}

	public void setNext(IHandler handler) {
		next = handler;
	}
	
	@Override
	public IHandler getNext() {
		return next;
	}

}
