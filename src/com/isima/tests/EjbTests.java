package com.isima.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.isima.annotations.ejb;
import com.isima.defaut.EJBContainer;
import com.isima.interfaces.IMaClasse;

public class EjbTests {

	//do not forget exceptions management
	
	@ejb
	IMaClasse obj;
		
	@Test
	public void testImplementationEJB() {
		EJBContainer EjbC = new EJBContainer();
		assertNotNull(EjbC);
	}

	@Test
	public void testNonManaged() {
		EJBContainer EjbC = new EJBContainer();
		Object object = EjbC.create(IMaClasse.class);
		assertNotNull(object);
	}
		
	@Test
	public void testManaged()
	{
		EJBContainer EjbC = new EJBContainer();
		EjbC.consultTransationManager();
		obj.readDB();
	}
	
	
}
