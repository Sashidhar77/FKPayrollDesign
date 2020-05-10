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

class Union extends Days {
	protected int unionId = -1;
	protected int unionJoinDate, unionDuePerWeek;
	protected double totalUnionFare;

	public void joinUnion(int unId, int dues, int memberShipFee){
		double oldUnionFare = getTotalUnionFare();
		unionId = unId;
		unionDuePerWeek = dues;
		unionJoinDate = days;
		totalUnionFare += memberShipFee + oldUnionFare;
	}
	public void addSpecialUnionFare(int uid, double toBeAdded){
		if(unionId == uid)
		totalUnionFare += toBeAdded;
	}
	public double getTotalUnionFare(){
		if(unionId != -1)
			totalUnionFare += unionDuePerWeek*(days - unionJoinDate)/7.0;
		double storeDue = totalUnionFare;
		totalUnionFare = 0;
		unionJoinDate = days;
		return storeDue;
	}
}

class Employee extends Union implements Basic {
	protected int empId, joinDate; 
	private double salary;
	protected String modeOfPayment;

	public Employee(int eId) {
		empId = eId;
		joinDate = days;
		salary = 0;
		modeOfPayment = "Cash";
		totalUnionFare = 0;
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

class DayWager extends Employee {
	private double wagePerDay;

	DayWager(int eId, double rate){
		super(eId);
		wagePerDay = rate;
	}
	public void addMoney(double numOfHours) { 
		double toBeAdded = 0.0;
		toBeAdded = wagePerDay * numOfHours;
		if(numOfHours > 8){ 
			toBeAdded += wagePerDay * numOfHours * (0.5);
		}

		super.addMoney(toBeAdded);
	}
	public void payMoney(){
		if(days % 7 == 0){
			System.out.println(empId + " " + (super.getSalary() - super.getTotalUnionFare()) + " " + modeOfPayment);
			super.setSalaryToZero();
		}
	}
}

class MonthWager extends Employee { 
	private int datePaidTill;
	private double wagePerMonth;

	MonthWager(int eId, double rate){
		super(eId);
		datePaidTill = super.getJoinDate();
		wagePerMonth = rate;
	}
	public void addMoney(int x) {

	}
	public void payMoney(){
		if(days % 30 == 0){
			double toBeAdded = 0.0;
			toBeAdded = wagePerMonth * ((days - datePaidTill)/30.0);
			super.addMoney(toBeAdded);
			datePaidTill = days;
			System.out.println(empId + " " + (super.getSalary() - super.getTotalUnionFare()) + " " + modeOfPayment);
			super.setSalaryToZero();
		}
	}
}

class ComWager extends Employee {
	private double saleValue;
	private double commissionPercentange;

	ComWager(int eId, double saleValue){
		super(eId);
		saleValue = 0;
	}
	public void addMoney(int sale){
		double tobeAdded = (commissionPercentange * sale)/100.0;
		super.addMoney(tobeAdded);
		saleValue = 0;
	}
	public void payMoney(){
		if(days % 14 == 0){
			System.out.println(empId + " " + (super.getSalary() - super.getTotalUnionFare()) + " " + modeOfPayment);
			super.setSalaryToZero();
		}
	}

}

public class project {
	public static void main(String[] args) {
		
		
	}
}