package de.hsrm.mi.swt.Network.Communication.enums;

/**
 * Every Command-Object consists of Properties to specify itself...
 * The listed Strings in this file are keys and each key shows to a value.
 * @author jalbe001, mwand001
 */
public enum Keys{
	/**
	 * The key ACTION says what the command is up to do 'Login', 'Get', ...
	 */
	ACTION, 
	/**
	 * The key TARGET specify on which object the command will be execute...
	 */
	TARGET, 
	/**
	 * The key DATA shows on the value, which includes the relevant files. 
	 * The files can be JSON-Strings, String, byte-Arrays, ...
	 */
	DATA,
	/**
	 * SessionID, for the 
	 */
	SID,
	/**
	 * CommandID, send back to request with command
	 */
	CID,
	/**
	 * FactoryID, each factory got an Id to identify and choose the right one
	 */
	FID,
	/**
	 * Type of binary data
	 */
	TYPE;
}