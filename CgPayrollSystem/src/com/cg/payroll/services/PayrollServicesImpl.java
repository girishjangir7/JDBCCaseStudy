package com.cg.payroll.services;

import java.util.List;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;
import com.cg.payroll.util.PayrollDBUtil;

public class PayrollServicesImpl implements PayrollServices{
	double taxableAmount;
	private AssociateDAO associateDAO;
	
	public PayrollServicesImpl() {
		associateDAO = new AssociateDAOImpl();
	}

	public PayrollServicesImpl(AssociateDAO associateDAO) {
		super();
		this.associateDAO = associateDAO;
	}

	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String emailId, String department,
			String designation, String pancard, int yearlyInvestmentUnder8oC, int basicSalary, int epf, int companyPf,
			int accountNumber, String bankName, String ifscCode) {
	
		Associate associate = new Associate( yearlyInvestmentUnder8oC, firstName, lastName, department, designation, emailId,pancard,new Salary(basicSalary, epf, companyPf), new BankDetails(accountNumber, bankName, ifscCode));
		associate = associateDAO.save(associate);// returns associate with ID
		return associate.getAssociateId();
	}

	@Override
	public int calculateGrossSalary(int associateId) throws AssociateDetailNotFoundException {
		Associate associate = getAssociateDetails(associateId);
		associate.salary.setHra((associate.salary.getBasicSalary()*30)/100);
		associate.salary.setConveyenceAllowance((associate.salary.getBasicSalary()*30)/100);
		associate.salary.setOtherAllowance((associate.salary.getBasicSalary()*35)/100);
		associate.salary.setPersonalAllowance((associate.salary.getBasicSalary())/5);
		associate.salary.setGrossSalary(associate.salary.getBasicSalary()+associate.salary.getConveyenceAllowance()+associate.salary.getHra()
											+associate.salary.getOtherAllowance()+associate.salary.getPersonalAllowance()
											+associate.salary.getCompanyPf()+associate.salary.getEpf());
		return associate.salary.getGrossSalary();
	}
	@Override
	public int calculateNetSalary(int associateId) throws AssociateDetailNotFoundException {
		Associate associate = getAssociateDetails(associateId);
		taxableAmount = calculateGrossSalary(associateId)-associate.getYearlyInvestmentUnder8oC();
		int netSalary = (int) (taxableAmount -calculateTaxAmount(associateId))+associate.getYearlyInvestmentUnder8oC()+
				associate.salary.getCompanyPf()+associate.salary.getConveyenceAllowance()+associate.salary.getEpf()+associate.salary.getHra()+
				associate.salary.getMonthlyTax()+associate.salary.getOtherAllowance()+associate.salary.getPersonalAllowance();
		return netSalary;
	}

@Override
public int calculateTaxAmount(int associateId) throws AssociateDetailNotFoundException{
	double taxAmount = 0;
	Associate associate = getAssociateDetails(associateId);
	while(taxableAmount>250000) {
		if(taxableAmount>250000&&taxableAmount<=500000) {
			taxableAmount -= 250000;
			taxAmount = taxAmount+taxableAmount/10;
		}
		if(taxableAmount>500000&&taxableAmount<=1000000) {
			taxableAmount -= 500000;
			taxAmount = taxAmount+taxableAmount/20;
		}
		if(taxableAmount>1000000) {
			taxableAmount -= 1000000;
			taxAmount = taxAmount+taxableAmount/30;
		}
	}
	return (int) taxAmount;
}
	@Override
	public Associate getAssociateDetails(int associateId) throws AssociateDetailNotFoundException {
		Associate associate = associateDAO.findOne(associateId);
		if(associate == null)
			throw new AssociateDetailNotFoundException("Associate details not found for id: "+associateId);
		return associate;
	}

	@Override
	public List<Associate> getAllAssociatesDetails() {
		 return  associateDAO.findAll();
		
	}
	
	@Override
	public Associate updateAssociate(int associateId, int basicSalary, int epf, int companyPf,int yearlyInvestmentUnder80c)
			throws AssociateDetailNotFoundException {
		Associate associate = getAssociateDetails(associateId);
		associate.setYearlyInvestmentUnder8oC(yearlyInvestmentUnder80c);
		associate.salary.setBasicSalary(basicSalary);
		associate.salary.setEpf(epf);
		associate.salary.setCompanyPf(companyPf);
		associate.salary.setHra((associate.salary.getBasicSalary()*30/100));
		associate.salary.setConveyenceAllowance((associate.salary.getBasicSalary()*30/100));
		associate.salary.setOtherAllowance((associate.salary.getBasicSalary()*35/100));
		associate.salary.setPersonalAllowance(associate.salary.getBasicSalary()/5);
		int grossSalary=calculateGrossSalary(associate.getAssociateId());
		associate.salary.setGrossSalary(grossSalary);
		int netSalary=calculateNetSalary(associate.getAssociateId());
		associate.salary.setNetSalary(netSalary);
		int monthlyTax=calculateTaxAmount(associate.getAssociateId())/12;
		associate.salary.setMonthlyTax(monthlyTax);
		return associateDAO.update(associate);
	}
	
}
