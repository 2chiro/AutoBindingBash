package nishimoto.yoshiken;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String input = args[0];
		String outname = args[1];
		int mode = Integer.parseInt(args[2]);
		FileRead.input(input);
		FileRead.dataArrange();

		int[] ei = FileRead.getEdge();
		int[] v1 = FileRead.getVer1();
		int[] v2 = FileRead.getVer2();
		int[] vt = FileRead.getVertex();
		String[] ty = FileRead.getType();
		int[] lf = FileRead.getLife();
		int a = FileRead.getAdd();
		int s = FileRead.getSub();
		int m = FileRead.getMult();
		int d = FileRead.getDiv();

		int[] st;
		int[] ed;
		int[] ch;
		int mt;

		if(mode == 0){
			LifetimeAnalysis.Basic(ei, v1, v2, ei, ty, lf);
			st = LifetimeAnalysis.getStart();
			ed = LifetimeAnalysis.getEnd();
			ch = LifetimeAnalysis.getKab();
			mt = LifetimeAnalysis.getMaxtime();
			ModuleAllocation.Basic(a, s, m, d, vt, ty, lf);
			RegisterAllocation.Basic(ei, st, ed, ch, mt);
			FileWrite.output(outname);
			FileRead.resetRC();
		}
		else if(mode == 1){
			while(true){
				ConstructTOPs.resetCycleChanger();
				ConstructTOPs.resetNewSDFGListener();
				ModuleAllocation.resetInModule();
				FindCOs.Basic(v1, v2, ty);
				ArrayList<Integer> co = FindCOs.getCOs();
				LifetimeAnalysis.Basic(ei, v1, v2, ei, ty, lf);
				st = LifetimeAnalysis.getStart();
				ed = LifetimeAnalysis.getEnd();
				ch = LifetimeAnalysis.getKab();
				mt = LifetimeAnalysis.getMaxtime();
				System.out.println("mt:" + mt);
				ConstructTOPs.Basic(co, vt, ei, ty, lf, a, s, m, d, mt);
				if(ConstructTOPs.getCycleChanger()){
					mt = mt + 1;
				}
				else{
					break;
				}
			}


			int[] vt1 = null;
			String[] ty1 = null;
			int[] lf1 = null;
			ArrayList<Integer> addver;
			int[] ver1_2 = null;
			int[] ei2 = null;

			int[] st2 = null;
			int[] ed2 = null;

			if(ConstructTOPs.getNewSDFGListener()){
				boolean outsdfg = false;
				if(!outsdfg){
					String outname_sdfg = input;
					if(input.toString().substring(input.toString().length() - 5).equals(".sdfg")){
						outname_sdfg = outname_sdfg.replace(".sdfg", "");
					}
					outname_sdfg = outname_sdfg + "_wang.sdfg";
					FileWrite.newSDFGOutput(outname_sdfg);
					outsdfg = true;
				}

				ArrayList<Integer> ver = new ArrayList<Integer>();
				addver = ConstructTOPs.getAddVer();
				ArrayList<String> type = new ArrayList<String>();
				ArrayList<String> addtype = ConstructTOPs.getAddType();
				ArrayList<Integer> life = new ArrayList<Integer>();
				ArrayList<Integer> addlife = ConstructTOPs.getAddLife();
				ArrayList<Integer> edge = new ArrayList<Integer>();
				ArrayList<Integer> addedge = ConstructTOPs.getAddEdge();
				ArrayList<Integer> ver1  = new ArrayList<Integer>();
				ArrayList<Integer> addver1 = ConstructTOPs.getAddVer1();
				ArrayList<Integer> addver2 = ConstructTOPs.getAddVer2();

				LifetimeAnalysis.Wang(addedge, addver1, addver2, addtype, addlife);
				st2 = LifetimeAnalysis.getAddStart();
				ed2 = LifetimeAnalysis.getAddEnd();

				for(int k = 0; k < vt.length; k++){
					ver.add(vt[k]);
					type.add(ty[k]);
					life.add(lf[k]);
				}
				ver.addAll(addver);
				type.addAll(addtype);
				life.addAll(addlife);
				vt1 = new int[ver.size()];
				ty1 = new String[type.size()];
				lf1 = new int[life.size()];
				for(int n = 0; n < vt1.length; n++){
					vt1[n] = ver.get(n);
					ty1[n] = type.get(n);
					lf1[n] = life.get(n);
				}
				for(int k = 0; k < ei.length; k++){
					edge.add(ei[k]);
					ver1.add(v1[k]);
				}
				edge.addAll(addedge);
				ver1.addAll(addver1);
				ei2 = new int[edge.size()];
				ver1_2 = new int[ver1.size()];
				for(int n = 0; n < ei2.length; n++){
					ei2[n] = edge.get(n);
					ver1_2[n] = ver1.get(n);
				}
			}

			int[][] atop = ConstructTOPs.getAddTOP();
			int[][] stop = ConstructTOPs.getSubTOP();
			int[][] mtop = ConstructTOPs.getMulTOP();
			int[][] dtop = ConstructTOPs.getDivTOP();
			ArrayList<Integer> top = ConstructTOPs.getTOPEdge();
			if(!ConstructTOPs.getNewSDFGListener()){
				ModuleAllocation.Wang(atop, stop, mtop, dtop, a, s, m, d, vt, ty, lf);
				RegisterAllocation.Wang(top, ei, v1, st, ed, ch, mt);
			}
			else{
				ModuleAllocation.Wang(atop, stop, mtop, dtop, a, s, m, d, vt1, ty1, lf1);
				RegisterAllocation.Wang(top, ei2, ver1_2, st2, ed2, ch, mt);
			}
			if(ModuleAllocation.getInModule()){
				System.out.println("エラーが発生しました。演算機が不足しています。リソース制約を見直してください。");
			}
			else{
				FileWrite.output(outname);
				FileRead.resetRC();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start)  + "ms");
	}
}
