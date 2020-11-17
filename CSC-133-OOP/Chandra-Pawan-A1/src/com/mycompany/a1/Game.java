package com.mycompany.a1;

import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

public class Game extends Form {
	private GameWorld gw;
	boolean isQuitting = false;

	public Game() {
		gw = new GameWorld(this);
		gw.init();
		play();
	}

/*
Using switch-case to handle all user inputs and direct it to proper method in game
world. 

Implementing both boolean and dialog for quit. 

isEmpty checks for any input in textField before input
*/
	private void play() {
		final Label myLabel=new Label ("Enter a Command:");
		this.addComponent(myLabel);

		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();

		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				String sCommand = myTextField.getText().toString();
				myTextField.clear();

				if(!sCommand.isEmpty()) {
					if (!isQuitting){
						switch (sCommand.charAt(0)){
							case 'a':
								gw.addAsteroid();
								break;
							case 'y':
								gw.addNPS();
								break;
							case 'b':
								gw.addBlinkStation();
								break;
							case 's':
								gw.addPS();
								break;
							case 'i':
								gw.incSpeed();
								break;
							case 'd':
								gw.decSpeed();
								break;
							case 'l':
								gw.leftTurn();
								break;
							case 'r':
								gw.rightTurn();
								break;
							case '>':
								gw.direction();
								break;
							case 'f':
								gw.fireMissile();
								break;
							case 'L':
								gw.launchMissile();
							case 'j':
								gw.jump();
								break;
							case 'n':
								gw.reloadMissiles();
								break;
							case 'k':
								gw.killedAsteroid();
								break;
							case 'e':
								gw.missileHitNPS();
								break;
							case 'E':
								gw.psDown();
								break;
							case 'c':
								gw.asteroidHitPS();
								break;
							case 'h':
								gw.psHitNPS();
								break;
							case 'x':
								gw.psHitAsteroid();
								break;
							case 'I':
								gw.asteroidHitNPS();
								break;
							case 't':
								gw.tick();
								break;
							case 'p':
								gw.print();
								break;
							case 'm':
								gw.printMap();
								break;
							case 'q':
								boolean bOk= Dialog.show("Confirm quit", "Are you sure you want to quit?", "Ok", "Cancel");
								if(bOk) {
									isQuitting = true;
									System.exit(0);
								}
								show();
								break;
							default :
								System.out.println("Invalid Keyboard Input");
						}
					}else{
						System.out.println("Invalid Input");
					}
				}
			}
		});
	}
}

