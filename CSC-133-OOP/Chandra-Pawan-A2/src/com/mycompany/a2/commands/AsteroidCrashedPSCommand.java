package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AsteroidCrashedPSCommand extends Command
{
	private GameWorld gw;
	
	public AsteroidCrashedPSCommand(GameWorld gw)
	{
		super("Ship Crash");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.asteroidCrashedPS();
			System.out.println("Ship Crash command~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
