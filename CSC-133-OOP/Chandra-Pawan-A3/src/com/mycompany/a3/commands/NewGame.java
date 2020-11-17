package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class NewGame extends Command
{
	private GameWorld gw;
	
	public NewGame(GameWorld gw)
	{
		super("NEW");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.newGame();
		System.out.println("------- NEW Game -------");
	}
}
