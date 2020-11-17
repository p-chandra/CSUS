package com.mycompany.a2;

import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.mycompany.a2.interfaces.IGameWorld;
import com.mycompany.a2.interfaces.IIterator;
import com.mycompany.a2.objects.*;

public class GameWorld extends Observable implements IGameWorld
{
	private boolean psExist;
	private boolean soundOn;
	private boolean gameOver;
	private int score;
	private int lives;
	private int time;
	private int width;
	private int height;
	
	private Game game;
	
	//All state variables are stored here
	private GameCollection store;
	
	public GameWorld(Game game)
	{
		this.game = game;
		init();
		soundOn = true;
	}
	
	public void init() 
	{
		game.setTitle("Asteroids");
		store = new GameCollection();
		psExist = false;
		gameOver = false;
		score = 0;
		lives = 3;
		time = 0;
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public int getPlayerScore()
	{
		return score;
	}
	
	public int getPlayerLives()
	{
		return lives;
	}

	public int getPlayerMissileCount()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				return ((PlayerShip) obj).getMissileCount();
			}
		}
		return 0;
	}

	public boolean getPlayerSoundOn()
	{
		return soundOn;
	}
	
	public int getPlayerTime()
	{
		return time;
	}
	
	public void toggleSound(boolean state)
	{
		soundOn = state;
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
		System.out.println("state: "+ soundOn);
	}
	
	public void addNewAsteroid()
	{
		Asteroid asteroid = new Asteroid(width, height);
		asteroid.setColor(ColorUtil.GRAY);
		store.add(asteroid);
		System.out.println("A new ASTEROID has been created.");
		
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void addNPS()
	{
		NonPlayerShip nps = new NonPlayerShip(width, height);
		nps.setColor(ColorUtil.CYAN);
		store.add(nps);
		System.out.println("A new NONPLAYERSHIP has been created.");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void addSpaceStation()
	{
		SpaceStation ss = new SpaceStation(width, height);
		ss.setColor(ColorUtil.GREEN);
		store.add(ss);
		System.out.println("A new SPACESTATION has been created.");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void addPlayerShip()
	{
		if (psExist && !gameOver)
		{
			System.out.println("PLAYERSHIP already exists.");
		}
		else
		{
			if(!gameOver)
			{
				PlayerShip ps = new PlayerShip(width, height);
				ps.setColor(ColorUtil.WHITE);
				store.add(ps);
				psExist = true;
				System.out.println("A new PLAYERSHIP has been created.");
			}
			else
			{
				System.out.println("GAME OVER.");
			}
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void increaseSpeed()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				// increase speed by 1
				psObj.setSpeed(psObj.getSpeed() + 1);
				System.out.println("PlayerShip speed increased.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void decreaseSpeed()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				// decrease speed by 1
				psObj.setSpeed(psObj.getSpeed() - 1);
				System.out.println("PlayerShip speed decreased.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void turnPlayerShipLeft(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				psObj.steerLeft(turnAmt);
				System.out.println("PlayerShip turned left.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void turnPlayerShipRight(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.steerRight(turnAmt);
				System.out.println("PlayerShip turned right.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void turnMissileLauncherLeft(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.steerLauncherLeft(turnAmt);
				System.out.println("Missile Launcher turned left.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void launchPSMissile()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				if (psObj.hasMissile())
				{
					psObj.fire();
					int launcherDirection = psObj.getLauncherDir();
					PlayerMissile missile = new PlayerMissile(launcherDirection, psObj.getX(), psObj.getY());
					missile.setColor(ColorUtil.YELLOW);
					store.add(missile);
					System.out.println("Launching PlayerShip missile.");
					break;
				}
				else
				{
					System.out.println("Player ship out of missile.");
					break;
				}
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void launchNPSMissile()
	{
		IIterator it = store.getIterator();
		Boolean npsExist = false;
		
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				npsExist = true;
				NonPlayerShip npsObj= (NonPlayerShip) obj;
				
				if (npsObj.hasMissile())
				{
					npsObj.fire();
					int launcherDirection = npsObj.getLauncher().getDirection();
					NonPlayerMissile missile = new NonPlayerMissile(launcherDirection, npsObj.getX(), npsObj.getY());
					missile.setColor(ColorUtil.YELLOW);
					store.add(missile);
					System.out.println("Launching Non-PlayerShip missile.");
					break;
				}
				else
				{
					System.out.println("Non-player ship out of missile.");
					break;
				}
			}
		}
		if (!npsExist)
		{
			System.out.println("NonPlayerShip does not exist.");
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void jump()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.setX(width/2);
				psObj.setY(height/2);
				System.out.println("PlayerShip jumped back to center.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void reloadMissiles()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.reload();
				System.out.println("Reloading PlayerShip missile.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void killAsteroid()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Missile)
			{
				store.remove(it.getIndex());
				
				it = store.getIterator();
				while (it.hasNext())
				{
					Object o = it.getNext();
					if (o instanceof Asteroid)
					{
						store.remove(it.getIndex());
						score+=1;
						System.out.println("Asteroid is destoried by missile.");
						break;
					}
				}
				break;
			}
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void killNPS()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Missile)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		
		System.out.println("NonPlayerShip is destoried by missile.");
		score += 1;
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void npsKillsPS()
	{
		if (lives > 1)
		{
			IIterator it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof Missile)
				{
					store.remove(it.getIndex());
					break;
				}
			}
			it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof PlayerShip)
				{
					store.remove(it.getIndex());
					psExist = false;
					lives -= 1;
					break;
					
				}
			}
			System.out.println("Non-PlayerShip killed PlayerShip.");
		}
		else
		{
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			gameOver();
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void asteroidCrashedPS()
	{
		if(lives > 1)
		{
			int asteroidLoc = 0;
			int asteroidCount = 0;
			
			IIterator it = store.getIterator();
			if (!psExist)
			{
				System.out.println("PlayerShip does not exist.");
			}
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof Asteroid)
				{
					// get first asteroid's location
					asteroidLoc = it.getIndex();
					asteroidCount++;
				}
				if (psExist && (asteroidCount > 0))
				{
					// remove first asteroid found
					store.remove(asteroidLoc);
					// start a new while loop to remove playership before break
					it = store.getIterator();
					while (it.hasNext())
					{
						obj = it.getNext();
						if (obj instanceof PlayerShip)
						{
							store.remove(it.getIndex());
							psExist = false;
							lives -= 1;
							System.out.println("PlayerShip crashed into Asteroid.");
							// finish inside loop
							break;
						}
					}
					// finish outside loop
					break;
				}
			}
		}
		else
		{
			/*
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			*/
			gameOver();
			return;
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
		
	}
	
	public void playerShipHitsNPS()
	{
		if (lives > 1)
		{
			IIterator it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof NonPlayerShip)
				{
					store.remove(it.getIndex());
					lives -= 1;
					System.out.println("PlayerShip crashed into Non-PlayerShip.");
					break;
				}
			}
		}
		else
		{
			System.out.println("PlayerShip crashed into Non-PlayerShip.");
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			gameOver();
			return;
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void twoAsteroidsCrash()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		System.out.println("Two Asteroids crashed into each other.");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void asteroidHitsNPS()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				store.remove(it.getIndex());
				break;
				
			}
		}
		System.out.println("Asteroid destoried a Non-PlayerShip.");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void tick()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof MoveableGameObject)
			{
				MoveableGameObject mgObj = (MoveableGameObject) obj;
				mgObj.move();
			}
			if (obj instanceof SpaceStation)
			{
				SpaceStation ssObj = (SpaceStation) obj;
				if ( (time % ssObj.getBlinkRate()) == 0 )
				{
					ssObj.setLightOn(true);
				}
				else
				{
					ssObj.setLightOn(false);
				}
			}
			if (obj instanceof Missile)
			{
				Missile mObj = (Missile) obj;
				mObj.setFuelLevel(mObj.getFuelLevel() - 2);
				if (mObj.getFuelLevel() <= 0)
				{
					store.remove(it.getIndex());
					// changing pointer after removing
					it.setIndex(it.getIndex()-1);
				}
			}
		}
		
		time += 1;
		System.out.println("One tick has passed.");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public void printDisplay()
	{
		int missile = 0;
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				missile = psObj.getMissileCount();
			}
		}
		System.out.println("Score point: "+ score + "\n"
				+ "Missile count: " + missile + "\n"
				+ "Elaspsed time: " + time + "\n"
				+ "Lives left: " + lives);
	}
	
	public void printMap()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				System.out.println( "Player Ship: " + psObj.toString() );
			}
			if (obj instanceof PlayerMissile)
			{
				PlayerMissile pmObj= (PlayerMissile) obj;
				System.out.println( "PlayerMissile: " + pmObj.toString() );
			}
			if (obj instanceof NonPlayerMissile)
			{
				NonPlayerMissile npmObj= (NonPlayerMissile) obj;
				System.out.println( "NonPlayerMissile: " + npmObj.toString() );
			}
			if (obj instanceof NonPlayerShip)
			{
				NonPlayerShip npsObj= (NonPlayerShip) obj;
				System.out.println( "Non-Player Ship: " + npsObj.toString());
			}
			if (obj instanceof Asteroid)
			{
				Asteroid aObj= (Asteroid) obj;
				System.out.println( "Asteroid: " + aObj.toString() );
			}
			if (obj instanceof SpaceStation)
			{
				SpaceStation ssObj= (SpaceStation) obj;
				System.out.println("Station: " + ssObj.toString() );
			}
		}
		System.out.println();
	}
	
	public void quitGame()
	{
		game.setTitle("Closing Game");
		boolean quit = Dialog.show("Confirm Quit",
				"Are you sure you want to quit?", 
				"YES", "NO");
		if (quit)
		{
			Display.getInstance().exitApplication();
		}
		else
		{
			game.setTitle("Asteroids");
		}
		game.show();
	}
	
	public void gameOver()
	{
		game.setTitle("Game Over");
		boolean flag = Dialog.show("OUT OF LIVES",
				"Do you want to try again?", 
				"YES", "NO");
		if (flag)
		{
			init();
			System.out.println("====================================");
			System.out.println("============= NEW GAME =============");
			System.out.println("====================================");
		}
		else
		{
			game.setTitle("Asteroids");
			lives = 0;
			gameOver = true;
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
		game.show();
	}
	
	public void newGame()
	{
		boolean flag = Dialog.show("New Game",
				"Do you want to start a new game?", 
				"YES", "NO");
		if (flag)
		{
			init();
			System.out.println("====================================");
			System.out.println("============= NEW GAME =============");
			System.out.println("====================================");
		}
	}
	
	public void about()
	{
		Dialog.show("About",
		"Creator: Minquan Li\n" + "Course: CSC133\n" 
		+ "Fall 2018\n" + "Asteroids v1.0",
		"Ok",null);
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}

}
