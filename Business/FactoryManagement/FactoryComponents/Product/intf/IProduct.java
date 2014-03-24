package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf;

import java.io.InputStream;
import java.io.Serializable;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;

/**
 * A factory produce which can be changed in several machines.
 * 
 */
public interface IProduct extends Serializable{

	public String getID();

	public String getType();

	public String getDescription();

	public InputStream getData();

	public String getTexture();

	public void setId(String id);

	public void setType(String type);

	public void setDescription(String description);

	public void setData(InputStream data);

	public void setTexture(String texture);

	/**
	 * Sets the Node the Product is on
	 * 
	 * @param n
	 */
	public void setNode(INode n);

	/**
	 * Gets the Node the Product is on ATM
	 * 
	 * @return
	 */
	public INode getNode();
	
	public int getPosX();
	
	public int getPosY();

}
