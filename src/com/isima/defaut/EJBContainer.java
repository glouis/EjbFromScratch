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
	
	private Integer nb_ejb = 0;
	private Map<Integer, Object > ejbsNonDispos;
	private Map<Integer, Object>  ejbsDispos;
	
	private HashMap<String, List<Integer>> registre;
	
	int NB_EJB = 1;
	
	
	private EJBContainer()
	{
		ejbsNonDispos = new HashMap<>();
		ejbsDispos = new HashMap<>();
		registre = new HashMap<>();
	}
	
	public void manage(Object s)
	{

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
			 
			 if ( null == registre.get(c.getName()) || NB_EJB == registre.get(c.getName()).size() )
			 {
				 
				// création du proxy
				 
				 Class proxyClass = Proxy.getProxyClass(
						 class1.getClassLoader(), new Class[] { class1 });
				 
				 InvocationHandler handler = new MyInvocationHandler(c);
				 
				 res = (Proxy) Proxy.newProxyInstance(IMaClasse.class.getClassLoader(),
                         new Class[] { IMaClasse.class},
                         handler);
				 
				 
				 
				
				
				if (res != null)
				{
					// enregistrer le proxy
					if (registre.get(c.getName()) == null)
					{
						List list =  new ArrayList<Integer>();
						list.add(nb_ejb);
						registre.put(c.getName(), list);
					}
					else
						registre.get(c.getName()).add(nb_ejb);
					
					Proxy r = res;
					
				
				 	System.out.println(ejbsNonDispos.toString() + " "+ (class1.cast( r )) +  " " + c.getName());
			
				    ejbsNonDispos.put( nb_ejb, (Proxy) res);
						 
						 
				
					nb_ejb++;
				}
					
			 }
			 
		
			
		 }
		 
		
		
		return class1.cast(res);
	}
	
	public Object createEntityManager()
	{
		return "un autre entity manager";
		// ou avec la methode create : un entity manager change avec la transaction
	}
	
	public Transaction consultTransationManager()
	{
		// ici on finalise le singleton de transaction manager et on le retourne.
		TransactionManager t = new TransactionManager();
		return t.getCurrent();
	}
	
    
	

}
