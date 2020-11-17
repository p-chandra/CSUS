package com.mycompany.a1;

public class PlayerShip extends Ship implements ISteerable
{
	private SteerableMissileLauncher launcher = new SteerableMissileLauncher();
	private final int maxMissile = 10;
	
	/*
	Code sets coordinates, checks for milles and contains all methods for game 
	such as reload, steer, and steerlauncher
	*/
	public PlayerShip()
	{
		super.setXCoordinate(1024/2);
		super.setYCoordinate(768/2);	
		super.setSpeed(0);
		super.setDirection(0);
		super.setMissileCount(maxMissile);
		
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
		super.setMissileCount(maxMissile);
	}
	
	public void steer(char direction, int turnAmt)
	{
		int dir;
		switch (direction)
		{
			case 'r':
			case 'R':
				dir = (super.getDirection() + turnAmt) % 360;
				super.setDirection(dir);
				break;
				
			case 'l':
			case 'L':
				dir = (super.getDirection() 
						+ 360 - (turnAmt%360)) % 360;
				super.setDirection(dir);
				break;
				
			default:
				System.out.println("Direction is 'r' or 'l', (0-359)");
				break;
		}
		
	}
	
	public void steerLauncher(char direction, int turnAmt)
	{
		this.launcher.steer(direction, turnAmt);
	}
	
	
	//Print ship details when called.
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Missiles=" + super.getMissileCount()
				    + " Missle Launcher_Dir=" + launcher.getDirection();
		return parentDesc + desc;
	}
	
}
