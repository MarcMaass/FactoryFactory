package de.hsrm.mi.swt.Network.Communication.enums;

/**
 * Enums for Network-Commands, this Enum keeps the commands
 * they are used to communicate between the clients and the server
 * It allows an easy way to expand the commands and define the commands easily
 * @author jalbe001, mwand001
 */
public enum CommandType{
	/**
	 * Login-Command. It includes all messages to authorize an user
	 */
	LOGIN,
	/**
	 * Logout-Command.
	 */
	LOGOUT,
	/**
	 * returns a list of factories to the sender
	 */
	LISTFAC,
	/**
	 * creates an object of the factory
	 */
	CREATE_FACTORY,
	/**
	 * loads a factory-object
	 */
	LOAD_FACTORY,
	/**
	 * creates an machine-object
	 */
	CREATE_MACHINE,
	/**
	 * affects machine placement
	 */
	MODIFY_MACHINE,
	/**
	 * removes a machine 
	 */
	DELETE_MACHINE,
	/**
	 * duplicates an object, e.g. machines
	 */
	DUPLICATE_MACHINE,
	/**
	 * Used, when binary-files should be send
	 */
	GET, 
	/**
	 * when some errors appear, this command should be used
	 */
	EXCEPTION,
	
	/**
	 * Undo Command
	 */
	UNDO,
	
	/**
	 * Redo a reversed Command
	 */
	REDO;
}
