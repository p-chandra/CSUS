package com.mycompany.a2.objects;


import com.mycompany.a2.utility.Util;

public abstract class FixGameObject extends GameObject
{
	private static int uniqueId = 1;
	private int id;
	
	public FixGameObject(int width, int height)
	{
		id = uniqueId;
		uniqueId++;
		
		// random location in map view
		super.setX(Util.randInt(0, width));
		super.setY(Util.randInt(0, height));
	}
	
	public int getId()
	{
		return id;
	}
	
	// blocking parent methods
	public void setX(double location)
	{
		System.out.println("stop it");
	}
	public void setY(double location)
	{
		System.out.println("impossible");
	}
	
}
