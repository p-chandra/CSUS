package com.mycompany.a1;

import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;

public class GameWorld 
{
	private Vector<GameObject> ObjectList;
	public boolean psExist;
	public int score;
	public int lives;
	public int gameTime;
	public int left = 2;
	public int right = 2;
	public int dir = 5;
	Game game;
	
	
	public GameWorld(Game game)
	{
		this.game = game;
	}
	
	//Calls everytime the game starts along with initializing score life and time
	public void init() 
	{
		game.setTitle("---- New Game ----");
		ObjectList = new Vector<GameObject>();
		psExist = false;
		score = 0;
		lives = 3;
		gameTime = 0;
	}
	
	//set color of asteroid and call its appropriate method
	public void addAsteroid()
	{
		int color = ColorUtil.rgb(45, 45, 90);
		Asteroid asteroid = new Asteroid();
		asteroid.setColor(color);
		ObjectList.add(asteroid);
		System.out.println("Asteroid added");
	}
	
	//add enemy ship along with color
	public void addNPS()
	{
		NonPlayerShip nps = new NonPlayerShip();
		int color = ColorUtil.rgb(96, 0, 96);
		nps.setColor(color);
		ObjectList.add(nps);
		System.out.println("Non-Player Ship added.");
	}
	
	//add spacestation aka blinkstation with color
	public void addBlinkStation()
	{
		SpaceStation blinkStation = new SpaceStation();
		int color = ColorUtil.rgb(0, 215, 15);
		blinkStation.setColor(color);
		ObjectList.add(blinkStation);
		System.out.println("A new Space Station added.");
	}
	
	//add player ship and also check if ps already exists
	public void addPS()
	{
		if (psExist)
		{
			System.out.println("Player Ship already exists.");
		}
		else
		{
			PlayerShip ps = new PlayerShip();
			int color = ColorUtil.rgb(0,0,0);
			ps.setColor(color);
			ObjectList.add(ps);
			psExist = true;
			System.out.println("Player Ship added");
		}
	}
	
	//increase speed method
	public void incSpeed()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.setSpeed(PlayerShipO.getSpeed() + 1);
			}
		}
		System.out.println("Sped up");
	}
	
	//decrease speed method
	public void decSpeed()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.setSpeed(PlayerShipO.getSpeed() - 1);
			}
		}
		System.out.println("Slowed down");
	}
	
	//left turn method
	public void leftTurn()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.steer('l', left);
			}
		}
		System.out.println("Player turned left");
	}
	
	// right turn method
	public void rightTurn()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO = (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.steer('r', right);
				
			}
		}
		System.out.println("Player turned right");
	}
	
	//change direction method 
	public void direction()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO = (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.steerLauncher('l', dir);
				
			}
		}
		System.out.println("Missile Launcher turned left");
	}
	
	//Player fire missle provided direction called in method
	public void fireMissile()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				if (PlayerShipO.hasMissile())
				{
					PlayerShipO.fire();
					int launcherDirection = PlayerShipO.getLauncherDir();
					PlayerMissile missile = new PlayerMissile(launcherDirection, PlayerShipO.getXCoordinate(), PlayerShipO.getYCoordinate());
					int color = ColorUtil.rgb(255, 0, 0);
					missile.setColor(color);
					ObjectList.add(missile);
				}
				else
				{
					System.out.println("Out of missiles");
				}
			}
		}
		System.out.println("Launching Player Ship missile");
	}
	
	//Enemy ship missile launcher method, also checks for out of missiles
	public void launchMissile()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof NonPlayerShip)
			{
				NonPlayerShip nPlayerShipO= (NonPlayerShip) ObjectList.elementAt(i);
				if (nPlayerShipO.hasMissile())
				{
					nPlayerShipO.fire();
					int launcherDirection = nPlayerShipO.getLauncher().getDirection();
					NonPlayerMissile missile = new NonPlayerMissile(launcherDirection, nPlayerShipO.getXCoordinate(), nPlayerShipO.getYCoordinate());
					int color = ColorUtil.rgb(255, 50, 0);
					missile.setColor(color);
					ObjectList.add(missile);
				}
				else
				{
					System.out.println("Non-player Ship's out of missiles");
				}
			}
		}
		System.out.println("Launching Non-PlayerShip missile.");
	}
	
	// teleport back to center
	public void jump()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO = (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.setXCoordinate(1024/2);
				PlayerShipO.setYCoordinate(768/2);
			}
		}
		System.out.println("PlayerShip jumped back to center.");
	}
	
	//reload missile method
	public void reloadMissiles()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO = (PlayerShip) ObjectList.elementAt(i);
				PlayerShipO.reload();
			}
		}
		System.out.println("Reloading Player Ship missile.");
	}
	
	// asteroid is destroyed by a missile
	public void killedAsteroid()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Missile)
			{
				ObjectList.removeElementAt(i);
				i = ObjectList.size();
			}
		}
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Asteroid)
			{
				ObjectList.removeElementAt(i);
				i = ObjectList.size();
			}
		}
		
		score+=1;
		System.out.println("Asteroid hit");
	}
	
	//missile hits enemy
	public void missileHitNPS()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Missile)
			{
				ObjectList.removeElementAt(i);
				i = ObjectList.size();
			}
		}
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof NonPlayerShip)
			{
				ObjectList.removeElementAt(i);
				i = ObjectList.size();
			}
		}
		
		System.out.println("NPS hit");
		score += 1;
	}
	
	//if a enemy missile hits you, you lose live and also checks for game over in method
	public void psDown()
	{
		if (lives > 0)
		{
			for (int i=0; i<ObjectList.size(); i++)
			{
				if (ObjectList.elementAt(i) instanceof Missile)
				{
					ObjectList.removeElementAt(i);
					i = ObjectList.size();
				}
			}
			for (int i=0; i<ObjectList.size(); i++)
			{
				if (ObjectList.elementAt(i) instanceof PlayerShip)
				{
					ObjectList.removeElementAt(i);
					psExist = false;
					i = ObjectList.size();
					lives -= 1;
					
				}
			}
			System.out.println("Non-PlayerShip killed PlayerShip.");
		}
		else
		{
			gameOver();
		}
	}
	
	//asteroid hits player ship decrementing life and checking if game over
	public void asteroidHitPS()
	{
		if(lives > 0)
		{
			int asteroidLoc = 0;
			int asteroidCount = 0;
			
			for (int i=0; i<ObjectList.size(); i++)
			{
				if (ObjectList.elementAt(i) instanceof Asteroid)
				{
					asteroidLoc = i;
					asteroidCount++;
				}
				if (psExist && (asteroidCount > 0))
				{
					ObjectList.removeElementAt(asteroidLoc);
					for (int k=0; k<ObjectList.size(); k++)
					{
						if (ObjectList.elementAt(k) instanceof PlayerShip)
						{
							ObjectList.removeElementAt(k);
							psExist = false;
							lives -= 1;
							// finish loops
							k = ObjectList.size() + 10;
							i = ObjectList.size() + 10;
						}
					}
				}
			}
			System.out.println("PlayerShip crashed into Asteroid.");
		}
		else
		{
			gameOver();
		}
		
	}
	
	//enemy colides with your ship
	public void psHitNPS()
	{
		if (lives > 1)
		{
			for (int i=0; i<ObjectList.size(); i++)
			{
				if (ObjectList.elementAt(i) instanceof NonPlayerShip)
				{
					ObjectList.removeElementAt(i);
					i = ObjectList.size();
					lives -= 1;
					System.out.println("PlayerShip crashed into Non-PlayerShip.");
				}
			}
		}
		else
		{
			System.out.println("PlayerShip crashed into Non-PlayerShip.");
			gameOver();
		}
	}
	
	public void psHitAsteroid()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Asteroid)
			{
				ObjectList.removeElementAt(i);
			}
		}
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Asteroid)
			{
				ObjectList.removeElementAt(i);
			}
		}
		System.out.println("Two Asteroids crashed into each other.");
	}
	
	public void asteroidHitNPS()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof Asteroid)
			{
				ObjectList.removeElementAt(i);
				i = ObjectList.size();
			}
		}
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof NonPlayerShip)
			{
				ObjectList.removeElementAt(i);
				psExist = false;
				i = ObjectList.size();
				
			}
		}
		System.out.println("Asteroid destroyed Non-PlayerShip.");
	}
	
	public void tick()
	{
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof MoveableGameObject)
			{
				MoveableGameObject mgObj = (MoveableGameObject) ObjectList.elementAt(i);
				mgObj.move();
			}
			if (ObjectList.elementAt(i) instanceof SpaceStation)
			{
				SpaceStation ssObj = (SpaceStation) ObjectList.elementAt(i);

			}
			if (ObjectList.elementAt(i) instanceof Missile)
			{
				Missile mObj = (Missile) ObjectList.elementAt(i);
				mObj.setFuelLevel(mObj.getFuelLevel() - 2);
				if (mObj.getFuelLevel() <= 0)
				{
					ObjectList.removeElementAt(i);
					i -= 1;
				}
			}
		}
		
		gameTime += 1;
		System.out.println("Increase Elapsed Time");
	}
	
	public void print()
	{
		int missile = 0;
		for (int i=0; i<ObjectList.size(); i++)
		{
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				missile = PlayerShipO.getMissileCount();
			}
		}
		System.out.println("Score point: "+ score + "\n"
				+ "Missile count: " + missile + "\n"
				+ "Lives left: " + lives + "\n"
				+ "Game time: " + gameTime+ "\n");
	}
	
	
	// Print method using multiple for-loops to print in the format specified by professor
	public void printMap()
	{
		System.out.println(" ~~~ Map Display View ~~~ ");

		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof PlayerShip)
			{
				PlayerShip PlayerShipO= (PlayerShip) ObjectList.elementAt(i);
				System.out.println( "PlayerShip: " + PlayerShipO.toString() );
			}
		}
		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof PlayerMissile)
			{
				PlayerMissile pmObj= (PlayerMissile) ObjectList.elementAt(i);
				System.out.println( "PlayerMissile: " + pmObj.toString() );
			}
		}
		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof NonPlayerMissile)
			{
				NonPlayerMissile npmObj= (NonPlayerMissile) ObjectList.elementAt(i);
				System.out.println( "NonPlayerMissile: " + npmObj.toString() );
			}
		}
		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof NonPlayerShip)
			{
				NonPlayerShip nPlayerShipO= (NonPlayerShip) ObjectList.elementAt(i);
				System.out.println( "Non-Player Ship: " + nPlayerShipO.toString());
			}
		}
		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof Asteroid)
			{
				Asteroid aObj= (Asteroid) ObjectList.elementAt(i);
				System.out.println( "Asteroid: " + aObj.toString() );
			}
		}
		for (int i=0; i<ObjectList.size(); i++){
			if (ObjectList.elementAt(i) instanceof SpaceStation)
			{
				SpaceStation ssObj= (SpaceStation) ObjectList.elementAt(i);
				System.out.println("Station: " + ssObj.toString() );
			}
		}
		System.out.println();
	}

	// Out of lives givin you option to try agian or quit 
	public void gameOver()
	{
		game.setTitle("Game Over");

		boolean over = Dialog.show("You Lost", "Try Again?", "Yes", "No");
		if (over)
			System.exit(0);
		else
			init();

	}
	
	
	

}
