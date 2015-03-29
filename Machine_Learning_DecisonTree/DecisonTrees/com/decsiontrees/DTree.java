package com.decsiontrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class DTree {
	/*
	 * Author-Vedashree Govinda Gowda
	 * This class files contains functions for Calculating entropy,Information Gain 
	 * and also to check whether a node is leaf node
	 * .The build decision tree is called recursively to build Decision tree.
	 * DTree contains the subset of Data read from training file relevant to the node
	 * mAttributes in used to check if the attributes in the list are used in the particular node
	 * Boolean Leaf node is used to check whether the current node is a leaf node or not.
	 * level attribute helps in getting the number of levels the paticular node is in the built tree
	 */
	ArrayList<DataPoint> mList;
	double mEntropy = 0;
	ArrayList<Integer> mAttributes;
	ArrayList<DTree> mTrees;
	boolean isLeafnode = false;
	int mSplit;
	int mChildValue;
	int attribute;
	int level;

	public DTree(ArrayList<DataPoint> mDataPoints, ArrayList<Integer> mEList) {
		/*
		 * constructor to initialize  DTree
		 */
		mList = new ArrayList<DataPoint>(mDataPoints);
		mAttributes = new ArrayList<Integer>(mEList);
		mTrees = new ArrayList<DTree>();
		attribute=0;
		level=0;
	}

	void buildDecisionTree() {
		/*
		 * This function is used to build the tree by calling the 
		 * necessary methods to compute the class value,information gain 
		 * and entropy
		 */
		calculateEntropy();
		int max = calculateIG();
		if (max == -1) {
			this.isLeafnode = true;
			this.mChildValue = getChildValue();
			return;

		} else {
			mAttributes.remove((Object) max);
			split(max);
		}

	}

	int getChildValue() {
		// Get the class value of each node
		ArrayList<Integer> mClassValue = new ArrayList<Integer>();

		for (int b = 0; b < mList.size(); b++) {
		mClassValue.add(mList.get(b).ClassValue);
		}

		ArrayList<Integer> mVals = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
		mVals.add(Collections.frequency(mClassValue, i));
		}

		return mVals.get(0) > mVals.get(1) ? 0 : 1;

	}

	void calculateEntropy() {
		//Calculate Entropy for each subset of the dataset 
		HashSet<Integer> mHashSet = new HashSet<Integer>();
		for (int a = 0; a < mList.size(); a++) {
			mHashSet.add(mList.get(a).dataval.get(mList.get(a).dataval.size() - 1));
		}
		ArrayList<Integer> mPoints = new ArrayList<Integer>(mHashSet);

		double sum = 0;
		ArrayList<Integer> mUnique = new ArrayList<Integer>();
		for (int b = 0; b < mList.size(); b++) {
			mUnique.add(mList.get(b).ClassValue);
		}
		for (int i = 0; i < mPoints.size(); i++) {

			double p = (double) Collections.frequency(mUnique, mPoints.get(i))
					/ (double) mUnique.size();
			sum = sum + (double) (-p * (Math.log(p) / (double) Math.log(2)));
		}

		this.mEntropy = sum;

	}

	int calculateIG() {
		/*
		 * Calculate the Information Gain for each node
		 * 
		 */
		ArrayList<Double> mMaxIG = new ArrayList<Double>();

		for (int a = 0; a < mList.get(0).dataval.size() - 1; a++) {
			ArrayList<Integer> mArrayList = new ArrayList<Integer>();
			HashSet<Integer> mHashSet = new HashSet<Integer>();

			for (int i = 0; i < mList.size(); i++) {

				mArrayList.add(mList.get(i).dataval.get(a));
				mHashSet.add(mList.get(i).dataval.get(a));

			}

			ArrayList<Integer> mPoints = new ArrayList<Integer>(mHashSet);

			double sum = 0;

			double conditionalEntropy = 0;

			for (int i = 0; i < mPoints.size(); i++) {

				ArrayList<Integer> mCount = getcount(mArrayList, mPoints.get(i));
				// 2 is hard coded for unique class value
				for (int z = 0; z < 2; z++) {

					double p = (double) Collections.frequency(mCount, z)
							/ (double) mCount.size();
					if (p != 0) {
						sum = sum
								+ (double) (-p * (Math.log(p) / (double) Math
										.log(2)));
					}
				}

				conditionalEntropy = (double) conditionalEntropy
						+ (sum * ((double) mCount.size() / (double) mList
								.size()));
				
				sum = 0;

			}
			
			mMaxIG.add(mEntropy - conditionalEntropy);
			// System.out.println("IG = " + (mEntropy - conditionalEntropy));

		}
		double IG = 0;
		boolean val=false;
		for (int c = 0; c < mMaxIG.size(); c++) {
			IG = IG + mMaxIG.get(c);
		}

		if (IG == 0) {
			return -1;
		}
		else {
			int max = getMax(mMaxIG);
			return max;
		}

	}

	
	int getMax(ArrayList<Double> mList) {
		//Get the value which is max in an arraylist
		int max = 0;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(max) < mList.get(i)) {
				max = i;
			}
		}

		return max;
	}

	ArrayList<Integer> getcount(ArrayList<Integer> mAList, int attr) {
		//Count the no of instances having a particular class value for an attribute
		ArrayList<Integer> mEachAtttrCount = new ArrayList<Integer>();
		for (int i = 0; i < mAList.size(); i++) {
			if (mAList.get(i) == attr) {
				mEachAtttrCount.add(mList.get(i).ClassValue);
			}
		}

		return mEachAtttrCount;
	}

	void split(int max) {
		/*
		 * Function used to split the dataset
		 */
		HashSet<Integer> mHashSet = new HashSet<Integer>();

		for (int i = 0; i < mList.size(); i++) {

			mHashSet.add(mList.get(i).dataval.get(max));

		}

		ArrayList<Integer> mPoints = new ArrayList<Integer>(mHashSet);
		for (int a = 0; a < mPoints.size(); a++) {
			ArrayList<DataPoint> mChild = getChildData(mPoints.get(a), max);
			DTree mDTree = new DTree(mChild, mAttributes);
			this.mSplit = max;
			this.mTrees.add(mDTree);
			mDTree.attribute=mPoints.get(a);
			mDTree.level=this.level+1;
			mDTree.buildDecisionTree();
		}

	}

	ArrayList<DataPoint> getChildData(int a, int max) {
		/*
		 * Function used to get the child data based on the attribute value 
		 */
		ArrayList<DataPoint> mChild = new ArrayList<DataPoint>();
		
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).dataval.get(max) == a)
				mChild.add(mList.get(i));
		}
		return mChild;
	}
	
	ArrayList<DataPoint> getChildData(){
		return mList;
	}
	
	
}



