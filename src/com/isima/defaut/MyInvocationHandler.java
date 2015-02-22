package com.isima.defaut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import com.isima.annotations.ejb;

public class MyInvocationHandler implements InvocationHandler {

	private Class ejb;
	
	private EntityManager em;
	
	
	public MyInvocationHandler(Class c)
	{
		ejb = c;
		

		
		 Reflections r = new Reflections (c, new FieldAnnotationsScanner());
		 
		
		 System.out.println(r.getFieldsAnnotatedWith(ejb.class).toString()  + " " +  c.getName());
		 Set<Class<?>> d = r.getTypesAnnotatedWith(ejb.class);
		 for (Class a : d)
		 {
			 System.out.println("Annotation founded : " + a.toString());
		 }
		
		// pour tout @ejb --> create
		
		
		
		
		
		// si persistantContext --> new Entity Manager
		
		
		
		
		
		// statefull - stateless
		
		
		
	}
	
	
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println(arg1.toString());
		if (arg1.toString().contains("toString"))
			return this.toString();
		else 
			return "coucou";
	
	}

}