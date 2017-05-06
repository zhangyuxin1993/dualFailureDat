package network;

import java.util.ArrayList;

import request.Request;
import subgraph.LinearRoute;

public class VirtualLink {
	private Node srcnode = null;
	private Node desnode = null;
	private int fullcapacity = 0;
	private int restcapacity = 0;
	private int wavelengthindex = 0;
	private int firstindex = 0;
	private int lastindex = 0;
	private int sub_firstindex1 = 0;
	private int sub_lastindex1 = 0;
	private int sub_firstindex2 = 0;
	private int sub_lastindex2 = 0;
	private LinearRoute optroute = null;
	private ArrayList<Request> greqlist = null;
	
	public VirtualLink(Node srcnode, Node desnode, LinearRoute optroute, int fullcapacity, int restcapacity) {
		this.srcnode = srcnode;
		this.desnode = desnode;
		this.optroute = optroute;
		this.fullcapacity = fullcapacity;
		this.restcapacity = restcapacity;
		this.greqlist = new ArrayList<Request>();
	}
	
	public Node getSrcnode() {
		return srcnode;
	}
	
	public void setSrcnode(Node srcnode) {
		this.srcnode = srcnode;
	}
	
	public Node getDesnode() {
		return desnode;
	}
	
	public void setDesnode(Node desnode) {
		this.desnode = desnode;
	}
	
	public int getFullcapacity() {
		return fullcapacity;
	}
	
	public void setFullcapacity(int fullcapacity) {
		this.fullcapacity = fullcapacity;
	}
	
	public int getRestcapacity() {
		return restcapacity;
	}
	
	public void setRestcapacity(int restcapacity) {
		this.restcapacity = restcapacity;
	}
	
	public int getWavelengthindex() {
		return wavelengthindex;
	}

	public void setWavelengthindex(int wavelengthindex) {
		this.wavelengthindex = wavelengthindex;
	}
	
	public int getFirstindex() {
		return firstindex;
	}

	public void setFirstindex(int firstindex) {
		this.firstindex = firstindex;
	}

	public int getLastindex() {
		return lastindex;
	}

	public void setLastindex(int lastindex) {
		this.lastindex = lastindex;
	}
	
	public int getSub_firstindex1() {
		return sub_firstindex1;
	}

	public void setSub_firstindex1(int sub_firstindex1) {
		this.sub_firstindex1 = sub_firstindex1;
	}

	public int getSub_lastindex1() {
		return sub_lastindex1;
	}

	public void setSub_lastindex1(int sub_lastindex1) {
		this.sub_lastindex1 = sub_lastindex1;
	}

	public int getSub_firstindex2() {
		return sub_firstindex2;
	}

	public void setSub_firstindex2(int sub_firstindex2) {
		this.sub_firstindex2 = sub_firstindex2;
	}

	public int getSub_lastindex2() {
		return sub_lastindex2;
	}

	public void setSub_lastindex2(int sub_lastindex2) {
		this.sub_lastindex2 = sub_lastindex2;
	}
	
	public LinearRoute getOptroute() {
		return optroute;
	}
	
	public void setOptroute(LinearRoute optroute) {
		this.optroute = optroute;
	}
	
	public ArrayList<Request> getGreqlist() {
		return greqlist;
	}

	public void setGreqlist(ArrayList<Request> greqlist) {
		this.greqlist = greqlist;
	}
	
}
