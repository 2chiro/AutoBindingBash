package nishimoto.yoshiken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWrite {
	public static void output(String path){
		File file = new File(path);
		try {
			file.createNewFile();
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(filewriter);
			PrintWriter pw = new PrintWriter(bw);

			int[][] reg = RegisterAllocation.getRegs();
			int[][] addop = ModuleAllocation.getAddops();
			int[][] subop = ModuleAllocation.getAddops();
			int[][] multop = ModuleAllocation.getMultops();
			int[][] divop = ModuleAllocation.getDivops();

			//レジスタ＆演算器の数出力
			pw.println("Register. " + String.format("%1$4d", RegisterAllocation.getLines() - RegisterAllocation.getExreg()));
			pw.println("add. " + String.format("%1$9d", FileRead.getAdd()));
			pw.println("sub. " + String.format("%1$9d", FileRead.getSub()));
			pw.println("mult. " + String.format("%1$8d", FileRead.getMult()));
			pw.println("div. " + String.format("%1$9d", FileRead.getDiv()));

			pw.println("--register binding");

			//レジスタ割当結果出力
			for(int i = 0; i < reg.length; i++){
				if(i < RegisterAllocation.getLines() - RegisterAllocation.getExreg()){
					int num = i + 1;
					pw.print(String.format("%-2d",num));
					for(int j = 0; j < reg[i].length; j++){
						if(reg[i][j] != -1){
							pw.print(" " + String.format("%1$4d", reg[i][j]));
						}
					}
					pw.print("\n");
				}
			}

			pw.println("--operation binding");

			//加算器割当出力
			if(FileRead.getAdd() != 0){
				for(int i = 0; i < addop.length; i++){
					int num = i + 1;
					pw.print("Add" + num);
					for(int j = 0; j < addop[i].length; j++){
						if(addop[i][j] != -1 && addop[i][j] != -2){
							pw.print(" " + String.format("%1$4d", addop[i][j]));
						}
					}
					pw.print("\n");
				}
			}

			//減算器割当出力
			if(FileRead.getSub() != 0){
				for(int i = 0; i < subop.length; i++){
					int num = i + 1;
					pw.print("Sub" + num);
					for(int j = 0; j < subop[i].length; j++){
						if(subop[i][j] != -1 && subop[i][j] != -2){
							pw.print(" " + String.format("%1$4d", subop[i][j]));
						}
					}
					pw.print("\n");
				}
			}

			//乗算器割当出力
			if(FileRead.getMult() != 0){
				for(int i = 0; i < multop.length; i++){
					int num = i + 1;
					pw.print("Mul" + num);
					for(int j = 0; j < multop[i].length; j++){
						if(multop[i][j] != -1 && multop[i][j] != -2){
							pw.print(" " + String.format("%1$4d", multop[i][j]));
						}
					}
					pw.print("\n");
				}
			}

			//除算器割当出力
			if(FileRead.getDiv() != 0){
				for(int i = 0; i < divop.length; i++){
					int num = i + 1;
					pw.print("Div" + num);
					for(int j = 0; j < divop[i].length; j++){
						if(divop[i][j] != -1 && divop[i][j] != -2){
							pw.print(" " + String.format("%1$4d", divop[i][j]));
						}
					}
					pw.print("\n");
				}
			}

			pw.println("--exclusive block");

			pw.close();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

	public static void newSDFGOutput(String path){
		File file = new File(path);
		try{
			file.createNewFile();
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(filewriter);
			PrintWriter pw = new PrintWriter(bw);

			int add = FileRead.getAdd();
			int sub = FileRead.getSub();
			int mult = FileRead.getMult();
			int div = FileRead.getDiv();
			int[] ver = FileRead.getVertex();
			String[] type = FileRead.getType();
			int[] life = FileRead.getLife();
			int[] edge = FileRead.getEdge();
			int[] ver1 = FileRead.getVer1();
			int[] ver2 = FileRead.getVer2();
			String[] port = FileRead.getPort();
			double[] axis_x = FileRead.getAxis_X();
			double[] axis_y = FileRead.getAxis_Y();

			ArrayList<Integer> addver = ConstructTOPs.getAddVer();
			ArrayList<String> addtype = ConstructTOPs.getAddType();
			ArrayList<Integer> addlife = ConstructTOPs.getAddLife();

			ArrayList<Integer> addedge = ConstructTOPs.getAddEdge();
			ArrayList<Integer> addver1 = ConstructTOPs.getAddVer1();
			ArrayList<Integer> addver2 = ConstructTOPs.getAddVer2();
			ArrayList<String> addport = ConstructTOPs.getAddPort();

			//pw.println("#add=>" + add + " sub=>" + sub + " multi=>" + mult + " div=>" + div);
			pw.println("add. " + String.format("%1$9d", add));
			pw.println("sub. " + String.format("%1$9d", sub));
			pw.println("mult. " + String.format("%1$8d", mult));
			pw.println("div. " + String.format("%1$9d", div));

			pw.println("--vertex");
			//pw.println("#vertex_ID\ttype\tlifetime");
			for(int i = 0; i < ver.length; i++){
				if(type[i].equals("I") || type[i].equals("O")){
					life[i] = -1;
				}
				pw.println(ver[i] + "\t" + type[i] + "\t" + life[i] + "\t" + String.format("%.1f", axis_x[i]) + "\t" + String.format("%.1f", axis_y[i]));
			}
			for(int i = 0; i < addver.size(); i++){
				pw.println(addver.get(i) + "\t" + addtype.get(i) + "\t" + addlife.get(i) + "\twangtest");
			}
			//pw.print("\n");

			pw.println("--edge");
			//pw.println("#edge_ID\tedge(ver1\tver2)\tport");
			for(int i = 0; i < edge.length; i++){
				pw.println(edge[i] + "\t" + ver1[i] + "\t" + ver2[i] + "\t" + port[i]);
			}
			for(int i = 0; i < addedge.size(); i++){
				pw.println(addedge.get(i) + "\t" + addver1.get(i) + "\t" + addver2.get(i) + "\t" + addport.get(i) + "\twangtest");
			}

			pw.println("--exclusive block");

			pw.close();

		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
