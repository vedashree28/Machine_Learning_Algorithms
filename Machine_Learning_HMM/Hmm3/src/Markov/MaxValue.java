package Markov;
/*
 * Author-Vedashree Govinda Gowda(vxg142830)
 */
public class MaxValue {
	/*
	 * This class contains memebers to 
	 * store maximum value and the path value
	 */
	private double max;
	private int path;
	
	public MaxValue(){
		max=0.0;
		path=0;
	}
	
	public double getMax(){
		return max;
	}
	
	public int getPath(){
		return path;
	}
	
	public void setMax(double j){
		max=j;
	}
	
	public void setPath(int i){
		path=i;
	}
}

