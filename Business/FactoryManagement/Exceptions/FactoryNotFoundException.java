package de.hsrm.mi.swt.Business.FactoryManagement.Exceptions;

public class FactoryNotFoundException extends Exception {
	
	public FactoryNotFoundException(){
		super();
	}
	
	public FactoryNotFoundException(String msg){
		super(msg);
	}
}
