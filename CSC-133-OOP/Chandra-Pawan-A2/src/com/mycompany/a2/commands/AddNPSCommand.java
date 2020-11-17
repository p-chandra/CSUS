package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AddNPSCommand extends Command
{
	private GameWorld gw;
	
	public AddNPSCommand(GameWorld gw)
	{
		super("Add Non-PlayerShip");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.addNPS();
			System.out.println("Add Non-PlayerShip command~~~~~~~~~~~~~~");
		}
	}
}
