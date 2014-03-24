package de.hsrm.mi.swt.Persistence;

import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;
import de.hsrm.mi.swt.Persistence.intf.IPersistence;

public class Persistence implements IPersistence {

	private final static String SAVE_DIR_FACTORY = "save/fac/";
	private final static String SAVE_DIR_MACHINE = "save/mac/";
	private final static String SAVE_DIR_USER = "save/usr/";
	private final static String SAVE_DIR_PRODUCT = "save/prd/";

	private final static String SAVE_ENDING_FACTORY = ".fac";
	private final static String SAVE_ENDING_FACTORY_HEAD = ".hfac";
	private final static String SAVE_ENDING_MACHINE = ".mac";
	private final static String SAVE_ENDING_USER = ".usr";
	private final static String SAVE_ENDING_PRODUCT = ".prd";
	
	private ReadIn read;
	
	public Persistence(){
		read = new ReadIn();
	}
	/* 
	 * 	FACTORY
	 */
	
	@Override
	public boolean saveFactory(IFactory factory) throws PersistenceException {
		return read.save(String.valueOf(factory.getId()), factory, SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY) && read.save(factory.getName(), factory.getHeader(), SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY_HEAD);
	}

	@Override
	public List<IFactoryHeader> getFactories() throws PersistenceException {
		return read.getHeaderList(SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY_HEAD);
	}

	@Override
	public IFactory loadFactory(long factoryLongID) throws PersistenceException {
		String factoryID = String.valueOf(factoryLongID);
		return (IFactory)read.load(factoryID, SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY);
	}

	
	@Override
	public boolean deleteFactory(long factoryLongID) throws PersistenceException {
		String factoryID = String.valueOf(factoryLongID);
		return read.delete(factoryID, SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY) && read.delete(factoryID, SAVE_DIR_FACTORY, SAVE_ENDING_FACTORY_HEAD);

	}

	/* 
	 * 	MACHINE UNIT
	 */
	
	@Override
	public boolean saveMachineUnit(IMachineUnit machineUnit) throws PersistenceException {
		return read.save(machineUnit.getTypeID(), machineUnit, SAVE_DIR_MACHINE, SAVE_ENDING_MACHINE);
	}

	@Override
	public List<String> getMachineUnits() throws PersistenceException {
		return read.getList(SAVE_DIR_MACHINE, SAVE_ENDING_MACHINE);
	}

	@Override
	public IMachineUnit loadMachineUnit(String typeID) throws PersistenceException {
		return (IMachineUnit)read.load(typeID, SAVE_DIR_MACHINE, SAVE_ENDING_MACHINE);

	}

	@Override
	public boolean deleteMachineUnit(String typeID) throws PersistenceException {
		return read.delete(typeID, SAVE_DIR_MACHINE, SAVE_ENDING_MACHINE);
	}

	/* 
	 * 	USER
	 */
	
	@Override
	public boolean saveUser(IUser user) throws PersistenceException {
		return read.save(user.getName(), user, SAVE_DIR_USER, SAVE_ENDING_USER);
	}

	@Override
	public List<String> getUsers() throws PersistenceException {
		return read.getList(SAVE_DIR_USER, SAVE_ENDING_USER);
	}

	@Override
	public IUser loadUser(String name) throws PersistenceException {
		return (IUser)read.load(name, SAVE_DIR_USER, SAVE_ENDING_USER);
	}

	@Override
	public boolean deleteUser(String name) throws PersistenceException {
		return read.delete(name, SAVE_DIR_USER, SAVE_ENDING_USER);

	}

	
	/* 
	 * 	PRODUCT
	 */
	
	@Override
	public boolean saveProduct(IProduct product) throws PersistenceException {
		return read.save(product.getID(), product, SAVE_DIR_PRODUCT, SAVE_ENDING_PRODUCT);
	}

	@Override
	public List<String> getProducts() throws PersistenceException {
		return read.getList(SAVE_DIR_PRODUCT, SAVE_ENDING_PRODUCT);
	}

	@Override
	public IProduct loadProduct(String productID) throws PersistenceException {
		return (IProduct)read.load(productID, SAVE_DIR_PRODUCT, SAVE_ENDING_PRODUCT);
	}

	@Override
	public boolean deleteProduct(String productID) throws PersistenceException {
		return read.delete(productID, SAVE_DIR_PRODUCT, SAVE_ENDING_PRODUCT);
	}
	
	public boolean clear() throws PersistenceException{
		return read.clear(SAVE_DIR_USER);
	}

}
