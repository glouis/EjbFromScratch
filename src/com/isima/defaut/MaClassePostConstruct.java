package com.isima.defaut;

import com.isima.annotations.postConstruct;
import com.isima.annotations.stateless;
import com.isima.interfaces.IMaClassePostConstruct;


public class MaClassePostConstruct implements IMaClassePostConstruct{

	private String str;
	
	
	public MaClassePostConstruct()
	{
		System.out.println("construct");
		str = "failure";
	}
	
	@postConstruct
	public void init()
	{
		System.out.println("PostConstruct");
		str = "success";
	}
	
	public String sayHello()
	{
		System.out.println("Hello from MaClassePostConstruct");
		return str;
	}
	
	
	
}
