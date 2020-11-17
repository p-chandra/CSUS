package com.mycompany.a1;

public abstract class Ship extends MoveableGameObject
{
	private int missileCount = 0;
	
	public void setMissileCount(int missileCount)
	{
		this.missileCount = missileCount;
		
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
