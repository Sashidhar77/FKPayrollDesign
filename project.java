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

class Payroll extends Days{
	private HashMap<Integer, Employee> workers;

	Payroll(){
		workers = new HashMap<Integer, Employee>();
	}
	public void addEmployee(int empType, int eId, double rate){
	 	if(empType == 1){
	 		DayWager newEmp = new DayWager(eId, rate);
	 		workers.put(eId, newEmp);
	 	}
	 	else if(empType == 2){
	 		MonthWager newEmp = new MonthWager(eId, rate);
	 		workers.put(eId, newEmp);
	 	}
	 	else{
	 		ComWager newEmp = new ComWager(eId, rate);
	 		workers.put(eId, newEmp);
	 	}
	 }
	 public void removeEmployee(int eId){
	 	workers.remove(eId);
	 }
	 public void postTimeCard(int eId, double hrs){
	 	workers.get(eId).addMoney(hrs);
	 }
	 public void postSalesReceipt(int eId, double sale){
	 	workers.get(eId).addMoney(sale);
	 }
	 public void runPayroll(){
	 	for(Integer currKey : workers.keySet()){
	 		workers.get(currKey).payMoney();
	 	}
	 }
	 public void joinUnion(int eId, int uId, int dues, int fee){
	 	workers.get(eId).joinUnion(uId, dues, fee);
	 }
	 public void addSpecialUnionFare(int uId, int fee){
		 for(Integer currKey : workers.keySet()){
		 		workers.get(currKey).addSpecialUnionFare(uId, fee);
		 }
	 }
}

public class project {
	public static void main(String[] args) {
		Payroll software = new Payroll();
		Scanner obj = new Scanner(System.in);
		int numOfDays = obj.nextInt();
		for(int count = 0; count < numOfDays; count++){
			String op = obj.nextLine();
			if(op == "add"){
				int type, id;
				double wage;
				type = obj.nextInt();
				id = obj.nextInt();
				wage = obj.nextDouble();
				software.addEmployee(type, id, wage);
			}
			else if(op == "delete"){
				int id;
				id = obj.nextInt();
				software.removeEmployee(id);
			}
			else if(op == "timecard"){
				int id = obj.nextInt();
				double hrs = obj.nextDouble();
				software.postTimeCard(id, hrs);
			}
			else if(op == "salesreceipt"){
				int id = obj.nextInt();
				double amount = obj.nextDouble();
				software.postSalesReceipt(id, amount);
			}
			else if(op == "joinunion"){
				int eId = obj.nextInt();
				int uId = obj.nextInt();
				int dues = obj.nextInt();
				int fee = obj.nextInt();
				software.joinUnion(eId, uId, dues, fee);
			}
			else if(op == "addspecialunionfare"){
				int uId = obj.nextInt();
				int fee = obj.nextInt();
				software.addSpecialUnionFare(uId, fee);
			}

			software.runPayroll();
			Days.updateDays();

		}
	}
}