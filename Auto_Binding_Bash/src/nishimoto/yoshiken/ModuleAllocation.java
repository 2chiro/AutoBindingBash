package nishimoto.yoshiken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleAllocation {
	private static int[][] addops;
	private static int[][] subops;
	private static int[][] multops;
	private static int[][] divops;

	private static boolean in_module;

	public static int[][] getAddops(){
		return addops;
	}
	public static int[][] getSubops(){
		return subops;
	}
	public static int[][] getMultops(){
		return multops;
	}
	public static int[][] getDivops(){
		return divops;
	}

	public static void resetInModule(){
		in_module = false;
	}
	public static boolean getInModule(){
		return in_module;
	}

	public static void Basic(int add, int sub, int mult, int div, int[] vertexId, String[] type, int[] lifetime){
		Map<Integer, Integer> addmap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> submap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> multmap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> divmap = new HashMap<Integer, Integer>();
		int endtime = 0;
		for(int i = 0; i < vertexId.length; i++){
			if("A".equals(type[i])){
				addmap.put(vertexId[i], lifetime[i]);
			}
			else if("S".equals(type[i])){
				submap.put(vertexId[i], lifetime[i]);
			}
			else if("M".equals(type[i])){
				multmap.put(vertexId[i], lifetime[i]);
			}
			else if("D".equals(type[i])){
				divmap.put(vertexId[i], lifetime[i]);
			}
			if(endtime < lifetime[i]){
				endtime = lifetime[i];
			}
		}

		int[] addlist = new int[addmap.size()]; int[] addtime = new int[addmap.size()];
		int[] sublist = new int[submap.size()]; int[] subtime = new int[submap.size()];
		int[] multlist = new int[multmap.size()]; int[] multtime = new int[multmap.size()];
		int[] divlist = new int[divmap.size()]; int[] divtime = new int[divmap.size()];

		//加算割当-修正
		if(add != 0){
			addops = new int[add][endtime];
			int k = 0;
			for(int key : addmap.keySet()){
				addlist[k] = key;
				addtime[k] = addmap.get(key);
				k = k + 1;
			}
			for(int j = 1; j <= endtime; j++){
				int m = 0;
				for(int n = 0; n < addlist.length; n++){
					if(addtime[n] == j){
						addops[m][j-1] = addlist[n];
						addlist[n] = -1; addtime[n] = -1;
						m = m + 1;
					}
					if(add < m + 1){
						//エラー処理
					}
				}
				for(int c = m; c < addops.length; c++){
					addops[c][j-1] = -1;
				}
			}
		}

		//減算割当-修正
		if(sub != 0){
			subops = new int[sub][endtime];
			int k = 0;
			for(int key : submap.keySet()){
				sublist[k] = key;
				subtime[k] = submap.get(key);
				k = k + 1;
			}
			for(int j = 1; j <= endtime; j++){
				int m = 0;
				for(int n = 0; n < sublist.length; n++){
					if(subtime[n] == j){
						subops[m][j-1] = sublist[n];
						sublist[n] = -1; subtime[n] = -1;
						m = m + 1;
					}
					if(sub < m + 1){
						//エラー処理
					}
				}
				for(int c = m; c < subops.length; c++){
					subops[c][j-1] = -1;
				}
			}
		}

		//乗算割当-修正
		if(mult != 0){
			multops = new int[mult][endtime];
			int k = 0;
			for(int key : multmap.keySet()){
				multlist[k] = key;
				multtime[k] = multmap.get(key);
				k = k + 1;
			}
			for(int j = 1; j <= endtime; j++){
				int m = 0;
				for(int n = 0; n < multlist.length; n++){
					if(multtime[n] == j){
						multops[m][j-1] = multlist[n];
						multlist[n] = -1; multtime[n] = -1;
						m = m + 1;
					}
					if(mult < m + 1){
						//エラー処理
					}
				}
				for(int c = m; c < multops.length; c++){
					multops[c][j-1] = -1;
				}
			}
		}

		//除算割当-修正
		if(div != 0){
			divops = new int[div][endtime];
			int k = 0;
			for(int key : divmap.keySet()){
				divlist[k] = key;
				divtime[k] = divmap.get(key);
				k = k + 1;
			}
			for(int j = 1; j <= endtime; j++){
				int m = 0;
				for(int n = 0; n < divlist.length; n++){
					if(divtime[n] == j){
						divops[m][j-1] = divlist[n];
						divlist[n] = -1; divtime[n] = -1;
						m = m + 1;
					}
					if(div < m + 1){
						//エラー処理
					}
				}
				for(int c = m; c < divops.length; c++){
					divops[c][j-1] = -1;
				}
			}
		}
	}

	public static void Wang(int[][] addtop, int[][] subtop, int[][] multop, int[][] divtop,
			int add, int sub, int mult, int div, int[] vertexId, String[] type, int[] lifetime){
		Map<Integer, Integer> addmap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> submap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> multmap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> divmap = new HashMap<Integer, Integer>();
		int endtime = 0;
		for(int i = 0; i < vertexId.length; i++){
			if("A".equals(type[i])){
				addmap.put(vertexId[i], lifetime[i]);
			}
			else if("S".equals(type[i])){
				submap.put(vertexId[i], lifetime[i]);
			}
			else if("M".equals(type[i])){
				multmap.put(vertexId[i], lifetime[i]);
			}
			else if("D".equals(type[i])){
				divmap.put(vertexId[i], lifetime[i]);
			}
			if(endtime < lifetime[i]){
				endtime = lifetime[i];
			}
		}

		int[] addlist; int[] addtime;
		int[] sublist; int[] subtime;
		int[] multlist; int[] multtime;
		int[] divlist; int[] divtime;

		ArrayList<Integer> addaltop = new ArrayList<Integer>();
		ArrayList<Integer> subaltop = new ArrayList<Integer>();
		ArrayList<Integer> mulaltop = new ArrayList<Integer>();
		ArrayList<Integer> divaltop = new ArrayList<Integer>();

		//加算割当
		if(add != 0){
			addops = new int[add][endtime];
			for(int i = 0; i < addops.length; i++){
				for(int j = 0; j < addops[i].length; j++){
					addops[i][j] = -1;
				}
			}
			for(int i = 0; i < addtop.length; i++){
				//int life = 0;
				for(int j = 0; j < addtop[i].length; j++){
					if(addtop[i][j] != -1){
						addops[i][lifetime[addtop[i][j]] - 1] = addtop[i][j];
						addaltop.add(addtop[i][j]);
					}
					//if(j == 0){
					//	life = lifetime[addtop[i][j]] - 1;
					//}
				}
				//for(int j = 0; j < life; j++){
				//	addops[i][j] = -2;
				//}
			}
			addlist = new int[addmap.size() - addaltop.size()];
			addtime = new int[addmap.size() - addaltop.size()];

			int k = 0;
			for(int key : addmap.keySet()){
				if(!addaltop.contains(key)){
					addlist[k] = key;
					addtime[k] = addmap.get(key);
					k = k + 1;
				}
			}
			for(int n = 0; n < addlist.length; n++){
				for(int j = 1; j <= endtime; j++){
					int m = 0;
					if(addtime[n] == j){
						boolean d = false;
						while(!d){
							if(addops[m][j-1] == -1){
								addops[m][j-1] = addlist[n];
								addlist[n] = -1; addtime[n] = -1;
								d = true;
							}
							else{
								m = m + 1;
							}
							if(add < m + 1){
								System.out.println("加算器が不足しています。");
								in_module = true;
								d = true;
							}
						}
					}
				}
			}
		}

		//減算割当
		if(sub != 0){
			subops = new int[sub][endtime];
			for(int i = 0; i < subops.length; i++){
				for(int j = 0; j < subops[i].length; j++){
					subops[i][j] = -1;
				}
			}
			for(int i = 0; i < subtop.length; i++){
				//int life = 0;
				for(int j = 0; j < subtop[i].length; j++){
					if(subtop[i][j] != -1){
						subops[i][lifetime[subtop[i][j]] - 1] = subtop[i][j];
						subaltop.add(subtop[i][j]);
					}
					//if(j == 0){
					//	life = lifetime[subtop[i][j]] - 1;
					//}
				}
				//for(int j = 0; j < life; j++){
				//	subops[i][j] = -2;
				//}
			}
			sublist = new int[submap.size() - subaltop.size()];
			subtime = new int[submap.size() - subaltop.size()];

			int k = 0;
			for(int key : submap.keySet()){
				if(!subaltop.contains(key)){
					sublist[k] = key;
					subtime[k] = submap.get(key);
					k = k + 1;
				}
			}
			for(int n = 0; n < sublist.length; n++){
				for(int j = 1; j <= endtime; j++){
					int m = 0;
					if(subtime[n] == j){
						boolean d = false;
						while(!d){
							if(subops[m][j-1] == -1){
								subops[m][j-1] = sublist[n];
								sublist[n] = -1; subtime[n] = -1;
								d = true;
							}
							else{
								m = m + 1;
							}
							if(sub < m + 1){
								System.out.println("減算器が不足しています。");
								in_module = true;
								d = true;
							}
						}
					}
				}
			}
		}

		//乗算割当
		if(mult != 0){
			multops = new int[mult][endtime];
			for(int i = 0; i < multops.length; i++){
				for(int j = 0; j < multops[i].length; j++){
					multops[i][j] = -1;
				}
			}
			for(int i = 0; i < multop.length; i++){
				//int life = 0;
				for(int j = 0; j < multop[i].length; j++){
					if(multop[i][j] != -1){
						multops[i][lifetime[multop[i][j]] - 1] = multop[i][j];
						mulaltop.add(multop[i][j]);
					}
					//if(j == 0){
					//	life = lifetime[multop[i][j]] - 1;
					//}
				}
				//for(int j = 0; j < life; j++){
				//	multops[i][j] = -2;
				//}
			}
			multlist = new int[multmap.size() - mulaltop.size()];
			multtime = new int[multmap.size() - mulaltop.size()];

			int k = 0;
			for(int key : multmap.keySet()){
				if(!mulaltop.contains(key)){
					multlist[k] = key;
					multtime[k] = multmap.get(key);
					k = k + 1;
				}
			}
			for(int n = 0; n < multlist.length; n++){
				for(int j = 1; j <= endtime; j++){
					int m = 0;
					if(multtime[n] == j){
						boolean d = false;
						while(!d){
							if(multops[m][j-1] == -1){
								multops[m][j-1] = multlist[n];
								multlist[n] = -1; multtime[n] = -1;
								d = true;
							}
							else{
								m = m + 1;
							}
							if(mult < m + 1){
								System.out.println("乗算器が不足しています。");
								in_module = true;
								d = true;
							}
						}
					}
				}
			}
		}

		//除算割当
		if(div != 0){
			divops = new int[div][endtime];
			for(int i = 0; i < divops.length; i++){
				for(int j = 0; j < divops[i].length; j++){
					divops[i][j] = -1;
				}
			}
			for(int i = 0; i < divtop.length; i++){
				//int life = 0;
				for(int j = 0; j < divtop[i].length; j++){
					if(divtop[i][j] != -1){
						divops[i][lifetime[divtop[i][j]] - 1] = divtop[i][j];
						divaltop.add(divtop[i][j]);
					}
				//	if(j == 0){
				//		life = lifetime[divtop[i][j]] - 1;
				//	}
				}
				//for(int j = 0; j < life; j++){
				//	divops[i][j] = -2;
				//}
			}
			divlist = new int[divmap.size() - divaltop.size()];
			divtime = new int[divmap.size() - divaltop.size()];

			int k = 0;
			for(int key : divmap.keySet()){
				if(!divaltop.contains(key)){
					divlist[k] = key;
					divtime[k] = divmap.get(key);
					k = k + 1;
				}
			}
			for(int n = 0; n < divlist.length; n++){
				for(int j = 1; j <= endtime; j++){
					int m = 0;
					if(divtime[n] == j){
						boolean d = false;
						while(!d){
							if(divops[m][j-1] == -1){
								divops[m][j-1] = divlist[n];
								divlist[n] = -1; divtime[n] = -1;
								d = true;
							}
							else{
								m = m + 1;
							}
							if(div < m + 1){
								System.out.println("除算器が不足しています。");
								in_module = true;
								d = true;
							}
						}
					}
				}
			}
		}
		//デバッグ
		if(add != 0) {
			for(int i = 0; i < addops.length; i++){
				for(int j = 0; j < addops[i].length; j++){
					System.out.println("addops[" + i + "][" + j + "]=" + addops[i][j]);
				}
			}
		}
		if(mult != 0) {
			for(int i = 0; i < multops.length; i++){
				for(int j = 0; j < multops[i].length; j++){
					System.out.println("mulops[" + i + "][" + j + "]=" + multops[i][j]);
				}
			}
		}
		if(sub != 0) {
			for(int i = 0; i < subops.length; i++){
				for(int j = 0; j < subops[i].length; j++){
					System.out.println("subops[" + i + "][" + j + "]=" + subops[i][j]);
				}
			}
		}
		if(div != 0) {
			for(int i = 0; i < divops.length; i++){
				for(int j = 0; j < divops[i].length; j++){
					System.out.println("divops[" + i + "][" + j + "]=" + divops[i][j]);
				}
			}
		}
	}
}
