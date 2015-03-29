package Markov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
/*
 * Author-Vedashree Govinda Gowda(vxg142830)
 */


public class Viterbi {
	/*
	 * This class contains the main method
	 * Read all input lines from both model.dat and test.dat
	 */
	public static void main(String[] args) {
		try{
		
		int no_of_input_lines=1;
		String read=null;
		int no_of_states=0;
		int no_of_output_var=0;
		Double[] initialProb=null;
		String[] outputVar=null;
		ArrayList<Double> temp= new ArrayList<Double>();
		Double [][]transitionProb=null;
		Double [][]outputDistribution=null;
		String []arr1=null;
		String []str1=null;
		FileReader in= new FileReader(System.getProperty("user.dir")
				+ "\\"+ args[0]);
		BufferedReader br=new BufferedReader(in);
		
		while(no_of_input_lines<7){
			if((read=br.readLine())!=null){
				read.trim();
				switch(no_of_input_lines){
				
					case 1:no_of_states= Integer.parseInt(read);
				 		break;
					case 2:if(no_of_states==0){
								throw new IOException("No of States not found");
							}
							initialProb=new Double[no_of_states];
							arr1=new String[no_of_states];
							arr1=read.split("\\s");
							for(int i=0;i<no_of_states;i++){
								initialProb[i]=(Double.parseDouble(arr1[i]));	
							}
						break;
					case 3:
							ArrayList<Double> tempValues=new ArrayList<Double>();
							ArrayList<Double> temp2=new ArrayList<Double>();
								arr1=new String[(no_of_states*no_of_states)];
								arr1=read.split("\\s");
								for(int k=0;k<(no_of_states*no_of_states);k++){
									tempValues.add(Double.parseDouble(arr1[k]));	
								}
								Integer traverse=0;
								transitionProb=new Double[no_of_states][no_of_states];
								   for(int k=0;k<no_of_states;k++){
									  for(int j=0;j<no_of_states;j++){
										  transitionProb[k][j]=tempValues.get(traverse++);
									  }
								   }
						break;
					case 4:no_of_output_var=Integer.parseInt(read);
						break;
					case 5:str1=new String[no_of_output_var];
						   str1=read.split("\\s");
						   
						break;
					case 6:arr1=new String[(no_of_states*no_of_output_var)];
						   arr1=read.split("\\s");
						   traverse=0;
						   outputDistribution=new Double[no_of_states][no_of_output_var];
						   for(int k=0;k<no_of_states;k++){
							  for(int j=0;j<no_of_output_var;j++){
								  outputDistribution[k][j]=Double.parseDouble(arr1[traverse++]);
							  }
						   }
						break;
				}
			}
			no_of_input_lines++;
		}
		
		
		CalculateProb vmm; 
		in=new FileReader(System.getProperty("user.dir")
				+ "\\" + args[1]);
		br=new BufferedReader(in);
		temp=null;
		arr1=null;
		while((read=br.readLine())!=null){
			read.trim();
			if(read.isEmpty())
				continue;
			arr1=read.split("\\s");
			vmm=new CalculateProb(arr1,no_of_states,initialProb,transitionProb,no_of_output_var,str1,outputDistribution);
			vmm.ViterbiAlgo();
		}
		in.close();
		}
		
		catch(IOException  e){
			System.out.println("File Not Found :"+e.getMessage());
		}
	}

}

