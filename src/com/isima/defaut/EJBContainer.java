package com.isima.defaut;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import com.isima.annotations.ejb;
import com.isima.annotations.local;





public class EJBContainer {

	List<Object> ejbsNonDispos;
	List<Object> ejbsDispos;
	
	
	public void manage(Object s)
	{

	}
	
	
	public Object create(Class class1)
	{
		
		Object res = null;
		
		// recherche de la classe implémentant l'interface
		Reflections reflections = new Reflections("");

		 Set<Class<?>> allClasses = 
		    reflections.getSubTypesOf(class1);
	
		 for (Class<?> c : allClasses)
		 {
			 System.out.println("class found : " + c.getName());
			 
			 
			 //nb d'ejb == 1
			 if ( c.getInterfaces().length == 1 )
			 {
				 
				// création du proxy
				 
				 Class proxyClass = Proxy.getProxyClass(
						 class1.getClassLoader(), new Class[] { class1 });
				 
				 
				 InvocationHandler handler = new MyInvocationHandler(c);
				 
				 
				 try {
					res =  proxyClass.
					         getConstructor(new Class[] { InvocationHandler.class }).
					         newInstance(new Object[] { handler }) ;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 
			 }
		 }
		
		 
		
		
		
		
		return class1.cast(res);
	}
	
	public Object createEntityManager()
	{
		return "un autre entity manager";
		// ou avec la methode create : un entitymanager change avec la transaction
	}
	
	public Transaction consultTransationManager()
	{
		// ici on finalise le singleton de transaction manager et on le retourne.
		TransactionManager t = new TransactionManager();
		return t.getCurrent();
	}
	
    
	

}
