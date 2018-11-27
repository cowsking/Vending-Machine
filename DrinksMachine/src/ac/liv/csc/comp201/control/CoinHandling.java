package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class CoinHandling {
	private IMachine machine;
	public CoinHandling(IMachine machine) {
		this.machine = machine;
		if(AllowEnterCoins()) {
			String temp = machine.getCoinHandler().getCoinKeyCode();
			machine.setBalance(machine.getBalance()+AddBalance(temp));
		}
	}
	
	public static int AddBalance(String temp) {//convert string to values in penny
		if(temp == null) {
			return 0;
		}
	    else if(temp.equals("ab")){
			return 1;
		}
		else if(temp.equals("ac")){
			return 5;
		}
		else if(temp.equals("ba")){
			return 10;
		}
		else if(temp.equals("bc")){
			return 20;
		}
		else if(temp.equals("bd")){
			return 50;
		}
		else if(temp.equals("ef")){
			return 100;
		}
		return 0;
	}
	public void ReturnCoin(){
		String[] CoinNames = {"ab","ac","ba","bc","bd","bd","ef"};
		int balance = machine.getBalance();
		machine.setBalance(0);
		for(int i = CoinNames.length-1; i >=0; i--) {
			if (balance==0) {
				break;
			}
			int value = AddBalance(CoinNames[i]);
			int need = balance/value;//calculate how many coins need
			if(machine.getCoinHandler().coinAvailable(CoinNames[i]) && need>0) {//if can dispense this coin
				boolean flag = true;
				while(flag && need > 0) {
					flag = machine.getCoinHandler().dispenseCoin(CoinNames[i]);//if return successfully 
					if(flag) {
						balance -= need * AddBalance(CoinNames[i]);
						need -= 1;
					}
				}
			}
		}
	}
	
	public boolean AllowEnterCoins(){
		if(2 > machine.getHoppers().getHopperLevelsGrams(0) && 28 > machine.getHoppers().getHopperLevelsGrams(3)) {//no drink to make
			machine.getDisplay().setTextString("Error: insufficient ingredients");
			machine.getCoinHandler().lockCoinHandler();
			return false;
		}
		machine.getCoinHandler().unlockCoinHandler();
		return true;
	}

	
	
	
	
}
