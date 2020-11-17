package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AsteroidHitsNPSCommand extends Command
{
	private GameWorld gw;
	
	public AsteroidHitsNPSCommand(GameWorld gw)
	{
		super("Asteroid Hits NPS");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.asteroidHitsNPS();
			System.out.println("Asteroid Hits NPS command~~~~~~~~~~~~~~~");
		}
	}
}
