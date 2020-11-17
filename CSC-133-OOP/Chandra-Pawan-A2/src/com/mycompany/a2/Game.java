package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.a2.commands.*;
import com.mycompany.a2.view.MapView;
import com.mycompany.a2.view.PointsView;


public class Game extends Form
{
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private boolean checkBoxState;
	private CheckBox soundCheckBox;
	
	
	public Game()
	{
		init();
	}
	
	private void init()
	{
		// make game world
		gw = new GameWorld(this);
		// add toolbar
		addToolBar();
		// set layout
		setLayout(new BorderLayout());
		// add buttons
		addButtons();
		// add views
		mv = new MapView();
		pv = new PointsView(gw);
		// set observer
		gw.addObserver(mv);
		gw.addObserver(pv);
		// setup layout
		add(BorderLayout.NORTH, pv);
		add(BorderLayout.CENTER, mv);
		
		
		this.show();
		
		// set width and height
		int width = mv.getWidth();
		int height = mv.getHeight();
		gw.setWidth(width);
		gw.setHeight(height);
		
		System.out.println("map width:" + width);
		System.out.println("map height:" + height);
	}
	
	private void addToolBar()
	{
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("Asteroids");
		
		
		//(overflow menu aka 3 dots)===================================
		myToolbar.addCommandToOverflowMenu(new NewGameCommand(gw));

		
		// side menu======================================================
		myToolbar.addCommandToSideMenu(new NewGameCommand(gw));
		myToolbar.addCommandToSideMenu(new SaveGameCommand(gw));
		myToolbar.addCommandToSideMenu(new UndoGameCommand(gw));
		myToolbar.addCommandToSideMenu(new AboutGameCommand(gw));
		myToolbar.addCommandToSideMenu(new QuitGameCommand(gw));

		
		// sound checkbox
		soundCheckBox = new CheckBox("SOUND");
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.setSelected(gw.getPlayerSoundOn());
		myToolbar.addComponentToSideMenu(soundCheckBox);
		ToggleSoundCommand myAddSound = new ToggleSoundCommand(gw, this);
		soundCheckBox.setCommand(myAddSound);

		// quit command
		myToolbar.addCommandToSideMenu(new QuitCommand(gw));
	}
	
	private void addButtons()
	{
		Container myContainer = new Container(new GridLayout(1,1));

		Label commandTextLabel = new Label("Commands");
		myContainer.add(commandTextLabel);

		Button addAsteroid = new Button("Add Asteroid");
		setButton(addAsteroid, myContainer);
		AddAsteroidCommand myAddAsteroid = new AddAsteroidCommand(gw);
		addAsteroid.setCommand(myAddAsteroid);

		Button addPlayerShip = new Button ("Add Player Ship");
		setButton(addPlayerShip, myContainer);
		AddPlayerShipCommand myAddPlayerShip = new AddPlayerShipCommand(gw);
		addPlayerShip.setCommand(myAddPlayerShip);

		Button addSpaceStation = new Button("Add Space Station");
		setButton(addSpaceStation, myContainer);
		AddSpaceStationCommand myAddSpaceStation = new AddSpaceStationCommand(gw);
		addSpaceStation.setCommand(myAddSpaceStation);

		Button turnLeft = new Button ("Turn Left");
		setButton(turnLeft, myContainer);
		ShipTurnLeftCommand myTurnLeft = new ShipTurnLeftCommand(gw);
		turnLeft.setCommand(myTurnLeft);

		Button turnRight = new Button ("Turn Right");
		setButton(turnRight, myContainer);
		ShipTurnRightCommand myTurnRight = new ShipTurnRightCommand(gw);
		turnRight.setCommand(myTurnRight);

		Button increaseSpeed = new Button ("Increase Speed");
		setButton(increaseSpeed, myContainer);
		IncreaseSpeedCommand myIncSpeed = new IncreaseSpeedCommand(gw);
		increaseSpeed.setCommand(myIncSpeed);
		
		Button decreaseSpeed = new Button ("Decrease Speed");
		setButton(decreaseSpeed, myContainer);
		DecreaseSpeedCommand myDecSpeed = new DecreaseSpeedCommand(gw);
		decreaseSpeed.setCommand(myDecSpeed);
		
		Button addTick = new Button("Clock Tick");
		setButton(addTick, myContainer);
		TickCommand myAddTick = new TickCommand(gw);
		addTick.setCommand(myAddTick);

		Button addMissile = new Button ("Shoot Missile");
		setButton(addMissile, myContainer);
		PlayerShipFireCommand myAddMissile = new PlayerShipFireCommand(gw);
		addMissile.setCommand(myAddMissile);

		Button addJump = new Button("Jump HyperSpace");
		setButton(addJump, myContainer);
		JumpCommand myAddJump = new JumpCommand(gw);
		addJump.setCommand(myAddJump);

		Button addKillAsteroid = new Button("Kill Asteroid");
		setButton(addKillAsteroid, myContainer);
		KillAsteroidCommand myAddKillAsteroid = new KillAsteroidCommand(gw);
		addKillAsteroid.setCommand(myAddKillAsteroid);

		Button addReload = new Button("Reload");
		setButton(addReload, myContainer);
		ReloadCommand myAddReload = new ReloadCommand(gw);
		addReload.setCommand(myAddReload);
		
		Button addAsteroidCrashedShip = new Button("Ship Crash");
		setButton(addAsteroidCrashedShip, myContainer);
		AsteroidCrashedPSCommand myAsteroidCrashedShip 
		= new AsteroidCrashedPSCommand(gw);
		addAsteroidCrashedShip.setCommand(myAsteroidCrashedShip);
		
		Button addTwoAsteroidsCrash = new Button("Asteroids Collision");
		setButton(addTwoAsteroidsCrash, myContainer);
		TwoAsteroidsCrashCommand myAddTwoAsteroidsCrash 
		= new TwoAsteroidsCrashCommand(gw);
		addTwoAsteroidsCrash.setCommand(myAddTwoAsteroidsCrash);

		Button addRotateGun = new Button("Rotate Gun");
		setButton(addRotateGun, myContainer);
		ShipGunTurnLeftCommand myAddRotateGun = new ShipGunTurnLeftCommand(gw);
		addRotateGun.setCommand(myAddRotateGun);

		Button addQuit = new Button("Quit");
		setButton(addQuit, myContainer);
		QuitCommand myAddQuit = new QuitCommand(gw);
		addQuit.setCommand(myAddQuit);
		
		add(BorderLayout.WEST, myContainer);

		// set to listener
		this.addKeyListener(-93, myTurnLeft);
		this.addKeyListener(-92, myDecSpeed);
		this.addKeyListener(-91, myIncSpeed);
		this.addKeyListener(-94, myTurnRight);
		this.addKeyListener(-90, myAddMissile);
		this.addKeyListener('q', myAddQuit);
		this.addKeyListener(',', myAddRotateGun);
		this.addKeyListener('x', myAddTwoAsteroidsCrash);
		this.addKeyListener('c', myAsteroidCrashedShip);
		this.addKeyListener('n', myAddReload);
		this.addKeyListener('k', myAddKillAsteroid);
		this.addKeyListener('j', myAddJump);
		this.addKeyListener('t', myAddTick);
		this.addKeyListener('b', myAddSpaceStation);
		this.addKeyListener('a', myAddAsteroid);
		this.addKeyListener('s', myAddPlayerShip);


	}
	
	private void setButton(Button button, Container container)
	{
		button.getAllStyles().setBgTransparency(255);
		button.getUnselectedStyle().setBgColor(ColorUtil.rgb(255,115,0));
		button.getAllStyles().setFgColor(ColorUtil.WHITE);
		button.getAllStyles().setPadding(RIGHT, 10);
		button.getAllStyles().setPadding(LEFT, 10);
		button.getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.WHITE));
		container.add(button);
	}
	
	public boolean getCheckBox()
	{
		return checkBoxState;
	}
	
	public void setCheckBox()
	{
		checkBoxState = soundCheckBox.isSelected();
	}
	
	
	
}
