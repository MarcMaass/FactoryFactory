package de.hsrm.mi.swt.Test.Persistence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceUserTest {

	IUser usr;
	String usrID;
	
	IUser usr2;
	String usrID2;

	Persistence pers;

	@Before
	public void start(){
		pers = new Persistence();

		usrID = "user1";
		usrID2 = "user2";
		
		Set<String> groups = new HashSet<String>();
		groups.add("users");
		
		usr = new User(usrID, "123", 12, groups);
		usr2 = new User(usrID2, "123", 12, groups);
		

	}
	
	@Test
	public void saveLoadUser() {
		try {
			pers.saveUser(usr);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User usr2 = null;
		try {
			usr2 = (User)pers.loadUser(usrID);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotSame(usr, usr2);
	}
	
	
	@Test
	public void getUserList() {
		
		try {
			pers.saveUser(usr);
			pers.saveUser(usr2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		List<String> usrList = null;
		try {
			usrList = pers.getUsers();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		for (String s : usrList){
		System.out.println(s);
		}
		assertTrue(usrList.contains(usrID));
		assertTrue(usrList.contains(usrID2));
		
	}
	
	@Test
	public void deleteUser() {
		
		// save User and compare ID in FactopryList
		try {
			pers.saveUser(usr);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> usrList = null;
		try {
			usrList = pers.getUsers();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(usrList.contains(usrID));
		
		// delete User now, ID shouldn't be in UserList
		try {
			pers.deleteUser(usrID);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			usrList = pers.getUsers();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(usrList.contains(usrID));
		
	}

	@After
	public void stop(){
		try {
			pers.deleteUser(usrID);
			pers.deleteUser(usrID2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
