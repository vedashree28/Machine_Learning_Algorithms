package com.decsiontrees;

import java.io.*;
import java.util.*;

public class DecisonTree {
	/*
	 * Author-Vedashree Govinda Gowda This class file contains the main
	 * function,print function and the function used to test instances of test
	 * files
	 */
	public static void main(String[] args) {
		/*
		 * T This function contains the code for reading the training and
		 * testing file
		 */
		ArrayList<String> attributeList = new ArrayList<String>(); // Intialize
																	// the
																	// variables
		ArrayList<Integer> maxAttributeCount = new ArrayList<Integer>();
		ArrayList<DataPoint> dataIn = new ArrayList<DataPoint>();
		ArrayList<DataPoint> dataTest = new ArrayList<DataPoint>();
		ArrayList<Integer> Temp = new ArrayList<Integer>();
		ArrayList<Integer> Temp1 = new ArrayList<Integer>();
		int count = 0;
		double value = 0;
		String instances = "";

		try {
			// Use Buffered read to read files
			BufferedReader reader = new BufferedReader(new FileReader("train-1.dat"));//training file
			instances = reader.readLine();
			String[] parts = instances.split("\\s"); // split based on \t single
														// space
			for (int i = 0; i < 6; i++) { 
				// store the value and name ofattributes in two arrays
				attributeList.add(parts[count++]);
				// maxAttributeCount.add(Integer.parseInt(parts[count++]));
				maxAttributeCount.add(i);
			}
			while ((instances = reader.readLine()) != null) {
				parts = instances.split("\\s");
				for (int i = 0; i < 7; i++) {
					//parse the data according to spaces
					Temp.add(Integer.parseInt(parts[i]));
				}

				dataIn.add(new DataPoint(Temp, Temp.get(Temp.size() - 1)));
				Temp = new ArrayList<Integer>();
			}
			// create an instance of type DTree with the complete training
			// samples
			DTree mDTree = new DTree(dataIn, maxAttributeCount);
			DTree mTemp = mDTree;
			// call the function to build the tree
			mDTree.buildDecisionTree();
			reader.close();

			// call print tree to print the build decison tree
			printTree(mTemp, -1);

			attributeList = new ArrayList<String>();
			maxAttributeCount = new ArrayList<Integer>();
			Temp = new ArrayList<Integer>();

			// --------Testingdata--------------------------------------

			BufferedReader reader1 = new BufferedReader(new FileReader("test-1.dat"));//testing file
			// args[1] is testing file
			instances = reader1.readLine();
			String[] parts1 = instances.split("\\s");
			while ((instances = reader1.readLine()) != null) {
				parts1 = instances.split("\\s");
				for (int i = 0; i < 7; i++) {
					Temp.add(Integer.parseInt(parts1[i]));

				}

				dataTest.add(new DataPoint(Temp, Temp.get(Temp.size() - 1)));
				Temp = new ArrayList<Integer>();
			}
			// Return the accuracy after trying the testing data on the tree
			value = Testdata(mTemp, dataIn);
			System.out.println(" ");
			System.out.println("Accuracy on Training set" + "(" + dataIn.size()
					+ ") instances : " + value);
			value = Testdata(mTemp, dataTest);
			System.out.println("Accuracy on Testing set" + "("
					+ dataTest.size() + ") instances : " + value);
		} catch (FileNotFoundException e) { 
			// catch blocks
			System.out.println("File Not Found ");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	static int printTree(DTree mTree, int val) {
		/*
		 * Function used to print the tree built on training data. Traverse the
		 * tree until leaf node to obtain class value.
		 */
		if (mTree.isLeafnode) {

			return mTree.mChildValue;

		} else {
			int eVal = -1;
			++val;
			for (int i = 0; i < mTree.mTrees.size(); i++) {
				System.out.println();
				for (int b = 0; b < val; b++)
					System.out.print("|  ");
				System.out.print("attr" + (mTree.mSplit + 1) + " = "
						+ mTree.mTrees.get(i).attribute + " : ");

				eVal = printTree(mTree.mTrees.get(i), val);
				if (eVal >= 0) {
					System.out.print(eVal);

				}

			}
			return -1;
		}
	}

	static double Testdata(DTree mTemp, ArrayList<DataPoint> data) {
		/*
		 * Function used to get individual records to Test function
		 * and call it for all records in Test data set.
		 */
		double accuracy = 0;
		double count = 0;
		int class_val;
		double total = data.size();
		int temp;
		ArrayList<Integer> Calculated_entropy = new ArrayList<Integer>();
		ArrayList<Integer> Expected_entropy = new ArrayList<Integer>();
		for (int i = 0; i < data.size(); i++) {
			Expected_entropy.add(data.get(i).getClassLabel());
		}
		ArrayList<Integer> Record = new ArrayList<Integer>();
		for (int i = 0; i < data.size(); i++) {
			Record = data.get(i).getRecord();
			Record.remove(Record.size() - 1);
			class_val = Test(mTemp, Record);
			Calculated_entropy.add(class_val);
		}
		for (int i = 0; i < data.size(); i++) {
			if (Expected_entropy.get(i) == Calculated_entropy.get(i))
				count++;
		}
		accuracy = (count / total) * 100;
		return accuracy;
	}

	static int Test(DTree mTree, ArrayList<Integer> record) {
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
