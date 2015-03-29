package com.decsiontrees;

import java.util.ArrayList;


public class DataPoint {
	/*
	 * Author-Vedashree Govinda Gowda 
	 * This class file contains getter and setter method
	 * The data -Structure is an ArrayList of DataPoints for storing data
	 * DataPoints datastructure contains an ArrayList for storing the values in an individual record
	 * It also contains a field for seperately storing the class value
	 */
	ArrayList<Integer> dataval;
	Integer ClassValue;
	public DataPoint(ArrayList<Integer> list,int value){
		//constructor to intailize data
		dataval=list;
		ClassValue=value;
	}
	public DataPoint(){}
	
	public ArrayList<Integer> getRecord(){
		return dataval;
	}
	
	public int getValueofRecord(int j){
		return dataval.get(j);
	}
	
	public Integer getClassLabel(){
		return ClassValue;
	}
	
	public void setClassLabel(int value){
		ClassValue=value;
	}
	
	public void setDatapoint(ArrayList<Integer> list){
		dataval=list;
	}
} 
