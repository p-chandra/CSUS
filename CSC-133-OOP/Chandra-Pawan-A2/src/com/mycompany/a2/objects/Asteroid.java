package com.mycompany.a2.objects;

import com.mycompany.a2.utility.Util;

public class Asteroid extends MoveableGameObject
{
	private int size = Util.randInt(6, 30);
	
	public Asteroid(int width, int height)
	{
		super.setX(Util.randInt(0, width));
		super.setY(Util.randInt(0, height));
	}

	// size getter
	public int getSize()
	{
		return size;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Size=" + size;
		return parentDesc + desc;
	}
	
}
