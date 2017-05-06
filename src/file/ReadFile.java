package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import network.Layer;
import network.NodePair;

public class ReadFile {
	public BufferedReader bufread;
	private String readStr = "";

	public String[] ReadTxtFile(String filename) {
		String[] stringlist = new String[100];
		
		File file = new File(filename);
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		try {
			line = bufRdr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// read the first title line
		// read each line of text file
		try {
			// boolean link = false;
			while ((line = bufRdr.readLine()) != null) {
				stringlist = line.split(",");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringlist;
	}

	public String readTxtFileFunction(String filename) {
		String read;
		FileReader fileread;
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return readStr;
	}
	
	public int readSlotNum(Layer layer, String writename) {
		String[] data = new String[10];
		File file = new File(writename);
		
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
		}
		
		// read the first title line
		// read each line of text file
		try {
			boolean Nodepair = false;
			while ((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					data[col] = st.nextToken();
					col++;
				}
				col = 0;
				String name = data[0];
				if (name.equals("Nodepair")) {
					Nodepair = true;
				}
				
				// read nodes
				if (Nodepair) {
					if (!(name.equals("Nodepair"))) {
						NodePair currentnodepair = layer.getNodepairlist().get(data[0]);
						int slotNum = Integer.parseInt(data[1]);
						currentnodepair.setSlotsnum(slotNum);
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
		return 0;
	}

	public int readRate(Layer layer, String writename) {
		String[] data = new String[10];
		File file = new File(writename);
		
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
		}
		
		// read the first title line
		// read each line of text file
		try {
			boolean Nodepair = false;
			while ((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					data[col] = st.nextToken();
					col++;
				}
				col = 0;
				String name = data[0];
				if (name.equals("Nodepair")) {
					Nodepair = true;
				}
				
				// read nodes
				if (Nodepair) {
					if (!(name.equals("Nodepair"))) {
						NodePair currentnodepair = layer.getNodepairlist().get(data[0]);
						int rate = Integer.parseInt(data[1]);
						currentnodepair.setRate(rate);
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
		return 0;
	}
	
}
