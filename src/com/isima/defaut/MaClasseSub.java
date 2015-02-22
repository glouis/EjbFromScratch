package com.isima.defaut;

import com.isima.annotations.persitantContext;
import com.isima.annotations.stateless;
import com.isima.annotations.transactionnal;

@transactionnal
@stateless
public class MaClasseSub {

	@persitantContext
	EntityManager monManager;
	
	
	public void readDB() {
		// TODO Auto-generated method stub
		
		monManager.persist();
		
		
	}
	
}
