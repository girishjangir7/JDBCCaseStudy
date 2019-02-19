package com.cg.payroll.daoservices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;
import com.cg.payroll.util.PayrollDBUtil;

public class AssociateDAOImpl implements AssociateDAO{

	private static Connection con = PayrollDBUtil.getConnection();
	@Override
	public Associate save(Associate associate) {
		try {
			con.setAutoCommit(false);

			PreparedStatement pstmt1 = con.prepareStatement("insert into Associate ( associateId,yearlyInvestmentUnder8oC,  firstName,  lastName,"
					+ " department, designation,   emailId, panCard) values(Associate_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?)");
			pstmt1.setInt(1,associate.getYearlyInvestmentUnder8oC());
			pstmt1.setString(2,associate.getFirstName());
			pstmt1.setString(3,associate.getLastName());
			pstmt1.setString(4,associate.getDepartment());
			pstmt1.setString(5,associate.getDesignation());
			pstmt1.setString(6,associate.getEmailId());
			pstmt1.setString(7,associate.getPanCard());
			pstmt1.executeUpdate();

			PreparedStatement pstmt2 = con.prepareStatement("select max(associateId) from Associate");
			ResultSet rs = pstmt2.executeQuery();
			rs.next();
			int associateId = rs.getInt(1);

			PreparedStatement pstmt3 = con.prepareStatement("insert into Salary (associateId,basicSalary,epf,companyPf) values (?,?,?,?)");
			pstmt3.setInt(1, associateId);
			pstmt3.setInt(2, associate.getSalary().getBasicSalary());
			pstmt3.setInt(3, associate.getSalary().getEpf());
			pstmt3.setInt(4, associate.getSalary().getCompanyPf());
			pstmt3.executeUpdate();

			PreparedStatement pstmt4 = con.prepareStatement("insert into BankDetails (associateId,accountNumber,bankName,ifscCode) values (?,?,?,?)");
			pstmt4.setInt(1, associateId);
			pstmt4.setInt(2, associate.getBankDetails().getAccountNumber());
			pstmt4.setString(3,associate.getBankDetails().getBankName());
			pstmt4.setString(4,associate.getBankDetails().getIfscCode());
			pstmt4.executeUpdate();

			associate.setAssociateId(associateId);
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return associate;
	}

	@Override
	public Associate findOne(int associateId) {
		try{
		PreparedStatement pstmt1 = con.prepareStatement("select * from Associate where associateId = "+ associateId);
			ResultSet associateRs= pstmt1.executeQuery();
			if(associateRs.next()) {
				int yearlyInvestmentUnder8oC = associateRs.getInt("yearlyInvestmentUnder8oC");
				String firstName = associateRs.getString("firstName");
				String lastName = associateRs.getString("lastName");
				String department = associateRs.getString("department");
				String designation = associateRs.getString("designation");
				String panCard = associateRs.getString("panCard");
				String emailId = associateRs.getString("emailId");

				Associate associate = new Associate(associateId, yearlyInvestmentUnder8oC, firstName, lastName, department, designation, panCard, emailId, null, null);

				PreparedStatement pstmt2 = con.prepareStatement("select * from Salary where associateId = "+ associateId);
				ResultSet salaryRs = pstmt2.executeQuery();
				salaryRs.next();

				int basicSalary = salaryRs.getInt("basicSalary");
				int hra = salaryRs.getInt("hra");
				int conveyenceAllowance = salaryRs.getInt("conveyenceAllowance");
				int otherAllowance = salaryRs.getInt("otherAllowance");
				int personalAllowance = salaryRs.getInt("personalAllowance");
				int monthlyTax = salaryRs.getInt("monthlyTax");
				int epf = salaryRs.getInt("epf");
				int companyPf = salaryRs.getInt("companyPf");
				int grossSalary = salaryRs.getInt("grossSalary");
				int netSalary = salaryRs.getInt("netSalary");

				Salary salary =new Salary(basicSalary, epf, companyPf);
				associate.setSalary(salary);

				PreparedStatement pstmt3 = con.prepareStatement("select * from BankDetails where associateId = "+ associateId);
				ResultSet bankDetailsRs =pstmt3.executeQuery();
				bankDetailsRs.next();
				associate.setBankDetails(new BankDetails(bankDetailsRs.getInt("ACCOUNTNUMBER"), bankDetailsRs.getString("BANKNAME"), bankDetailsRs.getString("IFSCCODE")));
				return associate;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Associate> findAll() {
		ArrayList<Associate> associates = new ArrayList<Associate>();
		try{
			PreparedStatement pstmt1 = con.prepareStatement("select * from Associate ");
				ResultSet associateRs= pstmt1.executeQuery();
				while(associateRs.next()) {
					int associateId = associateRs.getInt("associateId");
					int yearlyInvestmentUnder8oC = associateRs.getInt("yearlyInvestmentUnder8oC");
					String firstName = associateRs.getString("firstName");
					String lastName = associateRs.getString("lastName");
					String department = associateRs.getString("department");
					String designation = associateRs.getString("designation");
					String panCard = associateRs.getString("panCard");
					String emailId = associateRs.getString("emailId");

					Associate associate = new Associate(associateId, yearlyInvestmentUnder8oC, firstName, lastName, department, designation, panCard, emailId, null, null);

					PreparedStatement pstmt2 = con.prepareStatement("select * from Salary where associateId = "+ associateId);
					ResultSet salaryRs = pstmt2.executeQuery();
					salaryRs.next();

					int basicSalary = salaryRs.getInt("basicSalary");
					int hra = salaryRs.getInt("hra");
					int conveyenceAllowance = salaryRs.getInt("conveyenceAllowance");
					int otherAllowance = salaryRs.getInt("otherAllowance");
					int personalAllowance = salaryRs.getInt("personalAllowance");
					int monthlyTax = salaryRs.getInt("monthlyTax");
					int epf = salaryRs.getInt("epf");
					int companyPf = salaryRs.getInt("companyPf");
					int grossSalary = salaryRs.getInt("grossSalary");
					int netSalary = salaryRs.getInt("netSalary");

					Salary salary =new Salary(basicSalary, epf, companyPf);
					associate.setSalary(salary);

					PreparedStatement pstmt3 = con.prepareStatement("select * from BankDetails where associateId = "+ associateId);
					ResultSet bankDetailsRs =pstmt3.executeQuery();
					bankDetailsRs.next();
					associate.setBankDetails(new BankDetails(bankDetailsRs.getInt("ACCOUNTNUMBER"), bankDetailsRs.getString("BANKNAME"), bankDetailsRs.getString("IFSCCODE")));
					
					associates.add(associate);
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return associates;
	}
	
	@Override
    public Associate update(Associate associate) {
	try {con.setAutoCommit(false);
		int associateId = associate.getAssociateId();
		PreparedStatement pstmt1 = con.prepareStatement("update Salary SET basicSalary=?, hra=?, conveyenceAllowance=?,otherAllowance=?, personalAllowance=?, monthlyTax=?, epf=?,companyPf=?, grossSalary=?, netSalary=? where associateId="+associateId);
	    pstmt1.setDouble(1,associate.getSalary().getBasicSalary() );
	    pstmt1.setDouble(2, associate.getSalary().getHra());
	    pstmt1.setDouble(3, associate.getSalary().getConveyenceAllowance());
	    pstmt1.setDouble(4, associate.getSalary().getOtherAllowance());
	    pstmt1.setDouble(5, associate.getSalary().getPersonalAllowance());
	    pstmt1.setDouble(6, associate.getSalary().getMonthlyTax());
	    pstmt1.setDouble(7, associate.getSalary().getEpf());
	    pstmt1.setDouble(8, associate.getSalary().getCompanyPf());
	    pstmt1.setDouble(9, associate.getSalary().getGrossSalary());
	    pstmt1.setDouble(10, associate.getSalary().getNetSalary());
	    pstmt1.executeUpdate();
	    con.commit();
	    return associate;
	}catch (SQLException e) {
		e.printStackTrace();
		try {
			con.rollback();
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	return null;
}

}
