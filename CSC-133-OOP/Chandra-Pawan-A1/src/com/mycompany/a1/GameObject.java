package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

public class GameObject
{
	//Setting location of object with the frame size
	private Point2D location = new Point2D(
			Util.randInt(0, 1024),
			Util.randInt(0, 768) );
	private int color = 0;
	
	// location getters and setters
	public void setXCoordinate(double location)
	{
		if (location > 0) // non negative
		{
			this.location.setX(location);
		}
	}
	public void setYCoordinate(double location)
	{
		if (location > 0) // non negative 
		{
			this.location.setY(location);
		}
	}
	public double getXCoordinate()
	{
		return location.getX();
	}
	public double getYCoordinate()
	{
		return location.getY();
	}
	
	// color getter and setters
	public void setColor(int color)
	{
		this.color = color;
	}
	public int getColor()
	{
		return color;
	}
	
	// toString
	public String toString()
	{
		String desc = "Loc=(" + Math.round(getXCoordinate()*10.0)/10.0 + ", " 
					+ Math.round(getYCoordinate()*10.0)/10.0
					+ ") color=[" + ColorUtil.red(color) + ","
					+ ColorUtil.green(color) + "," 
					+ ColorUtil.blue(color) + "]";
		return desc;
	}
}
