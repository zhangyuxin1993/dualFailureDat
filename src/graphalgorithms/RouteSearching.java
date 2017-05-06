package graphalgorithms;

import general.Constant;
//import groupwork.Message;
//import groupwork.SearchConstraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import network.Layer;
import network.Link;
import network.Node;
import network.NodePair;
import subgraph.Cycle;
import subgraph.LinearRoute;

public class RouteSearching {
	public void Dijkstras(Node srcNode, Node destNode, Layer layer, LinearRoute newRoute, SearchConstraint constraint) {
		ArrayList<Node> visitedNodeList = new ArrayList<Node>();
		visitedNodeList.clear();

		// initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			node.setStatus(Constant.UNVISITED);
			node.setParentNode(null);
			node.setCost_from_src(10000000);
			node.setHop_from_src(10000000);
		}

		// Initialization
		Node currentNode = srcNode;
		currentNode.setCost_from_src(0);
		currentNode.setHop_from_src(0);
		currentNode.setStatus(Constant.VISITEDTWICE);

		if (constraint == null) {
			for (Node node : currentNode.getNeinodelist()) {
				if (node.getStatus() == Constant.UNVISITED) {
					Link link = layer.findLink(currentNode, node);
					// node.setLength_from_src(currentNode.getLength_from_src()
					// + link.getLength());
					node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
					node.setHop_from_src(currentNode.getHop_from_src() + 1);
					node.setStatus(Constant.VISITEDONCE);
					node.setParentNode(currentNode);
					visitedNodeList.add(node);
				}
			}
		} else {
			for (Node node : currentNode.getNeinodelist()) {
				if (!constraint.getExcludedNodelist().contains(node)) {
					if (node.getStatus() == Constant.UNVISITED) {
						Link link = layer.findLink(currentNode, node);
						if (!constraint.getExcludedLinklist().contains(link)) {
							// node.setLength_from_src(currentNode.getLength_from_src()
							// + link.getLength());
							// System.out.println(currentNode.getCost_from_src());
							// System.out.println(link.getCost());
							node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src() + 1);
							node.setStatus(Constant.VISITEDONCE);
							node.setParentNode(currentNode);
							visitedNodeList.add(node);
						}
					}
				}
			}
		}

		// find the node with the lowest cost from the visited node list
		currentNode = this.getLowestCostNode(visitedNodeList);
		if (currentNode != null) {
			while (!currentNode.equals(destNode)) {
				// set the current node double visited
				currentNode.setStatus(Constant.VISITEDTWICE);
				// remove the node from the visited node list
				visitedNodeList.remove(currentNode);

				// navigate the neighboring nodes of the current node
				if (constraint == null) {
					for (Node node : currentNode.getNeinodelist()) {
						if (node.getStatus() == Constant.UNVISITED) { // if the
																		// neighbor
																		// node
																		// is
																		// not
																		// visited
							Link link = layer.findLink(currentNode, node);
							// node.setLength_from_src(currentNode.getLength_from_src()
							// + link.getLength());
							node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src() + 1);
							node.setStatus(Constant.VISITEDONCE);
							node.setParentNode(currentNode);
							visitedNodeList.add(node);
						} else if (node.getStatus() == Constant.VISITEDONCE) { // if
																				// the
																				// neighbor
																				// node
																				// is
																				// first
																				// visited
							Link link = layer.findLink(currentNode, node);
							if (node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()) {
								// update the node status
								node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
								node.setHop_from_src(currentNode.getHop_from_src() + 1);
								node.setParentNode(currentNode);
							}
						}
					}
				} else {
					for (Node node : currentNode.getNeinodelist()) {
						if (!constraint.getExcludedNodelist().contains(node)) {
							if (node.getStatus() == Constant.UNVISITED) { // if
																			// the
																			// neighbor
																			// node
																			// is
																			// not
																			// visited
								Link link = layer.findLink(currentNode, node);
								if (!constraint.getExcludedLinklist().contains(link)) {
									node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
									node.setHop_from_src(currentNode.getHop_from_src() + 1);
									node.setStatus(Constant.VISITEDONCE);
									node.setParentNode(currentNode);
									visitedNodeList.add(node);
								}
							} else if (node.getStatus() == Constant.VISITEDONCE) { // if
																					// the
																					// neighbor
																					// node
																					// is
																					// first
																					// visited
								String name;
								if (currentNode.getIndex() < node.getIndex()) {
									name = currentNode.getName() + "-" + node.getName();
								} else {
									name = node.getName() + "-" + currentNode.getName();
								}

								Link link = layer.getLinklist().get(name);
								if (!constraint.getExcludedLinklist().contains(link)) {
									if (node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()) {
										// update the node status
										node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
										node.setHop_from_src(currentNode.getHop_from_src() + 1);
										node.setParentNode(currentNode);
									}
								}
							}
						}
					}
				}

				// find the node with the lowest cost from the visited node list
				currentNode = this.getLowestCostNode(visitedNodeList);
				if (currentNode == null) {
					break;
				}
			}
		}

		// clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		// add the visited nodes into the node list
		currentNode = destNode;
		if (destNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.setsumcost(destNode.getCost_from_src());

			while (currentNode != srcNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}
	}

	public void Dijkstras(Node srcNode, Node middleNode, Node destNode, Layer layer, LinearRoute newRoute,
			SearchConstraint constraint) {
		LinearRoute route1 = new LinearRoute("", 0, "");
		LinearRoute route2 = new LinearRoute("", 1, "");

		SearchConstraint constraint1 = new SearchConstraint();
		SearchConstraint constraint2 = new SearchConstraint();

		// initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			node.setStatus(Constant.UNVISITED);
			node.setParentNode(null);
			node.setCost_from_src(10000000);
			node.setHop_from_src(10000000);
		}

		constraint1.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		// constraint1.getExcludedNodelist().add(destNode);
		constraint1.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		// Dijkstras(srcNode, middleNode, layer, route1, constraint1);
		Dijkstras(middleNode, destNode, layer, route1, constraint1);
		// route1.OutputRoute_link(route1);
		// System.out.println();

		// clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		// add the visited nodes into the node list
		Node currentNode = null;

		currentNode = destNode;
		if (destNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.setsumcost(destNode.getCost_from_src());

			while (currentNode != middleNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}

		constraint2.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		// constraint2.getExcludedNodelist().addAll(route1.getNodelist());
		// constraint2.getExcludedNodelist().remove(middleNode);
		constraint2.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		// constraint2.getExcludedLinklist().addAll(route1.getLinklist());
		// Dijkstras(middleNode, destNode, layer, route2, constraint2);
		Dijkstras(srcNode, middleNode, layer, route2, constraint2);
		// route2.OutputRoute_link(route2);
		// System.out.println();

		currentNode = middleNode;
		if (middleNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.addsumcost(middleNode.getCost_from_src());

			while (currentNode != srcNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}
		// newRoute.OutputRoute_link(newRoute);
		// System.out.println();
	}

	public void Dijkstra(Node srcNode, Node destNode, Layer layer, LinearRoute newRoute, SearchConstraint constraint) {
		ArrayList<Node> visitedNodeList = new ArrayList<Node>();
		visitedNodeList.clear();

		// initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			node.setStatus(Constant.UNVISITED);
			// node.setParentNode(null);
			node.setCost_from_src(10000000);
			node.setHop_from_src(10000000);
		}

		// Initialization
		Node currentNode = srcNode;
		currentNode.setCost_from_src(0);
		currentNode.setHop_from_src(0);
		currentNode.setStatus(Constant.VISITEDTWICE);

		if (constraint == null) {
			for (Node node : currentNode.getNeinodelist()) {
				if (node.getStatus() == Constant.UNVISITED) {
					Link link = layer.findLink(currentNode, node);
					// node.setLength_from_src(currentNode.getLength_from_src()
					// + link.getLength());
					node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
					node.setHop_from_src(currentNode.getHop_from_src() + 1);
					node.setStatus(Constant.VISITEDONCE);
					node.setParentNode(currentNode);
					visitedNodeList.add(node);
				}
			}
		} else {
			for (Node node : currentNode.getNeinodelist()) {
				if (!constraint.getExcludedNodelist().contains(node)) {
					if (node.getStatus() == Constant.UNVISITED) {
						Link link = layer.findLink(currentNode, node);
						if (!constraint.getExcludedLinklist().contains(link)) {
							// node.setLength_from_src(currentNode.getLength_from_src()
							// + link.getLength());
							node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src() + 1);
							node.setStatus(Constant.VISITEDONCE);
							node.setParentNode(currentNode);
							visitedNodeList.add(node);
						}
					}
				}
			}
		}

		// find the node with the lowest cost from the visited node list
		currentNode = this.getLowestCostNode(visitedNodeList);
		if (currentNode != null) {
			while (!currentNode.equals(destNode)) {
				// set the current node double visited
				currentNode.setStatus(Constant.VISITEDTWICE);
				// remove the node from the visited node list
				visitedNodeList.remove(currentNode);

				// navigate the neighboring nodes of the current node
				if (constraint == null) {
					for (Node node : currentNode.getNeinodelist()) {
						if (node.getStatus() == Constant.UNVISITED) { // if the
																		// neighbor
																		// node
																		// is
																		// not
																		// visited
							Link link = layer.findLink(currentNode, node);
							// node.setLength_from_src(currentNode.getLength_from_src()
							// + link.getLength());
							node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
							node.setHop_from_src(currentNode.getHop_from_src() + 1);
							node.setStatus(Constant.VISITEDONCE);
							node.setParentNode(currentNode);
							visitedNodeList.add(node);
						} else if (node.getStatus() == Constant.VISITEDONCE) { // if
																				// the
																				// neighbor
																				// node
																				// is
																				// first
																				// visited
							Link link = layer.findLink(currentNode, node);
							if (node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()) {
								// update the node status
								node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
								node.setHop_from_src(currentNode.getHop_from_src() + 1);
								node.setParentNode(currentNode);
							}
						}
					}
				} else {
					for (Node node : currentNode.getNeinodelist()) {
						if (!constraint.getExcludedNodelist().contains(node)) {
							if (node.getStatus() == Constant.UNVISITED) { // if
																			// the
																			// neighbor
																			// node
																			// is
																			// not
																			// visited
								Link link = layer.findLink(currentNode, node);
								if (!constraint.getExcludedLinklist().contains(link)) {
									node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
									node.setHop_from_src(currentNode.getHop_from_src() + 1);
									node.setStatus(Constant.VISITEDONCE);
									node.setParentNode(currentNode);
									visitedNodeList.add(node);
								}
							} else if (node.getStatus() == Constant.VISITEDONCE) { // if
																					// the
																					// neighbor
																					// node
																					// is
																					// first
																					// visited
								String name;
								if (currentNode.getIndex() < node.getIndex()) {
									name = currentNode.getName() + "-" + node.getName();
								} else {
									name = node.getName() + "-" + currentNode.getName();
								}

								Link link = layer.getLinklist().get(name);
								if (!constraint.getExcludedLinklist().contains(link)) {
									if (node.getCost_from_src() > currentNode.getCost_from_src() + link.getCost()) {
										// update the node status
										node.setCost_from_src(currentNode.getCost_from_src() + link.getCost());
										node.setHop_from_src(currentNode.getHop_from_src() + 1);
										node.setParentNode(currentNode);
									}
								}
							}
						}
					}
				}

				// find the node with the lowest cost from the visited node list
				currentNode = this.getLowestCostNode(visitedNodeList);
				if (currentNode == null) {
					break;
				}
			}
		}

		// clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		// add the visited nodes into the node list
		currentNode = destNode;
		if (destNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.setsumcost(destNode.getCost_from_src());

			while (currentNode != srcNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}
	}

	public void Dijkstras1(Node srcNode, Node middleNode, Node destNode, Layer layer, LinearRoute newRoute,
			SearchConstraint constraint) {
		LinearRoute route1 = new LinearRoute("", 0, "");
		LinearRoute route2 = new LinearRoute("", 1, "");

		SearchConstraint constraint1 = new SearchConstraint();
		SearchConstraint constraint2 = new SearchConstraint();

		// initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			node.setStatus(Constant.UNVISITED);
			node.setParentNode(null);
			node.setCost_from_src(10000000);
			node.setHop_from_src(10000000);
		}

		constraint1.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint1.getExcludedNodelist().add(destNode);
		constraint1.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		Dijkstra(srcNode, middleNode, layer, route1, constraint1);

		constraint2.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint2.getExcludedNodelist().addAll(route1.getNodelist());
		constraint2.getExcludedNodelist().remove(middleNode);
		constraint2.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		constraint2.getExcludedLinklist().addAll(route1.getLinklist());
		Dijkstra(middleNode, destNode, layer, route2, constraint2);

		// clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		// add the visited nodes into the node list
		Node currentNode = destNode;
		if (destNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.setsumcost(destNode.getCost_from_src());

			while (currentNode != middleNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}

		currentNode = middleNode;
		if (middleNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.addsumcost(middleNode.getCost_from_src());

			while (currentNode != srcNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}
	}

	public void Dijkstras(Node srcNode, Node firstNode, Node secondNode, Node destNode, Layer layer,
			LinearRoute newRoute1, LinearRoute newRoute2, SearchConstraint constraint) {
		SearchConstraint constraint1 = new SearchConstraint();
		SearchConstraint constraint2 = new SearchConstraint();

		constraint1.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint1.getExcludedNodelist().add(secondNode);
		constraint1.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		Dijkstras(srcNode, firstNode, destNode, layer, newRoute1, constraint1);

		constraint2.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint2.getExcludedNodelist().remove(firstNode);
		constraint2.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		Dijkstras(srcNode, secondNode, destNode, layer, newRoute2, constraint2);
	}

	public void Dijkstras(Node srcNode, Node firstNode, Node secondNode, Node destNode, Layer layer,
			LinearRoute newRoute, SearchConstraint constraint) {
		LinearRoute route1 = new LinearRoute("", 0, "");
		LinearRoute route2 = new LinearRoute("", 1, "");
		LinearRoute route3 = new LinearRoute("", 2, "");

		SearchConstraint constraint1 = new SearchConstraint();
		SearchConstraint constraint2 = new SearchConstraint();
		SearchConstraint constraint3 = new SearchConstraint();

		// initialize all the node states
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			node.setStatus(Constant.UNVISITED);
			node.setParentNode(null);
			node.setCost_from_src(10000000);
			node.setHop_from_src(10000000);
		}

		constraint1.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint1.getExcludedNodelist().add(destNode);
		constraint1.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		Dijkstra(srcNode, firstNode, layer, route1, constraint1);

		HashMap<String, Link> mapLink = layer.getLinklist();
		Iterator<String> iterLink = mapLink.keySet().iterator();
		while (iterLink.hasNext()) {
			Link link = (Link) (mapLink.get(iterLink.next()));
			if (link.getNodeA().getLayer() == 1 || link.getNodeB().getLayer() == 1) {
				constraint2.getExcludedLinklist().add(link);
			}
		}

		constraint2.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint2.getExcludedNodelist().addAll(route1.getNodelist());
		constraint2.getExcludedNodelist().remove(firstNode);
		constraint2.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		constraint2.getExcludedLinklist().addAll(route1.getLinklist());
		Dijkstra(firstNode, secondNode, layer, route2, constraint2);

		constraint3.getExcludedNodelist().addAll(constraint.getExcludedNodelist());
		constraint3.getExcludedNodelist().addAll(route1.getNodelist());
		constraint3.getExcludedNodelist().addAll(route2.getNodelist());
		constraint3.getExcludedNodelist().remove(secondNode);
		constraint3.getExcludedLinklist().addAll(constraint.getExcludedLinklist());
		constraint3.getExcludedLinklist().addAll(route1.getLinklist());
		constraint3.getExcludedLinklist().addAll(route2.getLinklist());
		Dijkstra(secondNode, destNode, layer, route3, constraint3);

		// clear the route
		newRoute.getNodelist().clear();
		newRoute.getLinklist().clear();
		// add the visited nodes into the node list
		Node currentNode = destNode;
		if (destNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.setsumcost(destNode.getCost_from_src());

			while (currentNode != secondNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}

		currentNode = secondNode;
		if (secondNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.addsumcost(secondNode.getCost_from_src());

			while (currentNode != firstNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}

		currentNode = firstNode;
		if (firstNode.getParentNode() != null) {
			newRoute.getNodelist().add(0, currentNode);
			newRoute.addsumcost(firstNode.getCost_from_src());

			while (currentNode != srcNode) {
				Link link = layer.findLink(currentNode, currentNode.getParentNode());
				newRoute.getLinklist().add(0, link);
				currentNode = currentNode.getParentNode();
				newRoute.getNodelist().add(0, currentNode);
			}
		}
	}

	// find a node from the visited node list that is closest to the src node
	private Node getLowestCostNode(ArrayList<Node> visitedNodeList) {
		Node currentnode = null;
		double current_cost_to_desc = 100000000;
		for (Node node : visitedNodeList) {
			if (node.getCost_from_src() < current_cost_to_desc) {
				currentnode = node;
				current_cost_to_desc = node.getCost_from_src();
			}
		}
		return currentnode;
	}

	public void KshortestPath(Layer layer, SearchConstraint constraint) {
		HashMap<String, NodePair> map1 = layer.getNodepairlist();
		Iterator<String> iter1 = map1.keySet().iterator();
		while (iter1.hasNext()) {
			NodePair nodepair = (NodePair) (map1.get(iter1.next()));

			Kshortest(nodepair.getSrcNode(), nodepair.getDesNode(), layer, 5, nodepair.getLinearroutelist(),
					constraint);
		}
	}

	public int Kshortest(Node srcNode, Node destNode, Layer layer, int k, ArrayList<LinearRoute> routelist,
			SearchConstraint constraint) {
		routelist.clear();
		ArrayList<Link> RemoveLinklist = new ArrayList<Link>();
		Layer searchLayer = layer;
		int num_found = 0; // number of found route

		while (true) {
			LinearRoute newRoute = new LinearRoute("", 0, "", Constant.WORKING);
			this.Dijkstras(srcNode, destNode, searchLayer, newRoute, constraint);

			Layer newLayer = new Layer("Textlayer", 0, "");
			if (newRoute.getLinklist().size() > 0) {
				// System.out.println("*****");
				// newRoute.OutputRoute_link(newRoute);
				routelist.add(newRoute);
				newLayer.setNodelist(searchLayer.getNodelist());
				newLayer.setLinklist(searchLayer.getLinklist());
				newLayer.setNodepairlist(searchLayer.getNodepairlist());

				for (Link routeLinks : newRoute.getLinklist()) {
					newLayer.removeLink(routeLinks);
					routeLinks.getNodeA().removeNeiNode(routeLinks.getNodeB());
					routeLinks.getNodeB().removeNeiNode(routeLinks.getNodeA());
					RemoveLinklist.add(routeLinks);
				}

				searchLayer = newLayer;
				num_found++;

				if (num_found == k) {
					break;
				} else if (srcNode.getNeinodelist().size() == 0 || destNode.getNeinodelist().size() == 0) {
					break;
				}
			} else {
				break;
			}
		}

		for (Link routeLinks : RemoveLinklist) {
			layer.addLink(routeLinks);
			routeLinks.getNodeA().addNeiNode(routeLinks.getNodeB());
		}
		return num_found; // the number of found routes
	}

	/**
	 * find K-disjoint shortest path routes
	 */
	public int Kshortest(Node srcNode, Node destNode, Layer layer, int k, ArrayList<LinearRoute> routelist) {
		routelist.clear();
		int num_found = 0; // number of found route

		SearchConstraint constraint = new SearchConstraint();

		while (true) {
			LinearRoute newRoute = new LinearRoute("", 0, "");
			this.Dijkstras(srcNode, destNode, layer, newRoute, constraint);
			if (newRoute.getLinklist().size() > 0) {
				routelist.add(newRoute);
				constraint.getExcludedLinklist().addAll(newRoute.getLinklist());
				num_found++;

				if (num_found == k) {
					break;
				}
			} else {
				break;
			}
		}
		return num_found; // the number of found routes
	}

	/**
	 * find all routes between a pair of nodes
	 */
	public int findAllRoute(Node nodeA, Node nodeB, Layer layer, SearchConstraint constraint, int k,
			ArrayList<LinearRoute> routelist) {
		// ArrayList<Route> routeList = new ArrayList<Route>();
		int hoplimit = 100000;
		if (constraint != null) {
			hoplimit = constraint.getMax_hop();
		}

		ArrayList<Message> messageList = new ArrayList<Message>();
		// the list of messages that exist in the current network
		Message newMessage = new Message(nodeA);
		messageList.add(newMessage);

		while (!messageList.isEmpty()) {
			// get the header message
			Message currentmessage = messageList.remove(0);
			Node currentNode = currentmessage.getCurrentNode();

			if (currentNode == nodeB) {
				// find one route
				LinearRoute newroute = new LinearRoute("", 0, "");
				newroute.getNodelist().addAll(currentmessage.getVisitedNodelist());
				routelist.add(newroute);
				newroute.ConvertfromNodeListtoLinkList();
				if (routelist.size() == k) {
					break;
				}
			} else {
				if (currentmessage.getVisitedNodelist().size() - 1 < hoplimit) {
					for (Node neinode : currentNode.getNeinodelist()) {
						if (!currentmessage.getVisitedNodelist().contains(neinode)) {
							newMessage = new Message(neinode);
							newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
							messageList.add(newMessage);
						}
					}
				}
			}
		}
		return routelist.size();
	}

	/**
	 * find a cycle of cycles start from a certain nodes
	 */
	// public void findAllCycle(Node node, Layer layer, SearchConstraint
	// constraint, ArrayList<Cycle> cyclelist) {
	// int hoplimit = 100000;
	// if (constraint != null) {
	// hoplimit = constraint.getMax_hop();
	// }
	//
	// ArrayList<Message> messageList = new ArrayList<Message>();
	// // the list of messages that exist in the current network
	// Message newMessage = new Message(node);
	// messageList.add(newMessage);
	//
	// // int hop_count = 0;
	// while (!messageList.isEmpty()) {
	// // get the header message
	// Message currentmessage = messageList.remove(0);
	// Node currentNode = currentmessage.getCurrentNode();
	//
	// if (currentNode == node && currentmessage.getVisitedNodelist().size() >
	// 3) {
	// // find one route
	// Cycle newCycle = new Cycle("", 0, "");
	// newCycle.getNodelist().addAll(currentmessage.getVisitedNodelist());
	// // if the new cycle has existed
	// Boolean existing = false;
	// for (Cycle cycle : cyclelist) {
	// if (newCycle.isEqual(cycle)) {
	// existing = true;
	// break;
	// }
	// }
	//
	// if (!existing) {
	// cyclelist.add(newCycle);
	// newCycle.ConvertfromNodeListtoLinkList();
	// }
	// } else {
	// if (currentmessage.getVisitedNodelist().size() - 1 < hoplimit) {
	// for (Node neinode : currentNode.getNeinodelist()) {
	// if (constraint != null &&
	// !constraint.getExcludedNodelist().contains(neinode)) {
	// if (!currentmessage.getVisitedNodelist().contains(neinode)) {
	// newMessage = new Message(neinode);
	// newMessage.getVisitedNodelist().addAll(0,
	// currentmessage.getVisitedNodelist());
	// messageList.add(newMessage);
	// } else if (neinode == node && currentmessage.getVisitedNodelist().size()
	// > 2) {
	// newMessage = new Message(neinode);
	// newMessage.getVisitedNodelist().addAll(0,
	// currentmessage.getVisitedNodelist());
	// messageList.add(newMessage);
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	public void findAllCycle(Node node, Layer layer, SearchConstraint constraint, ArrayList<Cycle> cyclelist) {
//经过修改的找环程序 （已经删去了相同的环）
		int hoplimit = 100000;
		if (constraint != null)
			hoplimit = constraint.getMax_hop();


		ArrayList<Message> messageList = new ArrayList<Message>();  

		Message newMessage = new Message(node);

		messageList.add(newMessage);

		// int hop_count = 0;

		while (!messageList.isEmpty()) {
			// get the header message
			Message currentmessage = messageList.remove(0);
			Node currentNode = currentmessage.getCurrentNode();

			if (currentNode == node && currentmessage.getVisitedNodelist().size() > 3) {
				// find one route
				Cycle newCycle = new Cycle("", 0, "");
				newCycle.getNodelist().addAll(currentmessage.getVisitedNodelist());
				// if the new cycle has existed
				Boolean existing = false;
				for (Cycle cycle : cyclelist)
					if (newCycle.isEqual(cycle)) {
						existing = true;
						break;
					}
				/*
				 * 以下为附加
				 */
				// /*
				int newcyclenode1 = 0;
				Node newcycleSnode1 = newCycle.getNodelist().get(newcyclenode1);
				Node newcycleSnode2 = newCycle.getNodelist().get(newcyclenode1 + 1);

				for (int c = 0; c < cyclelist.size(); c++) {
					Cycle cycle = cyclelist.get(c);
				 
					if (newCycle.getNodelist().size() == cycle.getNodelist().size()) {
						for (int cyclenode1 = 0; cyclenode1 < cycle.getNodelist().size() - 1; cyclenode1++) {
							// 取出cycle中的两个节点
							Node cycleSnode1 = cycle.getNodelist().get(cyclenode1);
							Node cycleSnode2 = cycle.getNodelist().get(cyclenode1 + 1);
						 

							if ((cycleSnode1.equals(newcycleSnode1) && cycleSnode2.equals(newcycleSnode2))
									|| (cycleSnode2.equals(newcycleSnode1) && cycleSnode1.equals(newcycleSnode2))) {
								newcyclenode1++;
								if (newcyclenode1 < newCycle.getNodelist().size() - 1) {
									newcycleSnode1 = newCycle.getNodelist().get(newcyclenode1);
									newcycleSnode2 = newCycle.getNodelist().get(newcyclenode1 + 1);

									cyclenode1 = -1;
								}
								if (newcyclenode1 == newCycle.getNodelist().size() - 1) {
									existing = true;
									break;
								}

							}
						}
					}

				}

				// */

				if (!existing) {

					cyclelist.add(newCycle);
					newCycle.ConvertfromNodeListtoLinkList();
				}
			} else {
				if (currentmessage.getVisitedNodelist().size() - 1 < hoplimit)
					for (Node neinode : currentNode.getNeinodelist()) {
						if (constraint != null && !constraint.getExcludedNodelist().contains(neinode)) {
							if (!currentmessage.getVisitedNodelist().contains(neinode)) {
								newMessage = new Message(neinode);
								newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
								messageList.add(newMessage);
							} else if (neinode == node && currentmessage.getVisitedNodelist().size() > 2) {
								newMessage = new Message(neinode);
								newMessage.getVisitedNodelist().addAll(0, currentmessage.getVisitedNodelist());
								messageList.add(newMessage);
							}
						}
					}
			}

		}

	}

	/********************************************
	 * Traveling salesman problem
	 ********************************************/
	public void NearestNeighbor(Node srcNode, ArrayList<Node> nodelist, ArrayList<Link> linklist, LinearRoute route) {
		/**
		 * initialize all the node status
		 */
		for (Node node : nodelist) {
			node.setStatus(Constant.UNVISITED);
		}

		Node currentnode = srcNode;
		Node nextNode = null;
		Link currentlink = null;
		double min_cost = 1000000;
		currentnode.setStatus(Constant.VISITEDONCE);
		route.getNodelist().add(currentnode);
		route.getObjectlist().add(currentnode);

		while (true) {
			min_cost = 1000000;
			nextNode = null;
			currentlink = null;
			for (Node node : currentnode.getNeinodelist()) {
				if (node.getStatus() == Constant.UNVISITED) {
					Link link = this.findLink(currentnode, node, linklist);
					if (link.getCost() < min_cost) {
						min_cost = link.getCost();
						currentlink = link;
						nextNode = node;
					}
				}
			}

			currentnode = nextNode;
			currentnode.setStatus(Constant.VISITEDONCE);
			route.getNodelist().add(currentnode);
			route.getLinklist().add(currentlink);
			route.getObjectlist().add(currentlink);
			route.getObjectlist().add(currentnode);
			// check if all the nodes are visited
			if (this.isAllvisted(nodelist)) {
				break;
			}
		}
	}

	/**
	 * find a link from a link list
	 */
	public Link findLink(Node nodeA, Node nodeB, ArrayList<Link> linklist) {
		for (Link link : linklist) {
			if (link.getNodeA().equals(nodeA) && link.getNodeB().equals(nodeB)) {
				return link;
			}

			if (link.getNodeA().equals(nodeB) && link.getNodeB().equals(nodeA)) {
				return link;
			}
		}
		return null;
	}

	/**
	 * checke if all the nodes are visited
	 */
	public boolean isAllvisted(ArrayList<Node> nodelist) {
		boolean allvisted = true;
		for (Node node : nodelist) {
			if (node.getStatus() == Constant.UNVISITED) {
				allvisted = false;
				break;
			}
		}
		return allvisted;
	}

}
