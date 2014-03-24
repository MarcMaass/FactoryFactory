package de.hsrm.mi.swt.Business.UserManagement.intf;

import java.io.Serializable;
import java.util.Set;

import de.hsrm.mi.swt.Network.Communication.intf.ICommand;

public interface IUser extends Serializable {

	/**
	 * Getter for the session id
	 * 
	 * @return
	 */
	public Long getSessionId();

	/**
	 * Setter for the session id
	 * 
	 * @param id
	 */
	public void setSessionId(long id);

	/**
	 * Getter for the users name
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Getter for the users password
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * Getter for all groups the user participate
	 * 
	 * @return
	 */
	public Set<String> getGroups();

	/**
	 * Adds the user to the assigned group
	 * 
	 * @param group
	 */
	public void addToGroup(String group);

	/**
	 * Removes the user as a memeber from the assigned group
	 * 
	 * @param group
	 */
	public void removeGroup(String group);

	/**
	 * Setter for the force flag
	 * 
	 * @param forced
	 */
	public void setForceFlag(boolean forced);

	/**
	 * Checks whether the user's undo-redo-list contains any operations which
	 * haven't been saved yet
	 * 
	 * @return
	 */
	public boolean hasUnsavedOperations();

	/**
	 * Checks whether the force flag is set
	 * 
	 * @return
	 */
	public boolean forceFlagIsSet();

	/**
	 * Removes all operations from undo-redo-list
	 */
	public void clearOperations();

	/**
	 * adds a Command to the undo/redo tack
	 * @param c
	 */
	public void addCommand(ICommand c);

	/**
	 * @return whether there are any commands to undo
	 */
	public boolean isUndoPossible();
	
	/**
	 * @return whether there are any commands to redo
	 */
	public boolean isRedoPossible();
	
	
	public ICommand undo();

	public ICommand redo();
	
	/**
	 * Sets the factory the User is currently editing
	 * @param id
	 */
	public void setActiveFactoryID(long id);
	
	/**
	 * Gets the factory the User is currently editing
	 * @param id
	 */
	public long getActiveFactoryID();
}
