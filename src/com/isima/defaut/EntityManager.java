package com.isima.defaut;

public class EntityManager {

	public Transaction getTransaction()
	{
		
		return TransactionManager.getInstance().getCurrent();
		
	}
	
	
	public void persist()
	{
		

		System.out.println("EntityManager : persist");
	
		
		//doit appeler transaction.execute
		getTransaction().execute();
		
	}
}
