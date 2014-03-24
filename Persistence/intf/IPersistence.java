package de.hsrm.mi.swt.Persistence.intf;

import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

public interface IPersistence {

	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean saveFactory(IFactory factory) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public List<IFactoryHeader> getFactories() throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public IFactory loadFactory(long factoryLongID) throws PersistenceException;
	
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public boolean deleteFactory(long factoryLongID) throws PersistenceException;
	
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean saveMachineUnit(IMachineUnit machineUnit) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public List<String> getMachineUnits() throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public IMachineUnit loadMachineUnit(String machineUnitID) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean deleteMachineUnit(String machineUnitID) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean saveUser(IUser user) throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public List<String> getUsers() throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public IUser loadUser(String userID) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean deleteUser(String userID) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean saveProduct(IProduct product) throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public List<String> getProducts() throws PersistenceException;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public IProduct loadProduct(String productID) throws PersistenceException;
	
	/**
	 * 
	 * @param factory
	 * @return
	 */
	public boolean deleteProduct(String productID) throws PersistenceException;
}
