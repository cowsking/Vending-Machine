package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class HotWaterHandling {

	private IMachine machine;
	
	public HotWaterHandling(IMachine machine) {
		this.machine = machine;
		
	}
	
	public void KeepTemperature(double temperature) {
		machine.getWaterHeater().setHeaterOn();
		if(machine.getWaterHeater().getTemperatureDegreesC()>temperature) {
			machine.getWaterHeater().setHeaterOff();
			//if the temperature is higher it should be off
		}
	}
	
	public void Exception(float temperature) {
		double temp = temperature;
		double nowtemp = machine.getWaterHeater().getTemperatureDegreesC();
		if(machine.getWaterHeater().getTemperatureDegreesC()==100) {
			//for special situation to shutdown the vending machine
			machine.getDisplay().setTextString("Water Temperature Error: boiling water point");
			machine.shutMachineDown();
		}
		//for heater out of control
		if(machine.getWaterHeater().getHeaterOnStatus()) {
			if(temp > nowtemp+1) {
				machine.getDisplay().setTextString("Water Temperature Error: SHUTDOWN");
				machine.shutMachineDown();
			}
		}
		else{
			if(temp < nowtemp-1) {
				machine.getDisplay().setTextString("Water Temperature Error: SHUTDOWN1");
				machine.shutMachineDown();
			}
		}
	}
		
	
}

