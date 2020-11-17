package com.mycompany.a2.objects;

import com.mycompany.a2.objects.MoveableGameObject;

public abstract class Ship extends MoveableGameObject
{
	private int missileCount = 0;
	
	//missileCount getters and setters
	public void setMissileCount(int missileCount)
	{
		this.missileCount = missileCount;
		
		// cannot have negative missiles
		if(this.missileCount < 0)
		{
			this.missileCount = 0;
		}
	}
	
	public int getMissileCount()
	{
		return missileCount;
	}
}
