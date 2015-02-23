package com.isima.defaut;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;









import org.reflections.Reflections;

import com.isima.annotations.ejb;
import com.isima.annotations.local;
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
	
	private HashMap<String, List<Object>> registre;
	
	int NB_EJB = 3;
	
	
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

		if(ejbsNonDispos != null && ejbsNonDispos.contains(obj) ) {
			
			ejbsDispos.add(obj);
			ejbsNonDispos.remove(obj);
			
			
			
		} else {
			
			ejbsNonDispos.add(obj);
			ejbsDispos.remove(obj);
			
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
			 
			 if ( null == registre.get(c.getName()) || NB_EJB > registre.get(c.getName()).size() )
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
					if (registre.get(c.getName()) == null)
					{
						List list =  new ArrayList<Object>();
						list.add(res);
						registre.put(c.getName(), list);
					}
					else
						registre.get(c.getName()).add(res);
				
					ejbsNonDispos.add( (Proxy) res);
						 
					
			
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
