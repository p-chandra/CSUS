package com.mycompany.a1;

public class Missile extends MoveableGameObject
{
	private int fuelLevel = 10;
	private final int missileSpeed = 12;
	
	public Missile(int direction, double x, double y)
	{
		super.setDirection(direction);
		
		super.setSpeed(missileSpeed);
		
		super.setXCoordinate(x);
		super.setYCoordinate(y);
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
		System.out.println("Invalid setSpeed");
	}
	public void setDirection(int direction)
	{
		System.out.println("Invalid Direction");
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Fuel=" + fuelLevel;
		return parentDesc + desc;
	}
}
