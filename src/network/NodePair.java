package network;

import java.util.ArrayList;
import subgraph.LinearRoute;
import general.CommonObject;
public class NodePair extends CommonObject{
	private Layer associateLayer =null;//the layer that the node pair associated with
	private Node srcNode = null; //the source node
	private Node desNode = null; //Destination node
	private int rate;
	private int slotsnum;
	private int sub_slotsnum1;
	private int sub_slotsnum2;
	private int leasthop;
	private ArrayList<LinearRoute> linearroutelist = null; //list of three shortest linear route associated with the node pair
	private ArrayList<LinearRoute> routelist = null;
	private ArrayList<NodePair> sharednodepairlist = null; //record nodepair used to share protection path(not applied to slotplane algorithm)
	private ArrayList<VirtualLink> virtuallinklist = null;
	
	public NodePair(String name, int index, String comments,
			Layer associateLayer, Node srcNode, Node desNode) {
		super(name, index, comments);
		this.associateLayer = associateLayer;
		this.srcNode = srcNode;
		this.desNode = desNode;
		this.linearroutelist = new ArrayList<LinearRoute>();
		this.routelist = new ArrayList<LinearRoute>();
		this.sharednodepairlist=new ArrayList<NodePair>();
		this.virtuallinklist = new ArrayList<VirtualLink>();
	}
	
	public ArrayList<VirtualLink> getVirtuallinklist() {
		return virtuallinklist;
	}

	public void setVirtuallinklist(ArrayList<VirtualLink> virtuallinklist) {
		this.virtuallinklist = virtuallinklist;
	}

	public Layer getAssociateLayer() {
		return associateLayer;
	}
	
	public void setAssociateLayer(Layer associateLayer) {
		this.associateLayer = associateLayer;
	}
	
	public Node getSrcNode() {
		return srcNode;
	}
	
	public void setSrcNode(Node srcNode) {
		this.srcNode = srcNode;
	}
	
	public Node getDesNode() {
		return desNode;
	}
	
	public void setDesNode(Node desNode) {
		this.desNode = desNode;
	}
	
	public ArrayList<LinearRoute> getLinearroutelist() {
		return linearroutelist;
	}
	
	public void setLinearroutelist(ArrayList<LinearRoute> linearroutelist) {
		this.linearroutelist = linearroutelist;
	}
	
	public ArrayList<LinearRoute> getRoutelist() {
		return routelist;
	}

	public void setRoutelist(ArrayList<LinearRoute> routelist) {
		this.routelist = routelist;
	}
	
	public ArrayList<NodePair> getsharednodepairlist(){
		return sharednodepairlist;
	}
	
	/**
	 * add a route to the route list
	 */
	public void addRoute(LinearRoute route){
		this.linearroutelist.add(route);		
	}
	
	/**
	 * remove route from the list 
	 */
	public void removeRoute(LinearRoute route){
		this.linearroutelist.remove(route);
	}
	
	/**
	 * remove route from the list based on index
	 */
	public void removeRoute(int index){
		for(int i=0;i<this.linearroutelist.size();i++){
			if(this.linearroutelist.get(i).getIndex()==index){
				this.linearroutelist.remove(i);
				break;			
			}
		}
	}
	
	/**
	 * remove route from the list based on name
	 */
	public void removeRoute(String name){
		for(int i=0;i<this.linearroutelist.size();i++){
			if(this.linearroutelist.get(i).getName().equals(name)){
				this.linearroutelist.remove(i);
				break;			
			}
		}
	}
	
	public int getSub_slotsnum1() {
		return sub_slotsnum1;
	}
	
	public void setSub_slotsnum1(int sub_slotsnum1) {
		this.sub_slotsnum1 = sub_slotsnum1;
	}
	
	public int getSub_slotsnum2() {
		return sub_slotsnum2;
	}
	
	public void setSub_slotsnum2(int sub_slotsnum2) {
		this.sub_slotsnum2 = sub_slotsnum2;
	}
	
	public void setSlotsnum(int slotsnum) {
		this.slotsnum = slotsnum;
	}
	
	public int getSlotsnum() {
		return slotsnum;
	}
	
	public void setleasthop(int hop) {
		this.leasthop=hop;
	}
	
	public int getleasthop() {
		return leasthop;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
}

