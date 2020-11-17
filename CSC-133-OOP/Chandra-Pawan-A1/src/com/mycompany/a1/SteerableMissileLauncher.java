package com.mycompany.a1;

public class SteerableMissileLauncher extends MissileLauncher implements ISteerable
{
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
}
