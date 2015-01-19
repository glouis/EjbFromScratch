package com.isima.defaut;

public class Transaction {

	void begin()
	{
		System.out.println("begin");
	}
	
	void rollBack()
	{
		System.out.println("rollBack");
	}
	
	void commit()
	{
		System.out.println("commit");
	}
	
	void execute()
	{
		System.out.println("execute");
	}
}
