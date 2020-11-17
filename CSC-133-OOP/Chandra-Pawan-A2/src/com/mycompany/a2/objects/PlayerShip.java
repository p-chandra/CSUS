package com.mycompany.a2.objects;

import com.mycompany.a2.interfaces.ISteerable;

public class PlayerShip extends Ship implements ISteerable
{
	private SteerableMissileLauncher launcher;
	private final int MAX_MISSILE = 10;
	
	public PlayerShip(int width, int height)
	{
		launcher = new SteerableMissileLauncher();
		// center of world
		super.setX(width/2);
		super.setY(height/2);
		// 0 speed
		super.setSpeed(0);
		// head north
		super.setDirection(0);
		// starting missile count
		super.setMissileCount(MAX_MISSILE);
		// set launcher direction at north
		launcher.setDirection(0);
	}
	
	public int getLauncherDir()
	{
		return this.launcher.getDirection();
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
	
	public void reload()
	{
		super.setMissileCount(MAX_MISSILE);
	}
	
	public void steer(char direction, int turnAmt)
	{
		switch (direction)
		{
			case 'r':
			case 'R':
				steerRight(turnAmt);
				break;
				
			case 'l':
			case 'L':
				steerLeft(turnAmt);
				break;
				
			default:
				System.out.println("Steerable Ship direction is 'r' or 'l', turn range 0-359");
				break;
		}
		
	}
	
	public void steerLeft(int turnAmt)
	{
		int dir = (super.getDirection() 
				+ 360 - (turnAmt%360)) % 360;
		super.setDirection(dir);
	}
	
	public void steerRight(int turnAmt)
	{
		int dir = (super.getDirection() + turnAmt) % 360;
		super.setDirection(dir);
	}
	
	public void steerLauncherLeft(int turnAmt)
	{
		this.launcher.steerLeft(turnAmt);
	}
	
	public void steerLauncherRight(int turnAmt)
	{
		this.launcher.steerRight(turnAmt);
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Missiles=" + super.getMissileCount()
				    + " Missile_Launcher_Dir=" + launcher.getDirection();
		return parentDesc + desc;
	}
	
}
