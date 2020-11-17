package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.commands.*;
import com.mycompany.a3.interfaces.IIterator;
import com.mycompany.a3.interfaces.ISelectable;
import com.mycompany.a3.sound.BGSound;
import com.mycompany.a3.view.MapView;
import com.mycompany.a3.view.PointsView;


public class Game extends Form implements Runnable
{
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private boolean checkBoxState;
	private boolean paused;
	private CheckBox soundCheckBox;
	private BGSound bgSound;
	private UITimer timer;
	private Toolbar myToolbar;
	private Container myContainer;
	private Label commandTextLabel;
	private Command myAddAsteroid, myAddSpaceStation, myAddPlayerShip, myIncSpeed, 
					myDecSpeed, myTurnLeft, myTurnRight, myAddMissile, myAddJump, 
					myAddRotateGunLeft, myAddRotateGunRight, myAddRefuel, 
					myPauseCommand, myAddQuit, myAddReload;
	private Button addAsteroid, addSpaceStation, addPlayerShip, increaseSpeed, 
					decreaseSpeed, turnLeft, turnRight, addMissile, addJump,
					addRotateGunLeft, addRotateGunRight, addRefuel, addPause, addReload;
	
	
	
	
	public Game()
	{
		init();
	}
	
	private void init()
	{
		gw = new GameWorld(this);
		addToolBar();
		setLayout(new BorderLayout());
		addButtons();
		mv = new MapView(gw);
		pv = new PointsView(gw);
		gw.addObserver(mv);
		gw.addObserver(pv);
		add(BorderLayout.NORTH, pv);
		add(BorderLayout.CENTER, mv);
		timer = new UITimer(this);
		startTimer();
		
		this.show();
		
		// set width and height
		int width = mv.getWidth();
		int height = mv.getHeight();
		gw.setWidth(width);
		gw.setHeight(height);
		System.out.println("map width:" + width);
		System.out.println("map height:" + height);
		
		// sounds
		bgSound = new BGSound("background.wav");
		checkBoxState = true;
		bgSound.play();
		
		// select object function
		select();
		
	}
	
	private void addToolBar()
	{
		myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("Asteroids");

		// side menu======================================================
		myToolbar.addCommandToSideMenu(new NewGame(gw));
		myToolbar.addCommandToSideMenu(new SaveGame(gw));
		myToolbar.addCommandToSideMenu(new UndoGame(gw));
		myToolbar.addCommandToSideMenu(new AboutGame(gw));
		myToolbar.addCommandToSideMenu(new QuitGame(gw));

		soundCheckBox = new CheckBox("SOUND");
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getUnselectedStyle().setBgColor(ColorUtil.rgb(51,153,255));
		soundCheckBox.setSelected(gw.getPlayerSoundOn());
		myToolbar.addComponentToSideMenu(soundCheckBox);
		ToggleSound myAddSound = new ToggleSound(gw, this);
		soundCheckBox.setCommand(myAddSound);

		myToolbar.addCommandToSideMenu(new Quit(gw));
	}
	
	private void addButtons()
	{
		myContainer = new Container(new GridLayout(1,1));
		
		commandTextLabel = new Label("Commands");
		myContainer.add(commandTextLabel);
		
		addAsteroid = new Button(" ");
		setButton(addAsteroid, myContainer);
		myAddAsteroid = new AddAsteroid(gw);
		addAsteroid.setCommand(myAddAsteroid);

		addSpaceStation = new Button(" ");
		setButton(addSpaceStation, myContainer);
		myAddSpaceStation = new AddSpaceStation(gw);
		addSpaceStation.setCommand(myAddSpaceStation);

		addPlayerShip = new Button (" ");
		setButton(addPlayerShip, myContainer);
		myAddPlayerShip = new AddPlayerShip(gw);
		addPlayerShip.setCommand(myAddPlayerShip);

		increaseSpeed = new Button (" ");
		setButton(increaseSpeed, myContainer);
		myIncSpeed = new IncreaseSpeed(gw);
		increaseSpeed.setCommand(myIncSpeed);
		this.addKeyListener(-91, myIncSpeed);
		
		decreaseSpeed = new Button (" ");
		setButton(decreaseSpeed, myContainer);
		myDecSpeed = new DecreaseSpeed(gw);
		decreaseSpeed.setCommand(myDecSpeed);
		this.addKeyListener(-92, myDecSpeed);
		
		turnLeft = new Button (" ");
		setButton(turnLeft, myContainer);
		myTurnLeft = new ShipTurnLeft(gw);
		turnLeft.setCommand(myTurnLeft);
		this.addKeyListener(-93, myTurnLeft);
		
		turnRight = new Button (" ");
		setButton(turnRight, myContainer);
		myTurnRight = new ShipTurnRight(gw);
		turnRight.setCommand(myTurnRight);
		this.addKeyListener(-94, myTurnRight);

		addRotateGunLeft = new Button(" ");
		setButton(addRotateGunLeft, myContainer);
		myAddRotateGunLeft = new ShipGunTurnLeft(gw);
		addRotateGunLeft.setCommand(myAddRotateGunLeft);
		this.addKeyListener(44, myAddRotateGunLeft);

		addRotateGunRight = new Button(" ");
		setButton(addRotateGunRight, myContainer);
		myAddRotateGunRight = new ShipGunTurnRight(gw);
		addRotateGunRight.setCommand(myAddRotateGunRight);
		this.addKeyListener(46, myAddRotateGunRight);
		
		addMissile = new Button (" ");
		setButton(addMissile, myContainer);
		myAddMissile = new PlayerShipFire(gw);
		addMissile.setCommand(myAddMissile);
		this.addKeyListener(-90, myAddMissile);
		
		addJump = new Button(" ");
		setButton(addJump, myContainer);
		myAddJump = new Jump(gw);
		addJump.setCommand(myAddJump);
		this.addKeyListener('j', myAddJump);

		addReload = new Button(" ");
		setButton(addReload, myContainer);
		myAddReload = new Reload(gw);
		addReload.setCommand(myAddReload);

		addPause = new Button(" ");
		setButton(addPause, myContainer);
		myPauseCommand = new Pause(this);
		addPause.setCommand(myPauseCommand);
		
		addRefuel = new Button(" ");
		setButton(addRefuel, myContainer);
		myAddRefuel = new Refuel(gw);
		addRefuel.setCommand(myAddRefuel);
		addRefuel.setEnabled(false);
		addRefuel.getAllStyles().setBgTransparency(127);

		//setButton(addQuit, myContainer);
		myAddQuit = new Quit(gw);
		//addQuit.setCommand(myAddQuit);
		this.addKeyListener('Q', myAddQuit);


		add(BorderLayout.WEST, myContainer);
	}
	
	private void setButton(Button button, Container container)
	{
		button.getAllStyles().setBgTransparency(255);
		button.getUnselectedStyle().setBgColor(ColorUtil.rgb(255,115,0));

		button.getAllStyles().setFgColor(ColorUtil.BLACK);
		button.getAllStyles().setPadding(RIGHT, 6);
		button.getAllStyles().setPadding(LEFT, 6);
		button.getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.BLACK));
		container.add(button);
	}
	
	public boolean getCheckBox()
	{
		return checkBoxState;
	}
	
	public void setCheckBox()
	{
		checkBoxState = soundCheckBox.isSelected();
		if(checkBoxState)
		{
			bgSound.play();
		}
		else
		{
			bgSound.pause();
		}
	}
	
	public void pauseTimer()
	{
		timer.cancel();
		paused = true;
		addPause.setText("PLAY");
		// enable refuel
		addRefuel.setEnabled(true);
		addRefuel.getAllStyles().setBgTransparency(255);
		// disable buttons
		addAsteroid.setEnabled(false);
		addAsteroid.getAllStyles().setBgTransparency(127);
		
		addSpaceStation.setEnabled(false);
		addSpaceStation.getAllStyles().setBgTransparency(127);
		
		addPlayerShip.setEnabled(false);
		addPlayerShip.getAllStyles().setBgTransparency(127);
		
		increaseSpeed.setEnabled(false);
		increaseSpeed.getAllStyles().setBgTransparency(127);
		
		decreaseSpeed.setEnabled(false);
		decreaseSpeed.getAllStyles().setBgTransparency(127);
		
		turnLeft.setEnabled(false);
		turnLeft.getAllStyles().setBgTransparency(127);
		
		turnRight.setEnabled(false);
		turnRight.getAllStyles().setBgTransparency(127);
		
		addMissile.setEnabled(false);
		addMissile.getAllStyles().setBgTransparency(127);
		
		addJump.setEnabled(false);
		addJump.getAllStyles().setBgTransparency(127);
		
		addRotateGunLeft.setEnabled(false);
		addRotateGunLeft.getAllStyles().setBgTransparency(127);
		
		addRotateGunRight.setEnabled(false);
		addRotateGunRight.getAllStyles().setBgTransparency(127);

		addReload.setEnabled(false);
		addReload.getAllStyles().setBgTransparency(127);

		// disable key bindings
		this.removeKeyListener(-91, myIncSpeed);
		this.removeKeyListener(-92, myDecSpeed);
		this.removeKeyListener(-93, myTurnLeft);
		this.removeKeyListener(-94, myTurnRight);
		this.removeKeyListener(-90, myAddMissile);
		this.removeKeyListener('j', myAddJump);
		this.removeKeyListener(44, myAddRotateGunLeft);
		this.removeKeyListener(46, myAddRotateGunRight);
		this.removeKeyListener('Q', myAddQuit);
		
		// disable toolbar
		myToolbar.setEnabled(false);
		myToolbar.getDisabledStyle().setBgColor(ColorUtil.GRAY);
		
	}
	
	public void startTimer()
	{
		timer.schedule(34, true, this);
		paused = false;
		addPause.setText("PAUSE");
		// disable refuel
		addRefuel.setEnabled(false);
		addRefuel.getAllStyles().setBgTransparency(127);
		// enable buttons
		addAsteroid.setEnabled(true);
		addAsteroid.getAllStyles().setBgTransparency(255);
		
		addSpaceStation.setEnabled(true);
		addSpaceStation.getAllStyles().setBgTransparency(255);
		
		addPlayerShip.setEnabled(true);
		addPlayerShip.getAllStyles().setBgTransparency(255);
		
		increaseSpeed.setEnabled(true);
		increaseSpeed.getAllStyles().setBgTransparency(255);
		
		decreaseSpeed.setEnabled(true);
		decreaseSpeed.getAllStyles().setBgTransparency(255);
		
		turnLeft.setEnabled(true);
		turnLeft.getAllStyles().setBgTransparency(255);
		
		turnRight.setEnabled(true);
		turnRight.getAllStyles().setBgTransparency(255);
		
		addMissile.setEnabled(true);
		addMissile.getAllStyles().setBgTransparency(255);
		
		addJump.setEnabled(true);
		addJump.getAllStyles().setBgTransparency(255);
		
		addRotateGunLeft.setEnabled(true);
		addRotateGunLeft.getAllStyles().setBgTransparency(255);
		
		addRotateGunRight.setEnabled(true);
		addRotateGunRight.getAllStyles().setBgTransparency(255);

		addReload.setEnabled(true);
		addReload.getAllStyles().setBgTransparency(255);
		
		// disable key bindings
		this.addKeyListener(-91, myIncSpeed);
		this.addKeyListener(-92, myDecSpeed);
		this.addKeyListener(-93, myTurnLeft);
		this.addKeyListener(-94, myTurnRight);
		this.addKeyListener(-90, myAddMissile);
		this.addKeyListener('j', myAddJump);
		this.addKeyListener(44, myAddRotateGunLeft);
		this.addKeyListener(46, myAddRotateGunRight);
		this.addKeyListener('Q', myAddQuit);
		
		// enable toolbar
		myToolbar.setEnabled(true);
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void select()
	{
		// action listener
		mv.addPointerPressedListener(new ActionListener() 
		{
			public void actionPerformed (ActionEvent evt)
			{
				Point p = new Point();
				p.setX(evt.getX()-mv.getX());
				p.setY(-(evt.getY()-mv.getAbsoluteY()-mv.getHeight()));
				
				if(paused)
				{
					IIterator it = gw.getGameObjects().getIterator();
					while(it.hasNext())
					{
						Object obj = it.getNext();
						
						if(obj instanceof ISelectable)
						{
							ISelectable selected = (ISelectable) obj;
							selected.isSelected(p);
							mv.repaint();
						}
						
					}
				}
			}
		});
	}

	public void run()
	{
		gw.tick();
	}
	
	
	
}
