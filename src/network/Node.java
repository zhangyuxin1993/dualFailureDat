package network;

import general.CommonObject;
import general.Constant;
import java.util.ArrayList;

public class Node extends CommonObject{
	private Layer associatedLayer = null; //the layer that the node is associated with
	private ArrayList<Node> neinodelist = null; //list of neighbor nodes	 
	private double cost_from_src = 10000000; //used search algorithm to get he cost from the src node
	private double length_from_src = 10000000; //used search algorithm to get he cost from the src node
	private int hop_from_src = 100000000; //used in search algorithm to get the number of hops from the src node
	private int status = Constant.UNVISITED; //not visited yet
	private int type = 0;
	private int layer = 0;
	private int group = 0;
	private int group1 = 0;
	private int group2 = 0;
	private Node parentNode = null; //parent node of the current node
	private Node firstAscriptionNode = null;
	private Node secondAscriptionNode = null;
	
	public Node(String name, int index, String comments, Layer associatedLayer) {
		super(name, index, comments);
		this.associatedLayer = associatedLayer;
		this.neinodelist = new ArrayList<Node>();
	}
	
	public Node(String name, int index, String comments, Layer associatedLayer, int layer, int group, int group1, int group2, int type) {
		super(name, index, comments);
		this.associatedLayer = associatedLayer;
		this.neinodelist = new ArrayList<Node>();
		this.layer = layer;
		this.group = group;
		this.group1 = group1;
		this.group2 = group2;
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getGroup1() {
		return group1;
	}

	public void setGroup1(int group1) {
		this.group1 = group1;
	}

	public int getGroup2() {
		return group2;
	}

	public void setGroup2(int group2) {
		this.group2 = group2;
	}
	
	public Node getParentNode() {
		return parentNode;
	}
	
	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	
	public Node getFirstAscriptionNode() {
		return firstAscriptionNode;
	}

	public void setFirstAscriptionNode(Node firstAscriptionNode) {
		this.firstAscriptionNode = firstAscriptionNode;
	}

	public Node getSecondAscriptionNode() {
		return secondAscriptionNode;
	}

	public void setSecondAscriptionNode(Node secondAscriptionNode) {
		this.secondAscriptionNode = secondAscriptionNode;
	}
	
	public double getLength_from_src() {
		return length_from_src;
	}
	
	public void setLength_from_src(double length_from_src) {
		this.length_from_src = length_from_src;
	}
	
	public double getCost_from_src() {
		return cost_from_src;
	}
	
	public void setCost_from_src(double cost_from_src) {
		this.cost_from_src = cost_from_src;
	}
	
	public int getHop_from_src() {
		return hop_from_src;
	}
	
	public void setHop_from_src(int hop_from_src) {
		this.hop_from_src = hop_from_src;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Layer getAssociatedLayer() {
		return associatedLayer;
	}
	
	public void setAssociatedLayer(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}
	
	public ArrayList<Node> getNeinodelist() {
		return neinodelist;
	}
	
	public void setNeinodelist(ArrayList<Node> neinodelist) {
		this.neinodelist = neinodelist;
	}
	
	/**
	 * add neighbor node
	 */
	public void addNeiNode(Node node) {
		this.neinodelist.add(node);
	}
	
	/**
	 * remove neighbor node from the neinode list by index
	 */
	public void removeNeiNode(int index) {
		for (int i = 0; i < this.neinodelist.size(); i++) {
			if (this.neinodelist.get(i).getIndex() == index) {
				this.neinodelist.remove(this.neinodelist.get(i));
				break;
			}
		}
	}
	
	/**
	 * remove neighbor node from the neinode list by name
	 */
	public void removeNeiNode(String name) {
		for (int i = 0; i < this.neinodelist.size(); i++) {
			if (this.neinodelist.get(i).getName().equals(name)) {
				this.neinodelist.remove(this.neinodelist.get(i));
				break;
			}
		}
	}
	
	/**
	 * remove neighbor node from the neinode list
	 */
	public void removeNeiNode(Node node) {
		this.getNeinodelist().remove(node);
	}	   
	
	/**
	 * get degree of the node
	 */
	public int getDegree() {
		return this.neinodelist.size();
	}
	
}
