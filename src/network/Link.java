package network;

import java.util.ArrayList;
import general.CommonObject;
import general.Constant;
import general.Slot;

public class Link extends CommonObject {
	private Layer associatedLayer = null; // the layer that the link belongs to
	private Node nodeA = null; // node A
	private Node nodeB = null; // node B
	private double length = 0;
	private double cost = 0; // the cost of the link
	private int status = Constant.UNVISITED; // the visited status
	private int w = 0;
	private ArrayList<Slot> slotarray;
	private ArrayList<Integer> slotsindex;
	private ArrayList<Integer> selectindex;
	private int mostusetime = 0;
	private int unusedslot = Constant.F;
	private ArrayList<VirtualLink> virtuallinklist = null;
	
	public Link(String name, int index, String comments, Layer associatedLayer, Node nodeA, Node nodeB, double length, double cost) {
		super(name, index, comments);
		this.associatedLayer = associatedLayer;
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.length = length;
		this.cost = cost;
		this.status = Constant.UNVISITED;
		
		this.slotarray = new ArrayList<Slot>();
		
		for (int i = 0; i < Constant.F; i++) {
			Slot slot = new Slot();
			this.slotarray.add(slot);
		}
		this.slotsindex = new ArrayList<Integer>();
		this.selectindex = new ArrayList<Integer>();
		this.virtuallinklist = new ArrayList<VirtualLink>();
		// this.selectindexarray = new ArrayList<Integer>();
	}
	
	public ArrayList<VirtualLink> getVirtuallinklist() {
		return virtuallinklist;
	}

	public void setVirtuallinklist(ArrayList<VirtualLink> virtuallinklist) {
		this.virtuallinklist = virtuallinklist;
	}

	public Layer getAssociatedLayer() {
		return associatedLayer;
	}

	public void setAssociatedLayer(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}

	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSlotsarray(ArrayList<Slot> slotsarray) {
		this.slotarray = slotsarray;
	}

	public ArrayList<Slot> getSlotsarray() {
		return slotarray;
	}

	public void setSlotsindex(ArrayList<Integer> randomnum) {
		this.slotsindex = randomnum;
	}

	public ArrayList<Integer> getSlotsindex() {
		return slotsindex;
	}

	public void setSelectindex(ArrayList<Integer> selectindex) {
		this.selectindex = selectindex;
	}

	public ArrayList<Integer> getSelectindex() {
		return selectindex;
	}

	public int getunusedslot() {
		return this.unusedslot;
	}

	public void setunusedslot(int unusedslot) {
		this.unusedslot = unusedslot;
	}

	public int getmostusetime() {
		return mostusetime;
	}

	public void setmostusetime(int mostusetime) {
		this.mostusetime = mostusetime;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}	
	
}
