package nishimoto.yoshiken;

import java.util.ArrayList;

public class LifetimeAnalysis {
	private static int[] start;
	private static int[] end;
	private static int[] kab;
	private static int maxtime;

	private static ArrayList<Integer> addedge;
	private static ArrayList<Integer> addlifetime;
	private static ArrayList<String> addtype;
	private static int[] addstart;
	private static int[] addend;

	public static int[] getStart(){
		return start;
	}

	public static int[] getEnd(){
		return end;
	}

	public static int[] getKab(){
		return kab;
	}

	public static int getMaxtime(){
		return maxtime;
	}

	public static ArrayList<Integer> getAddEdge(){
		return addedge;
	}
	public static ArrayList<Integer> getAddLifetime(){
		return addlifetime;
	}
	public static ArrayList<String> getAddType(){
		return addtype;
	}
	public static int[] getAddStart(){
		return addstart;
	}
	public static int[] getAddEnd(){
		return addend;
	}

	public static void Basic(int[] edgeId, int[] ver1, int[] ver2, int[] vertexId, String[] type, int[] lifetime){
		start = new int[edgeId.length]; end = new int[edgeId.length];
		ArrayList<Integer> kaburi = new ArrayList<Integer>();
		int x = 0;
		for(int i = 0; i < edgeId.length; i++){
			for(int k = i+1; k < edgeId.length; k++){
				if(ver1[i] == ver1[k]){
					if(lifetime[ver2[i]] >= lifetime[ver2[k]]){
						kaburi.add(edgeId[k]);
					}
					else{
						boolean pe = false;
						for(int l : kaburi){
							if(l == edgeId[i]){
								pe = true;
								break;
							}
						}
						if(!pe){
							kaburi.add(edgeId[i]);
						}
					}
				}
			}
			if(type[ver1[i]].equals("I")){
				lifetime[ver1[i]]= 0;
			}
			if(type[ver2[i]].equals("O")){
				if(x == 0){
					for(int j = 0; j < lifetime.length; j++){
						if(x < lifetime[j]){
							x = lifetime[j];
						}
					}
					x = x + 1;
				}
				lifetime[ver2[i]] = x;
			}
			end[i] = lifetime[ver2[i]];
			start[i] = lifetime[ver1[i]] + 1;
			System.out.println("start["+i+"]="+ start[i] + " end["+i+"]="+end[i]);
		}
		int ka = 0;
		kab = new int[kaburi.size()];
		for(int kav : kaburi){
			kab[ka] = kav;
			ka = ka + 1;
		}
		maxtime = x;

		//wang用
		addedge = new ArrayList<Integer>();
		for(int i = 0; i < edgeId.length; i++){
			addedge.add(edgeId[i]);
		}
		addlifetime = new ArrayList<Integer>();
		addtype = new ArrayList<String>();
		for(int i = 0; i < lifetime.length; i++){
			addlifetime.add(lifetime[i]);
			addtype.add(type[i]);
		}

	}

	public static void Wang(ArrayList<Integer> edgeId, ArrayList<Integer> ver1, ArrayList<Integer> ver2,
			ArrayList<String> type, ArrayList<Integer> lifetime){
		int x = addedge.size();
		addlifetime.addAll(lifetime);
		addtype.addAll(type);
		addedge.addAll(edgeId);
		addstart = new int[addedge.size()]; addend = new int[addedge.size()];
		int[] lifetime2 = new int[addlifetime.size()];
		for(int i = 0; i < x; i++){
			lifetime2[i] = addlifetime.get(i);
			addstart[i] = start[i];
			addend[i] = end[i];
		}
		int m = 0;
		for(int i = addedge.size() - edgeId.size(); i < addedge.size(); i++){
			if(addtype.get(ver1.get(m)).equals("I")){
				lifetime2[ver1.get(m)] = 0;
			}
			else{
				lifetime2[ver1.get(m)] = addlifetime.get(ver1.get(m));
			}
			if(addtype.get(ver2.get(m)).equals("O") || addtype.get(ver2.get(m)).equals("R")){
				lifetime2[ver2.get(m)] = maxtime;
			}
			else{
				lifetime2[ver2.get(m)] = addlifetime.get(ver2.get(m));
			}
			addend[i] = lifetime2[ver2.get(m)];
			addstart[i] = lifetime2[ver1.get(m)] + 1;
			System.out.println("start["+i+"]="+ addstart[i] + " end["+i+"]="+addend[i]);
			m = m + 1;
		}
	}
}
