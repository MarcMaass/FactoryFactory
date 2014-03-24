package de.hsrm.mi.swt.Business.FactoryManagement.Exceptions;

public class NameInUseException extends Exception {
	
	public NameInUseException(){
		super();
	}
	
	public NameInUseException(String msg){
		super(msg);
	}
}
