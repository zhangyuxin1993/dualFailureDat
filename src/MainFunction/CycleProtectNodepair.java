package MainFunction;

import java.util.ArrayList;
import file.File_output;
import graphalgorithms.RouteSearching;
import network.Layer;
import network.Node;
import network.NodePair;
import subgraph.Cycle;
import subgraph.LinearRoute;

public class CycleProtectNodepair {
	public ArrayList<NodePairProtect> cycleprotectnodepair(ArrayList<NodePair> nodepairlist,ArrayList<Cycle> cyclelist,Layer mylayer,String OutFileName){
		NodelistCompare nc=new NodelistCompare();
		ArrayList<NodePairProtect> NodeAndPro= new ArrayList<NodePairProtect>();
		boolean protect=false;	
		ArrayList<Cycle> newcyclelist=new ArrayList<Cycle>();
		for(NodePair nodepair:nodepairlist){
			RouteSearching rs=new RouteSearching();
			LinearRoute newRoute=new LinearRoute(null, 0, null);
			rs.Dijkstra(nodepair.getSrcNode(), nodepair.getDesNode(), mylayer, newRoute, null);
//			System.out.println(nodepair.getName());//debug
			protect=false;
			
			for(Cycle cycle:cyclelist){		
//				CycleOutput  outcycle=new CycleOutput();//debug
//				outcycle.cycleoutput(cycle, "E:\\programFile\\p+mixed\\Cycle.dat");//debug
				//判断环上是否包含这对节点对
				if(cycle.getNodelist().contains(nodepair.getSrcNode())&&cycle.getNodelist().contains(nodepair.getDesNode())){
					Node srcnode=nodepair.getSrcNode();
					Node desnode=nodepair.getDesNode();
					if(Math.abs(cycle.getNodelist().indexOf(srcnode)-cycle.getNodelist().indexOf(desnode))==1) continue;
					if(cycle.getNodelist().indexOf(srcnode)==0&&cycle.getNodelist().indexOf(desnode)==cycle.getNodelist().size()-2)
						continue;
					if(cycle.getNodelist().indexOf(desnode)==0&&cycle.getNodelist().indexOf(srcnode)==cycle.getNodelist().size()-2)
						continue;
		 
					 int  cross=nc.nodelistcompare(newRoute.getNodelist(), cycle.getNodelist());
					 if(cross==1) continue;
						protect=true;
						newcyclelist.add(cycle);				
				}		
			}
//			System.out.println(newcyclelist.size());//debug
			if(protect){
				NodePairProtect mypair=new NodePairProtect();
				mypair.setnodepair(nodepair);
				mypair.setcyclelist(newcyclelist);
				//DEBUG
//				System.out.println(newcyclelist.size());
//				CycleOutput CO=new CycleOutput();
//				CO.cycleoutput(newcyclelist, "E:\\programFile\\p+mixed\\TEST.dat");
				newcyclelist.clear();
				mypair.setworkroute(newRoute);
				NodeAndPro.add(mypair);
			}			
			}
		
		File_output out = new File_output();
		CycleOutput CO=new CycleOutput();
		
		for(NodePairProtect nodepairprotect:NodeAndPro){
			out.filewrite(OutFileName, "set ProtectCycleForDemand["+nodepairprotect.getnodepair().getName()+"]:="   );
			CO.cycleoutput(nodepairprotect.getcyclelist(), OutFileName);
			out.filewrite(OutFileName, ";"   );
		}
		
	    	return NodeAndPro;
	}

}
