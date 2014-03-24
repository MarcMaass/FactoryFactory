package de.hsrm.mi.swt.Persistence.intf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

public interface IReadIn {
	/**
	 * 
	 * @param id
	 * @param obj
	 * @param saveDir
	 * @param saveEnd
	 * @return
	 */
	public boolean save(String id, Object obj, String saveDir, String saveEnd) throws PersistenceException;

	/**
	 * 
	 * @param saveDir
	 * @param saveEnd
	 * @return
	 */
	public List<String> getList(String saveDir, String saveEnd) throws PersistenceException;

	/**
	 * 
	 * @param id
	 * @param saveDir
	 * @param saveEnd
	 * @return
	 */
	public Object load(String id, String saveDir, String saveEnd) throws PersistenceException;


	/**
	 * 
	 * @param id
	 * @param saveDir
	 * @param saveEnd
	 * @return
	 */
	public boolean delete(String id, String saveDir, String saveEnd) throws PersistenceException;

}
