package com.mycompany.a2.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.GameWorld;

public class AboutGameCommand extends Command
{
	private GameWorld gw;
	
	public AboutGameCommand(GameWorld gw)
	{
		super("ABOUT");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.about();
		System.out.println("ABOUT command~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
