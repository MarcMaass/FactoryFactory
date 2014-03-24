package de.hsrm.mi.swt.Network.Exceptions;

/**
 * This Exception should be raised when a Mesasge should be send to an unknown Server
 * 
 * @author Justin Albert
 *
 */
public class UnknownServerException extends Exception{

	/**
	 * Standard-Constructor
	 */
	public UnknownServerException(){
		super();
	}
	
	/**
	 * Constructor with parameter s as exactly Exception-String
	 * @param s contains the message of the exception
	 */
	public UnknownServerException(String s){
		super(s);
	}
	
}
