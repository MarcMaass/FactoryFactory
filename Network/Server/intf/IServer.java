package de.hsrm.mi.swt.Network.Server.intf;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Listener.ServerListener;

/**
 * Server Interface
 * @author mwand001
 *
 */
public interface IServer<T> {
	
	public void send(T receiver, ICommand command);
	public void addServerListener(ServerListener sl);
	public void removeServerListener(ServerListener sl);
}
