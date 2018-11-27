package ac.liv.csc.comp201;

import ac.liv.csc.comp201.control.CoinHandling;
import ac.liv.csc.comp201.control.DrinkItems;
import ac.liv.csc.comp201.control.Drinks;
import ac.liv.csc.comp201.control.MakingDrinks;
import ac.liv.csc.comp201.control.HotWaterHandling;
import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;

public class MachineController  extends Thread implements IMachineController {

	private boolean running=true;

	private IMachine machine;
	String input;//record input digits
	int drinkNum;//record selected drink
	boolean makingdrinking;//enter drink-making phase
	//long startTime; for time detection
	//long finishTime;
	//boolean timeDetect;
	Drinks drink;
	float temperature;
	private static final String version="1.22";

	public void startController(IMachine machine) {
		this.machine=machine;				// Machine that is being controlled
		//initialize variable
		machine.getKeyPad().setCaption(9,"Return Coins");
		input = "";
		drinkNum = 0;
		makingdrinking = false;
		//timeDetect = false; for time detection
		drink = new Drinks();//initialize series of drink information
		super.start();
	}


	public MachineController() {

	}


	private synchronized void runStateMachine() {
		CoinHandling coinhandling = new CoinHandling(machine);
		MakingDrinks drinkingMaking = new MakingDrinks(machine, drink);
		HotWaterHandling hotWaterHandling = new HotWaterHandling(machine);
		if(!makingdrinking) {
			hotWaterHandling.KeepTemperature(91);//control the default temperature
		}
		hotWaterHandling.Exception(temperature);
		boolean valid = false;//variable recording if it is a valid input
		int keyCode=machine.getKeyPad().getNextKeyCode();
		if (keyCode >= 0 && keyCode <= 8) {
			input = input + keyCode;
			machine.getDisplay().setTextString(input);
		}
		if(keyCode==9) {
			machine.getCoinHandler().clearCoinTry();
			input = "";
			machine.getDisplay().setTextString(input);
			coinhandling.ReturnCoin();//return the Coin
		}


		if(input.length()==3 || input.length()==4) {
			if(input.length()==3 && !(input.substring(0, 1).equals("5") || input.substring(0, 1).equals("6")) && !(input.equals("101")||input.equals("102")||input.equals("201")||input.equals("202")||input.equals("300"))){
				input = "";
				machine.getDisplay().setTextString("Wrong Code!");
			//if it is in three digits and no prefix, no included in valid code
			}
			else if(input.length()==4 || (input.length()==3 && !(input.substring(0, 1).equals("5") || input.substring(0, 1).equals("6")))){
				for(int i=0; i<drink.drinks.size();i++) {
					if(drink.drinks.get(i).code.equals(input)) {
						//find valid number
						drinkNum = i;
						valid = true;
						input = "";
					}
				}
				if(!valid) {
					input ="";
					machine.getDisplay().setTextString("Wrong Code!");
				}
			}

			if(valid){
				DrinkItems tempDrink = drink.drinks.get(drinkNum);
				if(tempDrink.cost*100 <= machine.getBalance()) {
					//if balance is enough 
					makingdrinking = drinkingMaking.enoughIngredients(drinkNum);
					//a boolean variable to decide if the drink can be made
					machine.setBalance(machine.getBalance()-(int)(drink.drinks.get(drinkNum).cost*100));
				}
				else {
					machine.getDisplay().setTextString("insufficient credit");
				}
				if(makingdrinking) {
					//startTime = System.currentTimeMillis();
					//timeDetect = true;
					if(machine.getCup()!=null) {
						machine.getCup().setWaterLevelLitres(0);
					}
					if(tempDrink.code.substring(0,1).equals("5")) {
						machine.vendCup(Cup.MEDIUM_CUP);
					}
					else if(tempDrink.code.substring(0,1).equals("6")) {
						machine.vendCup(Cup.LARGE_CUP);
					}
					else{
						machine.vendCup(Cup.SMALL_CUP);
					}
				}
			}
		}
	/**code for time detection
		if(makingdrinking && timeDetect) {
			timeDetect = false;
			finishTime = System.currentTimeMillis();
			System.out.println((double)(finishTime-startTime));
		}
**/
		if(makingdrinking) {
			drinkingMaking.makeDrink(drink.drinks.get(drinkNum));
			hotWaterHandling.KeepTemperature(drink.drinks.get(drinkNum).temperature);
			//keep the temperature to make the drink
			if(machine.getCup()!=null) {
				if(machine.getCup().getWaterLevelLitres()==drink.drinks.get(drinkNum).size) {
					valid = false;
					makingdrinking = false;
					//restart preparation phase
				}
			}

		}
		
		temperature = machine.getWaterHeater().getTemperatureDegreesC();

	}

	public void run() {
		// Controlling thread for coffee machine
		int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			counter++;
			try {
				Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}
	}

	public void updateController() {
		//runStateMachine();
	}

	public void stopController() {
		running=false;
	}




}
