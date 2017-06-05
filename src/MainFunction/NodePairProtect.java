package MainFunction;

import java.util.ArrayList;

import network.NodePair;
import subgraph.Cycle;
import subgraph.LinearRoute;

public class NodePairProtect {
	 
	private ArrayList<Cycle> cyclelist= new ArrayList<Cycle>();
	private LinearRoute workroute;
	private NodePair nodepair;
	private ArrayList<LinearRoute> routelist=new ArrayList<LinearRoute>();

	
	public NodePairProtect() {
		this.nodepair=nodepair;
		this.cyclelist = cyclelist;
		this.workroute=workroute;
	}


	public void setcyclelist(ArrayList<Cycle> cyclelist){
		this.cyclelist.addAll(cyclelist);
	}
	public void setroutelist(ArrayList<LinearRoute> routelist){
		this.routelist.addAll(routelist);
	}
	public void setworkroute(LinearRoute workroute){
		this.workroute=workroute;
	}
	public void setnodepair(NodePair nodepair){
		this.nodepair=nodepair;
	}

	
	public ArrayList<Cycle> getcyclelist( ){
		return cyclelist;
	}
	public ArrayList<LinearRoute> getroutelist( ){
		return routelist;
	}
	public LinearRoute getworkroute( ){
		return workroute;
	}
	public NodePair getnodepair( ){
		return nodepair;
	}


}
