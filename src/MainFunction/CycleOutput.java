package MainFunction;

import java.util.ArrayList;

import file.File_output;
import network.Node;
import subgraph.Cycle;

 
public class CycleOutput {
	public  void cycleoutput(ArrayList<Cycle> cyclelist,String filename)
	{
		
		for(int i=0;i<cyclelist.size();i++)
		{
			File_output filewrite=new File_output();
			Cycle CycleContain=cyclelist.get(i);
			
			int m=0;
			for(Node Newnode:CycleContain.getNodelist())
			{
				
				filewrite.filewrite_without(filename, Newnode.getName());
				if(Newnode.getName()==CycleContain.getNodelist().get(0).getName()&&m==0)
				{
					filewrite.filewrite_without(filename, "-");
				}
				if(Newnode.getName()!=CycleContain.getNodelist().get(0).getName())
				{	
				 filewrite.filewrite_without(filename, "-");
				}
				m=1;
			}
			filewrite.filewrite_without(filename, "\r\n");
		}
	}
	public  void cycleoutput(Cycle CycleContain,String filename)
	{
							
			int m=0;
			File_output filewrite=new File_output();
			for(Node Newnode:CycleContain.getNodelist())
			{
				
				filewrite.filewrite_without(filename, Newnode.getName());
				if(Newnode.getName()==CycleContain.getNodelist().get(0).getName()&&m==0)
				{
				filewrite.filewrite_without(filename, "-");
				}
				if(Newnode.getName()!=CycleContain.getNodelist().get(0).getName())
				{	
				filewrite.filewrite_without(filename, "-");}
				m=1;
			}
		}
	}
	

