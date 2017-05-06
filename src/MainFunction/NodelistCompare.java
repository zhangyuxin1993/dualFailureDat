package MainFunction;

import java.util.ArrayList;
import network.Node;

 

public class NodelistCompare {
	//看两个路径有没有相交
	public  int nodelistcompare(ArrayList<Node> nodelist1,ArrayList<Node> nodelist2)
	{

		int D_value=0;
		for(int n=0;n<=nodelist2.size()-2;n++)
		{
			if(nodelist1.contains(nodelist2.get(n))&&nodelist1.contains(nodelist2.get(n+1)))
			{
				D_value=Math.abs(nodelist1.indexOf(nodelist2.get(n))-nodelist1.indexOf(nodelist2.get(n+1)));
				if(D_value==1)
				{
					break;
				}
				else
				{
					D_value=0;
				}
			}
		}
		return D_value;
		
	
	}
}
