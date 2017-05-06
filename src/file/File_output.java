package file;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import network.Layer;
import network.NodePair;

public class File_output {
	public ArrayList<NodePair> readDemand(Layer layer, String writename) {
		ArrayList<NodePair> list = new ArrayList<NodePair>();
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
						list.add(currentnodepair);
						// System.out.println(currentnodepair.getName());
						int slotNum = Integer.parseInt(data[1]);
						currentnodepair.setSlotsnum(slotNum);
						// System.out.println(slotNum);
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
		return list;
	}
	
	public void filewrite(String filename, String result) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(filename, true);
			byte[] buffer1 = result.getBytes();
			fos.write(buffer1);

			String str = "\r\n";
			byte[] buffer = str.getBytes();

			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
/*
 * 输出不换行
 */
	public void filewrite_without(String filename, String result) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(filename, true);
			byte[] buffer1 = result.getBytes();
			fos.write(buffer1);

//			String str = "\r\n";
//			byte[] buffer = str.getBytes();
//
//			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
	
	public void filewrite(String filename, int result) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
			String str1 = String.valueOf(result);
			byte[] buffer1 = str1.getBytes();
			fos.write(buffer1);
			String str = "\r\n";
			byte[] buffer = str.getBytes();
			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
	
	/*
	 * 输出result不换行
	 */
	public void filewrite_without(String filename, int result) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
			String str1 = String.valueOf(result);
			byte[] buffer1 = str1.getBytes();
			fos.write(buffer1);
//			String str = "\r\n";
//			byte[] buffer = str.getBytes();
//			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
	
	
	public void filewriteContinuous(String filename, String result) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
			byte[] buffer1 = result.getBytes();
			fos.write(buffer1);
			String str = "";
			byte[] buffer = str.getBytes();
			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
	
	public void filewriteContinuous(String filename, int result) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename, true);
			String str1 = String.valueOf(result);
			byte[] buffer1 = str1.getBytes();
			fos.write(buffer1);
			String str = "";
			byte[] buffer = str.getBytes();
			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e1) {
			System.out.println(e1);
		}
	}
}
