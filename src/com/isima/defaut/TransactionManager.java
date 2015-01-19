package com.isima.defaut;

import java.util.ArrayList;
import java.util.List;

import com.isima.annotations.singleton;

@singleton
public class TransactionManager {

	private  List<Transaction> transactions;
	
	public TransactionManager()
	{
		transactions = new ArrayList<Transaction>();
	}
	
	
	public Transaction getCurrent()
	{
		return transactions.get(transactions.size() -1);
	}
	
	boolean hasCurrent()
	{
		return (transactions.size() != 0);
	}
	
	Transaction addNewTransaction()
	{
		Transaction t = new Transaction();
		transactions.add(t);
		return t;
	}
	
	Transaction begin(Transaction t)
	{
		
		t.begin();
		transactions.add(t);
		return t;
		
	}
	
	void rollBack( Transaction t)
	{
		t.rollBack();
	}
	
	void commit(  Transaction t )
	{
		t.commit();
	}
	
	
}
