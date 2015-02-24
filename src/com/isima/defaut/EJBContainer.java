package com.isima.defaut;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;











import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.isima.annotations.ejb;
import com.isima.annotations.local;
import com.isima.annotations.preDestroy;
import com.isima.annotations.stateless;
import com.isima.interfaces.IMaClasse;



public class EJBContainer {

	private static EJBContainer instance;
	
	public static EJBContainer getInstance()
	{
		
		if (instance == null)
			synchronized (EJBContainer.class) {
				if (instance == null)
					instance = new EJBContainer();
			}
		
		return instance;
	}
	
	private List < Object > ejbsNonDispos;
	private List < Object>  ejbsDispos;
	
	private HashMap<Class<?>, Integer> registre;
	
	
	int NB_EJB = 1;
	
	
	private EJBContainer()
	{
		ejbsNonDispos = new ArrayList<>();
		ejbsDispos = new ArrayList<>();
		registre = new HashMap<>();
		
	}
	
	
	public boolean isDispo(Object obj)
	{
		boolean res = false;
		
		if (ejbsDispos.contains(obj))
		{
			res = true;
		}
		
		return res;
	}
	
	
	public void manage(Object obj)
	{
		ejbsDispos.add(obj);
	}
	
	
	public void viderPool()
	{
		List<Object> objects = new ArrayList<Object>();
		for (Object o : ejbsDispos)
		{
			objects.add(o);
		}
		for (Object o : ejbsNonDispos)
		{
			objects.add(o);
		}
		
		for (Object o : objects)
		{
			detach(o);
		}
	}
	
	
	public void detach(Object obj)
	{
		
		System.out.println(obj.getClass().getName());
		Reflections r1 = new Reflections (obj.getClass(),new MethodAnnotationsScanner());
		Set<Method> preDestroys = r1.getMethodsAnnotatedWith(preDestroy.class);
		
		for (Method methode : obj.getClass().getDeclaredMethods())
		{
			if (preDestroys.contains(methode))
			{
				try {
					methode.invoke(obj.getClass().cast(obj));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ejbsNonDispos.remove(obj);
		ejbsDispos.remove(obj);
		System.out.println("destroy (non stateless):" + obj);
		System.out.println("Dispo" + ejbsDispos.toString());
		System.out.println("Non Dispo" + ejbsNonDispos.toString());
	
	}
	
	
	public void switchDispo(Object obj)
	{
		synchronized (EJBContainer.class) {

			if(ejbsNonDispos != null && ejbsNonDispos.contains(obj) ) {
				
				ejbsDispos.add(obj);
				ejbsNonDispos.remove(obj);
				
				
				
			} else {
				
				ejbsNonDispos.add(obj);
				ejbsDispos.remove(obj);
				
			}
			//System.out.println("Dispo" + ejbsDispos.toString());
			//System.out.println("Non Dispo" + ejbsNonDispos.toString());
		
		}
	}
	
	
	public Object create(Class class1)
	{
		
		Proxy res = null;
		
		// recherche de la classe implémentant l'interface
		Reflections reflections = new Reflections("");

		 Set<Class<?>> allClasses = 
		    reflections.getSubTypesOf(class1);
	
		 for (Class<?> c : allClasses)
		 {
			 System.out.println("class found : " + c.getName());
			 
			 
			 //nb d'ejb atteint? 
			 
			 if ( null == registre.get(c) || NB_EJB > registre.get(c) )
			 {
				 
				// création du proxy
				 System.out.println("init proxy...");
				 
				 InvocationHandler handler = new MyInvocationHandler(c);
				 
				 res = (Proxy) Proxy.newProxyInstance(class1.getClassLoader(),
                         new Class[] { class1},
                         handler);			
				
				if (res != null)
				{
					// enregistrer le proxy
					if (registre.get(c) == null)
					{
						
						registre.put(c, 1);
					}
					else
						registre.replace(c, registre.get(c.getName())+1);
	
					
			
				}
					
			 }
			 else
			 {
				 for (Object obj : ejbsDispos)
				 {
					if (obj.getClass().equals(c))
					{
						InvocationHandler handler = new MyInvocationHandler(obj);
						
						res = (Proxy) Proxy.newProxyInstance(class1.getClassLoader(),
		                         new Class[] { class1},
		                         handler);			
						
						break;
					}
				 }
			 }
			 
		
			
		 }
		 
		
		
		return class1.cast(res);
	}
	

	
	public Transaction consultTransationManager()
	{
		// ici on finalise le singleton de transaction manager et on le retourne.
		TransactionManager t = TransactionManager.getInstance();
		return t.getCurrent();
	}
	
    
	

}
