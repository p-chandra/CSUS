package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class PlayerShipHitsNPSCommand extends Command
{
	private GameWorld gw;
	
	public PlayerShipHitsNPSCommand(GameWorld gw)
	{
		super("Player Crash in NPS");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.playerShipHitsNPS();
			System.out.println("Player Crash in NPS~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
