package graphalgorithms;

import java.util.ArrayList;
import general.Constant;
import general.SlotPlane;
import network.Node;
import network.Link;
import network.Layer;
import request.Request;
import subgraph.LinearRoute;
import graphalgorithms.RouteSearching;
import graphalgorithms.SearchConstraint;

public class SlotPlaneForUDWDM {
	private ArrayList<SlotPlane> SlotPlanelist = null;
	private ArrayList<SlotPlane> SlotSubPlanelist = null;
	private ArrayList<SlotPlane> SlotSubPlanelist1 = null;
	private ArrayList<SlotPlane> SlotSubPlanelist2 = null;
	private ArrayList<SlotPlane> SlotSubPlanelist3 = null;
	private ArrayList<SlotPlane> SlotSubPlanelist4 = null;
	
	public SlotPlaneForUDWDM() {
		this.SlotPlanelist = new ArrayList<SlotPlane>();
		this.SlotSubPlanelist = new ArrayList<SlotPlane>();
		this.SlotSubPlanelist1 = new ArrayList<SlotPlane>();
		this.SlotSubPlanelist2 = new ArrayList<SlotPlane>();
		this.SlotSubPlanelist3 = new ArrayList<SlotPlane>();
		this.SlotSubPlanelist4 = new ArrayList<SlotPlane>();
	}
	
	public SlotPlaneForUDWDM(Layer layer, int requiredslot) {
		this.SlotPlanelist = new ArrayList<SlotPlane>();
		InitSlotPlanelist(layer, requiredslot);
	}
	
	public void InitSlotPlanelist(Layer layer, int requiredslot) {
		SlotPlanelist.clear();
		if (requiredslot > 0) {
			for (int i = 0; i <= Constant.F - requiredslot; i++) {
				SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
				SlotPlanelist.add(currentSlotPlane);
			}
		}
	}
	
	public void InitSlotPlanelist_common(Layer layer, int requiredslot) {
		SlotPlanelist.clear();
		if (requiredslot > 0) {
			for (int i = 0; i <= Constant.F - requiredslot; i++) {
				if (i / Constant.num == (i + requiredslot - 1) / Constant.num) {
					SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
					SlotPlanelist.add(currentSlotPlane);
				}
			}
		}
	}
	
	public void InitSlotPlanelist_DWDM(Layer layer, int requiredslot) {
		SlotPlanelist.clear();
		if (requiredslot > 0) {
			for (int i = 0; i <= Constant.S - requiredslot; i++) {
				SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
				SlotPlanelist.add(currentSlotPlane);
			}
		}
	}
	
	public void InitSlotPlanelist(Layer layer, int startindex, int endindex, int requiredslot) {
		SlotSubPlanelist.clear();
		if (requiredslot > 0) {
			if (startindex / Constant.num == 0) {
				for (int i = 10; i <= 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 79) {
				for (int i = 780; i <= 790 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist.add(currentSlotPlane);
					}
				}
			} else {
				int index = startindex / Constant.num * 10;
				for (int i = index - 10; i <= index - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist.add(currentSlotPlane);
					}
				}
				
				for (int i = index + 10; i <= index + 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist.add(currentSlotPlane);
					}
				}
			}
		}
	}
	
	public void InitSlotPlanelist_super(Layer layer, int startindex, int endindex, int requiredslot) {
		SlotSubPlanelist1.clear();
		SlotSubPlanelist2.clear();
		SlotSubPlanelist3.clear();
		SlotSubPlanelist4.clear();
		
		if (requiredslot > 0) {
			if (startindex / Constant.num == 0) {
				for (int i = 10; i <= 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 20; i <= 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 1) {
				for (int i = 0; i <= 10 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 20; i <= 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = 30; i <= 40 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 78) {
				for (int i = 760; i <= 770 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 770; i <= 780 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = 790; i <= 800 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 79) {
				for (int i = 770; i <= 780 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 780; i <= 790 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
			} else {
				int index = startindex / Constant.num * 10;
				for (int i = index - 20; i <= index - 10 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = index - 10; i <= index - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = index + 10; i <= index + 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
				
				for (int i = index + 20; i <= index + 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist4.add(currentSlotPlane);
					}
				}
			}
		}
	}
	
	public void InitSlotPlanelist_fixsuper(Layer layer, int startindex, int endindex, int requiredslot) {
		SlotSubPlanelist1.clear();
		SlotSubPlanelist2.clear();
		SlotSubPlanelist3.clear();
		SlotSubPlanelist4.clear();
		
		if (requiredslot > 0) {
			if (startindex / Constant.num == 0) {
				for (int i = 10; i <= 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 20; i <= 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 1) {
				for (int i = 0; i <= 10 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 20; i <= 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = 30; i <= 40 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 78) {
				for (int i = 760; i <= 770 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 770; i <= 780 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = 790; i <= 800 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
			} else if (startindex / Constant.num == 79) {
				for (int i = 770; i <= 780 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = 780; i <= 790 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
			} else {
				int index = startindex / Constant.num * 10;
				for (int i = index - 20; i <= index - 10 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 20 || i > endindex - 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
					}
				}
				
				for (int i = index - 10; i <= index - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex - 10 || i > endindex - 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist2.add(currentSlotPlane);
					}
				}
				
				for (int i = index + 10; i <= index + 20 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 10 || i > endindex + 10) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist3.add(currentSlotPlane);
					}
				}
				
				for (int i = index + 20; i <= index + 30 - requiredslot; i++) {
					if (i + requiredslot - 1 < startindex + 20 || i > endindex + 20) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist4.add(currentSlotPlane);
					}
				}
			}
		}
	}
	
	public void InitSlotPlanelist_flexsuper(Layer layer, int startindex, int endindex, int requiredslot) {
		SlotSubPlanelist1.clear();
		SlotSubPlanelist2.clear();
		SlotSubPlanelist3.clear();
		SlotSubPlanelist4.clear();
		
		if (requiredslot > 0) {
			for (int i = 0; i <= Constant.F - requiredslot; i++) {
				if (i / Constant.num == (i + requiredslot - 1) / Constant.num) {
					if (((i + requiredslot - 1) % Constant.num < startindex % Constant.num 
							|| i % Constant.num > endindex % Constant.num) && i / Constant.num != startindex / Constant.num) {
						SlotPlane currentSlotPlane = new SlotPlane(" ", i, " ", i, i + requiredslot - 1);
						SlotSubPlanelist1.add(currentSlotPlane);
						SlotSubPlanelist2.add(currentSlotPlane);
						SlotSubPlanelist3.add(currentSlotPlane);
						SlotSubPlanelist4.add(currentSlotPlane);
					}
				}
			}
		}
	}
	
	public ArrayList<SlotPlane> getSlotPlanelist() {
		return SlotPlanelist;
	}

	public void setSlotPlanelist(ArrayList<SlotPlane> slotPlanelist) {
		SlotPlanelist = slotPlanelist;
	}
	
	public ArrayList<SlotPlane> getSlotSubPlanelist() {
		return SlotSubPlanelist;
	}

	public void setSlotSubPlanelist(ArrayList<SlotPlane> slotSubPlanelist) {
		SlotSubPlanelist = slotSubPlanelist;
	}
	
	public ArrayList<SlotPlane> getSlotSubPlanelist1() {
		return SlotSubPlanelist1;
	}

	public void setSlotSubPlanelist1(ArrayList<SlotPlane> slotSubPlanelist1) {
		SlotSubPlanelist1 = slotSubPlanelist1;
	}

	public ArrayList<SlotPlane> getSlotSubPlanelist2() {
		return SlotSubPlanelist2;
	}

	public void setSlotSubPlanelist2(ArrayList<SlotPlane> slotSubPlanelist2) {
		SlotSubPlanelist2 = slotSubPlanelist2;
	}

	public ArrayList<SlotPlane> getSlotSubPlanelist3() {
		return SlotSubPlanelist3;
	}

	public void setSlotSubPlanelist3(ArrayList<SlotPlane> slotSubPlanelist3) {
		SlotSubPlanelist3 = slotSubPlanelist3;
	}

	public ArrayList<SlotPlane> getSlotSubPlanelist4() {
		return SlotSubPlanelist4;
	}

	public void setSlotSubPlanelist4(ArrayList<SlotPlane> slotSubPlanelist4) {
		SlotSubPlanelist4 = slotSubPlanelist4;
	}
	
	public int AssginmentForWorkingPathBasedOnSlotPlane_LC(Layer layer, Request currentrequest) {
		// System.out.println("the size of SlotPlane is:"+this.SlotPlanelist.size());
		// System.out.println("trans_dis="+trans_dis);
		/*
		 * for(Link link:layer.getLinklist1()) link.setCost(link.getLength());
		 */
		int flag = 0;
		for (SlotPlane currentslotplane : this.SlotPlanelist) {
			int startindex = currentslotplane.getstartindex();
			int endindex = currentslotplane.getendindex();
			for (Link link : layer.getLinklist1()) {
				for (int i = startindex; i <= endindex; i++) {
					if (link.getSlotsarray().get(i).getoccupiedreqlist().size() != 0) {
						if (!currentslotplane.getConstraintlinklist().contains(link)) {
							currentslotplane.getConstraintlinklist().add(link);
							break;
						}
					}
				}
			}
			
			RouteSearching workingpath = new RouteSearching();
			LinearRoute newroute = new LinearRoute("", 0, "");
			Node srcnode = currentrequest.getNodepair().getSrcNode();
			Node desnode = currentrequest.getNodepair().getDesNode();
			SearchConstraint workingpathconstraint = new SearchConstraint();
			workingpathconstraint.getExcludedLinklist().addAll(currentslotplane.getConstraintlinklist());
			workingpath.Dijkstras(srcnode, desnode, layer, newroute, workingpathconstraint);
			if (newroute.getLinklist().size() == 0) {
				continue;
			} else {
				if (currentrequest.getworkingRoute() == null) {
					currentrequest.setworking_index(startindex);
					currentrequest.setworkingRoute(newroute);
				} else {
					if (newroute.getLinklist().size() < currentrequest.getworkingRoute().getLinklist().size()) {
						currentrequest.setworking_index(startindex);
						currentrequest.setworkingRoute(newroute);
					}
				}
			}
		}

		// 先暂时分配资源，防止该业务保护路径使用工作路径资源，若业务建立不成功，会在主函数中对所占用的工作路径资源进行释放
		if (currentrequest.getworkingRoute() != null) {
			/*
			 * System.out.println("the working route is:"); for(Link
			 * link:currentrequest.getworkingRoute().getLinklist())
			 * System.out.print(link.getName()+'\t'); System.out.println();
			 * System.out.println("index of working route is:"
			 * +currentrequest.getworking_index());
			 */
			flag = 1;
		}
		return flag;
	}

	// 动态路由,首次命中，找到满足条件的路由后则不再搜索路径
	public int AssginmentForWorkingPathBasedOnSlotPlane_FF(Layer layer, Request currentrequest, int hoplimit) {
		// System.out.println("the size of SlotPlane is:"+this.SlotPlanelist.size());
		int flag = 0;
		int limitedhop = currentrequest.getNodepair().getleasthop() + hoplimit;// 工作路径所经过的跳数限制在最短路径跳数加2;
		// System.out.println("the limit hop of working path is:"+limitedhop);
		l: for (SlotPlane currentslotplane : this.SlotPlanelist) {
			int startindex = currentslotplane.getstartindex();
			int endindex = currentslotplane.getendindex();
			for (Link link : layer.getLinklist1()) {
				for (int i = startindex; i <= endindex; i++) {
					if (link.getSlotsarray().get(i).getoccupiedreqlist().size() != 0) {
						if (!currentslotplane.getConstraintlinklist().contains(link)) {
							currentslotplane.getConstraintlinklist().add(link);
							break;
						}
					}
				}
			}
			
			RouteSearching workingpath = new RouteSearching();
			LinearRoute newroute = new LinearRoute("", 0, "");
			Node srcnode = currentrequest.getNodepair().getSrcNode();
			Node desnode = currentrequest.getNodepair().getDesNode();
			SearchConstraint workingpathconstraint = new SearchConstraint();
			workingpathconstraint.getExcludedLinklist().addAll(currentslotplane.getConstraintlinklist());
			workingpath.Dijkstras(srcnode, desnode, layer, newroute, workingpathconstraint);
			if (newroute.getLinklist().size() == 0 || newroute.getLinklist().size() > limitedhop) {
				continue;
			} else {
				currentrequest.setworking_index(startindex);
				currentrequest.setworkingRoute(newroute);
				break l;
			}
		}
		
		// 先暂时分配资源，防止该业务保护路径使用工作路径资源，若业务建立不成功，会在主函数中对所占用的工作路径资源进行释放
		if (currentrequest.getworkingRoute() != null) {
			flag = 1;
		}
		return flag;
	}
	
	public boolean AssginmentForWorkingPathBasedOnSlotPlane2_FF(Layer layer, Request currentrequest, int hoplimit) {
		// System.out.println("the size of SlotPlane is:"+this.SlotPlanelist.size());
		boolean flag = false;
		int limitedhop = currentrequest.getNodepair().getleasthop() + hoplimit;// 工作路径所经过的跳数限制在最短路径跳数加2;
		// System.out.println("the limit hop of working path is:"+limitedhop);
		l: for (SlotPlane currentslotplane : this.SlotPlanelist) {
			int startindex = currentslotplane.getstartindex();
			int endindex = currentslotplane.getendindex();
			for (Link link : layer.getLinklist1()) {
				for (int i = startindex; i <= endindex; i++) {
					if (link.getSlotsarray().get(i).getoccupiedreqlist().size() != 0) {
						if (!currentslotplane.getConstraintlinklist().contains(link)) {
							currentslotplane.getConstraintlinklist().add(link);
							break;
						}
					}
				}
			}
			
			RouteSearching workingpath = new RouteSearching();
			LinearRoute newroute = new LinearRoute("", 0, "");
			Node srcnode = currentrequest.getNodepair().getSrcNode();
			Node desnode = currentrequest.getNodepair().getDesNode();
			SearchConstraint workingpathconstraint = new SearchConstraint();
			workingpathconstraint.getExcludedLinklist().addAll(currentslotplane.getConstraintlinklist());
			workingpath.Dijkstras(srcnode, desnode, layer, newroute, workingpathconstraint);
			if (newroute.getLinklist().size() == 0 || newroute.getLinklist().size() > limitedhop) {
				continue;
			} else {
				currentrequest.setworking_index(startindex);
				currentrequest.setworkingRoute(newroute);
				break l;
			}
		}
		
		// 先暂时分配资源，防止该业务保护路径使用工作路径资源，若业务建立不成功，会在主函数中对所占用的工作路径资源进行释放
		if (currentrequest.getworkingRoute() != null) {
			flag = true;
		}
		return flag;
	}

	// 固定路由，搜索工作路径 First-Fit
	public int AssginmentForWorkingPathBasedOnSlotPlane_StaticRoute(Layer layer, Request currentrequest, double trans_dis) {
		int flag_workingpath = 1;
		LinearRoute workingpath = currentrequest.getNodepair().getLinearroutelist().get(0);
		double wor_dis = 0;
		for (Link link : workingpath.getLinklist()) {
			wor_dis = wor_dis + link.getLength();
		}
		
		if (wor_dis < trans_dis) {
			l1: for (SlotPlane currentslotplane : this.SlotPlanelist) {
				int flag = 1;
				int startindex = currentslotplane.getstartindex();
				int endindex = currentslotplane.getendindex();
				l2: for (Link link : workingpath.getLinklist()) {
					for (int i = startindex; i <= endindex; i++) {
						if (link.getSlotsarray().get(i).getoccupiedreqlist().size() != 0) {
							flag = 0;
							break l2;
						}
					}
				}
				
				if (flag == 1) {
					currentrequest.setworkingRoute(workingpath);
					currentrequest.setworking_index(startindex);
					break l1;
				}
			}
		}
		
		if (currentrequest.getworkingRoute() == null) {
			flag_workingpath = 0;
		}
		return flag_workingpath;
	}
	
}



