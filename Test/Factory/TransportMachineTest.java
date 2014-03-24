package de.hsrm.mi.swt.Test.Factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;

public class TransportMachineTest {
	
	IMachineUnit mu;
	List<INode> nodes;
	IProduct p;
	
	@Before
	public void setup(){
		nodes = new ArrayList<INode>();
		p = new Product("123861278936", "teddybaer");
	}
	
	@Test
	public void buildMachine() throws NodeException{
		INode n1 = new TransportNode(1);
		INode n2 = new TransportNode(1);
		INode n3 = new TransportNode(1);
		
		n1.setPos(0, 0);
		n2.setPos(0, 1);
		n3.setPos(0, 2);
		
		((TransportNode)n1).setOutput(n2);
		((TransportNode)n2).setOutput(n3);
		
		((TransportNode)n1).addAcceptedProduct(p.getType());
		((TransportNode)n2).addAcceptedProduct(p.getType());
		((TransportNode)n3).addAcceptedProduct(p.getType());
		
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		
		List<Integer> inputs = new ArrayList<Integer>();
		inputs.add(0);
		
		List<Integer> outputs = new ArrayList<Integer>();
		outputs.add(2);
		
		mu = new MachineUnit("transportmachine", 3, 1, nodes, inputs, outputs);
		
		mu.getInputNodes().get(0).addProduct(p);
		
		
		//System.out.println(Arrays.toString(p.getNode().getPos()));
		p.getNode().execute();
		//System.out.println(Arrays.toString(p.getNode().getPos()));
		p.getNode().execute();
		//System.out.println(Arrays.toString(p.getNode().getPos()));
		
		assertEquals(p, n3.getProduct());
	}

}
