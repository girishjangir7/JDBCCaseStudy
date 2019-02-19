package com.cg.payroll.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;

public class PayrollServicesTestEasyMock {
	private static PayrollServices payrollServices;
	private static AssociateDAO mockAssociateDao;
	
	@BeforeClass
	public static void setUpTestEnv() {
		mockAssociateDao = EasyMock.mock(AssociateDAO.class);
		payrollServices = new PayrollServicesImpl(mockAssociateDao);
	}
	
	@Before
	public void setUpMockData() {
		Associate associate1 = new Associate(101,78000,"Satish","Mahajan","Training","Manager",
				"DTDYF736","satish.mahajan@capgemini.com", new Salary(87738,1800,1800), new BankDetails(12345,"HDFC","4563" ));
		Associate associate2 = new Associate(102,77000,"Satish","Majan","Training","Ex-Manager",
				"DTJHS36","satish.majan@capgemini.com", new Salary(81738,1200,1200), new BankDetails(12332,"HDFC","5453" ));
		Associate associate3 = new Associate(102,77000,"Satish","Majan","Training","Ex-Manager",
				"DTBJK36","satish.maan@capgemini.com", new Salary(81758,1250,1100), new BankDetails(12832,"HDFC","6453" ));
		
		ArrayList<Associate> associatesList = new ArrayList<Associate>();
		associatesList.add(associate1);
		associatesList.add(associate2);
		
		EasyMock.expect(mockAssociateDao.save(associate3)).andReturn(associate3);
		
		EasyMock.expect(mockAssociateDao.findOne(101)).andReturn(associate1);
		EasyMock.expect(mockAssociateDao.findOne(102)).andReturn(associate2);
		EasyMock.expect(mockAssociateDao.findOne(1234)).andReturn(null);
		EasyMock.expect(mockAssociateDao.findAll()).andReturn(associatesList);
		EasyMock.replay(mockAssociateDao);
	}
@Test(expected=AssociateDetailNotFoundException.class)
public void testGetAssociateDetailsForInvalidAssociateId() throws AssociateDetailNotFoundException {
	payrollServices.getAssociateDetails(1234);
	EasyMock.verify(mockAssociateDao.findOne(1234));
}
@Test
public void testGetAssociateDetailsForValidAssociateId() throws AssociateDetailNotFoundException {
	Associate expectedAssociate = new Associate(101,78000,"Satish","Mahajan","Training","Manager",
			"DTDYF736","satish.mahajan@capgemini.com", new Salary(87738,1800,1800), new BankDetails(12345,"HDFC","4563" ));
	Associate actualAssociate = payrollServices.getAssociateDetails(101);
	assertEquals(expectedAssociate, actualAssociate);
	EasyMock.verify(mockAssociateDao.findOne(101));
}

@Test(expected=AssociateDetailNotFoundException.class)
public void testCalculateNetSalaryForInvalidAssociateId() throws AssociateDetailNotFoundException {
	payrollServices.calculateNetSalary(1234);
}
@Test
public void testAcceptAssociateDetailsForValidData() {
	int expectedId=102;
	int actualId=payrollServices.acceptAssociateDetails("Satish","Majan","Training","Ex-Manager",
			"DTJHS36","satish.majan@capgemini.com", 81738,1200,100,1200,12332,"HDFC","5453");
	Assert.assertEquals(expectedId, actualId);
}
@After
public void tearDownTestMockData() {
EasyMock.resetToDefault(mockAssociateDao);
	}

@AfterClass
public static void tearDownTestEnv() {
	
mockAssociateDao = null;
payrollServices = null;
	}

}