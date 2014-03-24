package de.hsrm.mi.swt.Network.Communication.intf;

import de.hsrm.mi.swt.Network.Communication.enums.*;


/**
 * ICommand.java
 * This Interface specifies the methods of a Command-Object
 * @author jalbe001, mwand001
 * @since 08-12-2012
 */
public interface ICommand {
	
	/**
	 * getter for the ACTION-Property
	 * @return the ACTION-value from a command-object
	 */
	public CommandType getAction();
	
	/**
	 * getter for the Target-Property
	 * @return Target-value from a command-object
	 */
	public Targets getTarget();
	
	/**
	 * getter for the Data. 
	 * Type Object because it can take different values (JSON, String, ...)
	 * @return data as Type Object from command-object
	 */
	public Object getData();

	/**
	 * getter for BinaryData.
	 * returns if the object keeps binaryData, e.g. a picture, texture, ...
	 * @return binary Data from command-object
	 */
	public byte[] getBinaryData() ;

	/**
	 * 	getter for Error Message
	 * @return String of the Error Message
	 */
	public String getErrorMessage();
	
	/**
	 * getter for the SessionID
	 * @return SessionId as long
	 */
	public long getSessionID();
	
	/**
	 * setter for sessionID
	 */
	public void setSessionID(long sessionID);
}
