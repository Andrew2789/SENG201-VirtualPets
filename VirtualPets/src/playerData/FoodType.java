
/**
 * @author Andrew Davidson (ada130)
 * Instances of this class represent the various food types available to buy and consume in the game.
 */
public class FoodType {
	private String name;
	private double price;
	private int nutrition;
	private int tastiness;
	private int weight;
	
	/**
	 * Initialises all variables used by a FoodType instance.
	 * 
	 * @param name
	 * The name of the food.
	 * @param price
	 * How much it will cost to buy this food from the ingame store using ingame currency.
	 * @param nutrition
	 * How nutritious the food is - a more nutritious food will remove more hunger when consumed.
	 * @param tastiness
	 * How tasty the food is - a tastier food will increase happiness more when consumed.
	 */
	public FoodType(String name, double price, int nutrition, int tastiness, int weight) {
		this.name = name;
		this.price = price;
		this.nutrition = nutrition;
		this.tastiness = tastiness;
		this.weight = weight;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public double getPrice() {
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
}