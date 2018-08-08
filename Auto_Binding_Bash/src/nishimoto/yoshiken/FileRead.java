package nishimoto.yoshiken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {
	private static String[] ims_a;
	private static int[] vertex;
	private static String[] type;
	private static int[] life;
	private static int[] edge;
	private static int[] ver1;
	private static int[] ver2;
	private static String[] port;
	private static int add = 0;
	private static int sub = 0;
	private static int mult = 0;
	private static int div = 0;

	private static double[] axis_x;
	private static double[] axis_y;

	public static int[] getVertex(){
		return vertex;
	}
	public static String[] getType(){
		return type;
	}
	public static int[] getLife(){
		return life;
	}
	public static int[] getEdge(){
		return edge;
	}
	public static int[] getVer1(){
		return ver1;
	}
	public static int[] getVer2(){
		return ver2;
	}
	public static String[] getPort(){
		return port;
	}
	public static int getAdd(){
		return add;
	}
	public static int getSub(){
		return sub;
	}
	public static int getMult(){
		return mult;
	}
	public static int getDiv(){
		return div;
	}

	public static double[] getAxis_X(){
		return axis_x;
	}
	public static double[] getAxis_Y(){
		return axis_y;
	}

	public static void input(String path){
		ims_a = null;
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> ims = new ArrayList<String>();
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null){
				line = line.replaceAll("\t", " ");
				line = line.trim();
				line = line.replaceAll("  *", " ");
				if(line.length() > 0){
					ims.add(line);
				}
			}
			ims_a = new String[ims.size()];
			for(int i = 0; i < ims.size(); i++){
				ims_a[i] = ims.get(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try{
				br.close();
				fr.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public static void dataArrange(){
		boolean v = false;
		boolean l = false;
		int vi = 0;
		int li = 0;
		for(int i = 0; i < ims_a.length; i++){
			String a = ims_a[i];
			System.out.println(a);
			boolean ha = false;
			char d = 0;
			if(a.length() > 0){
				d = a.charAt(0);
			}
			else{
				ha = true;
			}
			if(a.equals("--vertex")){
				v = true;
				l = false;
				ha = true;
			}
			else if(a.equals("--edge")){
				v = false;
				l = true;
				ha = true;
			}
			else if(a.equals("--exclusive block")){
				ha = true;
			}
			if(!ha){
				if(d != '#'){
					if(v){
						vi = vi + 1;
					}
					else if(l){
						li = li + 1;
					}
				}
			}
		}

		vertex = new int[vi];
		type = new String[vi];
		life = new int[vi];
		edge = new int[li];
		ver1 = new int[li];
		ver2 = new int[li];
		port = new String[li];
		axis_x = new double[vi];
		axis_y = new double[vi];

		int ve = 0;
		int le = 0;

		for(int i = 0; i < ims_a.length; i++){
			String d = ims_a[i];
			boolean ha = false;
			char b = 0;
			if(d.length() > 0){
				b = d.charAt(0);
			}
			else{
				ha = true;
			}
			if(d.equals("--exclusive block")){
				v = false;
				l = false;
				ha = true;
			}
			String[] imars = d.split("\\s", 0);
			if(imars[0].equals("--vertex")){
				v = true;
				l = false;
				ha = true;
			}
			else if(imars[0].equals("--edge")){
				v = false;
				l = true;
				ha = true;
			}
			else if(imars[0].equals("add.")){
				add = Integer.parseInt(imars[1]);
			}
			else if(imars[0].equals("sub.")){
				sub = Integer.parseInt(imars[1]);
			}
			else if(imars[0].equals("mult.")){
				mult = Integer.parseInt(imars[1]);
			}
			else if(imars[0].equals("div.")){
				div = Integer.parseInt(imars[1]);
			}
			if(b == '#'){
				imars[0] = imars[0].replaceAll("#", "");
				for(int k = 0; k < imars.length; k++){
					String[] imarse = imars[k].split(">",0);
					if(imarse[0].equals("adder=")){
						add = Integer.parseInt(imarse[1]);
					}
					else if(imarse[0].equals("multiplier=")){
						mult = Integer.parseInt(imarse[1]);
					}
					else if(imarse[0].equals("add=")){
						add = Integer.parseInt(imarse[1]);
					}
					else if(imarse[0].equals("sub=")){
						sub = Integer.parseInt(imarse[1]);
					}
					else if(imarse[0].equals("multi=")){
						mult = Integer.parseInt(imarse[1]);
					}
					else if(imarse[0].equals("div=")){
						div = Integer.parseInt(imarse[1]);
					}
				}
				ha = true;
			}
			if(!ha){
				if(v){
					vertex[ve] = Integer.parseInt(imars[0]);
					type[ve] = imars[1];
					life[ve] = Integer.parseInt(imars[2]);
					if(imars.length > 2){
						axis_x[ve] = Double.parseDouble(imars[3]);
						axis_y[ve] = Double.parseDouble(imars[4]);
					}
					ve = ve + 1;
				}
				else if(l){
					edge[le] = Integer.parseInt(imars[0]);
					ver1[le] = Integer.parseInt(imars[1]);
					ver2[le] = Integer.parseInt(imars[2]);
					port[le] = imars[3];
					le = le + 1;
				}
			}
		}
	}

	public static void resetRC(){
		add = 0;
		sub = 0;
		mult = 0;
		div = 0;
	}
}
