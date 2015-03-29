package Markov;
/*
 * Author-Vedashree Govinda Gowda(vxg142830)
 */
import java.util.ArrayList;

public class CalculateProb {
	/*
	 * Contains the datastructure to hold the 
	 * path and viterbi table details
	 */
	Double[][] viterbiData;
	Integer[][] path;
	String[] observedData;
	MarkovModel vHmm;

	CalculateProb(String[] data, Integer ns, Double[] ip, Double[][] tp,
			Integer ov, String[] ol, Double[][] ob) {
		observedData = data;
		vHmm = new MarkovModel(ns, ip, tp, ov, ol, ob);
		viterbiData = new Double[observedData.length][vHmm.getNoStates()];
		path = new Integer[observedData.length][vHmm.getNoStates()];
	}

	void ViterbiAlgo() {
		/*
		 * Calls all the method to 
		 * calculate sequence
		 */
		for (int i = 0; i < observedData.length; i++) {
			for (int j = 0; j < vHmm.getNoStates(); j++) {
				viterbiData[i][j] = 0.0;
				path[i][j] = 0;
			}
		}
		int k = getObservedLabel(observedData[0]);
		InitialProb(k);
		StateProb();
		DisplayHmmPath();
	}

	void InitialProb(int k) {
		/*
		 * Calculate the inital probability at
		 * time zero
		 */
		for (int j = 0; j < vHmm.getNoStates(); j++) {
			viterbiData[0][j] += vHmm.getInitalProbVal(j)
					* vHmm.getBetaVal(j, k);
		}
	}

	void StateProb() {
		/*
		 * Calculate to obtain viterbi table details
		 */
		Double[] storeViterbiValues = new Double[vHmm.getNoStates()];
		MaxValue max = new MaxValue();

		for (int i = 1; i < observedData.length; i++) {

			int k = getObservedLabel(observedData[i]);

			for (int j = 0; j < vHmm.getNoStates(); j++) {

				for (int l = 0; l < vHmm.getNoStates(); l++) {

					storeViterbiValues[l] = viterbiData[i - 1][l]
							* vHmm.getTransitionProbVal(l, j)
							* vHmm.getBetaVal(j, k);
				}

				max = getMaximum(storeViterbiValues);
				viterbiData[i][j] = max.getMax();
				path[i][j] = max.getPath();

			}
		}

		System.out.println("-----------------------------");
	}

	public int getObservedLabel(String c) {
		/*
		 * get index of observed Label
		 */
		String[] search = new String[vHmm.getNoOutputVar()];
		search = vHmm.getOutputLabels();// insert code here
		int index = -1;
		for (int i = 0; i < search.length; i++) {
			if (search[i].equals(c)) {
				index = i;
				break;
			}
		}
		return index;
	}

	public MaxValue getMaximum(Double[] s) {
		/*
		 * get maximum value of the column at 
		 * time j
		 */
		MaxValue Max = new MaxValue();
		Double maximum = 0.0;
		int p = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] > maximum) {
				maximum = s[i];
				p = i;
			}
		}
		Max.setMax(maximum);
		Max.setPath(p);
		return Max;
	}

	public void DisplayHmmPath() {
		/*
		 * Display the HMM path
		 */
		ArrayList<Integer> print=new ArrayList<Integer>();
		Double maxProb=0.0;
		Integer maxState=-1;
		int index=0,last=0;
		System.out.print("Sequence: ");
		for(int i=0;i<observedData.length;i++)
			System.out.print(observedData[i]+" ");
		System.out.println("");
		for(int i=0;i<vHmm.getNoStates();i++){
			if(maxProb<viterbiData[observedData.length-1][i]){
				maxProb=viterbiData[observedData.length-1][i];
				index=i;
			}				
		}
		maxState=path[observedData.length-1][index];
		print.add(maxState+1);
		Integer value;
		for (int i = observedData.length-1; i >0; i--) {
			value=path[i][maxState]+1;
			print.add(value);
			maxState=path[i][maxState];
		}
		for(int i=print.size()-1;i>=0;i--){
			System.out.print("S"+print.get(i)+" ");
		}
		System.out.println("");
		System.out.println();
	}
}
