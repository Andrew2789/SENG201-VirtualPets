package application;

import javax.swing.ImageIcon;

/**
 * Instances of this class represent the various food types available to buy and consume in the game.
 * @author Andrew Davidson (ada130)
 */
public class FoodType implements Comparable<FoodType> {
	private String name;
	private ImageIcon icon;
	private int price;
	private int nutrition;
	private int tastiness;
	private int weight;
	
	/**
	 * Initialises all variables used by a FoodType instance.
	 * 
	 * @param name
	 * The name of the food
	 * @param icon
	 * The icon for this food
	 * @param price
	 * How much it will cost to buy this food from the ingame store using ingame currency
	 * @param nutrition
	 * How nutritious the food is - a more nutritious food will remove more hunger when consumed
	 * @param tastiness
	 * How tasty the food is - a tastier food will increase happiness more when consumed
	 * @param weight
	 * How heavy the food is
	 */
	public FoodType(String name, ImageIcon icon, int price, int nutrition, int tastiness, int weight) {
		this.name = name;
		this.icon = icon;
		this.price = price;
		this.nutrition = nutrition;
		this.tastiness = tastiness;
		this.weight = weight;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getNutrition() {
		return nutrition;
	}

	public int getTastiness() {
		return tastiness;
	}
	
	public int getWeight() {
		return weight;
	}
	// End Getters
	
	/**
	 * Facilitates ordering of FoodTypes based on alphabetical order of name.
	 */
	public int compareTo(FoodType other) {
		return this.name.compareTo(other.name);
	}
}