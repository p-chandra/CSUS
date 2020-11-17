package com.mycompany.a2.objects;

public class Missile extends MoveableGameObject
{
	private int fuelLevel = 10;
	private final int MISSILE_SPEED = 12;
	
	public Missile(int direction, double x, double y)
	{
		// match launcher direction when created
		super.setDirection(direction);
		// set speed
		super.setSpeed(MISSILE_SPEED);
		// set starting location
		super.setX(x);
		super.setY(y);
	}
	
	//fuelLevel getters and setters
	public void setFuelLevel(int fuelLevel)
	{
		this.fuelLevel = fuelLevel;
		
		// fuelLevel is a positive number only
		if(this.fuelLevel < 0)
		{
			this.fuelLevel = 0;
		}
	}
	
	public int getFuelLevel()
	{
		return fuelLevel;
	}
	
	// blocking parent method
	public void setSpeed(int speed)
	{
		System.out.println("nice try guy");
	}
	public void setDirection(int direction)
	{
		System.out.println("oops, don't do that");
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Fuel=" + fuelLevel;
		return parentDesc + desc;
	}
}
