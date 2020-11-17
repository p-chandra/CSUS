package com.mycompany.a1;

public class SpaceStation extends FixGameObject
{
	private int blinkRate;
	
	public SpaceStation()
	{
		blinkRate = Util.randInt(0, 4);
	}

	public int getBlinkRate()
	{
		return blinkRate;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Blink Rate=" + blinkRate;
		return parentDesc + desc;
	}
}
