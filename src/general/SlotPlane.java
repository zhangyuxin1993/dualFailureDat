package general;

import java.util.ArrayList;
//import subgraph.LinearRoute;
import network.Layer;
import network.Link;
import graphalgorithms.SearchConstraint;

public class SlotPlane extends Layer {
	private ArrayList<Link> Constraintlinklist = null;
	SearchConstraint constraint = null;
	private int startindex;
	private int endindex;
	
	public SlotPlane(String name, int index, String comments) {
		super(name, index, comments);
		this.Constraintlinklist = new ArrayList<Link>();
		this.constraint = new SearchConstraint();
	}

	public SlotPlane(String name, int index, String comments, int startindex, int endindex) {
		super(name, index, comments);
		this.Constraintlinklist = new ArrayList<Link>();
		this.constraint = new SearchConstraint();
		this.startindex = startindex;
		this.endindex = endindex;
	}

	public SearchConstraint getconstraint() {
		return constraint;
	}

	public void RemoveConstrantLinks() {
		for (Link routeLinks : this.Constraintlinklist) {
			routeLinks.getNodeA().addNeiNode(routeLinks.getNodeB());
		}
	}

	public void InitWavePlaneLinks() {
		for (Link routeLinks : this.Constraintlinklist) {
			routeLinks.getNodeA().removeNeiNode(routeLinks.getNodeB());
		}
	}

	public int getstartindex() {
		return startindex;
	}

	public int getendindex() {
		return endindex;
	}
	
	public ArrayList<Link> getConstraintlinklist() {
		return Constraintlinklist;
	}

	public void setConstraintlinklist(ArrayList<Link> constraintlinklist) {
		Constraintlinklist = constraintlinklist;
	}

}