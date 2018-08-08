package nishimoto.yoshiken;

import java.util.ArrayList;
import java.util.Collections;

public class FindCOs {
	private static ArrayList<Integer> co = new ArrayList<Integer>();
	private static ArrayList<Integer> ni = new ArrayList<Integer>();
	private static ArrayList<Integer> dep_data1 = new ArrayList<Integer>();
	private static ArrayList<Integer> dep_data2 = new ArrayList<Integer>();

	public static ArrayList<Integer> getCOs(){
		return co;
	}

	public static ArrayList<Integer> getDep_Data1(){
		return dep_data1;
	}
	public static ArrayList<Integer> getDep_Data2(){
		return dep_data2;
	}

	public static void Basic(int[] ver1, int[] ver2, String[] type){
		ArrayList<Integer> ed = new ArrayList<Integer>();

		for(int i = ver2.length - 1; i >= 0; i--){
			boolean a = false;
			if(ed.contains(i) || ni.contains(i)){
				a = true;
			}
			if(!a){
				ArrayList<Integer> left = new ArrayList<Integer>();
				ArrayList<Integer> right = new ArrayList<Integer>();
				ArrayList<Integer> x = new ArrayList<Integer>();
				ArrayList<Integer> y = new ArrayList<Integer>();
				int op = ver2[i];
				if(type[ver1[i]].equals("I")){
					left.add(ver1[i]);
				}
				else{
					left.add(ver1[i]);
					x = OPInfo(ver1[i], ver1, ver2, type);
					left.addAll(x);
				}
				if(type[op].equalsIgnoreCase(type[ver1[i]])){
					System.out.println("dep_data:" + ver1[i] + ", " + op);
					dep_data1.add(op);
					dep_data2.add(ver1[i]);
				}
				for(int j = i - 1; j >= 0; j--){
					if(op == ver2[j]){
						if(type[ver1[j]].equals("I")){
							right.add(ver1[j]);
						}
						else{
							right.add(ver1[j]);
							y = OPInfo(ver1[j], ver1, ver2, type);
							right.addAll(y);
						}
						if(type[op].equalsIgnoreCase(type[ver1[i]])){
							System.out.println("dep_data:" + ver1[i] + ", " + op);
							dep_data1.add(op);
							dep_data2.add(ver1[i]);
						}
						ed.add(j);
						if(!ni.contains(ver1[j])){
							ni.add(ver1[j]);
						}
					}
				}
				if(!ni.contains(ver1[i])){
					ni.add(ver1[i]);
				}
				if(!right.isEmpty()){
					if(type[left.get(0)].equals("I")){
						if(type[right.get(0)].equals("I")){
							if(!co.contains(op)){
								co.add(op);
							}
						}
					}
					boolean w = false;
					for(int k = 0; k < left.size(); k++){
						for(int j = 0; j < right.size(); j++){
							if(left.get(k) == right.get(j)){
								w = true;
								break;
							}
						}
					}
					if(!w){
						if(!co.contains(op)){
							co.add(op);
						}
					}
					else {
						System.out.println("dep_data:" + left.get(0) + ", " + right.get(0));
						dep_data1.add(right.get(0));
						dep_data2.add(left.get(0));
					}
				}
				//デバッグ
				System.out.print("Left");
				for(int k : left){
					System.out.print(" " + k);
				}
				System.out.print("\nRight");
				for(int k : right){
					System.out.print(" " + k);
				}
				System.out.print("\n");
			}

		}
		Collections.sort(co);

		//デバッグ
		int k = 1;
		for(int c : co){
			System.out.println("CO" + k + "=" + c);
			k = k + 1;
		}
	}

	private static ArrayList<Integer> OPInfo(int op, int[] ver1, int[] ver2, String[] type){
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		boolean ne = false;
		for(int i = ver2.length - 1; i >= 0; i--){
			if(op == ver2[i]){
				if(!ne){
					if(type[ver1[i]].equals("I")){
						left.add(ver1[i]);
					}
					else{
						left.add(ver1[i]);
						x = OPInfo(ver1[i], ver1, ver2, type);
						left.addAll(x);
					}
					if(type[op].equalsIgnoreCase(type[ver1[i]])){
						System.out.println("dep_data:" + ver1[i] + ", " + op);
						dep_data1.add(op);
						dep_data2.add(ver1[i]);
					}
					ne = true;
				}
				else{
					if(type[ver1[i]].equals("I")){
						right.add(ver1[i]);
					}
					else{
						right.add(ver1[i]);
						y = OPInfo(ver1[i], ver1, ver2, type);
						right.addAll(y);
					}
					if(type[op].equalsIgnoreCase(type[ver1[i]])){
						System.out.println("dep_data:" + ver1[i] + ", " + op);
						dep_data1.add(op);
						dep_data2.add(ver1[i]);
					}
				}
				if(!ni.contains(ver1[i])){
					ni.add(ver1[i]);
				}
			}
		}

		if(!right.isEmpty()){
			if(type[left.get(0)].equals("I")){
				if(type[right.get(0)].equals("I")){
					if(!co.contains(op)){
						co.add(op);
					}
				}
			}
			boolean w = false;
			for(int i = 0; i < left.size(); i++){
				for(int j = 0; j < right.size(); j++){
					if(left.get(i) == right.get(j)){
						w = true;
						break;
					}
				}
			}
			if(!w){
				if(!co.contains(op)){
					co.add(op);
				}
			}
			else {
				System.out.println("dep_data:" + left.get(0) + ", " + right.get(0));
				dep_data1.add(right.get(0));
				dep_data2.add(left.get(0));
			}
		}
		//デバッグ
		System.out.print("Left");
		for(int i : left){
			System.out.print(" " + i);
		}
		System.out.print("\nRight");
		for(int i : right){
			System.out.print(" " + i);
		}
		System.out.print("\n");

		left.addAll(right);
		return left;
	}
}
