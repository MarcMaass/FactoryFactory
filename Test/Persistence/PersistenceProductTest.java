package de.hsrm.mi.swt.Test.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceProductTest {

	Product prd;
	String prdID;
	String type;

	
	Product prd2;
	String prdID2;
	String type2;
	
	Persistence pers;

	@Before
	public void start(){
		pers = new Persistence();
		
		prdID = "a2345";
		prdID2 = "b2345";
		
		type = "type";
		type2 = "type2";
		
		prd = new Product(prdID, type);
		prd2 = new Product(prdID2, type2);

	}
	
	@Test
	public void saveLoadProduct() {
		
		try {
			pers.saveProduct(prd);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Product prd3 = null;
		try {
			prd3 = (Product)pers.loadProduct(prdID);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotSame(prd, prd3);
		assertEquals(prd.getID(), prd3.getID());
	}
	
	
	@Test
	public void getProductList() {
		
		try {
			pers.saveProduct(prd);
			pers.saveProduct(prd2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<String> prdList = null;
		try {
			prdList = pers.getProducts();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		assertTrue(prdList.contains(prdID));
		assertTrue(prdList.contains(prdID2));
		
	}
	
	@Test
	public void deleteProduct() {
		
		// save Product and compare ID in FactopryList
		try {
			pers.saveProduct(prd);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> prdList = null;
		try {
			prdList = pers.getProducts();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(prdList.contains(prdID));
		
		// delete Product now, ID shouldn't be in ProductList
		try {
			pers.deleteProduct(prdID);
			prdList = pers.getProducts();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(prdList.contains(prdID));
		
	}

	@After
	public void stop(){
		try {
			pers.deleteProduct(prdID);
			pers.deleteProduct(prdID2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
