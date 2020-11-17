package com.mycompany.a2.objects;

import com.mycompany.a2.objects.FixGameObject;
import com.mycompany.a2.utility.Util;

public class SpaceStation extends FixGameObject
{
	private int blinkRate;
	private boolean lightOn;
	
	public SpaceStation(int width, int height)
	{
		super(width,height);
		
		blinkRate = Util.randInt(1, 4);
		lightOn = true;
	}
	
	// lightOn getters and setters
	public void setLightOn(boolean lightOn)
	{
		this.lightOn = lightOn;
	}
	
	public boolean getLightOn()
	{
		return lightOn;
	}
	
	// blinkRate getter
	public int getBlinkRate()
	{
		return blinkRate;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Blink Rate=" + blinkRate + " LightOn=" + lightOn;
		return parentDesc + desc;
	}
}
