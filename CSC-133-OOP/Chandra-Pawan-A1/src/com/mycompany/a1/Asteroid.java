package com.mycompany.a1;

public class Asteroid extends MoveableGameObject
{
	private int size = Util.randInt(6, 30);

	// return size of Asteroid
	public int getSize()
	{
		return size;
	}
	
	/*
	toString method to display the Asteroid attributes
	*/
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Size=" + size;
		return parentDesc + desc;
	}
	
}
