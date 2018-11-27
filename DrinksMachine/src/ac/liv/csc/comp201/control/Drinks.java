package ac.liv.csc.comp201.control;

import java.util.ArrayList;

public class Drinks {
	public ArrayList<DrinkItems> drinks = new ArrayList<DrinkItems>();
	final static double[] recipe1 = {2,0,0,0};
	final static double[] recipe2 = {2,0,5,0};
	final static double[] recipe3 = {2,3,0,0};
	final static double[] recipe4 = {2,3,5,0};
	final static double[] recipe5 = {0,0,0,28};
	
	public Drinks() {
		
		
		AddRecipe("Black coffee","101",1.20,recipe1,95.9);
		AddRecipe("Black coffee","5101",1.40,recipe1,95.9);
		AddRecipe("Black coffee","6101",1.45,recipe1,95.9);
		
		AddRecipe("Black coffee With sugar","102",1.30,recipe2,95.9);
		AddRecipe("Black coffee With sugar","5102",1.50,recipe2,95.9);
		AddRecipe("Black coffee With sugar","6102",1.55,recipe2,95.9);
		
		AddRecipe("White coffee","201",1.20,recipe3,95.9);
		AddRecipe("White coffee","5201",1.40,recipe3,95.9);
		AddRecipe("White coffee","6201",1.45,recipe3,95.9);
		
		AddRecipe("White coffee with sugar","202",1.30,recipe4,95.9);
		AddRecipe("White coffee with sugar","5202",1.50,recipe4,95.9);
		AddRecipe("White coffee with sugar","6202",1.55,recipe4,95.9);

		AddRecipe("Hot chocolate","300",1.10,recipe5,90);
		AddRecipe("Hot chocolate","5300",1.30,recipe5,90);
		AddRecipe("Hot chocolate","6300",1.35,recipe5,90);
	}
	
	
	void AddRecipe(String name, String code, double cost, double[] recipe, double temperature) {
		DrinkItems drink = new DrinkItems(name, code, cost, recipe, temperature);
		drinks.add(drink);
	}
}
