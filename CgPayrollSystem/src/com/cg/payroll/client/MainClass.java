package com.cg.payroll.client;

import java.util.Scanner;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;

public class MainClass {
	public static void main(String args[]) throws AssociateDetailNotFoundException{
		PayrollServices services = new PayrollServicesImpl();

		Scanner sc = new Scanner(System.in);
		int count= 0; 
		System.out.println("-----------------------:Welcome to the PayRoll System:-------------------------"); 
		for(int i=0;i<2;i++) 
		{
			System.out.println("Choose among the given options: ");
			System.out.println("1.Create Associate Account: ");
			System.out.println("2.Get Associate Detail:");
			System.out.println("3.Calculate Net Salary:");
			System.out.println("4.Get All Associate Details: ");
			System.out.println("5.Get Updated Salary Details");

			count=sc.nextInt();

			switch(count) { 
			case 1: System.out.println("Enter the Associate Details:");
			System.out.println("Enter the first name:"); 
			String firstName=sc.next();
			System.out.println("Enter the last name:"); 
			String lastName=sc.next();
			System.out.println("Enter the emailId:"); 
			String emailId = sc.next();
			System.out.println("Enter the department:"); 
			String department = sc.next();
			System.out.println("Enter the yearlyInvestmentUnder8oC:"); 
			int yearlyInvestmentUnder8oC = sc.nextInt();
			System.out.println("Enter the designation:"); 
			String designation = sc.next();
			System.out.println("Enter the pan card: "); 
			String panCard = sc.next();
			System.out.println("Enter the basic salary: "); 
			int basicSalary=sc.nextInt();
			System.out.println("Enter the epf: "); 
			int epf=sc.nextInt();
			System.out.println("Enter the company pf: "); 
			int companyPf=sc.nextInt();
			System.out.println("Enter the bank account number: "); 
			int accountNumber=sc.nextInt(); 
			System.out.println("Enter the bank name: ");
			String bankName=sc.next(); 
			System.out.println("Enter the bank ifsc code: ");
			String ifscCode = sc.next(); 
			int associateId=services.acceptAssociateDetails(firstName, lastName, emailId, department,
					designation, panCard, yearlyInvestmentUnder8oC, basicSalary, epf, companyPf,
					accountNumber, bankName, ifscCode);
			System.out.println("-:Associate Account Created:-");
			System.out.println("Your Associate ID is : "+associateId); 
			break; 
			case 2:System.out.println("Enter the Associate ID:"); 
			associateId=sc.nextInt();
			System.out.println(services.getAssociateDetails(associateId)); 
			break; 
			case 3:System.out.println("Enter the associate ID:");
			associateId = sc.nextInt();
			System.out.println("Net Salary of  "+associateId + " is:"); 
			int salary =services.calculateNetSalary(associateId); 
			System.out.println(salary); 
			break; 
			case 4:
				System.out.println("Details of all Accounts:");
				System.out.println(services.getAllAssociatesDetails()); 
				break;
			case 5:
				System.out.println("Enter the specified details for the account: ");
				System.out.println("Enter Associate Id:");
				int associateId1=sc.nextInt(); 
				System.out.println("Enter Basic Salary");
				int basicSalary1 = sc.nextInt();
				System.out.println("Enter EPF");
				int epf1 = sc.nextInt();
				System.out.println("Enter CompanyPF");
				int companyPf1= sc.nextInt();
				System.out.println("Enter InvestmentUnder80c");
				int yearlyInvestmentUnder80C1 =sc.nextInt();
				System.out.println(services.updateAssociate(associateId1, basicSalary1, epf1, companyPf1,yearlyInvestmentUnder80C1));	
				break;
			default: System.out.println("Invalid Option,Please Try Again!!!!!"); 
			}
			System.out.println("---------------------------------------------------------------------------------------"); 
			System.out.println("Do you want to continue?");
			System.out.println("1.Yes"); 
			System.out.println("2.No"); 
			int counter=sc.nextInt(); 
			if(counter==2) { 
				System.exit(0); 
			} else {
				main(null); 
			}
		}
	}
}
