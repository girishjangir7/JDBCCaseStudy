package com.cg.payroll.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PayrollDBUtil {
	
	private static Connection con=null;
	
	public static Connection getConnection() {
		if(con==null) {
			Properties payrollProperties = new Properties();
			try {
				payrollProperties.load(new FileInputStream(new File("./resources//payroll.properties")));
			    
				String driver = payrollProperties.getProperty("driver");
				String url = payrollProperties.getProperty("url");
				String user = payrollProperties.getProperty("user");
				String password = payrollProperties.getProperty("password");
			
				//1st Load Driver
				Class.forName(driver);
				
				//2nd Open Connection
				con = DriverManager.getConnection(url,user,password);
			
			} catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			} 
			
			
		}
		
		
		return con;
	}
	
}
