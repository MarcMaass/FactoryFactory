package de.hsrm.mi.swt.Business.UserManagement;

import java.util.HashMap;
import java.util.Map;

import de.hsrm.mi.swt.Business.UserManagement.Exceptions.AuthentificationException;
import de.hsrm.mi.swt.Business.UserManagement.Exceptions.LoginException;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUserManager;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;
import de.hsrm.mi.swt.Persistence.intf.IPersistence;

public class UserManager implements IUserManager {
	Map<Long, IUser> userpool = new HashMap<Long, IUser>();
	IPersistence persistence = new Persistence();

	@Override
	public IUser getUser(long sessionId) throws AuthentificationException {
		if (userpool.containsKey(sessionId)) {
			return userpool.get(sessionId);
		}
		throw new AuthentificationException("Session ID nicht vorhanden");
	}

	@Override
	public IUser createUser(IUser user) {
		userpool.put(user.getSessionId(), user);
		try {
			persistence.saveUser(user);
		} catch (PersistenceException e) {
			return null;
		}
		return user;
	}

	@Override
	public IUser login(IUser user) throws LoginException, PersistenceException {
		long sessionIdForUser;
		IUser orig = persistence.loadUser(user.getName());
		if (user.getPassword().equals(orig.getPassword())) {
			sessionIdForUser = user.getName().hashCode() + System.currentTimeMillis();
			orig.setSessionId(sessionIdForUser);
			userpool.put(sessionIdForUser, orig);
			return orig;
		}
		throw new LoginException("Benutzername oder Passwort falsch");
	}

	@Override
	public IUser edit(long sessionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser changeGroup(long sessionID, String newGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout(long sessionID) {
		if (userpool.remove(sessionID) != null)
			return true;
		return false;
	}

	@Override
	public boolean deleteUser(IUser user) {
		// TODO Auto-generated method stub
		return false;
	}

}
