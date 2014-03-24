package de.hsrm.mi.swt.Persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;
import de.hsrm.mi.swt.Persistence.intf.IReadIn;

public class ReadIn implements IReadIn {

	public boolean save(String id, Object obj, String saveDir, String saveEnd) throws PersistenceException {
		String factoryFileName = saveDir + id + saveEnd;
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdirs();
			dir.setWritable(true);
		}

		try {
			fos = new FileOutputStream(factoryFileName);
			out = new ObjectOutputStream(fos);
			out.writeObject(obj);
			out.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException("Error While Saving");
		}
	}

	public List<String> getList(String saveDir, String saveEnd)
			throws PersistenceException {
		List<String> list = new ArrayList<String>();

		try {
			File dir = new File(saveDir);
			File[] files = dir.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.getPath().endsWith(saveEnd) && f.isFile()) {
						String fullName = f.getName();
						String name = fullName.substring(0, fullName.lastIndexOf('.'));
						list.add(name);
					}
				}
			}
		} catch (Exception e) {
			throw new PersistenceException("No List Found");
		}
		return list;
	}

	public List<IFactoryHeader> getHeaderList(String saveDir, String saveEnd)
			throws PersistenceException {
		List<IFactoryHeader> list = new ArrayList<IFactoryHeader>();

		try {
			File dir = new File(saveDir);
			File[] files = dir.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.getPath().endsWith(saveEnd) && f.isFile()) {
						String fullName = f.getName();
						String name = fullName.substring(0, fullName.lastIndexOf('.'));
						IFactoryHeader fh = (IFactoryHeader)load(name, saveDir, saveEnd);
						list.add(fh);
					}
				}
			}
		} catch (Exception e) {
			throw new PersistenceException("No List Found");
		}
		return list;
	}
	
	public Object load(String id, String saveDir, String saveEnd)
			throws PersistenceException {
		String fileName = saveDir + id + saveEnd;

		FileInputStream fis = null;
		ObjectInputStream in = null;
		Object obj = null;
		try {
			fis = new FileInputStream(fileName);
			in = new ObjectInputStream(fis);
			obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			throw new PersistenceException("Error While Loading");
		}

	}

	public boolean delete(String id, String saveDir, String saveEnd)
			throws PersistenceException {
		try {
			File dir = new File(saveDir);

			File[] files = dir.listFiles();
			if (files != null) { // Erforderliche Berechtigungen etc. sind
									// vorhanden
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					String name = f.getName();
					if ((name.equals(id + saveEnd)) && f.isFile()) {
						f.delete();
					}
				}
				return true;
			}
		} catch (Exception e) {
			throw new PersistenceException("Error While Deleting");
		}
		return false;
	}

	public boolean clear(String saveDir) throws PersistenceException {
		try {
			File dir = new File(saveDir);

			File[] files = dir.listFiles();
			if (files != null) { // Erforderliche Berechtigungen etc. sind
									// vorhanden
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
				return true;
			}
		} catch (Exception e) {
			throw new PersistenceException("Error While Clearing");
		}
		return false;
	}
}
