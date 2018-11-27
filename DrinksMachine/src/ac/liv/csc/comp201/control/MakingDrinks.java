package ac.liv.csc.comp201.control;


import ac.liv.csc.comp201.model.IMachine;


public class MakingDrinks {
	private IMachine machine;
	Drinks temp;
	public MakingDrinks(IMachine machine, Drinks temp) {
		this.machine = machine;
		this.temp = temp;
	}
	


	public boolean enoughIngredients(int drinkNum) {
		DrinkItems drink = temp.drinks.get(drinkNum);
		for(int i=0; i < drink.recipe.length; i++) {
			if(drink.recipe[i] > machine.getHoppers().getHopperLevelsGrams(i)) {
				return false;
			}
		}
		return true;
	}
	
	public void makeDrink(DrinkItems drink) {
		boolean finishCoffee = true;
		boolean finishMilk = true;
		boolean finishSugar = true;
		boolean finishChocolate = true;
		// variable record if the ingredient has been added
		if(machine.getCup()!=null) {
			if(drink.recipe[0] > machine.getCup().getCoffeeGrams()) {
				machine.getHoppers().setHopperOn(0);
				finishCoffee = false;
			}		
		
			if(finishCoffee) {
				machine.getHoppers().setHopperOff(0);
			}
			
			if(drink.recipe[1] > machine.getCup().getMilkGrams()) {
				machine.getHoppers().setHopperOn(1);
				finishMilk = false;
			}		
		
			if(finishMilk) {
				machine.getHoppers().setHopperOff(1);
			}
			
			if(drink.recipe[2] > machine.getCup().getSugarGrams()) {
				machine.getHoppers().setHopperOn(2);
				finishSugar = false;
			}		
		
			if(finishSugar) {
				machine.getHoppers().setHopperOff(2);
			}
			
			if(drink.recipe[3] > machine.getCup().getChocolateGrams()) {
				machine.getHoppers().setHopperOn(3);
				finishChocolate = false;
			}		
		
			if(finishChocolate) {
				machine.getHoppers().setHopperOff(3);
			}
			if(finishCoffee && finishMilk && finishSugar && finishChocolate) {//finished adding
				double size = drink.size;
				boolean temperatureReady = (machine.getWaterHeater().getTemperatureDegreesC()>drink.temperature*0.9)&&(machine.getWaterHeater().getTemperatureDegreesC()<drink.temperature*1.05);
				if(machine.getCup().getWaterLevelLitres() < size && temperatureReady) {//ready to make drink
					if(machine.getCup().getWaterLevelLitres()<=size*0.2) {//ensure 20% hot temperature 
						machine.getWaterHeater().setHotWaterTap(true);
					}
				}
				else {
					machine.getWaterHeater().setHotWaterTap(false);
				}
			//ensure 80 degree temperature
				if(machine.getCup().getTemperatureInC()>=80 && machine.getCup().getWaterLevelLitres()>=size*0.2) {
					machine.getWaterHeater().setColdWaterTap(true);
					machine.getWaterHeater().setHotWaterTap(false);
				}
				else if(machine.getCup().getTemperatureInC()<80 && machine.getCup().getWaterLevelLitres()>size*0.2) {
					machine.getWaterHeater().setHotWaterTap(true);
					machine.getWaterHeater().setColdWaterTap(false);
				}
				
				if(machine.getCup().getWaterLevelLitres()==size) {
					machine.getWaterHeater().setHotWaterTap(false);
					machine.getWaterHeater().setColdWaterTap(false);
				}
			}
		}
	
		
		
		
		
	}
	
	
}
