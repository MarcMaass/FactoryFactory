package de.hsrm.mi.swt.Network.Server.intf;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Exceptions.UnknownServerException;

/**
 * Interface for a ServerManager. Manages all servers with Type T. The type is
 * required to define some receiver, e.g. a String or a Destination (see Apache
 * ActiveMQ). The 
 * 
 * @author Justin Albert & Mario Wandpflug
 * 
 */
public interface IServerManager<T> {

	/**
	 * Creates a Server with a certain type
	 * 
	 * @param type
	 *            e.g. HTTPServer or something else
	 * @param serverName
	 *            name of the Server
	 * @return server, so that you can use him directly (for listeners)
	 * @throws UnknownServerException
	 *             if the type is unknown
	 */
	IServer<T> createServer(String type, String serverName)
			throws UnknownServerException;

	/**
	 * Returns a Server with the special server name. If the server is not
	 * kept by the manager an UnknownServerException is thrown.
	 * @param serverName 
	 * @return server 
	 * @throws UnknownServerException
	 */
	IServer<T> getServer(String serverName) throws UnknownServerException;

	/**
	 * Checks if the ServerManager keeps a server
	 * @param serverName
	 * @return true if the server is available
	 */
	boolean hasServer(String serverName);

	void removeServer(String serverName) throws UnknownServerException;

	void send(String serverName, T receiver, ICommand command)
			throws UnknownServerException;

}