package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class PlayerKillNPS extends Command
{
	private GameWorld gw;
	
	public PlayerKillNPS(GameWorld gw)
	{
		super("Player kill NPS");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.killNPS();
			System.out.println("------- Player kill NPS -------");
		}
	}
}
