package com.isima.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.isima.annotations.ejb;
import com.isima.defaut.EJBContainer;
import com.isima.interfaces.IMaClasse;
import com.isima.interfaces.IMaClassePostConstruct;
import com.isima.interfaces.IMaClassePreDestroy;

public class EjbTests {

	//do not forget exceptions management
	
	@ejb
	IMaClasse obj;
		
	@Test
	public void testImplementationEJB() {
		EJBContainer EjbC = EJBContainer.getInstance();
		assertNotNull(EjbC);
	}

	@Test
	public void testBasicInjection() {
		EJBContainer EjbC = EJBContainer.getInstance();
		IMaClasse object =  (IMaClasse) EjbC.create(IMaClasse.class);

		assertEquals(object.sayHello(),"Hello");
		object = null;
		
		
	}
	
	@Test
	public void testAtEJB() {
		EJBContainer EjbC = EJBContainer.getInstance();
		IMaClasse object =  (IMaClasse) EjbC.create(IMaClasse.class);
		System.out.println(object.getClass().getInterfaces().toString());
		System.out.println(IMaClasse.class.toString());
		assertEquals(object.subHello(),"Hello from MaClasseSub");
		

		
	}
	
	@Test
	public void testTransactionnal() {
		EJBContainer EjbC = EJBContainer.getInstance();
		IMaClasse object =  (IMaClasse) EjbC.create(IMaClasse.class);
		System.out.println(object.getClass().getInterfaces().toString());
		System.out.println(IMaClasse.class.toString());
		object.readDB();
		assertNotNull(object);
	
		
	}
	
	
	@Test
	public void testPostConstruct() {
		EJBContainer EjbC = EJBContainer.getInstance();
		IMaClassePostConstruct object =  (IMaClassePostConstruct) EjbC.create(IMaClassePostConstruct.class);

		assertEquals(object.sayHello(),"success");
		
		
	}
	
	@Test
	public void testPreDestroy() {
		EJBContainer EjbC = EJBContainer.getInstance();
		IMaClassePreDestroy object = (IMaClassePreDestroy) EjbC.create(IMaClassePreDestroy.class);
		object.sayHello();
		EjbC.getInstance().viderPool();
	}
	
	
}
