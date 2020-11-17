package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class UndoGame extends Command
{
	private GameWorld gw;
	
	public UndoGame(GameWorld gw)
	{
		super("UNDO");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("------- UNDO -------");
	}
}
