package com.mycompany.a2.objects;

import com.mycompany.a2.utility.Util;

public class NonPlayerShip extends Ship
{
	private final int MAX_MISSILE = 2;
	private MissileLauncher launcher;
	// random size 10 or 20
	private int size = ( 10* Util.randInt(1, 2) );
	
	public NonPlayerShip(int width, int height)
	{
		// location
		super.setX(Util.randInt(0, width));
		super.setY(Util.randInt(0, height));
		
		launcher = new MissileLauncher();
		// max 2 missiles
		super.setMissileCount(MAX_MISSILE);
		// same direction and speed as missile launcher
		super.setDirection(launcher.getDirection());
		super.setSpeed(launcher.getSpeed());
		
	}
	
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	public int getLauncherDirection()
	{
		return super.getDirection();
	}
	
	public void fire()
	{
		if (hasMissile())
		{
			super.setMissileCount(super.getMissileCount()-1);
		}
	}
	
	public boolean hasMissile()
	{
		if (super.getMissileCount() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
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
