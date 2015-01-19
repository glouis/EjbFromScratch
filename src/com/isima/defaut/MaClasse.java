package com.isima.defaut;

import com.isima.annotations.ejb;
import com.isima.annotations.persitantContext;
import com.isima.annotations.stateless;
import com.isima.annotations.transactionnal;
import com.isima.interfaces.IMaClasse;

@transactionnal
@stateless
public class MaClasse implements IMaClasse{

	@persitantContext
	EntityManager monManager;
	
	@ejb
	MaClasseSub sub;
	
	public void readDB() {
		// TODO Auto-generated method stub
		
		monManager.persist();
		sub.readDB();
		
	}

	
}
