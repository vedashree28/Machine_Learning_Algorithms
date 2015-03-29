package SingleLayer;

import java.util.ArrayList;

public class Calculate {
	ArrayList<ArrayList<Integer>> values;
	int length;
	double [] weights;
	double treshold;
	int x0;
	double actual=0.0;
	double error=0.0;
	double network_output=0.0;
	Calculate(ArrayList<ArrayList<Integer>> data,int len,double treshold){
		this.values=data;
		this.length=len;
		this.weights=new double[len];
		this.treshold=treshold;
		this.x0=-1;
	}
	
	void calc(int total_no_runs,double learning_rate){
	
		ArrayList<Integer> expected=new ArrayList<Integer>();
		ArrayList<Integer>instance;
	
		for(int i=0;i<values.size();i++){
			/*
			 * Get the column with expected values
			 */
			expected.add(values.get(i).get(length-1));
		}
		
			for(int i=0;i<values.size();i++){
				instance=values.get(i);
			
				//Summation ofw[i]*x[i]
				actual=calculateactualweight(instance);
				
				//Calculate the value with the sigmoid function
				network_output=calc_output(actual);
				
				//calculate error i.e the difference between expected and predicted value
				error=expected.get(i)-network_output;
				
				//Alter the value of w0
				weights[0]= weights[0]+ (learning_rate *(1 - network_output) * network_output * error * x0);
				
				//Alter the value of w[i]
				for(int k=1;k<length;k++){
					
					weights[k]=weights[k]+(learning_rate*error*(network_output*(1-network_output))*instance.get(k-1));

				}
			}
				
	}
	
	double calculateactualweight(ArrayList<Integer> instance){
		/*
		 * Calculate the summation and return the value
		 */
		double weight=0.0;
		int no_of_variables=instance.size()-1;
		weight=weight+(weights[0]*x0);
		for(int i=0;i<no_of_variables;i++){
			weight=weight+(weights[i+1]*instance.get(i));
		}
		return weight;
	}
	
	double calc_output(double input){
		double sig_output=0.0;
		double temp=Math.exp(-input);
		sig_output=1/(1+temp);//sigmoid function
		return sig_output;
	}
	
	void printTrainingoutput(){
		/*
		 * Print the accuracy of percepton 
		 * Compare expected value with calculated value
		 */
		double count=0;
		ArrayList<Integer> expected=new ArrayList<Integer>();
		ArrayList<Integer> instances=new ArrayList<Integer>();
		ArrayList<Integer> output=new ArrayList<Integer>();
		int final_output=0;
		for(int i=0;i<values.size();i++){
			expected.add(values.get(i).get(length-1));
		}
		for(int i=0;i<values.size();i++){
			instances=values.get(i);
			double final_val=weights[0]*x0;
			final_val+=calculateactualweight(instances);
			if(final_val>=treshold)
				final_output=1;
			else
				final_output=0;
			output.add(final_output);
			if(expected.get(i)==output.get(i)){
				count++;
			}
		}	
		double length_data=values.size();
		double percentage=count/length_data*100;
		System.out.println("Accuracy ( "+values.size() +" instances) "+percentage);
	}
	
	void testPercepton(ArrayList<ArrayList<Integer>> testData){
		/*
		 * Test the output of percepton with the expected value 
		 * This function takes the Data values 
		 * It uses the weight array to compute the predicted value
		 * Compares it with expected value
		 */
		double count=0;
		ArrayList<Integer> instance =new  ArrayList<Integer>();
		for(int i=0;i<testData.size();i++){
			instance=testData.get(i);
			double expected_class=weights[0]+x0;
			expected_class+=calculateactualweight(instance);
			double actual_class=instance.get(length-1);
			if(expected_class>=treshold)
				expected_class=1;
			else
				expected_class=0;
			if(expected_class==actual_class){
				count++;
			}
		}
		double length_data=testData.size();
		double percentage=count/length_data*100;
		System.out.println("Accuracy ( "+values.size() +" instances) " +percentage);
	}
}































