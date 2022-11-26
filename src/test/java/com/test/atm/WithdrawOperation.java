package com.test.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import com.main.atm.data.AtmOperation;
import com.main.atm.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

public class WithdrawOperation {
	
	@Test
	public void testCase1() throws ClassNotFoundException, SQLException, InvalidAmountException
	{
		
		assertEquals(10000, AtmOperation.withdraw(1000000,10000));
		
	}

	
	@Test
	public void testCase2() throws ClassNotFoundException, SQLException, InvalidAmountException
	{
		
		assertEquals(25000, AtmOperation.withdraw(3000000,50000));
	}
}