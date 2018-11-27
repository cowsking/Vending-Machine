package ac.liv.csc.comp201.control;

public class DrinkItems {
	
	public String name;
	public String code;
	public double cost;
	public double temperature;
	public double recipe[] = new double[4];
	public static final double SMALL=0.34;
	public static final double MEDIUM=0.45;
	public static final double LARGE=0.56;
	public double size;
	
	public DrinkItems(String name, String code, double cost, double[] recipe, double temperature) {
		double temp[] = new double[4];
		//judge the cup size
		if(code.substring(0, 1).equals("5")) {
			this.name = "Medium "+ name;
			for(int i = 0; i< recipe.length; i++) {
				temp[i] = (MEDIUM/SMALL) * recipe[i];
			}
			this.recipe = temp;
			this.size = MEDIUM;
		}
		else if(code.substring(0, 1).equals("6")) {
			this.name = "Large "+ name;
			for(int i = 0; i< recipe.length; i++) {
				temp[i] = (LARGE/SMALL) * recipe[i];
			}
			this.recipe = temp;
			this.size = LARGE;
		}
		else {
			this.name = name;
			this.recipe = recipe;
			this.size = SMALL;
		}
		//save other information
		this.code = code;
		this.cost = cost;
		this.temperature = temperature;
		

	}
}
