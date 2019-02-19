package com.cg.payroll.services;

import java.util.List;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;

public interface PayrollServices {
	int acceptAssociateDetails(String firstName, String lastName, String emailId, String department, 
			String designation, String pancard, int yearlyInvestmentUnder8oC, int basicSalary, 
			int epf, int companyPf, int accountNumber,String bankName, String ifscCode);
	
	int calculateNetSalary(int associateId) throws AssociateDetailNotFoundException;
	
	Associate getAssociateDetails(int associateId) throws AssociateDetailNotFoundException;
	
	List <Associate> getAllAssociatesDetails();

	int calculateGrossSalary(int associateId) throws AssociateDetailNotFoundException;

	int calculateTaxAmount(int associateId) throws AssociateDetailNotFoundException;
	
	Associate updateAssociate(int associateId, int basicSalary, int epf, int companyPf, int yearlyInvestmentUnder80c)
			throws AssociateDetailNotFoundException;
}
