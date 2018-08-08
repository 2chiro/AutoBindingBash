package nishimoto.yoshiken;

import java.util.ArrayList;

public class ConstructTOPs {
	private static int[][] addtop;
	private static int[][] subtop;
	private static int[][] multop;
	private static int[][] divtop;
	private static ArrayList<Integer> toplist;
	private static ArrayList<Integer> topedge;

	private static ArrayList<Integer> addver;
	private static ArrayList<String> addtype;
	private static ArrayList<Integer> addlife;
	private static ArrayList<String> addport;

	private static ArrayList<Integer> addedge;
	private static ArrayList<Integer> addver1;
	private static ArrayList<Integer> addver2;

	private static ArrayList<Integer> dd1;
	private static ArrayList<Integer> dd2;

	private static boolean cyclechanger;

	private static boolean newsdfglistener = false;

	public static int[][] getAddTOP(){
		return addtop;
	}
	public static int[][] getSubTOP(){
		return subtop;
	}
	public static int[][] getMulTOP(){
		return multop;
	}
	public static int[][] getDivTOP(){
		return divtop;
	}
	public static ArrayList<Integer> getTOPEdge(){
		return topedge;
	}

	public static ArrayList<Integer> getAddVer(){
		return addver;
	}
	public static ArrayList<String> getAddType(){
		return addtype;
	}
	public static ArrayList<Integer> getAddLife(){
		return addlife;
	}
	public static ArrayList<String> getAddPort(){
		return addport;
	}

	public static ArrayList<Integer> getAddEdge(){
		return addedge;
	}
	public static ArrayList<Integer> getAddVer1(){
		return addver1;
	}
	public static ArrayList<Integer> getAddVer2(){
		return addver2;
	}

	public static void resetCycleChanger(){
		cyclechanger = false;
	}

	public static boolean getCycleChanger(){
		return cyclechanger;
	}

	public static void resetNewSDFGListener() {
		newsdfglistener = false;
	}

	public static boolean getNewSDFGListener(){
		return newsdfglistener;
	}

	public static void Basic(ArrayList<Integer> co, int[] vertexId, int[] edgeId, String[] type, int[] lifetime, int add, int sub, int mult, int div, int max){
		dd1 = FindCOs.getDep_Data1();
		dd2 = FindCOs.getDep_Data2();
		int av = lifetime.length;
		int ve = 0;
		int ae = edgeId.length;

		int[] addrc = new int[max];
		int[] subrc = new int[max];
		int[] mulrc = new int[max];
		int[] divrc = new int[max];
		for(int ii = 0; ii < max; ii++){
			addrc[ii] = 0;
			subrc[ii] = 0;
			mulrc[ii] = 0;
			divrc[ii] = 0;
		}
		for(int i = 0; i < type.length; i++){
			if(type[i].equals("A")){
				addrc[lifetime[i]] = addrc[lifetime[i]] + 1;
			}
			else if(type[i].equals("S")){
				subrc[lifetime[i]] = subrc[lifetime[i]] + 1;
			}
			else if(type[i].equals("M")){
				mulrc[lifetime[i]] = mulrc[lifetime[i]] + 1;
			}
			else if(type[i].equals("D")){
				divrc[lifetime[i]] = divrc[lifetime[i]] + 1;
			}

		}

		addtop = new int[add][2];
		subtop = new int[sub][2];
		multop = new int[mult][2];
		divtop = new int[div][2];
		toplist = new ArrayList<Integer>();
		topedge = new ArrayList<Integer>();

		int md = add + mult + sub + div;
		int a = 0;
		int m = 0;
		int s = 0;
		int d = 0;

		//phase 1 SDFGからTOPを生成
		for(int j = 0; j < co.size(); j++){
			//加算器（＋）
			if(type[co.get(j)].equals("A") && !toplist.contains(co.get(j)) && a < add){
				for(int k = 0; k < co.size(); k++){
					if(type[co.get(k)].equals("A") && !toplist.contains(co.get(k))){
						if(lifetime[co.get(k)] == lifetime[co.get(j)] + 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								addtop[a][0] = co.get(j);
								addtop[a][1] = co.get(k);
								toplist.add(co.get(j));
								toplist.add(co.get(k));
								topedge.add(co.get(k));
								a = a + 1;
								break;
							}
						}
						else if(lifetime[co.get(k)] == lifetime[co.get(j)] - 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								addtop[a][0] = co.get(k);
								addtop[a][1] = co.get(j);
								toplist.add(co.get(k));
								toplist.add(co.get(j));
								topedge.add(co.get(j));
								a = a + 1;
								break;
							}
						}
					}
				}
			}
			//乗算器（×）
			if(type[co.get(j)].equals("M") && !toplist.contains(co.get(j)) && m < mult){
				for(int k = 0; k < co.size(); k++){
					if(type[co.get(k)].equals("M") && !toplist.contains(co.get(k))){
						if(lifetime[co.get(k)] == lifetime[co.get(j)] + 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								System.out.println("J:" + co.get(j));
								System.out.println("K:" + co.get(k));
								multop[m][0] = co.get(j);
								multop[m][1] = co.get(k);
								toplist.add(co.get(j));
								toplist.add(co.get(k));
								topedge.add(co.get(k));
								m = m + 1;
								break;
							}
						}
						else if(lifetime[co.get(k)] == lifetime[co.get(j)] - 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								System.out.println("J:" + co.get(j));
								System.out.println("K:" + co.get(k));
								multop[m][0] = co.get(k);
								multop[m][1] = co.get(j);
								toplist.add(co.get(k));
								toplist.add(co.get(j));
								topedge.add(co.get(j));
								m = m + 1;
								break;
							}
						}
					}
				}
			}
			//減算器（－）
			if(type[co.get(j)].equals("S") && !toplist.contains(co.get(j)) && s < sub){
				for(int k = 0; k < co.size(); k++){
					if(type[co.get(k)].equals("S") && !toplist.contains(co.get(k))){
						if(lifetime[co.get(k)] == lifetime[co.get(j)] + 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								subtop[s][0] = co.get(j);
								subtop[s][1] = co.get(k);
								toplist.add(co.get(j));
								toplist.add(co.get(k));
								topedge.add(co.get(k));
								s = s + 1;
								break;
							}
						}
						else if(lifetime[co.get(k)] == lifetime[co.get(j)] - 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								subtop[s][0] = co.get(k);
								subtop[s][1] = co.get(j);
								toplist.add(co.get(k));
								toplist.add(co.get(j));
								topedge.add(co.get(j));
								s = s + 1;
								break;
							}
						}
					}
				}
			}
			//除算器（÷）
			if(type[co.get(j)].equals("D") && !toplist.contains(co.get(j)) && d < div){
				for(int k = 0; k < co.size(); k++){
					if(type[co.get(k)].equals("D") && !toplist.contains(co.get(k))){
						if(lifetime[co.get(k)] == lifetime[co.get(j)] + 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								divtop[d][0] = co.get(j);
								divtop[d][1] = co.get(k);
								toplist.add(co.get(j));
								toplist.add(co.get(k));
								topedge.add(co.get(k));
								d = d + 1;
								break;
							}
						}
						else if(lifetime[co.get(k)] == lifetime[co.get(j)] - 1){
							boolean dd = false;
							for(int ii = 0; ii < dd1.size(); ii++){
								if((dd1.get(ii) == co.get(k) && dd2.get(ii) == co.get(j)) || (dd1.get(ii) == co.get(j) && dd2.get(ii) == co.get(k))){
									dd = true;
									break;
								}
							}
							if(!dd){
								divtop[d][0] = co.get(k);
								divtop[d][1] = co.get(j);
								toplist.add(co.get(k));
								toplist.add(co.get(j));
								topedge.add(co.get(j));
								d = d + 1;
								break;
							}
						}
					}
				}
			}
		}
		/*
		//phase1 SDFGからVTOPを生成
		if(toplist.size() <= co.size() && md > a+m+s+d && mode == 1){
			for(int j = 0; j < co.size(); j++){
				//加算器
				if(type[co.get(j)].equals("A") && !toplist.contains(co.get(j)) && a < add){
					if(lifetime[co.get(j)] == 1){
						addtop[a][0] = co.get(j);
						addtop[a][1] = -1;
						toplist.add(co.get(j));
						topedge.add(co.get(j));
						a = a + 1;
					}
				}
				//乗算器
				if(type[co.get(j)].equals("M") && !toplist.contains(co.get(j)) && m < mult){
					if(lifetime[co.get(j)] == 1){
						multop[m][0] = co.get(j);
						multop[m][1] = -1;
						toplist.add(co.get(j));
						topedge.add(co.get(j));
						m = m + 1;
					}
				}
				//減算器
				if(type[co.get(j)].equals("S") && !toplist.contains(co.get(j)) && s < sub){
					if(lifetime[co.get(j)] == 1){
						subtop[s][0] = co.get(j);
						subtop[s][1] = -1;
						toplist.add(co.get(j));
						topedge.add(co.get(j));
						s = s + 1;
					}
				}
				//除算器
				if(type[co.get(j)].equals("D") && !toplist.contains(co.get(j)) && d < div){
					if(lifetime[co.get(j)] == 1){
						divtop[d][0] = co.get(j);
						divtop[d][1] = -1;
						toplist.add(co.get(j));
						topedge.add(co.get(j));
						d = d + 1;
					}
				}
			}
		}
		**/

		addver = new ArrayList<Integer>();
		addtype = new ArrayList<String>();
		addlife = new ArrayList<Integer>();
		addport = new ArrayList<String>();

		addedge = new ArrayList<Integer>();
		addver1 = new ArrayList<Integer>();
		addver2 = new ArrayList<Integer>();

		//phase 2 テスト演算を追加してTOPを生成
		if(toplist.size() <= co.size() && md > a+m+s+d){
			for(int j = 0; j < co.size(); j++){
				//加算器
				if(type[co.get(j)].equals("A") && !toplist.contains(co.get(j)) && a < add){
					if(addrc[lifetime[co.get(j)] - 1] < add && lifetime[co.get(j)] - 1 != 0){
						if(lifetime[co.get(j)] > 1) {
							for(int k = 0; k < 3; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("A");
									addlife.add(lifetime[co.get(j)]-1);
									addtop[a][0] = addver.get(ve);
									addtop[a][1] = co.get(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									toplist.add(co.get(j));
									topedge.add(co.get(j));
									addrc[lifetime[co.get(j)] - 1] = addrc[lifetime[co.get(j)] - 1] + 1;
									a = a + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
						}
					}
					else{
						System.out.println("co.get:" + co.get(j));
						if(lifetime[co.get(j)] + 1 < max){
							if(addrc[lifetime[co.get(j)] + 1] < add){
								for(int k = 0; k < 4; k++){
									addver.add(av);
									if(k == 2){
										addtype.add("A");
										addlife.add(lifetime[co.get(j)]+1);
										addtop[a][0] = co.get(j);
										addtop[a][1] = addver.get(ve);
										addver1.add(addver.get(ve - 2));
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addver2.add(addver.get(ve));
										addport.add("l");
										addport.add("r");
										toplist.add(co.get(j));
										topedge.add(addver.get(ve));
										addrc[lifetime[co.get(j)] + 1] = addrc[lifetime[co.get(j)] + 1] + 1;
										a = a + 1;
									}
									else if(k == 3){
										addedge.add(ae);
										addtype.add("R");
										addlife.add(lifetime[co.get(j)]+2);
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addport.add("c");
										ae = ae + 1;
									}
									else{
										addedge.add(ae);
										addtype.add("I");
										addlife.add(-1);
										ae = ae + 1;
									}
									ve = ve + 1;
									av = av + 1;
								}
								newsdfglistener = true;
							}
						}
					}
				}
				//乗算器
				if(type[co.get(j)].equals("M") && !toplist.contains(co.get(j)) && m < mult){
					if(mulrc[lifetime[co.get(j)] - 1] < mult && lifetime[co.get(j)] - 1 != 0){
						if(lifetime[co.get(j)] > 1) {
							for(int k = 0; k < 3; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("M");
									addlife.add(lifetime[co.get(j)]-1);
									multop[m][0] = addver.get(ve);
									multop[m][1] = co.get(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									toplist.add(co.get(j));
									topedge.add(co.get(j));
									mulrc[lifetime[co.get(j)] - 1] = mulrc[lifetime[co.get(j)] - 1] + 1;
									m = m + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
						}
					}
					else{
						if(lifetime[co.get(j)] + 1 < max){
							if(mulrc[lifetime[co.get(j)] + 1] < mult){
								for(int k = 0; k < 4; k++){
									addver.add(av);
									if(k == 2){
										addtype.add("M");
										addlife.add(lifetime[co.get(j)]+1);
										multop[m][0] = co.get(j);
										multop[m][1] = addver.get(ve);
										addver1.add(addver.get(ve - 2));
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addver2.add(addver.get(ve));
										addport.add("l");
										addport.add("r");
										toplist.add(co.get(j));
										topedge.add(addver.get(ve));
										mulrc[lifetime[co.get(j)] + 1] = mulrc[lifetime[co.get(j)] + 1] + 1;
										m = m + 1;
									}
									else if(k == 3){
										addedge.add(ae);
										addtype.add("R");
										addlife.add(lifetime[co.get(j)]+2);
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addport.add("c");
										ae = ae + 1;
									}
									else{
										addedge.add(ae);
										addtype.add("I");
									addlife.add(-1);
										ae = ae + 1;
									}
									ve = ve + 1;
									av = av + 1;
								}
								newsdfglistener = true;
							}
						}
					}
				}
				//減算器
				if(type[co.get(j)].equals("S") && !toplist.contains(co.get(j)) && s < sub){
					if(subrc[lifetime[co.get(j)] - 1] < sub && lifetime[co.get(j)] - 1 != 0){
						if(lifetime[co.get(j)] > 1) {
							for(int k = 0; k < 3; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("S");
									addlife.add(lifetime[co.get(j)]-1);
									subtop[s][0] = addver.get(ve);
									subtop[s][1] = co.get(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									toplist.add(co.get(j));
									topedge.add(co.get(j));
									subrc[lifetime[co.get(j)] - 1] = subrc[lifetime[co.get(j)] - 1] + 1;
									s = s + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
						}
					}
					else{
						if(lifetime[co.get(j)] + 1 < max){
							if(subrc[lifetime[co.get(j)] + 1] < sub){
								for(int k = 0; k < 4; k++){
									addver.add(av);
									if(k == 2){
										addtype.add("S");
										addlife.add(lifetime[co.get(j)]+1);
										subtop[s][0] = co.get(j);
										subtop[s][1] = addver.get(ve);
										addver1.add(addver.get(ve - 2));
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addver2.add(addver.get(ve));
										addport.add("l");
										addport.add("r");
										toplist.add(co.get(j));
										topedge.add(addver.get(ve));
										subrc[lifetime[co.get(j)] + 1] = subrc[lifetime[co.get(j)] + 1] + 1;
										s = s + 1;
									}
									else if(k == 3){
										addedge.add(ae);
										addtype.add("R");
										addlife.add(lifetime[co.get(j)]+2);
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addport.add("c");
										ae = ae + 1;
									}
									else{
										addedge.add(ae);
										addtype.add("I");
										addlife.add(-1);
										ae = ae + 1;
									}
									ve = ve + 1;
									av = av + 1;
								}
								newsdfglistener = true;
							}
						}
					}
				}
				//除算器
				if(type[co.get(j)].equals("D") && !toplist.contains(co.get(j)) && d < div){
					if(divrc[lifetime[co.get(j)] - 1] < div && lifetime[co.get(j)] - 1 != 0){
						if(lifetime[co.get(j)] > 1) {
							for(int k = 0; k < 3; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("D");
									addlife.add(lifetime[co.get(j)]-1);
									divtop[d][0] = addver.get(ve);
									divtop[d][1] = co.get(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									toplist.add(co.get(j));
									topedge.add(co.get(j));
									divrc[lifetime[co.get(j)] - 1] = divrc[lifetime[co.get(j)] - 1] + 1;
									d = d + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
						}
					}
					else{
						if(lifetime[co.get(j)] + 1 < max){
							if(divrc[lifetime[co.get(j)] + 1] < div){
								for(int k = 0; k < 4; k++){
									addver.add(av);
									if(k == 2){
										addtype.add("D");
										addlife.add(lifetime[co.get(j)]+1);
										divtop[d][0] = co.get(j);
										divtop[d][1] = addver.get(ve);
										addver1.add(addver.get(ve - 2));
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addver2.add(addver.get(ve));
										addport.add("l");
										addport.add("r");
										toplist.add(co.get(j));
										topedge.add(addver.get(ve));
										divrc[lifetime[co.get(j)] + 1] = divrc[lifetime[co.get(j)] + 1] + 1;
										d = d + 1;
									}
									else if(k == 3){
										addedge.add(ae);
										addtype.add("R");
										addlife.add(lifetime[co.get(j)]+2);
										addver1.add(addver.get(ve - 1));
										addver2.add(addver.get(ve));
										addport.add("c");
										ae = ae + 1;
									}
									else{
										addedge.add(ae);
										addtype.add("I");
										addlife.add(-1);
										ae = ae + 1;
									}
									ve = ve + 1;
									av = av + 1;
								}
								newsdfglistener = true;
							}
						}
					}
				}
			}
		}
		/*
		//phase3 exsta VTOPを生成
		if(md > a+m+s+d  && mode == 1){
			while(md > a + m + s + d){
				//加算器
				if(a < add){
					for(int k = 0; k < 3; k++){
						addver.add(av);
						if(k == 2){
							addtype.add("A");
							addlife.add(1);
							addtop[a][0] = addver.get(ve);
							addtop[a][1] = -1;
							addver1.add(addver.get(ve - 2));
							addver1.add(addver.get(ve - 1));
							addver2.add(addver.get(ve));
							addver2.add(addver.get(ve));
							addport.add("l");
							addport.add("r");
							a = a + 1;
						}
						else{
							addedge.add(ae);
							addtype.add("I");
							addlife.add(-1);
							ae = ae + 1;
						}
						ve = ve + 1;
						av = av + 1;
					}
					newsdfglistener = true;
				}
				//乗算器
				if(m < mult){
					for(int k = 0; k < 3; k++){
						addver.add(av);
						if(k == 2){
							addtype.add("M");
							addlife.add(1);
							multop[m][0] = addver.get(ve);
							multop[m][1] = -1;
							addver1.add(addver.get(ve - 2));
							addver1.add(addver.get(ve - 1));
							addver2.add(addver.get(ve));
							addver2.add(addver.get(ve));
							addport.add("l");
							addport.add("r");
							m = m + 1;
						}
						else{
							addedge.add(ae);
							addtype.add("I");
							addlife.add(-1);
							ae = ae + 1;
						}
						ve = ve + 1;
						av = av + 1;
					}
					newsdfglistener = true;
				}
				//減算器
				if(s < sub){
					for(int k = 0; k < 3; k++){
						addver.add(av);
						if(k == 2){
							addtype.add("S");
							addlife.add(1);
							subtop[s][0] = addver.get(ve);
							subtop[s][1] = -1;
							addver1.add(addver.get(ve - 2));
							addver1.add(addver.get(ve - 1));
							addver2.add(addver.get(ve));
							addver2.add(addver.get(ve));
							addport.add("l");
							addport.add("r");
							s = s + 1;
						}
						else{
							addedge.add(ae);
							addtype.add("I");
							addlife.add(-1);
							ae = ae + 1;
						}
						ve = ve + 1;
						av = av + 1;
					}
					newsdfglistener = true;
				}
				//除算器
				if(d < div){
					for(int k = 0; k < 3; k++){
						addver.add(av);
						if(k == 2){
							addtype.add("D");
							addlife.add(1);
							divtop[d][0] = addver.get(ve);
							divtop[d][1] = -1;
							addver1.add(addver.get(ve - 2));
							addver1.add(addver.get(ve - 1));
							addver2.add(addver.get(ve));
							addver2.add(addver.get(ve));
							addport.add("l");
							addport.add("r");
							d = d + 1;
						}
						else{
							addedge.add(ae);
							addtype.add("I");
							addlife.add(-1);
							ae = ae + 1;
						}
						ve = ve + 1;
						av = av + 1;
					}
					newsdfglistener = true;
				}
			}
		}
		*/
		//phase4 2つのextraテスト演算器の追加
		if(md > a+m+s+d){
			while(md > a + m + s + d){
				//加算器
				if(a < add){
					boolean kk = false;
					for(int j = 1; j < max - 1; j++) {
						if(addrc[j] < add && addrc[j + 1] < add) {
							for(int k = 0; k < 7; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("A");
									addlife.add(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									addrc[j] = addrc[j] + 1;
								}
								else if(k == 5){
									addtype.add("A");
									addlife.add(j + 1);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									addtop[a][0] = addver.get(ve - 3);
									addtop[a][1] = addver.get(ve);
									addrc[j + 1] = addrc[j + 1] + 1;
									a = a + 1;
								}
								else if(k == 6){
									addedge.add(ae);
									addtype.add("R");
									addlife.add(j + 2);
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addport.add("c");
									ae = ae + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
							kk = true;
							break;
						}
					}
					if(!kk) {
						cyclechanger = true;
					}
				}
				//乗算器
				if(m < mult){
					boolean kk = false;
					for(int j = 1; j < max - 1; j++) {
						if(mulrc[j] < mult && mulrc[j + 1] < mult) {
							for(int k = 0; k < 7; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("M");
									addlife.add(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									mulrc[j] = mulrc[j] + 1;
								}
								else if(k == 5){
									addtype.add("M");
									addlife.add(j + 1);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									multop[m][0] = addver.get(ve - 3);
									multop[m][1] = addver.get(ve);
									mulrc[j + 1] = mulrc[j + 1] + 1;
									m = m + 1;
								}
								else if(k == 6){
									addedge.add(ae);
									addtype.add("R");
									addlife.add(j + 2);
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addport.add("c");
									ae = ae + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
							kk = true;
							break;
						}
					}
					if(!kk){
						cyclechanger = true;
					}
				}
				//減算器
				if(s < sub){
					boolean kk = false;
					for(int j = 1; j < max - 1; j++) {
						if(subrc[j] < sub && subrc[j + 1] < sub) {
							for(int k = 0; k < 7; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("S");
									addlife.add(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									subrc[j] = subrc[j] + 1;
								}
								else if(k == 5){
									addtype.add("S");
									addlife.add(j + 1);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									subtop[s][0] = addver.get(ve - 3);
									subtop[s][1] = addver.get(ve);
									subrc[j + 1] = subrc[j + 1] + 1;
									s = s + 1;
								}
								else if(k == 6){
									addedge.add(ae);
									addtype.add("R");
									addlife.add(j + 2);
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addport.add("c");
									ae = ae + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
							kk = true;
							break;
						}
					}
					if(!kk) {
						cyclechanger = true;
					}
				}
				//除算器
				if(d < div){
					boolean kk = false;
					for(int j = 1; j < max - 1; j++) {
						if(divrc[j] < div && divrc[j + 1] < div) {
							for(int k = 0; k < 7; k++){
								addver.add(av);
								if(k == 2){
									addtype.add("D");
									addlife.add(j);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									divrc[j] = divrc[j] + 1;
								}
								else if(k == 5){
									addtype.add("D");
									addlife.add(j + 1);
									addver1.add(addver.get(ve - 2));
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addver2.add(addver.get(ve));
									addport.add("l");
									addport.add("r");
									divtop[d][0] = addver.get(ve - 3);
									divtop[d][1] = addver.get(ve);
									divrc[j + 1] = divrc[j + 1] + 1;
									d = d + 1;
								}
								else if(k == 6){
									addedge.add(ae);
									addtype.add("R");
									addlife.add(j + 2);
									addver1.add(addver.get(ve - 1));
									addver2.add(addver.get(ve));
									addport.add("c");
									ae = ae + 1;
								}
								else{
									addedge.add(ae);
									addtype.add("I");
									addlife.add(-1);
									ae = ae + 1;
								}
								ve = ve + 1;
								av = av + 1;
							}
							newsdfglistener = true;
							kk = true;
							break;
						}
					}
					if(!kk){
						cyclechanger = true;
					}
				}
				if(cyclechanger){
					break;
				}
			}
		}

		//デバッグ
		for(int i = 0; i < addtop.length; i++){
			for(int j = 0; j < addtop[i].length; j++){
				System.out.println("addtop["+ i +"]["+ j + "]=" + addtop[i][j]);
			}
		}
		for(int i = 0; i < multop.length; i++){
			for(int j = 0; j < multop[i].length; j++){
				System.out.println("multop["+ i +"]["+ j + "]=" + multop[i][j]);
			}
		}
		for(int i = 0; i < subtop.length; i++){
			for(int j = 0; j < subtop[i].length; j++){
				System.out.println("subtop["+ i +"]["+ j + "]=" + subtop[i][j]);
			}
		}
		for(int i = 0; i < divtop.length; i++){
			for(int j = 0; j < divtop[i].length; j++){
				System.out.println("divtop["+ i +"]["+ j + "]=" + divtop[i][j]);
			}
		}
	}

}
