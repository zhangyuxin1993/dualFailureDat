package MainFunction;

import java.util.ArrayList;
import java.util.HashMap;

import graphalgorithms.RouteSearching;
import graphalgorithms.SearchConstraint;
import network.Layer;
import network.Link;
import network.Node;
import subgraph.Cycle;
import subgraph.LinearRoute;

public class AEofCycle {
	  
	public HashMap<String, Double> aeofcycle(ArrayList<Cycle> cyclelist, Layer layer) {
		HashMap<String, Double> AeList = new HashMap<String, Double>();
		SearchConstraint constraint = new SearchConstraint(100000);
		double sum = 0;

		for (int n = 0; n < cyclelist.size(); n++) {
			ArrayList<Link> linklist = new ArrayList<Link>();
			int linknum = 0, straddlinglinknum = 0;
			double length = 0;
			Cycle nowcycle = cyclelist.get(n);
			// �����������·���Լ����ĳ���
			for (int m = 0; m < nowcycle.getLinklist().size() - 1; m++) {
				linklist.clear();
				Link link = nowcycle.getLinklist().get(m);
				length = length + link.getLength();
				linknum++;

			}
			// ��������·��

			for (int b = 0; b < nowcycle.getNodelist().size() - 2; b++) {
				Node A = nowcycle.getNodelist().get(b);
				for (int c = b + 1; c < nowcycle.getNodelist().size() - 1; c++) {
					Node B = nowcycle.getNodelist().get(c);

					// Ѱ��route��Դ�ڵ��ҵ�
					RouteSearching RS = new RouteSearching();
					ArrayList<LinearRoute> routelist = new ArrayList<LinearRoute>();
					RS.findAllRoute(A, B, layer, constraint, 100, routelist);// route�ҵ���

					// ����Ϊroute�뻷��Ƚ�
					for (int routeout = 0; routeout < routelist.size(); routeout++) {
						LinearRoute route = routelist.get(routeout);

						NodelistCompare NC = new NodelistCompare();
						int cross = NC.nodelistcompare(route.getNodelist(), nowcycle.getNodelist());
						if (cross == 1)
							continue;// �л���ͬ��route
						// route֮����Ƚ�
						for (int linkadd = 0; linkadd < route.getLinklist().size(); linkadd++) {
							Link addlink = route.getLinklist().get(linkadd);
							Node src = addlink.getNodeA();
							Node des = addlink.getNodeB();
							// �ı���˳��
							if (src.getIndex() > des.getIndex()) {
								addlink = layer.findLink(des, src);
							}
							if (!linklist.contains(addlink)) {
								linklist.add(addlink);
							}
						}
						straddlinglinknum = linklist.size();
					}
				}
			}

			double AE = (linknum + straddlinglinknum * 2) / length * 1000;
			AeList.put(nowcycle.toString(), AE);
			sum = sum + AE;

		}
		double average = 0;
		average = sum / cyclelist.size();
		System.out.println(" average =" + average);

		return AeList;

	}
}
