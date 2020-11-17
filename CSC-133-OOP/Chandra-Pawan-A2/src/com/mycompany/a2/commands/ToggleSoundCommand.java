package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.Game;
import com.mycompany.a2.GameWorld;

public class ToggleSoundCommand extends Command
{
	private GameWorld gw;
	private Game game;
	
	public ToggleSoundCommand(GameWorld gw, Game game)
	{
		super("SOUND");
		this.gw = gw;
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			game.setCheckBox();
			gw.toggleSound(game.getCheckBox());
			System.out.println("Toggle Sound command~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
