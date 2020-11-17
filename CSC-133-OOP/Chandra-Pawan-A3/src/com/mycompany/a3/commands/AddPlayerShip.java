package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AddPlayerShip extends Command
{
	private GameWorld gw;
	
	public AddPlayerShip(GameWorld gw)
	{
		super("Add PlayerShip");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.addPlayerShip();
			System.out.println("------- Add PlayerShip -------");
		}
	}
}
