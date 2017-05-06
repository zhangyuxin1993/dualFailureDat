package subgraph;

import java.util.ArrayList;
import network.*;
import general.CommonObject;

public class Subgraph extends CommonObject {
	private ArrayList<Node> nodelist = null; // node list
	private ArrayList<Link> linklist = null; // link list
	private ArrayList<CommonObject> objectlist = null; // object list
	private ArrayList<Subgraph> routelist = null; // a set of route associated with the current route

	public Subgraph(String name, int index, String comments) {
		super(name, index, comments);
		this.nodelist = new ArrayList<Node>();
		this.linklist = new ArrayList<Link>();
		this.objectlist = new ArrayList<CommonObject>();
		this.routelist = new ArrayList<Subgraph>();
	}

	public ArrayList<Node> getNodelist() {
		return nodelist;
	}

	public void setNodelist(ArrayList<Node> nodelist) {
		this.nodelist = nodelist;
	}

	public ArrayList<Link> getLinklist() {
		return linklist;
	}

	public void setLinklist(ArrayList<Link> linklist) {
		this.linklist = linklist;
	}

	public ArrayList<CommonObject> getObjectlist() {
		return objectlist;
	}

	public void setObjectlist(ArrayList<CommonObject> objectlist) {
		this.objectlist = objectlist;
	}

	public ArrayList<Subgraph> getRoutelist() {
		return routelist;
	}

	public void setRoutelist(ArrayList<Subgraph> routelist) {
		this.routelist = routelist;
	}

	/**
	 * convert a node list to link and common object list
	 */
	public void ConvertfromNodeListtoLinkList() {
		this.getLinklist().clear();
		for (Node node : this.getNodelist()) {
			int next_node_index = this.getNodelist().indexOf(node) + 1;
			if (next_node_index < this.getNodelist().size()) {
				Node next_node = this.getNodelist().get(next_node_index);
				if (next_node != null) {
					Link link = node.getAssociatedLayer().findLink(node, next_node);
					this.getLinklist().add(link);
				}
			}
		}
	}

	/**
	 * get the length of subgraph
	 */
	public double getLength() {
		double sum = 0;
		for (Link link : this.getLinklist()) {
			sum += link.getLength();
		}
		return sum;
	}

	/**
	 * get the cost of subgraph
	 */
	public double getCost() {
		double sum = 0;
		for (Link link : this.getLinklist()) {
			sum += link.getCost();
		}
		return sum;
	}
	
	
}

