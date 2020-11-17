package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class Refuel extends Command
{
	private GameWorld gw;
	
	public Refuel(GameWorld gw)
	{
		super("REFUEL");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.reFuel();
			System.out.println("------- Refueled -------");
		}
	}
}