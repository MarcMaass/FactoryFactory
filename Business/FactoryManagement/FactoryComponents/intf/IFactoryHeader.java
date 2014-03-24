package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf;

import java.io.Serializable;

public interface IFactoryHeader extends Serializable {
	public void setID(long newID);
	
	public long getID();
	
	public String getName();
	
	public void incrementRevision();
	
	public int getRevision();
	
	public void setWriteGroup(String groupname);
	
	public void setReadGroup(String groupname);
	
	public String getWriteGroup();
	
	public String getReadGroup();
}
