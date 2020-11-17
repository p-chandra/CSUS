package com.mycompany.a2.objects;

import com.mycompany.a2.interfaces.IMoveable;
import com.mycompany.a2.utility.Util;

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
		super.setX(super.getX() + deltaX);
		super.setY(super.getY() + deltaY);
	}
}
