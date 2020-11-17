package com.mycompany.a3.objects;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.interfaces.ICollider;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.objects.FixGameObject;

public class SpaceStation extends FixGameObject implements IDrawable, ICollider
{
	private int size;
	
	public SpaceStation(int width, int height)
	{
		super(width,height);
		setSize(80);
	}


	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		size = getSize();
		int x = (int) (pCmpRelPrnt.getX() + getX());
		int y = (int) (pCmpRelPrnt.getY() + getY());

		g.setColor(super.getColor());
		g.fillRoundRect(x-size/2, y-size/2, size, size, 2, 2);
	}
	
	
	public boolean collidesWith(ICollider obj)
	{
		return false;
	}
	
	public void handleCollision(ICollider obj)
	{
		// it does nothing
	}
	
	public boolean isAlive()
	{
		return true;
	}

}
