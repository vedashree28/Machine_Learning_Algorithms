package com.bagging;

import java.util.ArrayList;

public class DecisionTree{

	ArrayList<DataPoint> data;
	ArrayList<DataPoint> dtOut;
	Integer[][] bag ;
	ArrayList<Integer> mAttributes;
	Integer count;

	public DecisionTree(ArrayList<DataPoint> data,ArrayList<DataPoint> dataOut,ArrayList<Integer> mElist,int size){
		this.data=data;
		this.mAttributes = new ArrayList<Integer>(mElist);
		this.bag=new Integer[dataOut.size()][size];
		this.count=size;
		this.dtOut=dataOut;
	}

	public ArrayList<Integer> calcBaggingAccuracy(){
		ArrayList<Integer> TempRecord=new ArrayList<Integer>();
		ArrayList<DataPoint> dataTest = new ArrayList<DataPoint>();
		ArrayList<Integer> values=new ArrayList<Integer>();
		ArrayList<DataPoint> datavalues = new ArrayList<DataPoint>();
		ArrayList<Integer> Final=new ArrayList<Integer>();
		int count0=0,count1=0;
		datavalues=data;
		DTree mTree=null;
		for(int k=0;k<count;k++){

			for (int i = 0; i <= datavalues.size()-1 ; i++)
			{
				int Random = (int)(Math.random()*100);
				TempRecord=datavalues.get(Random).getRecord();
				dataTest.add(new DataPoint(TempRecord,TempRecord.get(TempRecord.size()-1)));
				TempRecord = new ArrayList<Integer>();
			}

			mTree=new DTree(dataTest,mAttributes);
			mTree.buildDecisionTree();

			values=Testdata(mTree,dtOut);

			addData(values,k);
			mTree=null;
			TempRecord = new ArrayList<Integer>();
			dataTest=new ArrayList<DataPoint>();
			datavalues=new ArrayList<DataPoint>();
			datavalues=this.data;


		}	

		for(int m=0;m<dtOut.size();m++){
			for(int k=0;k<count;k++){
				if(bag[m][k]==0){
					count0=count0 +1;
				}
				else{
					count1=count1+1;
				}
			}	
			if(count0>count1){
				Final.add(0);
				//System.out.print("Final:0");
			}
			else{
				//System.out.print("Final:1");
				Final.add(1);
			}
			//System.out.println("");
			count1=0;
			count0=0;
		}

		return Final;

	}

	public void addData(ArrayList<Integer> values,int index){

		for(int j=0;j<dtOut.size();j++){
			bag[j][index]=values.get(j);
		}

	}

	public ArrayList<Integer> Testdata(DTree mTemp, ArrayList<DataPoint> data) {
		/*
		 * Function used to get individual records to Test function
		 * and call it for all records in Test data set.
		 */

		int class_val;

		ArrayList<Integer> Calculated_entropy = new ArrayList<Integer>();

		ArrayList<Integer> Record = new ArrayList<Integer>();
		ArrayList<Integer> newRecord = new ArrayList<Integer>();
		for (int i = 0; i < dtOut.size(); i++) {
			Record = data.get(i).getRecord();
			int last_val=(Record.size()-1);
			for(int l=0;l<last_val;l++){
				newRecord.add(Record.get(l));
			}
			class_val = Test(mTemp, newRecord);
			Calculated_entropy.add(class_val);
			newRecord= new ArrayList<Integer>();
		}

		return Calculated_entropy;
	}


	public int Test(DTree mTree, ArrayList<Integer> record) {
		/*
		 * Test each record and find corresponding class value to it 
		 * Return the calculated class value after testing it for the record on the 
		 * built decision tree
		 */
		int record_values;
		int position = mTree.mSplit;
		int class_val = 0;
		int length2 = 0, length = 0;
		for (int k = 0; k < record.size() - 1; k++) {
			length = 0;
			length2 = 0;
			if (!mTree.isLeafnode) {
				record_values = record.get(position);
				length2 = mTree.mTrees.size();
				for (length = 0; length < mTree.mTrees.size(); length++) {
					if (record_values == mTree.mTrees.get(length).attribute) {
						//Go the subtree for that attribute
						mTree = mTree.mTrees.get(length);

						for (int i = 0; i < mTree.mList.size(); i++) {
							if (!mTree.isLeafnode) {
								position = mTree.mSplit;
							} else {
								//Assign class value and return it 
								class_val = mTree.mChildValue;
								break;
							}
						}
					}
				}
			}
		}
		return class_val;
	}
}