package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;

public class FactoryHeader implements IFactoryHeader {
	
	private static final long serialVersionUID = 1L;
	
	@Expose
	private long id;
	@Expose
	private String name;
	@Expose
	private int revision;
	@Expose
	private String readGroup;
	@Expose
	private String writeGroup; 
	@Expose
	private int sizex;
	@Expose
	private int sizey;
	
	public FactoryHeader(long id, String name, String readGroup, String writeGroup, int size_x, int size_y) {
		this.id = id;
		this.name = name;
		this.readGroup = readGroup;
		this.writeGroup = writeGroup;
		
		this.sizex = size_x;
		this.sizey = size_y;
		
		this.revision = 0;
		
	}
	
	public void setID(long newID) {
		id = newID;
	}
	
	public long getID(){
		return id;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getName(){
		return name;
	}
	
	public void incrementRevision(){
		revision++;
	}
	
	public int getRevision(){
		return revision;
	}
	
	public void setWriteGroup(String groupname) {
		writeGroup = groupname;
	}
	
	public void setReadGroup(String groupname) {
		readGroup = groupname;
	}
	
	public String getWriteGroup() {
		return writeGroup;
	}
	
	public String getReadGroup() {
		return readGroup;
	}
	
	public void setSize(int x, int y){
		sizex = x;
		sizey = y;
	}
	
	public int getSizeX(){
		return sizex;
	}
	
	public int getSizeY(){
		return sizey;
	}
}
