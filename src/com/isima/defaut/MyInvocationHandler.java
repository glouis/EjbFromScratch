package com.isima.defaut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import com.isima.annotations.ejb;

public class MyInvocationHandler implements InvocationHandler {

	private Class<?> ejb;
	
	private EntityManager em;
	
	
	public MyInvocationHandler(Class<?> c)
	{
		Object obj;

		ejb = c;

		Reflections r = new Reflections (c, new FieldAnnotationsScanner());
		 
		
		 System.out.println(r.getFieldsAnnotatedWith(ejb.class).toString()  + " " +  c.getName());
		 Set<Class<?>> d = r.getTypesAnnotatedWith(ejb.class);
		 for (Class<?> a : d)
		 {
			 System.out.println("Annotation founded : " + a.toString());
		 }
		
		// pour tout @ejb --> create
		 for(Field f : r.getFieldsAnnotatedWith(ejb.class)) {
			 if(f.getType() == Object.class) {
				for(Constructor<?> construct : f.getClass().getConstructors()) {
					if(construct.getParameterCount() == 0) {
						try {
							obj = construct.newInstance();
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
			 }
		 }
		 
		
		
		
		
		// si persistantContext --> new Entity Manager
		
		
		
		
		
		// statefull - stateless
		
		
		
	}
	
	
	
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		Method method;
		Object result;
		
		List<Class<?> > parameterTypes = new ArrayList<Class<?> >();
		if(arg2.length != 0) {
			for(Object o : arg2) {
				parameterTypes.add(o.getClass());
				System.out.println("invoke parameter types : " + o.getClass().toString());
			}
			method = ejb.getMethod(arg1.getName(), (Class[]) parameterTypes.toArray());
			result = method.invoke(ejb, arg2);
		}
		else {
			method = ejb.getMethod(arg1.getName());
			result = method.invoke(ejb);
		}

		return result;
	}

}