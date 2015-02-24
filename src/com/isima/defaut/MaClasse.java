package com.isima.defaut;

import com.isima.annotations.ejb;
import com.isima.annotations.persitantContext;
import com.isima.annotations.singleton;
import com.isima.annotations.stateless;
import com.isima.annotations.transactionnal;
import com.isima.interfaces.IMaClasse;
import com.isima.interfaces.IMaClasseSub;

@transactionnal
@stateless
public class MaClasse implements IMaClasse{

	@persitantContext
	EntityManager monManager;
	
	@ejb
	IMaClasseSub sub;
	
	
	public void readDB() {
		// TODO Auto-generated method stub
		
		monManager.persist();
		sub.readDB();
		
	}
	
	public String subHello() {
	
		return sub.sayHello();
		
	}

	public String sayHello() {
		System.out.println("Hello");
		return "Hello";
	}
}
