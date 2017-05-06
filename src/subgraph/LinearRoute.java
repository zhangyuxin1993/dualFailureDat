package subgraph;

import java.util.ArrayList;

import file.File_output;
import network.Link;
import network.Node;
import general.Constant;

public class LinearRoute extends Subgraph {
	private ArrayList<Node> nodelist = null;
	private ArrayList<Link> linklist = null;
	private int slotsnum;
	private ArrayList<Integer> slotsnumarray;
	private int startspectrum;
	private double sumcost;
	private int type = Constant.WORKING;//the default of the route type is working

	public LinearRoute(String name, int index, String comments) {
		super(name, index, comments);
		this.nodelist = new ArrayList<Node>();
		this.linklist = new ArrayList<Link>();
	}

	public LinearRoute(String name, int index, String comments, ArrayList<Link> route_linklist) {
		super(name, index, comments);
		this.slotsnumarray = new ArrayList<Integer>();
		this.linklist = new ArrayList<Link>();
		this.nodelist = new ArrayList<Node>();
		this.addRoute_linklist(route_linklist);
		this.setSlotsnum(0);
	}
	
	public LinearRoute(String name, int index, String comments, int type) {
		super(name, index, comments);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setRoute_linklist(ArrayList<Link> route_linklist) {
		this.linklist = route_linklist;
	}

	public ArrayList<Link> getRoute_linklist() {
		return linklist;
	}

	public void setRoute_nodelist(ArrayList<Node> route_nodelist) {
		this.nodelist = route_nodelist;
	}

	public ArrayList<Node> getRoute_nodelist() {
		return nodelist;
	}

	public void addRoute_linklist(ArrayList<Link> route_linklist) {
		this.linklist.clear();
		for (Link link : route_linklist) {
			this.linklist.add(link);
		}
	}

	public void setSlotsnum(int slotsnum) {
		this.slotsnum = slotsnum;
	}

	public int getSlotsnum() {
		return this.slotsnum;
	}

	public void setSlotsnumarray(ArrayList<Integer> slotsnumarray) {
		this.slotsnumarray = slotsnumarray;
	}

	public ArrayList<Integer> getSlotsnumarray() {
		return slotsnumarray;
	}

	public void setStartspectrum(int startspectrum) {
		this.startspectrum = startspectrum;
	}

	public int getStartspectrum() {
		return startspectrum;
	}

	// 向route中添加node
	public void addNode(Node node) {
		nodelist.add(node);
	}

	// 向route中添加link
	public void addLink(Link link) {
		linklist.add(link);
	}
	
	/*
	 * try by myself
	 */
	public void equals(LinearRoute route){
		LinearRoute copyroute=new LinearRoute(null, 0, null);
		copyroute.setNodelist(route.getNodelist());
		this.setNodelist(copyroute.getNodelist());
	}
//	public ArrayList<Node> getNodelist() {
//		return nodelist;
//	}
//
//	public void setNodelist(ArrayList<Node> nodelist) {
//		this.nodelist = nodelist;
//	}
//
//	public ArrayList<Link> getLinklist() {
//		return linklist;
//	}
//
//	public void setLinklist(ArrayList<Link> linklist) {
//		this.linklist = linklist;
//	}

	public double getsumcost() {
		return sumcost;
	}

	public void setsumcost(double sumcost) {
		this.sumcost = sumcost;
	}
	
	public void addsumcost(double sumcost) {
		this.sumcost += sumcost;
	}
	
	public void OutputRoute_link(LinearRoute newroute) {
		ArrayList<Link> linklist = newroute.getLinklist();
		for (Link link : linklist) {
			System.out.println(link.getNodeB().getName() + "------" + link.getNodeA().getName());
		}
	}

	public void OutputRoute_node(LinearRoute newroute) {
		File_output filewrite=new File_output();
		if (newroute.getNodelist().size() == 0) {
			System.out.println("no path to the desnode");
		} else {
			// System.out.println("the shortest path is:");
			/*
			 * for(Node node:newroute.getNodelist()){
			 * System.out.println(node.getName());
			 */
			int i = 0;
			int k = newroute.getNodelist().size();
			for (i = 0; i < k - 1; i++){
				System.out.print(newroute.getNodelist().get(i).getName() + "-");
			}
				System.out.println(newroute.getNodelist().get(i).getName());

		}
	}

	public void OutputRoute_node(LinearRoute newroute, String filename) {
		File_output filewrite=new File_output();
		if (newroute.getNodelist().size() == 0) {
			System.out.println("no path to the desnode");
		} else {
			// System.out.println("the shortest path is:");
			/*
			 * for(Node node:newroute.getNodelist()){
			 * System.out.println(node.getName());
			 */
			int i = 0;
			int k = newroute.getNodelist().size();
			for (i = 0; i < k - 1; i++){
//				System.out.print(newroute.getNodelist().get(i).getName() + "-");
				filewrite.filewrite_without(filename, newroute.getNodelist().get(i).getName() + "-");
			}
//				System.out.println(newroute.getNodelist().get(i).getName());
				filewrite.filewrite_without(filename, newroute.getNodelist().get(i).getName());							
		}
		
	}

//	public void OutputRoute_node(LinearRoute newroute, String filename) {
//		// TODO Auto-generated method stub
//		
//	}

}
