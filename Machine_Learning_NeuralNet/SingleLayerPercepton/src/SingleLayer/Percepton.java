package SingleLayer;
/*
 * This class is used to read files i.e train files and test files
 * 
 * */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Percepton {

	public static void main(String[] args) {
		
			ArrayList<ArrayList<Integer>> in= new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> test= new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> data = new ArrayList<Integer>();
			Calculate ann;
			int length=0;
			/*
			 * Initialize values for all variable;
			 */
			double learning_rate=0.01;
			int total_no_runs=33;
			double treshold=0.5;
			String reader=null;
		    StringTokenizer tokenizer=null;
		    
		    int numtokens=0;
		    if(args.length!=4){
		    	System.out.println("Taking default values");
		    }
		    if(args.length==4)
		    {
		    	learning_rate=Double.parseDouble(args[2]);
		    	total_no_runs=Integer.parseInt(args[3]);
		    }
			try{
				/*
				 * Read train file
				 */
				FileReader input = new FileReader(args[0]);
			    BufferedReader br = new BufferedReader(input);
			    br.readLine();
			    while(true) {
					reader= br.readLine();
					if (reader == null || reader.length() == 0) break;
					tokenizer = new StringTokenizer(reader);
					numtokens = tokenizer.countTokens();
					for (int i=0; i < numtokens; i++) {
						data.add(Integer.parseInt(tokenizer.nextToken()));
					}
					in.add(data);
					data=new ArrayList<Integer>();		
				}
			    length=numtokens;
			    ann=new Calculate(in,length,treshold);

			    
			    /*
			     * Calculate the no_of_runs and call the function with respect
			     * to that
			     */
			    while (total_no_runs> 0) {
			    	if (total_no_runs> in.size()) {
			    		ann.calc(total_no_runs,learning_rate); 
						total_no_runs= total_no_runs - in.size();
					} else {
						ann.calc(in.size(),learning_rate);
						total_no_runs= 0;
					}
				}
			    ann.calc(total_no_runs,learning_rate); 
			    ann.printTrainingoutput();
			    reader=null;
			    tokenizer=null;;
			    data=new ArrayList<Integer>();
			    
			    /*
			     * Read test file
			     */
			    input = new FileReader(args[1]);
			    br = new BufferedReader(input);
			    br.readLine();
			    while(true) {
					reader= br.readLine();
					if (reader == null || reader.length() == 0) break;
					tokenizer = new StringTokenizer(reader);
					numtokens = tokenizer.countTokens();
					for (int i=0; i < numtokens; i++) {
						data.add(Integer.parseInt(tokenizer.nextToken()));
					}
					
					test.add(data);
					data=new ArrayList<Integer>();		
				}
			    ann.testPercepton(test);
			    br.close();
			}
			catch(FileNotFoundException e){
				System.out.println("File not found");
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
		

	}
	
}
