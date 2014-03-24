package de.hsrm.mi.swt.Business.UserManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;

public class User implements IUser {

	protected static final long serialVersionUID = 1L;
	
	@Expose
	protected String name;
	@Expose
	protected long sessionId;
	@Expose
	protected String password;

	
	private boolean forceFlag;

	protected Set<String> groups;

	protected long activeFactoryID; //currently edited factory

	private List<ICommand> commandList;
	
	private int currentCommand;

	public User() {
		forceFlag = false;
		commandList = new ArrayList<ICommand>();
	}

	public User(String username, String password, long sessionId, Set<String> groups) {
		forceFlag = false;
		commandList = new ArrayList<ICommand>();
		
		this.sessionId = sessionId;

		this.name = username;
		this.password = password;
		this.groups = groups;
	}

	@Override
	public Long getSessionId() {
		return sessionId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setSessionId(long id) {
		sessionId = id;
	}

	public void addCommand(ICommand c) {
		if(commandList == null){
			commandList = new ArrayList<ICommand>();
		}
		// clear top:
		for (int i = currentCommand + 1; i < commandList.size(); ++i) {
			commandList.remove(i);
		}
		
		commandList.add(c);
		currentCommand += 1;
	}

	public ICommand redo() {
		if (commandList.size() == 0) {
			return null;
		}
		return commandList.get(currentCommand);
	}

	public ICommand undo() {
		if (commandList.size() == 0) {
			return null;
		}
		currentCommand -= 1;
		return commandList.get(currentCommand + 1);
	}

	@Override
	public Set<String> getGroups() {
		return groups;
	}

	@Override
	public void addToGroup(String group) {
		groups.add(group);
	}

	@Override
	public void removeGroup(String group) {
		groups.remove(group);

	}

	@Override
	public int hashCode() {
		return name.hashCode() + password.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() == obj.getClass()) {
			User o = (User) obj;
			if (o.hashCode() == obj.hashCode())
				return true;
		}

		return false;
	}

	@Override
	public boolean hasUnsavedOperations() {
		return !commandList.isEmpty();
	}

	@Override
	public boolean forceFlagIsSet() {
		return forceFlag == true;
	}

	@Override
	public void setForceFlag(boolean forced) {
		forceFlag = forced;
	}

	@Override
	public void clearOperations() {
		if(commandList != null){
			commandList.clear();
		}
	}

	@Override
	public void setActiveFactoryID(long id) {
		activeFactoryID = id;
	}

	@Override
	public long getActiveFactoryID() {
		return activeFactoryID;
	}

	@Override
	public boolean isUndoPossible() {
		return currentCommand > 0;
	}

	@Override
	public boolean isRedoPossible() {
		return currentCommand<commandList.size()-1;
	}
}
