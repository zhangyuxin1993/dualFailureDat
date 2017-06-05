package MainFunction;

import java.util.ArrayList;
import network.Node;

public class NodelistCompare {
	//看两个路径有没有相交
	public  int nodelistcompare(ArrayList<Node> nodelist1,ArrayList<Node> nodelist2)
	{
		int D_value=0;
		int D_value1=0;
		int D_value2=0;
		for(int n=0;n<=nodelist2.size()-2;n++)
		{
			if(nodelist1.contains(nodelist2.get(n))&&nodelist1.contains(nodelist2.get(n+1)))
			{
				D_value1=Math.abs(nodelist1.indexOf(nodelist2.get(n))-nodelist1.indexOf(nodelist2.get(n+1)));
				if(nodelist1.indexOf(nodelist2.get(n))==0&& nodelist1.indexOf(nodelist2.get(n+1))==nodelist1.size()-2 )
					D_value2=1;
				if(nodelist1.indexOf(nodelist2.get(n))==nodelist1.size()-2 && nodelist1.indexOf(nodelist2.get(n+1))==0)
					D_value2=1;
				if(D_value1==1||D_value2==1)
				{
					D_value=1;
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
