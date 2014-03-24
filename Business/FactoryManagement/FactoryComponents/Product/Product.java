package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product;

import java.io.InputStream;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class Product implements IProduct {
	private String id;
	private String type;
	private String description;
	private InputStream data;
	private String texture;

	private int posx;
	private int posy;

	private INode node = null;

	public Product(String id, String type) {
		this.id = id;
		this.type = type;
		this.description = type;
		this.texture = type;
	}

	public Product(String id, String type, String description, String texture) {
		this(id, type);
		this.description = description;
		this.texture = texture;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public InputStream getData() {
		return data;
	}

	@Override
	public String getTexture() {
		return texture;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setType(String name) {
		this.type = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setData(InputStream data) {
		this.data = data;
	}

	@Override
	public void setTexture(String texture) {
		this.texture = texture;
	}

	@Override
	public void setNode(INode n) {
		if (n != null) {
			posx = n.getPos()[0];
			posy = n.getPos()[1];
		} else {
			posx = -1;
			posy = -1;
		}
		node = n;
	}

	@Override
	public INode getNode() {
		return node;
	}

	@Override
	public int getPosX() {
		return posx;
	}

	@Override
	public int getPosY() {
		return posy;
	}

}
