package com.isima.defaut;

public class Transaction {

	public void begin()
	{
		System.out.println("from Transaction " + this.toString() + ": begin");
	}
	
	public void rollBack()
	{
		System.out.println("from Transaction " + this.toString() + ": rollBack");
	}
	
	public void commit()
	{
		System.out.println("from Transaction " + this.toString() + ": commit");
	}
	
	public void execute()
	{
		System.out.println("from Transaction " + this.toString() + ": execute");
	}
	
	
}
