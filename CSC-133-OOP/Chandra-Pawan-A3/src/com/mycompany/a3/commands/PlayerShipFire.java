package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class PlayerShipFire extends Command
{
	private GameWorld gw;
	
	public PlayerShipFire(GameWorld gw)
	{
		super("Shoot Missile");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.launchPSMissile();
			System.out.println("------- Shoot Missile -------");
		}
	}
}
