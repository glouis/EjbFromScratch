package com.isima.defaut;

public class Transaction {

	public void begin()
	{
		System.out.println("begin");
	}
	
	public void rollBack()
	{
		System.out.println("rollBack");
	}
	
	public void commit()
	{
		System.out.println("commit");
	}
	
	public void execute()
	{
		System.out.println("execute");
	}
	
	public void stop()
	{
		System.out.println("stopped");
	}
	
	public void resume()
	{
		System.out.println("resume");
	}
}
