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
		HashMap<NodePair, Integer> nodepairDemand=new HashMap<NodePair, Integer>();

		String topologyName = "G:/Topology/10.csv";
		myLayer.readTopology(topologyName);
		myLayer.generateNodepairs();

		String OutFileName = "F:\\programFile\\10node.dat";
		Main main = new Main();
		main.setSpan(myLayer, OutFileName);
		nodepairlist = main.NodepairRadom(myLayer, OutFileName);
		int AE = 0;
		cyclelist = main.CycleGenerate(myLayer, AE, OutFileName);// 如果不需要减少环
																	// 则将AE设置为0

		// parameter
		main.ConstantOut(OutFileName);
		nodepairDemand=main.DemandRadom(nodepairlist, OutFileName);
		WorkRouteList = main.OnorStrad(myLayer, nodepairlist, cyclelist, nodepairDemand,OutFileName);
		main.LinkOnCycle(myLayer, cyclelist,OutFileName);

		main.rival(WorkRouteList,OutFileName);

		System.out.println("Finish");
	}

	public void rival(HashMap<NodePair, LinearRoute> WorkRouteList,String OutFileName) {
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
			ArrayList<Cycle> cyclelist, HashMap<NodePair, Integer> nodepairDemand,String OutFileName) {
		
		HashMap<NodePair, LinearRoute> WorkRouteList = new HashMap<NodePair, LinearRoute>();
		RouteSearching rs = new RouteSearching();
		SearchConstraint sc = new SearchConstraint();
		String workroutefilename = "F:\\programFile\\WorkRoute.dat";

		File_output out = new File_output();
		out.filewrite(OutFileName, "param relation :=");
		int WorkCapacity=0;
		for (NodePair nodePair : nodepairlist) {
			Node srcnode = nodePair.getSrcNode();
			Node desnode = nodePair.getDesNode();
			LinearRoute WorkRoute = new LinearRoute(null, 0, null);
			rs.Dijkstra(srcnode, desnode, mylayer, WorkRoute, sc);
			int demand=nodepairDemand.get(nodePair);
			WorkCapacity=WorkCapacity+demand*WorkRoute.getLinklist().size();
			
			WorkRouteList.put(nodePair, WorkRoute);
			out.filewrite_without(workroutefilename, nodePair.getName() + "     ");
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
					relation = 1;
				else if (cross == 0)
					relation = 2;
				out.filewrite(OutFileName, relation);
			}
		}
		out.filewrite(workroutefilename, WorkCapacity);
		out.filewrite(OutFileName, ";");
		return WorkRouteList;
	}

	public HashMap<NodePair, Integer> DemandRadom(ArrayList<NodePair> nodepairlist, String OutFileName) {
		HashMap<NodePair, Integer> nodepairDemand=new HashMap<NodePair, Integer>();
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
		out.filewrite(OutFileName, "0.00001");
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

	public void setSpan(Layer mylayer, String OutFileName) {
		File_output out = new File_output();
		out.filewrite(OutFileName, "set S:=");
		HashMap<String, Link> map = mylayer.getLinklist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Link link = (Link) (map.get(iter.next()));
			out.filewrite(OutFileName, link.getName());
		}
		out.filewrite(OutFileName, ";");
	}

}
