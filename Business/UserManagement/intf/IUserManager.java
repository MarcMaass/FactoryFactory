package de.hsrm.mi.swt.Business.UserManagement.intf;

import de.hsrm.mi.swt.Business.UserManagement.Exceptions.AuthentificationException;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LoginException;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;


public interface IUserManager {

	public IUser getUser(long sessionID) throws AuthentificationException;
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param group
	 * @return
	 */
	public IUser createUser(IUser user);
	
	/**
	 * 
	 * 
	 * @return true if user is found and password correct
	 */
	public IUser login(IUser user) throws LoginException, PersistenceException;
	
	/**
	 * 
	 * @param sessionID
	 * @return
	 */
	public IUser edit(long sessionID);
	
	/**
	 * 
	 * @param sessionID
	 * @param newPermission
	 * @return
	 */
	public IUser changeGroup(long sessionID, String newGroup);
	
	/**
	 * 
	 * 
	 * @param sessionID
	 */
	public boolean logout(long sessionID);
	
	/**
	 * 
	 * @param SessionID
	 * @return
	 */
	public boolean deleteUser(IUser user);
	
}
