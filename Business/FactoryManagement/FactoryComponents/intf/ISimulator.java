package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf;

public interface ISimulator extends Runnable {
	/**
	 * Starts the Simulator-Loop
	 * 
	 */
	public void start();
	
	/**
	 * Pauses the Simulator-Loop
	 * 
	 */
	public void pause();
	
	/**
	 * Stops the Simulator
	 * 
	 */
	public void stop();
	
}