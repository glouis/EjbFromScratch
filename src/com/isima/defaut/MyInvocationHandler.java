package com.isima.defaut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.isima.annotations.ejb;
import com.isima.annotations.persitantContext;
import com.isima.annotations.postConstruct;
import com.isima.annotations.preDestroy;
import com.isima.annotations.stateless;
import com.isima.annotations.transactionnal;
import com.isima.defaut.EJBContainer;
import com.isima.defaut.EntityManager;

public class MyInvocationHandler implements InvocationHandler {

	private Class<?> ejb;
	private Object instanceEJB;
	
	private EntityManager em;
	
	public MyInvocationHandler(Object ejbDispo)
	{
		if (EJBContainer.getInstance().isDispo(ejbDispo)) EJBContainer.getInstance().switchDispo(ejbDispo);
		instanceEJB = ejbDispo;
		ejb = ejbDispo.getClass();
	
	}
	
	
	public MyInvocationHandler(Class<?> c)
	{
		Object obj;

		ejb = c;

		
		Reflections r = new Reflections (c,new FieldAnnotationsScanner());
		Reflections r1 = new Reflections (c,new MethodAnnotationsScanner());
		// Set<Class<?>> allClasses =  r.getSubTypesOf(c);
		//Set<?> allClasses =  r.getSubTypesOf(c);
		//System.out.println(allClasses.size());
		
		
		
		// instancier un ejb
		for(Constructor<?> construct : ejb.getConstructors()) {
					if(construct.getParameterCount() == 0) {
						try {
							instanceEJB = construct.newInstance();
							System.out.println("EJB instancié");
							EJBContainer.getInstance().manage(instanceEJB);
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
						}
					}
		 }
		 
		
		
		System.out.println("check fields" );
		
		Set<Field> ejbs = r.getFieldsAnnotatedWith(ejb.class);
		Set<Field> persistantContext = r.getFieldsAnnotatedWith(persitantContext.class);
		Set<Method> postConstructs = r1.getMethodsAnnotatedWith(postConstruct.class);
		
		for (Field a : ejb.getDeclaredFields())
		{
			System.out.println(a.getName());
			System.out.println(a.getType());
			
			
			// pour tout @ejb --> create
			if (ejbs.contains(a))
			{
			
					Proxy p = (Proxy)  EJBContainer.getInstance().create(a.getType());
					try {
						a.set(instanceEJB, p );
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			
			// si persistantContext --> new Entity Manager
			if (persistantContext.contains(a))
			{
				try {
					a.set(instanceEJB, new EntityManager());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		}
		
		//postConstruct
		for (Method method : ejb.getDeclaredMethods())
		{
			System.out.println(method.toString() + " " + postConstructs.toString());
			if (postConstructs.contains(method))
			{
				try {
					method.invoke(instanceEJB,(Object[]) null);
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
		
		
		
		
		
		
		
		
		// statefull - stateless
		
		
		
	}
	
	
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		
		
		Method method;
		Object result;
		
		////////////////////////////////// rendre non dispo /////////////////////////////////
		
		// fonction isDispo...
		
		System.out.println("non dispo");
		if (EJBContainer.getInstance().isDispo(instanceEJB))
		{
			EJBContainer.getInstance().switchDispo( instanceEJB);
		}
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////
			
		Transaction t;
		
		Reflections r = new Reflections(ejb);
		Set<Class<?>> persistantContext = r.getTypesAnnotatedWith(transactionnal.class);
		
		
		
		//get transaction
		if (persistantContext.contains(ejb))
		//transactionnal
		{
			t = TransactionManager.getInstance().addNewTransaction();
		}
		else
		// normal
		{
			t = TransactionManager.getInstance().getCurrent();
		}
		
		
		
		//transaction begin
		t.begin();
		
		
		// execute method
		List<Class<?> > parameterTypes = new ArrayList<Class<?> >();
		if(arg2 != null) {
			for(Object o : arg2) {
				parameterTypes.add(o.getClass());
				System.out.println("invoke parameter types : " + o.getClass().toString());
			}
			System.out.println(arg0.toString() + " " + arg1.toString() + " " + arg2.toString());
			method = ejb.getMethod(arg1.getName(), (Class[]) parameterTypes.toArray());
			result = method.invoke(instanceEJB, arg2);
		}
		else {
			
			method = ejb.getMethod(arg1.getName());
			result = method.invoke(instanceEJB, (Object[]) null);
			
		}
		
		// transaction end
		TransactionManager.getInstance().end(t);
		if (persistantContext.contains(ejb))
			TransactionManager.getInstance().remove(t);
		
		

		Set<Class<?>> state = r.getTypesAnnotatedWith(stateless.class);
	
		
		// destroy
		if (state.contains(ejb))
		{
			//si stateless -->  rendre dispo
			System.out.println("dispo");
			EJBContainer.getInstance().switchDispo(instanceEJB);
		}
		else
			//si statefull --> remove ejb
			EJBContainer.getInstance().detach(instanceEJB);
		
		
		
		return result;
	}
	
	
	
	
	

}

