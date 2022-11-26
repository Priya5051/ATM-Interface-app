package com.main.atm.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.atm.exception.InvalidAmountException;

public class AtmOperation {
	
	public static boolean login(int AtmId, String atmPassword) throws ClassNotFoundException, SQLException
	{
		try
		{
		Connection connection= MySqlDBConnection.dbconnect();
	    PreparedStatement statement=connection.prepareStatement("select * from atmuser where AtmId=?");
		statement.setInt(1, AtmId);
		ResultSet result=statement.executeQuery();
		if (result.next())
		{
			if(result.getString("AtmPin").equals(atmPassword))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("===========================================================================");
			System.out.println("Something Went Wrong!! "); 
			System.out.println("===========================================================================");
		}
		catch(SQLException e)
		{
			System.out.println("===========================================================================");
			System.out.println("AtmId is incorrect!! "); 
			System.out.println("===========================================================================");
		}
		return false;
	}

	public static double balanceCheck(int AtmId) throws ClassNotFoundException, SQLException
	{  double balance=0;
		try
		{
		Connection connection= MySqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmuser where AtmId=?");
		statement.setInt(1, AtmId);
		ResultSet result=statement.executeQuery();
		result.next();
		balance=result.getDouble("AccBal");
		}
		catch(SQLException e)
		{
			System.out.println("===========================================================================");
			System.out.println("Wrong AtmId or Pin!!");
			System.out.println("===========================================================================");
		}
		catch(Exception e)
		{
			System.out.println("===========================================================================");
			System.out.println("Something Went Wrong!!");
			System.out.println("===========================================================================");
		}
		return balance;
	}
	
	public static double withdraw(int AtmId, double withdrawalAmount) throws ClassNotFoundException, SQLException, InvalidAmountException
	{
		Connection connection= MySqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmuser where AtmId=?");
		statement.setInt(1, AtmId);	
		ResultSet result=statement.executeQuery();
		result.next();
		double avilableBalance=result.getDouble("AccBal");
		if(withdrawalAmount<avilableBalance) 
		{
		   double remainingBalance=avilableBalance-withdrawalAmount;
		   statement=connection.prepareStatement("update atmuser set AccBal=? where AtmId=?");
		   statement.setDouble(1, new Double(remainingBalance));
		   statement.setLong(2, AtmId);
		   
		   if(statement.executeUpdate()>0)
		   {
			   return remainingBalance;  
		   }
		   else
		   {
			   return 0;
		   }
		}
		else
		{
			throw new InvalidAmountException("Invalid Withdrawal Amount!!");
		}
	}
	
	public static double deposit(int AtmId, double depositAmount) throws ClassNotFoundException, SQLException
	{
		Connection connection= MySqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmuser where AtmId=?");
		statement.setInt(1, AtmId);
		ResultSet result=statement.executeQuery();
		result.next();
		double avilableBalance=result.getDouble("AccBal");
		double newBalance=avilableBalance+depositAmount;
	   	statement=connection.prepareStatement("update atmuser set AccBal=? where AtmId=?");
		statement.setDouble(1, new Double(newBalance));
		statement.setInt(2, AtmId);
		   
		   if(statement.executeUpdate()>0)
		   {
			   return newBalance;  
		   }
		   else
		   {
			   return 0;
		   }
	
	}
	public static ResultSet checkAccountInfo(int AtmId) throws ClassNotFoundException, SQLException
	{
		Connection connection= MySqlDBConnection.dbconnect();
		PreparedStatement statement=connection.prepareStatement("select * from atmuser where AtmId=?");
		statement.setInt(1, AtmId);	
		ResultSet result=statement.executeQuery();
		if(result.next())
		{
			return result;
		}
		else
		{
			return null;
		}
	}
}
