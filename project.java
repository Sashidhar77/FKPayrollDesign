package learnJava;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

interface Basic {
	void addMoney(double x);
	void payMoney();
}

class Days {
	protected static int days = 1;

	public static void updateDays(){
		days++;
	}
}


class Employee  implements Basic {
	protected int empId, joinDate; 
	private double salary;
	protected String modeOfPayment;

	public Employee(int eId) {
		empId = eId;
		joinDate = days;
		salary = 0;
		modeOfPayment = "Cash";
	}
	public int getEmpId(){
		return empId;
	}
	public double getSalary(){
		return salary;
	}
	public int getJoinDate(){
		return joinDate;
	}
	public void changeEmpId(int newEId){
		empId = newEId;
	}
	public void addMoney(double x){ 
		salary += x;
	}
	public void payMoney(){ }
	public void setSalaryToZero(){
		salary = 0;
	}
	
}



public class project {
	public static void main(String[] args) {
		
		
	}
}