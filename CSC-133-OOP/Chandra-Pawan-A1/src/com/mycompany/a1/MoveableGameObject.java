package com.mycompany.a1;

public abstract class MoveableGameObject extends GameObject implements IMoveable
{
	private int speed = Util.randInt(0, 10);
	private int direction = Util.randInt(0, 359);
	
	// speed getters and setters
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return speed;
	}
	
	// direction getters and setters
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	public int getDirection()
	{
		return direction;
	}
	
	public void move()
	{
		double deltaX = Math.cos(direction) * speed;
		double deltaY = Math.sin(direction) * speed;
		super.setXCoordinate(super.getXCoordinate() + deltaX);
		super.setYCoordinate(super.getYCoordinate() + deltaY);
	}
}
