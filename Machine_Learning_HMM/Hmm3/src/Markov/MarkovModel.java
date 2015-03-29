package Markov;

public class MarkovModel {
	private Integer no_states=0;
	private Integer output_var=0;
	private Double []initalProb;
	private Double [][]transitionProb;
	private Double [][]Beta;
	public String []outputLables;
	private Double [][]dataTable;
	
	MarkovModel(Integer ns,Double[]ip,Double [][]tp,Integer ov,String[] ol,Double[][]ob){
		no_states=ns;
		output_var=ov;
		initalProb=ip;
		transitionProb=tp;
		Beta=ob;
		outputLables=ol;
		dataTable=new Double[ns][ov];
	}
	
	public Integer getNoStates(){
		return no_states;
	}
	
	public Integer getNoOutputVar(){
		return output_var;
	}
	
	public Double[] getInitalProb(){
		return initalProb;
	}
	
	public Double[][] gettransitProb(){
		return transitionProb;
	}
	
	public Double getTransitionProbVal(int i,int j){
		return transitionProb[i][j];
	}
	
	public Double getInitalProbVal(int i){
		return initalProb[i];
	}
	
	public Double[][] getBeta(){
		return Beta;
	}
	
	public Double getBetaVal(int i,int j){
		return Beta[i][j];
	}
	
	public String[] getOutputLabels(){
		return outputLables;
	}
	
}

