package com.isima.defaut;

import com.isima.annotations.persitantContext;
import com.isima.annotations.stateless;
import com.isima.annotations.transactionnal;
import com.isima.interfaces.IMaClasseSub;

@transactionnal
@stateless
public class MaClasseSub implements IMaClasseSub{

	@persitantContext
	EntityManager monManager;
	
	public MaClasseSub() {
		
	}
	public void readDB() {
		// TODO Auto-generated method stub
		
		monManager.persist();
		
		
	}
	
	public String sayHello() {
		System.out.println("Hello from MaClasseSub");
		return "Hello from MaClasseSub";
	}
	
}
