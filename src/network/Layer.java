package network;

import general.CommonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import network.Layer;
import network.Node;

import java.util.ArrayList;

public class Layer extends CommonObject {
	private HashMap<String, Node> nodelist = null; // list of nodes within the layer
	private HashMap<String, Link> linklist = null;// list of links within the layer
	private HashMap<String, NodePair> nodepairlist = null;// list of node pairs within the layer
	private ArrayList<Node> nodelist1 = new ArrayList<Node>();
	private ArrayList<Link> linklist1 = new ArrayList<Link>();
	private ArrayList<NodePair> nodepairlist1 = new ArrayList<NodePair>();

	public HashMap<String, Node> getNodelist() {
		return nodelist;
	}

	public void setNodelist(HashMap<String, Node> nodelist) {
		this.nodelist = nodelist;
	}

	public HashMap<String, Link> getLinklist() {
		return linklist;
	}

	public void setLinklist(HashMap<String, Link> linklist) {
		this.linklist = linklist;
	}

	public HashMap<String, NodePair> getNodepairlist() {
		return nodepairlist;
	}

	public void setNodepairlist(HashMap<String, NodePair> nodepairlist) {
		this.nodepairlist = nodepairlist;
	}

	public ArrayList<Node> getNodelist1() {
		return nodelist1;
	}

	public void setNodelist1(ArrayList<Node> nodelist) {
		this.nodelist1 = nodelist;
	}

	public ArrayList<Link> getLinklist1() {
		return linklist1;
	}

	public void setLinklist1(ArrayList<Link> linklist) {
		this.linklist1 = linklist;
	}

	public ArrayList<NodePair> getNodepairlist1() {
		return nodepairlist1;
	}

	public void setNodepairlist1(ArrayList<NodePair> nodepairlist) {
		this.nodepairlist1 = nodepairlist;
	}

	public Layer(String name, int index, String comments) {
		super(name, index, comments);
		this.nodelist = new HashMap<String, Node>();
		this.linklist = new HashMap<String, Link>();
		this.nodepairlist = new HashMap<String, NodePair>();
	}

	/**
	 * add a node to the layer
	 * 
	 * @param node
	 */
	public void addNode(Node node) {
		this.nodelist.put(node.getName(), node);
		node.setAssociatedLayer(this);
	}

	/**
	 * add link to the layer
	 */
	public void addLink(Link link) {
		this.linklist.put(link.getName(), link);
		link.setAssociatedLayer(this);
//		link.getNodeA().getNeinodelist().add(link.getNodeB());
//		link.getNodeB().getNeinodelist().add(link.getNodeA());
	}
	
	public void removeLink(String linkname) {
		this.linklist.get(linkname).setAssociatedLayer(null);
		this.linklist.remove(linkname);
	}
	
	public void removeLink(Link link) {
		link.setAssociatedLayer(null);
		this.linklist.remove(link);
		link.getNodeA().getNeinodelist().remove(link.getNodeB());
		link.getNodeB().getNeinodelist().remove(link.getNodeA());
	}
	 public Node findNode(String name,Layer layer){
		  HashMap<String,Node>map=layer.getNodelist();
         Iterator<String>iter=map.keySet().iterator();
         Node currentnode=null;
         while(iter.hasNext()){
      	    currentnode=(Node)(map.get(iter.next()));
      	   if(currentnode.getName().endsWith(name))break;
         }
		 return currentnode; 
	 }
	/**
	 * add node pair to the layer
	 */
	public void addNodepair(NodePair nodepair) {
		this.nodepairlist.put(nodepair.getName(), nodepair);
		nodepair.setAssociateLayer(this);
	}

	/**
	 * get number of nodes
	 */
	public int getNodeNum() {
		return this.nodelist.size();
	}

	/**
	 * get number of links
	 */
	public int getLinkNum() {
		return this.linklist.size();
	}

	/**
	 * get number of node pairs
	 */
	public int getNodepairNum() {
		return this.nodepairlist.size();
	}

	public void readTopology(String filename) {
		String[] data = new String[10];
		File file = new File(filename);
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		int col = 0;
		try {
			line = bufRdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} // read the first title line
			
		// read each line of text file
		try {
			boolean link = false;
			while ((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					data[col] = st.nextToken();
					col++;
				}
				
				col = 0;
				String name = data[0];
				if (name.equals("Link")) {
					link = true;
				}
				
				if (!link) { // node operation
					int index = this.getNodelist().size();
					Node newnode = new Node(name, index, "", this);
					this.addNode(newnode);
				} else { // link operation
					if (!(name.equals("Link"))) {
						Node nodeA = this.getNodelist().get(data[1]);
						Node nodeB = this.getNodelist().get(data[2]);
						double length = Double.parseDouble(data[3]);
						double cost = Double.parseDouble(data[4]);
						int index = this.getLinklist().size();
						if (nodeA.getIndex() < nodeB.getIndex()) {
							name = nodeA.getName() + "-" + nodeB.getName();
						} else {
							name = nodeB.getName() + "-" + nodeA.getName();
						}
						
						Link newlink = new Link(name, index, "", this, nodeA, nodeB, length, cost);
						this.addLink(newlink);
						// update the neighbor node list
						nodeA.addNeiNode(nodeB);
						nodeB.addNeiNode(nodeA);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readTopologys(String filename) {
		String[] data = new String[10];
		File file = new File(filename);
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		int col = 0;
		try {
			line = bufRdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} // read the first title line
			
		// read each line of text file
		try {
			boolean link = false;
			while ((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					data[col] = st.nextToken();
					col++;
				}
				
				col = 0;
				String name = data[0];
				if (name.equals("Link")) {
					link = true;
				}
				
				if (!link) { // node operation
					int layer = Integer.parseInt(data[1]);
					int group = Integer.parseInt(data[2]);
					int group1 = Integer.parseInt(data[3]);
					int group2 = Integer.parseInt(data[4]);
					int type = Integer.parseInt(data[5]);
					int index = this.getNodelist().size();
					Node newnode = new Node(name, index, "", this, layer, group, group1, group2, type);
					this.addNode(newnode);
				} else { // link operation
					if (!(name.equals("Link"))) {
						Node nodeA = this.getNodelist().get(data[1]);
						Node nodeB = this.getNodelist().get(data[2]);
						double length = Double.parseDouble(data[3]);
						double cost = Double.parseDouble(data[4]);
						int index = this.getLinklist().size();
						if (nodeA.getIndex() < nodeB.getIndex()) {
							name = nodeA.getName() + "-" + nodeB.getName();
						} else {
							name = nodeB.getName() + "-" + nodeA.getName();
						}
						
						Link newlink = new Link(name, index, "", this, nodeA, nodeB, length, cost);
						this.addLink(newlink);
						// update the neighbor node list
						nodeA.addNeiNode(nodeB);
						nodeB.addNeiNode(nodeA);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create node pair based on the existing node list
	 */
	public void generateNodepairs() {
		HashMap<String, Node> map = this.getNodelist();
		HashMap<String, Node> map2 = this.getNodelist();
		Iterator<String> iter1 = map.keySet().iterator();

		while (iter1.hasNext()) {
			Node node1 = (Node) (map.get(iter1.next()));
			Iterator<String> iter2 = map2.keySet().iterator();
			while (iter2.hasNext()) {
				Node node2 = (Node) (map2.get(iter2.next()));
				if (!node1.equals(node2)) {
					if (node1.getIndex() < node2.getIndex()) {
						String name = node1.getName() + "-" + node2.getName();
						int index = this.getNodepairlist().size();
						NodePair nodepair = new NodePair(name, index, "", this, node1, node2);
						this.addNodepair(nodepair);
					}
				}
			}
		}
	}

	/**
	 * find a node based on two nodes
	 */
	public Link findLink(Node nodeA, Node nodeB) {
		String name;
		if (nodeA.getIndex() < nodeB.getIndex()) {
			name = nodeA.getName() + "-" + nodeB.getName();
		} else {
			name = nodeB.getName() + "-" + nodeA.getName();
		}
		return this.getLinklist().get(name);
	}
	
	public void CopyNodes(Layer layer) {
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			
			int index = this.getNodelist().size();
			Node newnode = new Node(node.getName(), index, "", this);
			this.addNode(newnode);
			
//			this.addNode(node);
		}
	}
	
	public void CopyLayer(Layer layer) {
		HashMap<String, Node> Copynodelist = new HashMap<String, Node>();
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			Copynodelist.put(node.getName(), node);
		}
		this.setNodelist(Copynodelist);

		HashMap<String, Link> Copylinklist = new HashMap<String, Link>();
		HashMap<String, Link> mapLink = this.getLinklist();
		Iterator<String> iterLink = mapLink.keySet().iterator();
		while (iterLink.hasNext()) {
			Link link = (Link) (mapLink.get(iterLink.next()));
			Copylinklist.put(link.getName(), link);
		}
		this.setLinklist(Copylinklist);
	}
	
	public void CopyCoreLayer(Layer layer) {
		HashMap<String, Node> Copynodelist = new HashMap<String, Node>();
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			if (node.getLayer() == 0) {
				Copynodelist.put(node.getName(), node);
			}
		}
		this.setNodelist(Copynodelist);

		HashMap<String, Link> Copylinklist = new HashMap<String, Link>();
		HashMap<String, Link> mapLink = this.getLinklist();
		Iterator<String> iterLink = mapLink.keySet().iterator();
		while (iterLink.hasNext()) {
			Link link = (Link) (mapLink.get(iterLink.next()));
			if (link.getNodeA().getLayer() == 0 && link.getNodeB().getLayer() == 0) {
				Copylinklist.put(link.getName(), link);
			}
		}
		this.setLinklist(Copylinklist);
	}
	
	public void CopyAggregationLayer(Layer layer) {
		HashMap<String, Node> Copynodelist = new HashMap<String, Node>();
		HashMap<String, Node> map = layer.getNodelist();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Node node = (Node) (map.get(iter.next()));
			if (node.getLayer() == 1) {
				Copynodelist.put(node.getName(), node);
			}
		}
		this.setNodelist(Copynodelist);

		HashMap<String, Link> Copylinklist = new HashMap<String, Link>();
		HashMap<String, Link> mapLink = this.getLinklist();
		Iterator<String> iterLink = mapLink.keySet().iterator();
		while (iterLink.hasNext()) {
			Link link = (Link) (mapLink.get(iterLink.next()));
			if (link.getNodeA().getLayer() == 1 || link.getNodeB().getLayer() == 1) {
				Copylinklist.put(link.getName(), link);
			}
		}
		this.setLinklist(Copylinklist);
	}
	
}



