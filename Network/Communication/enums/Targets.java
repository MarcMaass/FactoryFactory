package de.hsrm.mi.swt.Network.Communication.enums;

/**
 * The listed Strings in this file specify, wich object handles the Command
 * @author jalbe001
 */
public enum Targets {
	/**
	 * If the Command is addicted to an User-Object...
	 */
	USER, 
	/**
	 * If the Command is provided for a Factory...
	 */
	FACTORY, 
	/**
	 * Targets for Exception-Management
	 */
	EXCEPTION,
	/**
	 * If the Command is provided for a MachineHandler
	 */
	MACHINE,
}
