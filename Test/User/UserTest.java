package de.hsrm.mi.swt.Test.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

	User usr;
	String name;
	String password;
	MessageDigest m;
	Persistence p;
	Set<String> groups;

	@Before
	public void start() throws Exception {
		p = new Persistence();

		groups = new HashSet<String>();
		groups.add("users");
		name = "user";
		password = "5F4DCC3B5AA765D61D8327DEB882CF99";

		usr = new User(name, password, 0, groups);
		System.out.println("user" + usr);

	}

	@After
	public void stop() {

	}

}
