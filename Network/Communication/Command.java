package de.hsrm.mi.swt.Network.Communication;

import de.hsrm.mi.swt.Network.Communication.enums.*;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;

/**
 * Command-Class
 * This class creates the commands to send to the clint-side
 * 
 * @author mwand001, jalbe001
 */
public class Command implements ICommand{
	/**
	 * Every Command got an CommandType action to know what should be done
	 */
	private CommandType action;
	/**
	 * Every Command got an target to know what object the command is about
	 */
	private Targets target;
	/**
	 * Commands got an data property to send data to the clint-side, e.g. ExceptionStrings, JSONStrings...
	 */
	private Object data;
	/**
	 * for Commands who got binaryData to send. e.g. Textures, ...
	 */
	private byte[] binaryData;
	/**
	 * ErrorString. For commands who sould send an Error-Report
	 */
	private String errorMessage;
	/**
	 * Every User got an Session_ID to identify them
	 */
	private long session_id;
	
	/**
	 * Constructor for Command with an Object as Data
	 * @param action what should be done?
	 * @param target on what object should be operated?
	 * @param data what data should be given to the client?
	 */
	public Command(CommandType action, Targets target, Object data) {
		this.action = action;
		this.target = target;
		this.data = data;
	}

	/**
	 * Constructor for Command with binary Data
	 * @param action what should be done?
	 * @param target on what object should be operated?
	 * @param binaryData the data to send the informations
	 */
	public Command(CommandType action, Targets target, byte[] binaryData) {
		this.action = action;
		this.target = target;
		this.binaryData = binaryData;
	}

	/**
	 * Constructor for Exception-Commands
	 * @param action is this case action should be exception
	 * @param target target should be exception too
	 * @param errorMessage the ExceptionString of the occured Exception
	 * @param isException flag if really Exception
	 */
	public Command(CommandType action, Targets target, String errorMessage, boolean isException) {
		this.action = action;
		this.errorMessage = errorMessage;
		this.target = target;
	}

	/**
	 * getter for action-property in command
	 * @return enum-Value for action
	 */
	public CommandType getAction() {
		return action;
	}

	/**
	 * getter for targetvalue
	 * @return enum-literal for target
	 */
	public Targets getTarget() {
		return target;
	}

	/**
	 * getter for data in command-property data
	 * @return object don't know what object is kept to runtime
	 */
	public Object getData() {
		return data;
	}

	/**
	 * getter for binaryData
	 * @return byte[] for bytestream
	 */
	public byte[] getBinaryData() {
		return binaryData;
	}

	/**
	 * getter for errorMessage if Exception occured
	 * @return String the Exception-String
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * getter for SessionID to identify Users
	 * @return SessionID as long
	 */
	@Override
	public long getSessionID() {
		return session_id;
	}
	
	/**
	 * setter for current SessionID
	 */
	public void setSessionID(long sessionID) {
		session_id = sessionID;
	}

}
