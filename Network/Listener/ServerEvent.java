package de.hsrm.mi.swt.Network.Listener;

import java.util.EventObject;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;


/**
 * ServerEvent Class is used to fire ServerEvent-Objects to the Listeners.
 * This class contains a Command-Object which is used for the dispatching
 * of the request.
 * The requester need not to be set. It depends if a server got a request...
 * 
 * @author Mario Wandpflug
 *
 * @param <T> used for the requesting address, i.e. it could be a String,
 * or an Object used for your server
 */
public class ServerEvent<T> extends EventObject {

	/**
	 * SerializationID
	 */
	private static final long serialVersionUID = 4978034693316881278L;

	private ICommand myCommand; // Command 
	private T requester; // 

	/**
	 * Constructor of a ServerEvent with source (Class which fired the event)
	 * a Command-Object and the requester.
	 * @param source
	 * @param myCommand
	 * @param requester
	 */
	public ServerEvent(Object source, ICommand myCommand, T requester) {
		super(source);
		this.myCommand = myCommand;
		this.requester = requester;
	}

	/**
	 * Getter for the Command
	 * @return
	 */
	public ICommand getMyCommand() {
		return myCommand;
	}
	
	/**
	 * 
	 * @return
	 */
	public T getRequester() {
		return requester;
	}
}
