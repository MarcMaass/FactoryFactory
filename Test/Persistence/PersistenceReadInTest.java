package de.hsrm.mi.swt.Test.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Persistence.ReadIn;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceReadInTest {


	ReadIn read;
	String saveDir;
	String saveEnd;
	String obj;
	String id;
	
	@Before
	public void start(){
		read = new ReadIn();
		saveDir = "save/";
		saveEnd = ".paul";
		obj = "paul";
		id = "paul";
	}
	
	@Test
	public void readLifecycle() {
		
		try {
			assertEquals(read.getList(saveDir, saveEnd).size(), 0);
			
			assertTrue(read.save(id, obj, saveDir, saveEnd));

			assertEquals(read.getList(saveDir, saveEnd).size(), 1);
			
			assertEquals(read.load(id, saveDir, saveEnd), obj);
			
			assertTrue(read.delete(id, saveDir, saveEnd));
			
			assertEquals(read.getList(saveDir, saveEnd).size(), 0);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void stop(){
		try {
			read.delete(id, saveDir, saveEnd);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
