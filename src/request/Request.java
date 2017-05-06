package request;

import java.util.ArrayList;
import resource.ResourceOnLink;
import network.NodePair;
import network.VirtualLink;
import subgraph.LinearRoute;

public class Request {
	private NodePair nodepair;
	private double bandwidth = 0;
	private int slotnum = 0;
	private int w_slots = 0;
	private int sub_w_slots1 = 0;
	private int sub_w_slots2 = 0;
	private int p_slots = 0;
	private int requiredrate = 0;
	private double arrivaltime = 0;
	private double departtime = 0;
	private int requesttype = 0;
	private LinearRoute workingroute = null;
	private LinearRoute sub_workingroute1 = null;
	private LinearRoute sub_workingroute2 = null;
	private LinearRoute first_workingroute = null;
	private LinearRoute second_workingroute = null;
	private ArrayList<ResourceOnLink> rollist_w;
	private int working_index = 0;
	private int working_subindex1 = 0;
	private int working_subindex2 = 0;
	private int first_working_index = 0;
	private int second_working_index = 0;
	private boolean grooming;
	private ArrayList<VirtualLink> virtuallinklist = null;
	private ArrayList<LinearRoute> routelist = null;
	private ArrayList<Integer> wavelist = null;
	
	// private int spectrumassigment=1;//频谱分配方式；若为1，则为首次命中，否则为随机选择
	// private Route route;

	/*
	 * public Request(NodePair nodepair, double bandwidth, double time) {
	 * 
	 * this.setNodepair(nodepair);
	 * this.setRoute(nodepair.getLinearroutelist().get(0).getRoute_linklist());
	 * // this.setRoute(route); this.setBandwidth(bandwidth);
	 * this.setTime(time); this.rollist = new ArrayList<ResourceOnLink>(); }
	 */
	
	public Request(NodePair nodepair, int rate, double arrivaltime, double departtime, int requesttype) {
		this.setNodepair(nodepair);
		// nodepair.setSlotsnum(slots);
		this.requiredrate = rate;
		// this.setSlots(slots);
		this.setArrivalTime(arrivaltime);
		this.setDepartTime(departtime);
		this.setRequesttype(requesttype);
//		this.rollist_w = new ArrayList<ResourceOnLink>();
		this.workingroute = null;
		this.working_index = -1;
		this.virtuallinklist = new ArrayList<VirtualLink>();
		this.routelist = new ArrayList<LinearRoute>();
		this.wavelist = new ArrayList<Integer>();
//		this.grooming = false;
//		this.vllist = new ArrayList<VLOnIPLayer>();
	}

	public void workingspectrumrelease() {
		for (ResourceOnLink rol : this.rollist_w) {
			rol.resoucerelease();
		}
	}

	public void setNodepair(NodePair nodepair) {
		this.nodepair = nodepair;
	}

	public NodePair getNodepair() {
		return nodepair;
	}

	public void setRequiredrate(int rate) {
		this.requiredrate = rate;
	}

	public int getRequiredrate() {
		return requiredrate;
	}

	public void setBandwidth(double bandwith) {
		this.bandwidth = bandwith;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public double getArrivalTime() {
		return arrivaltime;
	}

	public void setArrivalTime(double timeGen) {
		this.arrivaltime = timeGen;
	}

	public double getDepartTime() {
		return departtime;
	}

	public void setDepartTime(double timeGen) {
		this.departtime = timeGen;
	}
	
	public LinearRoute getSub_workingroute1() {
		return sub_workingroute1;
	}

	public void setSub_workingroute1(LinearRoute sub_workingroute1) {
		this.sub_workingroute1 = sub_workingroute1;
	}

	public LinearRoute getSub_workingroute2() {
		return sub_workingroute2;
	}

	public void setSub_workingroute2(LinearRoute sub_workingroute2) {
		this.sub_workingroute2 = sub_workingroute2;
	}

	public void setworkingRoute(LinearRoute route) {
		this.workingroute = route;
	}

	public LinearRoute getworkingRoute() {
		return workingroute;
	}
	
	public LinearRoute getFirst_workingroute() {
		return first_workingroute;
	}

	public void setFirst_workingroute(LinearRoute first_workingroute) {
		this.first_workingroute = first_workingroute;
	}

	public LinearRoute getSecond_workingroute() {
		return second_workingroute;
	}

	public void setSecond_workingroute(LinearRoute second_workingroute) {
		this.second_workingroute = second_workingroute;
	}

	public void setRollist_w(ArrayList<ResourceOnLink> rollist) {
		this.rollist_w = rollist;
	}

	public void setRequesttype(int requesttype) {
		this.requesttype = requesttype;
	}

	public int getRequesttype() {
		return requesttype;
	}
	
	public int getSlotnum() {
		return slotnum;
	}

	public void setSlotnum(int slotnum) {
		this.slotnum = slotnum;
	}
	
	public int getSub_w_slots1() {
		return sub_w_slots1;
	}

	public void setSub_w_slots1(int sub_w_slots1) {
		this.sub_w_slots1 = sub_w_slots1;
	}

	public int getSub_w_slots2() {
		return sub_w_slots2;
	}

	public void setSub_w_slots2(int sub_w_slots2) {
		this.sub_w_slots2 = sub_w_slots2;
	}

	public void set_Wslots(int slots) {
		this.w_slots = slots;
	}

	public int getWslots() {
		return w_slots;
	}

	public void set_Pslots(int slots) {
		this.p_slots = slots;
	}

	public int getPslots() {
		return p_slots;
	}

	public ArrayList<ResourceOnLink> getRollist_w() {
		return rollist_w;
	}

	public void setworking_index(int index) {
		working_index = index;
	}

	public int getworking_index() {
		return working_index;
	}
	
	public int getWorking_subindex1() {
		return working_subindex1;
	}

	public void setWorking_subindex1(int working_subindex1) {
		this.working_subindex1 = working_subindex1;
	}

	public int getWorking_subindex2() {
		return working_subindex2;
	}

	public void setWorking_subindex2(int working_subindex2) {
		this.working_subindex2 = working_subindex2;
	}
	
	public int getFirst_working_index() {
		return first_working_index;
	}

	public void setFirst_working_index(int first_working_index) {
		this.first_working_index = first_working_index;
	}

	public int getSecond_working_index() {
		return second_working_index;
	}

	public void setSecond_working_index(int second_working_index) {
		this.second_working_index = second_working_index;
	}

	public void setGrooming(boolean grooming) {
		this.grooming = grooming;
	}

	public boolean getGrooming() {
		return this.grooming;
	}
	
	public ArrayList<VirtualLink> getVirtuallinklist() {
		return virtuallinklist;
	}

	public void setVirtuallinklist(ArrayList<VirtualLink> virtuallinklist) {
		this.virtuallinklist = virtuallinklist;
	}
	
	public ArrayList<LinearRoute> getRoutelist() {
		return routelist;
	}

	public void setRoutelist(ArrayList<LinearRoute> routelist) {
		this.routelist = routelist;
	}
	
	public ArrayList<Integer> getWavelist() {
		return wavelist;
	}

	public void setWavelist(ArrayList<Integer> wavelist) {
		this.wavelist = wavelist;
	}
	
}


