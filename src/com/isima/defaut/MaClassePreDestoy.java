package com.isima.defaut;

import com.isima.annotations.preDestroy;
import com.isima.annotations.stateless;
import com.isima.interfaces.IMaClassePreDestroy;

@stateless
public class MaClassePreDestoy implements IMaClassePreDestroy{

	boolean succes;
	
	public MaClassePreDestoy()
	{
		succes = false;
		
	}
	
	@preDestroy
	public void beforeDestroy()
	{
		succes = true;
		System.out.println("preDestroy");
	}
	
	public void sayHello()
	{
		System.out.println("Hello from MaClassePreDestroy");
	}
	
	
	
	
}
