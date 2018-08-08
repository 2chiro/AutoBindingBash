package nishimoto.yoshiken;

import java.util.ArrayList;

public class RegisterAllocation {
	private static int[][] regs;
	private static int lines;
	private static int exreg;

	public static int[][] getRegs(){
		return regs;
	}

	public static int getLines(){
		return lines;
	}

	public static void setLines(int k){
		lines = k;
	}

	public static int getExreg(){
		return exreg;
	}

	public static void Basic(int[] edgeId, int[] start, int[] end, int[] chou, int maxtime){
		System.out.println("edgeId-length:" + edgeId.length + " maxtime:" + maxtime);
		int t = 0;
		regs = null;
		regs = new int[edgeId.length][maxtime];
		exreg = 0;
		boolean check = false;
		while(!check){
			int h = 0;
			int watermark = 0;

			while(true){
				ArrayList<Integer> xs = new ArrayList<Integer>();
				for(int i = 0; i < edgeId.length; i++){
					if(start[i] > watermark){
						xs.add(i);
					}
				}
				if(xs.isEmpty()){
					break;
				}
				int num = start[xs.get(0)];
				int y = xs.get(0);

				for(int i = 0; i < xs.size(); i++){
					if(num > start[xs.get(i)]){
						num = start[xs.get(i)];
						y = xs.get(i);
					}
				}

				boolean ki = false;

				for(int r = 0; r < chou.length; r++){
					if(chou[r] == y){
						ki = true;
						break;
					}
				}

				if(!ki){
					watermark = end[y];
					int r = edgeId[y];
					regs[t][h] = r;

					System.out.print("regs["+ t +"][" + h + "]=" + regs[t][h] +" ");

					h = h + 1;
				}

				edgeId[y] = -1;
				start[y] = -1;
				end[y] = -1;

				xs.clear();
			}

			for(int p = h; p < maxtime; p++){
				regs[t][p] = -1;
				System.out.print("regs["+ t +"][" + p + "]=" + regs[t][h] +" ");
			}
			System.out.print("\n");

			t = t + 1;

			for(int i = 0; i < edgeId.length; i++){
				if(edgeId[i] != -1){
					check = false;
					break;
				}
				setLines(t);
				check = true;
			}
		}

		for(int i = 0; i < regs.length; i++){
			boolean rt = false;
			for(int j = 0; j < regs[i].length; j++){
				if(regs[i][j] != -1){
					rt = true;
				}
			}
			if(!rt){
				exreg = exreg + 1;
			}
		}
	}

	public static void Wang(ArrayList<Integer> top, int[] edgeId, int[] ver1,int[] start, int[] end, int[] chou, int maxtime){
		for(int i = 0; i < edgeId.length; i++){
			if(top.contains(ver1[i])){
				end[i] = maxtime;
			}
		}
		Basic(edgeId, start, end, chou, maxtime);
	}
}
