package de.hsrm.mi.swt.Test.Persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceFactoryTest {

	Factory fac;
	long facID;

	Factory fac2;
	long facID2;

	Persistence pers;

	@Before
	public void start() {
		pers = new Persistence();

		facID = 1;
		facID2 = 2;

		fac = new Factory(facID, "test", 100, 100, "users", "users");
		fac2 = new Factory(facID2,"test", 200, 200, "users", "users");

	}

	public boolean containsID(List<IFactoryHeader> facList, long id){
		
		for (IFactoryHeader fh: facList){
			if (fh.getID() == id){
				return true;
			}
		}
		
		return false;
	}
	
	@Test
	public void saveLoadFactory() {
		try {
			assertTrue(pers.saveFactory(fac));

			Factory fac2 = (Factory) pers.loadFactory("test");

			assertNotSame(fac, fac2);
			assertEquals(fac.getId(), fac2.getId());
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getFactoryList() {
		try {
			pers.saveFactory(fac);
			pers.saveFactory(fac2);

			List<IFactoryHeader> facList = pers.getFactories();

			assertTrue(containsID(facList, facID));
			assertTrue(containsID(facList, facID2));

		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(expected = PersistenceException.class)
	public void fileNotFound() throws PersistenceException {

		pers.deleteFactory("test");
		List<IFactoryHeader> facList = pers.getFactories();

		
		assertFalse(containsID(facList, facID));

		pers.loadFactory("test");


	}

	@Test
	public void deleteFactory() {
		try {
			// save Factory and compare ID in FactopryList
			pers.saveFactory(fac);
			List<IFactoryHeader> facList = pers.getFactories();
			assertTrue(containsID(facList, facID));

			// delete Factory now, ID shouldn't be in FactoryList
			pers.deleteFactory("test");
			facList = pers.getFactories();
			assertFalse(containsID(facList, facID));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	@After
	public void stop() {
		try {
			pers.deleteFactory("test");
			pers.deleteFactory("test");
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

}
