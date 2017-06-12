package MainFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import file.File_output;
import graphalgorithms.RouteSearching;
import graphalgorithms.SearchConstraint;
import network.Layer;
import network.Link;
import network.Node;
import network.NodePair;
import randomfunctions.randomfunction;
import subgraph.Cycle;
import subgraph.LinearRoute;

public class Main {

	public static void main(String[] args) {

		Layer myLayer = new Layer(null, 0, null);
		ArrayList<NodePair> nodepairlist = new ArrayList<NodePair>();
		ArrayList<Cycle> cyclelist = new ArrayList<Cycle>();
		HashMap<NodePair, LinearRoute> WorkRouteList = new HashMap<NodePair, LinearRoute>();
		HashMap<NodePair, Integer> nodepairDemand = new HashMap<NodePair, Integer>();
		ArrayList<NodePairProtect> NodeAndPro = new ArrayList<NodePairProtect>();
		ArrayList<Link> linklist = new ArrayList<Link>();

		String topologyName = "G:/Topology/5.csv";
		// String topologyName = "E:/ZYX/Topology/10.csv";
		myLayer.readTopology(topologyName);
		myLayer.generateNodepairs();

		String OutFileName = "F:\\programFile\\DualFailure\\5.dat";
		Main main = new Main();// start
		linklist = main.setSpan(myLayer, OutFileName);
//		nodepairlist = main.NodepairRadom(myLayer, OutFileName);
		File_output fo=new File_output();
		nodepairlist=fo.readDemand(myLayer, "D:/52.csv");
		main.NodePairAndDemandOut(nodepairlist, OutFileName);
		
		int AE = 0;// 表示不删除环
		cyclelist = main.CycleGenerate(myLayer, AE, OutFileName);// 如果不需要减少环
																	// 则将AE设置为0

		CycleProtectNodepair cpn = new CycleProtectNodepair();// 输出可以保护某一节点对的保护环集合
		NodeAndPro = cpn.cycleprotectnodepair(nodepairlist, cyclelist, myLayer, OutFileName);
		 main.FailureLinkOnCycle(NodeAndPro, linklist, OutFileName);
		 
		// parameter
		main.ConstantOut(OutFileName);
//		nodepairDemand = main.DemandRadom(nodepairlist, OutFileName);
		/*以下为附加==可以是p-cycle也通过读取业务而不自己产生业务
		 * 
		 */
		for(NodePair nodepair:nodepairlist){
			nodepairDemand.put(nodepair, nodepair.getSlotsnum());		
		}
		WorkRouteList = main.OnorStrad(myLayer, nodepairlist, cyclelist, nodepairDemand, OutFileName);
		main.LinkOnCycle(myLayer, cyclelist, OutFileName);

		main.rival(WorkRouteList, OutFileName);
	
		 
		// main.TwoDemandOnOneCycle(myLayer,WorkRouteList, cyclelist,OutFileName);//模型中已删除
		// main.CycleCross(NodeAndPro, OutFileName); //模型中已删除

		System.out.println("Finish");
	}
public void NodePairAndDemandOut(ArrayList<NodePair> nodepairlist,String OutFileName){
	File_output out = new File_output();
	out.filewrite(OutFileName, "set D:=");

	for (NodePair nodepair : nodepairlist) {
		out.filewrite(OutFileName, nodepair.getName());
	}
	out.filewrite(OutFileName, ";");
	
	out.filewrite(OutFileName, "param demand :=");

	for (NodePair nodepair : nodepairlist) {
		out.filewrite_without(OutFileName, nodepair.getName()+"    ");
		out.filewrite(OutFileName, nodepair.getSlotsnum());
	}
	out.filewrite(OutFileName, ";");
	
}
	
	public void FailureLinkOnCycle(ArrayList<NodePairProtect> NodeAndPro, ArrayList<Link> linklist,
			String OutFileName) {
		File_output out = new File_output();
		CycleOutput CO = new CycleOutput();
		NodelistCompare nc = new NodelistCompare();
		ArrayList<Node> nodelist = new ArrayList<Node>();

		for (NodePairProtect npp : NodeAndPro) {
			NodePair nodepair = npp.getnodepair();
			for (Link link : linklist) {
				nodelist.clear();
				Node nodeA = link.getNodeA();
				Node nodeB = link.getNodeB();
				nodelist.add(nodeA);
				nodelist.add(nodeB);
				out.filewrite(OutFileName,"set FailureLinkOnCycle [" + nodepair.getName() + "," + link.getName() + "]:=");
				for (Cycle cycle : npp.getcyclelist()) {
//					CO.cycleoutput(cycle, "F:\\programFile\\DualFailure\\test.dat");
					
					int cross = nc.nodelistcompare(cycle.getNodelist(), nodelist);
					if (cross == 1) {
						CO.cycleoutput(cycle, OutFileName);
						out.filewrite(OutFileName, "   ");
					}
				}
			out.filewrite(OutFileName, ";");
			}
		}

	}

	public void CycleCross(ArrayList<NodePairProtect> NodeAndPro, String OutFileName) {
		File_output out = new File_output();
		CycleOutput CO = new CycleOutput();
		NodelistCompare nc = new NodelistCompare();
		out.filewrite(OutFileName, "param CycleCross :=");

		for (NodePairProtect nodepairprotect : NodeAndPro) {

			for (int n = 0; n < nodepairprotect.getcyclelist().size(); n++) {
				Cycle cycle1 = nodepairprotect.getcyclelist().get(n);

				for (int m = 0; m < nodepairprotect.getcyclelist().size(); m++) {
					Cycle cycle2 = nodepairprotect.getcyclelist().get(m);
					if (m == n)
						continue;
					out.filewrite_without(OutFileName, nodepairprotect.getnodepair().getName() + "     ");
					CO.cycleoutput(cycle1, OutFileName);
					out.filewrite_without(OutFileName, "     ");
					CO.cycleoutput(cycle2, OutFileName);
					out.filewrite_without(OutFileName, "     ");
					int cyclecross = nc.nodelistcompare(cycle1.getNodelist(), cycle2.getNodelist());
					out.filewrite(OutFileName, cyclecross);
				}

			}
		}
		out.filewrite(OutFileName, ";");

	}

	public void TwoDemandOnOneCycle(Layer mylayer, HashMap<NodePair, LinearRoute> WorkRouteList,
			ArrayList<Cycle> cyclelist, String OutFileName) {
		File_output out = new File_output();
		NodelistCompare nc = new NodelistCompare();
		CycleOutput Cycleout = new CycleOutput();
		int share = 0;

		out.filewrite(OutFileName, "param noshare :=");
		for (NodePair nodePair1 : WorkRouteList.keySet()) {
			Node src1 = nodePair1.getSrcNode();
			Node des1 = nodePair1.getDesNode();

			for (NodePair nodePair2 : WorkRouteList.keySet()) {
				Node src2 = nodePair2.getSrcNode();
				Node des2 = nodePair2.getDesNode();

				if (nodePair1.getName().equals(nodePair2.getName()))
					continue;

				LinearRoute workroute1 = WorkRouteList.get(nodePair1);
				LinearRoute workroute2 = WorkRouteList.get(nodePair2);
				int cross = nc.nodelistcompare(workroute1.getNodelist(), workroute2.getNodelist());

				for (Cycle cycle : cyclelist) {
					share = 0;
					out.filewrite_without(OutFileName, nodePair1.getName() + "   ");
					out.filewrite_without(OutFileName, nodePair2.getName() + "   ");
					Cycleout.cycleoutput(cycle, OutFileName);
					out.filewrite_without(OutFileName, "    ");
					// 判断
					int nodeandcyclecross = nc.nodelistcompare(workroute1.getNodelist(), cycle.getNodelist());
					if (nodeandcyclecross == 1) {
						out.filewrite(OutFileName, share);
						continue;
					}
					int nodeandcyclecross2 = nc.nodelistcompare(workroute2.getNodelist(), cycle.getNodelist());
					if (nodeandcyclecross2 == 1) {
						out.filewrite(OutFileName, share);
						continue;
					}

					if (cross == 1) {// 两个节点对有共同容量，不能被一个环保护
						out.filewrite(OutFileName, share);
						continue;
					}
					if (!(cycle.getNodelist().contains(src1) && cycle.getNodelist().contains(des1)
							&& cycle.getNodelist().contains(src2) && cycle.getNodelist().contains(des2))) {
						// 环上不包含这四个节点时
						out.filewrite(OutFileName, share);
						continue;
					}
					int indexofsrc1 = cycle.getNodelist().indexOf(src1);
					int indexofdes1 = cycle.getNodelist().indexOf(des1);
					int indexofsrc2 = cycle.getNodelist().indexOf(src2);
					int indexofdes2 = cycle.getNodelist().indexOf(des2);
					if (Math.abs(indexofsrc1 - indexofdes1) == 1 || Math.abs(indexofsrc2 - indexofdes2) == 1) {
						out.filewrite(OutFileName, share);
						continue;
					}
					if (indexofsrc1 == indexofsrc2 || indexofsrc1 == indexofdes2 || indexofdes1 == indexofsrc2
							|| indexofdes1 == indexofdes2) {
						// 两个节点对有一个共同节点 那么该环可以保护
						out.filewrite(OutFileName, share);
						continue;
					}
					// indexofsrc1<indexofsrc2<indexofdes1
					if (indexofsrc1 < indexofdes1 && indexofsrc1 < indexofsrc2 && indexofsrc2 < indexofdes1
							&& (indexofdes2 < indexofsrc1 || indexofdes2 > indexofdes1)) {
						// 一个节点对的其中一个节点在另一个节点对之间 环上容量需要累加
						share = 1;
						out.filewrite(OutFileName, share);
						continue;
					}
					// indexofsrc1<indexofdes2<indexofdes1
					else if (indexofsrc1 < indexofdes1 && indexofdes2 > indexofsrc1 && indexofdes2 < indexofdes1
							&& (indexofsrc2 < indexofsrc1 || indexofsrc2 > indexofdes1)) {
						// 一个节点对的其中一个节点在另一个节点对之间 环上容量需要累加
						share = 1;
						out.filewrite(OutFileName, share);
						continue;
					}
					// indexofsrc1>indexofsrc2>indexofdes1
					else if (indexofsrc1 > indexofdes1 && indexofsrc2 < indexofsrc1 && indexofsrc2 > indexofdes1
							&& (indexofdes2 > indexofsrc1 || indexofdes2 < indexofdes1)) {
						share = 1;
						out.filewrite(OutFileName, share);
						continue;
					}
					// indexofsrc1>indexofdes2>indexofdes1
					else if (indexofsrc1 > indexofdes1 && indexofdes2 < indexofsrc1 && indexofdes2 > indexofdes1
							&& (indexofsrc2 > indexofsrc1 || indexofsrc2 < indexofdes1)) {
						share = 1;
						out.filewrite(OutFileName, share);
						continue;
					}
					out.filewrite(OutFileName, share);

				}
			}
		}

		out.filewrite(OutFileName, ";");
	}

	public void rival(HashMap<NodePair, LinearRoute> WorkRouteList, String OutFileName) {
		File_output out = new File_output();
		NodelistCompare nc = new NodelistCompare();

		Set<NodePair> nodepairlist = WorkRouteList.keySet();
		out.filewrite(OutFileName, "param rival :=");

		for (NodePair nodePair : nodepairlist) {

			for (NodePair ComnodePair : nodepairlist) {
				if (nodePair.getName().equals(ComnodePair.getName()))
					continue;
				out.filewrite_without(OutFileName, nodePair.getName() + "    ");
				out.filewrite_without(OutFileName, ComnodePair.getName() + "    ");
				LinearRoute workroute1 = WorkRouteList.get(nodePair);
				LinearRoute workroute2 = WorkRouteList.get(ComnodePair);
				int cross = nc.nodelistcompare(workroute1.getNodelist(), workroute2.getNodelist());
				out.filewrite(OutFileName, cross);
			}
		}
		out.filewrite(OutFileName, ";");
	}

	public void LinkOnCycle(Layer mylayer, ArrayList<Cycle> cyclelist, String OutFileName) {
		CycleOutput Cycleout = new CycleOutput();
		NodelistCompare nc = new NodelistCompare();
		ArrayList<Node> nodelist = new ArrayList<Node>();

		File_output out = new File_output();
		out.filewrite(OutFileName, "param LinkOnCycle :=");

		HashMap<String, Link> map = mylayer.getLinklist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Link link = (Link) (map.get(iter.next()));
			nodelist.clear();
			nodelist.add(link.getNodeA());
			nodelist.add(link.getNodeB());
			for (Cycle cycle : cyclelist) {
				out.filewrite_without(OutFileName, link.getName() + "     ");
				Cycleout.cycleoutput(cycle, OutFileName);
				out.filewrite_without(OutFileName, "     ");
				int cross = nc.nodelistcompare(nodelist, cycle.getNodelist());
				out.filewrite(OutFileName, cross);
			}
		}
		out.filewrite(OutFileName, ";");
	}

	public HashMap<NodePair, LinearRoute> OnorStrad(Layer mylayer, ArrayList<NodePair> nodepairlist,
			ArrayList<Cycle> cyclelist, HashMap<NodePair, Integer> nodepairDemand, String OutFileName) {

		HashMap<NodePair, LinearRoute> WorkRouteList = new HashMap<NodePair, LinearRoute>();
		RouteSearching rs = new RouteSearching();
		SearchConstraint sc = new SearchConstraint();
		String workroutefilename = "F:\\programFile\\DualFailure\\WorkRoute.dat";

		File_output out = new File_output();
		out.filewrite(OutFileName, "param relation :=");
		int WorkCapacity = 0;
		for (NodePair nodePair : nodepairlist) {
			Node srcnode = nodePair.getSrcNode();
			Node desnode = nodePair.getDesNode();
			LinearRoute WorkRoute = new LinearRoute(null, 0, null);
			rs.Dijkstra(srcnode, desnode, mylayer, WorkRoute, sc);
			int demand = nodepairDemand.get(nodePair);
			WorkCapacity = WorkCapacity + demand * WorkRoute.getLinklist().size();

			WorkRouteList.put(nodePair, WorkRoute);
			out.filewrite_without(workroutefilename, nodePair.getName() + "     ");
			out.filewrite_without(workroutefilename, "     ");
			WorkRoute.OutputRoute_node(WorkRoute, workroutefilename);
			out.filewrite(workroutefilename, "     ");

			for (Cycle cycle : cyclelist) {
				out.filewrite_without(OutFileName, nodePair.getName() + "     ");
				int relation = 0;
				CycleOutput Cycleout = new CycleOutput();
				Cycleout.cycleoutput(cycle, OutFileName);
				out.filewrite_without(OutFileName, "     ");
				if (!(cycle.getNodelist().contains(srcnode) && cycle.getNodelist().contains(desnode))) {
					out.filewrite(OutFileName, relation);
					continue;
				}

				NodelistCompare nc = new NodelistCompare();
				int cross = nc.nodelistcompare(WorkRoute.getNodelist(), cycle.getNodelist());
				if (cross == 1)
					relation = 0; // oncycle时为1，此时不考虑oncycle情况，故为0
				else if (cross == 0)
					relation = 1; // strad时应该为2，此时只考虑straddle情况，故表示可以保护，为1
				out.filewrite(OutFileName, relation);
			}
		}
		out.filewrite(workroutefilename, WorkCapacity);
		out.filewrite(OutFileName, ";");
		return WorkRouteList;
	}

	public HashMap<NodePair, Integer> DemandRadom(ArrayList<NodePair> nodepairlist, String OutFileName) {
		HashMap<NodePair, Integer> nodepairDemand = new HashMap<NodePair, Integer>();
		randomfunction radom = new randomfunction();
		File_output out = new File_output();
		out.filewrite(OutFileName, "param demand :=");

		for (NodePair nodePair : nodepairlist) {
			out.filewrite_without(OutFileName, nodePair.getName() + "    ");
			int demand = radom.Num_random(1, 99)[0] + 1;
			nodepairDemand.put(nodePair, demand);
			out.filewrite(OutFileName, demand);
		}
		out.filewrite(OutFileName, ";");
		return nodepairDemand;

	}

	public void ConstantOut(String OutFileName) {
		File_output out = new File_output();
		out.filewrite(OutFileName, "param LargeConstant:=");
		out.filewrite(OutFileName, 100000);
		out.filewrite(OutFileName, ";");
		out.filewrite(OutFileName, "param MiniConstant:=");
		out.filewrite(OutFileName, "0.0001");
		out.filewrite(OutFileName, ";");
	}

	public ArrayList<Cycle> CycleGenerate(Layer myLayer, int AE, String OutFileName) {

		RouteSearching shortestpath = new RouteSearching();
		ArrayList<Cycle> cyclelist = new ArrayList<Cycle>();
		ArrayList<Cycle> newcyclelist = new ArrayList<Cycle>();
		SearchConstraint constraint = new SearchConstraint(100000);
		File_output out = new File_output();
		out.filewrite(OutFileName, "set P:=");

		HashMap<String, Node> map = myLayer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			shortestpath.findAllCycle(node, myLayer, constraint, cyclelist);

		}
		// 环的效率计算
		HashMap<String, Double> AeList = new HashMap<String, Double>();
		AEofCycle aoc = new AEofCycle();
		AeList = aoc.aeofcycle(cyclelist, myLayer);

		for (int b = 0; b < cyclelist.size(); b++) {
			Cycle nowcycle = cyclelist.get(b);
			double nowae = AeList.get(nowcycle.toString());
			if (nowae > AE) {
				newcyclelist.add(nowcycle);
			}
		}

		CycleOutput Cycleout = new CycleOutput();
		Cycleout.cycleoutput(newcyclelist, OutFileName);
		out.filewrite(OutFileName, ";");
		return newcyclelist;
	}

	public ArrayList<NodePair> NodepairRadom(Layer mylayer, String OutFileName) {
		ArrayList<NodePair> nodepairlist = new ArrayList<NodePair>();
		HashMap<Integer, NodePair> serial = new HashMap<Integer, NodePair>();
		int ser = 0;
		int numOfDemand = 5;
		int[] serNumGenerate = new int[numOfDemand];
		HashMap<String, NodePair> map = mylayer.getNodepairlist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			NodePair nodepair = (NodePair) (map.get(iter.next()));
			serial.put(ser, nodepair);
			ser++;
		}
		File_output out = new File_output();
		out.filewrite(OutFileName, "set D:=");

		randomfunction random = new randomfunction();
		serNumGenerate = random.Dif_random(numOfDemand, mylayer.getNodepairlist().size());
		for (int n : serNumGenerate) {
			out.filewrite(OutFileName, serial.get(n).getName());
			nodepairlist.add(serial.get(n));
		}
		out.filewrite(OutFileName, ";");
		return nodepairlist;
	}

	public ArrayList<Link> setSpan(Layer mylayer, String OutFileName) {
		ArrayList<Link> linklist = new ArrayList<Link>();
		File_output out = new File_output();
		out.filewrite(OutFileName, "set S:=");
		HashMap<String, Link> map = mylayer.getLinklist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Link link = (Link) (map.get(iter.next()));
			linklist.add(link);
			out.filewrite(OutFileName, link.getName());
		}
		out.filewrite(OutFileName, ";");
		return linklist;
	}

}
