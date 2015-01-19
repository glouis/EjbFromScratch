package com.isima.defaut;

public class EJBContainer {

	public void manage(Object s)
	{

	}
	
	
	public Object create(Class<?> class1)
	{
		return (Object) class1;
	}
	
	public Object createEntityManager()
	{
		return (Object) "un autre entity manager";
		// ou avec la methode create : un entitymanager change avec la transaction
	}
	
	public Transaction consultTransationManager()
	{
		// ici on finalise le singleton de transaction manager et on retourne un truc
		TransactionManager t = new TransactionManager();
		return t.getCurrent();
	}

}
