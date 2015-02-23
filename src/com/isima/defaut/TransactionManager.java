package com.isima.defaut;

import java.util.ArrayList;
import java.util.List;

import com.isima.annotations.singleton;


public class TransactionManager {

	private static TransactionManager instance;
	
	public static TransactionManager getInstance()
	{
		if (instance == null)
			synchronized (TransactionManager.class) {
				if (instance == null)
					instance = new TransactionManager();
			}
		
		return instance;
	}
	
	
	
	
	
	private  List<Transaction> transactions;
	
	private TransactionManager()
	{
		transactions = new ArrayList<Transaction>();
	}
	
	
	public Transaction getCurrent()
	{
		if (transactions.size() > 0)
			return transactions.get(transactions.size() -1);
		else
			return addNewTransaction();
	}
	
	boolean hasCurrent()
	{
		return (transactions.size() != 0);
	}
	
	Transaction addNewTransaction()
	{
		Transaction t = new Transaction();
		transactions.add(t);
		System.out.println("new Transaction " + t.toString());
		return t;
	}
	
	void remove(Transaction t)
	{
		transactions.remove(t);
	}
	
	
	public void end( Transaction t)
	{
		t.commit();
	}
	
	void rollBack( Transaction t)
	{
		t.rollBack();
	}
	
	
}
