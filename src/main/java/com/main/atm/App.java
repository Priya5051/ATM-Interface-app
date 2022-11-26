package com.main.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.atm.data.AtmOperation;
import com.main.atm.exception.InvalidAmountException;

public class App
{
	static BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(System.in));
	
    public static void main( String[] args ) throws NumberFormatException, IOException, ClassNotFoundException, InvalidAmountException, SQLException
    {
        System.out.println( "######################################################################################" );
        System.out.println("\t\t\t\t\t\t\t");
        System.out.println( "***************************  WELCOM TO MY ATM INTERFACE  *****************************" );
        System.out.println("\t\t\t\t\t\t\t");
        System.out.println( "######################################################################################" );
    
        boolean status=false;
		do
		{
		System.out.println(" \t\t\t\t\t\t\t");	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print(" \t\t\t   INSERT THE CARD (ENTER ATMID) : ");
	    int AtmId=Integer.parseInt(bufferedReader.readLine());
	    System.out.println("\t\t\t\t\t\t\t");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print(" \t\t\t\t ENTER THE 4 DIGIT PIN : ");
		String atmPassword=bufferedReader.readLine();
		System.out.println("\t\t\t\t\t\t\t");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("\t\t\t\t\t\t\t");
		status=AtmOperation.login(AtmId, atmPassword);
		//status=AtmOperation.login(AtmId,AtmPin);
		ResultSet holderName=AtmOperation.checkAccountInfo(AtmId);
		
		if(status)
		{
		
			System.out.println( "######################################################################################" );
	        System.out.println("\t\t\t\t\t\t\t");
			System.out.println("**********************************  Hai " + holderName.getString("AtmUserName")+": WELCOME  ******************************");
			System.out.println("\t\t\t\t\t\t\t");
			System.out.println( "######################################################################################" );
		do
		{
			
			
			//System.out.println("");
			
			System.out.println("\t\t\t\t\t\t\t");
			System.out.println("********************************  WELCOME TO YOUR ATM ACCOUNT  ***********************");
			System.out.println("\t\t\t\t\t\t\t");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("   \t\t\t\t       Enter 1--> Balance Check");
			System.out.println("   \t\t\t\t       Enter 2--> Withdraw");
			System.out.println("   \t\t\t\t       Enter 3--> Deposit");
			System.out.println("   \t\t\t\t       Enter 4--> Check Account Details.");
			System.out.println("   \t\t\t\t       Enter 5--> Exit");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("    \t\t\t\t     Enter a valid input between 1 to 5: ");
			int choice=Integer.parseInt(bufferedReader.readLine());
			
			switch(choice)
			{
			
			case 1: System.out.println("\t\t\t\t");
			        ResultSet bankAccount=AtmOperation.checkAccountInfo(AtmId);
					System.out.println("-->-->--> Current available balance is :"+AtmOperation.balanceCheck(AtmId)+" in your "+ bankAccount.getString("BankName")+ " Account");
					break;
					
			case 2: System.out.println("\t\t\t\t");
				    ResultSet bankAccount1=AtmOperation.checkAccountInfo(AtmId);
					System.out.print("Enter Withdraw Amount: ");
					double withdrawalAmount=Double.parseDouble(bufferedReader.readLine());
					
					try
					{
						System.out.println("\t\t\t\t");
						double result=AtmOperation.withdraw(AtmId, withdrawalAmount);						
						System.out.println("-->-->--> Debited Successfull!!"+"from your "+bankAccount1.getString("BankName")+" Account");
						System.out.println("-->-->--> Your Current Balance is:"+result);
					}
					catch(InvalidAmountException e)
					{
						System.out.println("===================================================================================");
						System.out.println("Invalid Withdrawal Amount!!");
						System.out.println("===================================================================================");
					}
					break;
					
			case 3: System.out.println("\t\t\t\t");
				    System.out.print("Enter Deposit Amount: ");
					double depositAmount=Double.parseDouble(bufferedReader.readLine());
					double result=AtmOperation.deposit(AtmId, depositAmount);
					ResultSet bankName2=AtmOperation.checkAccountInfo(AtmId); 
					
					if(result==0)
					{
						System.out.println("==================================================================================");
						System.out.println("Transaction is Unsuccessfull!!");
						System.out.println("==================================================================================");
					}
					else
					{
						System.out.println("\t\t\t\t");
						System.out.println("-->-->--> Transaction is Successfully Done!!"+"To Your "+bankName2.getString("BankName")+" Account");
						System.out.println("-->-->--> Your Current Balance is: "+result);
					}
					break;	
					
			case 4: System.out.println("\t\t\t\t");
					System.out.println("******************************************************************************************");
					System.out.println(">:::>::>:::>:::>:::>:::>:::>:::>:::>:::>  ACCOUNT DETAIL  <:::<::<:::<:::<:::<:::<:::<:::<");
					System.out.println("\t\t\t\t");
					System.out.println("*******************************************************************************************");
					ResultSet accountInfo=AtmOperation.checkAccountInfo(AtmId); 
	        		System.out.println("\t\t\t\t    Account Holder Name  :"+accountInfo.getString("AtmUserName"));	
	        	    System.out.println("\t\t\t\t    Available Balance    :"+accountInfo.getInt("AccBal"));
	        		System.out.println("\t\t\t\t    Bank Name            :"+accountInfo.getString("BankName"));
	        		System.out.println("\t\t\t\t    Account Holder Ph.No :"+accountInfo.getLong("AtmUserMbNum"));
	        		//System.out.println("\t\t  Email :"+accountInfo.getString("email"));
					System.out.println("^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=^=");
					break;	
			
			case 5: status=false;
			        System.out.println("~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^");
					System.out.println("\t\t\t\t\t You Are Succsusfully Exited");
					System.out.println("\t\t\t\t\t Thank You");
					System.out.println("\t\t\t\t\t Please Collect Your Card");
					System.out.println("^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^^~^~^~^~^~^~^~^~^~^~^~^~^~^~^~^");
					break;
	
			default: System.out.println("====================================================================================");
					 System.out.println("Wrong Input!!!");		
					 System.out.println("====================================================================================");
			}	
		}
		while(status);
		}		
		else
		{
			System.out.println("====================================================================================");
			System.out.println("Incorrect Atmid or Pin!!!");
			System.out.println("====================================================================================");
		}
}
while(status);
}

}
