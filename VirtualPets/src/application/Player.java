package application;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alex Tompkins (ato47)
 *
 */
public class Player {
	private String name;
	
	private Pet[] pets;
	private HashMap<FoodType, Integer> food;
	private ArrayList<Toy> toys;
	
	private int money;
	private int score = 0;
	
	
	/**
	 * Initialises a new Player instance with the given name and array of pets.
	 * Sets food and toys to their respective empty collections.
	 * 
	 * @param name
	 * The name of the player, set at the start of the game. This will not change.
	 * @param pets
	 * A collection of the pets belonging to a player.
	 */
	public Player(String name, Pet[] pets, int money) {
		this.name = name;
		this.pets = pets;
		
		this.food = new HashMap<FoodType, Integer>();
		this.toys = new ArrayList<Toy>();
		
		this.money = money;
	}
	
	// Getters
	public String getName() {
		return this.name;
	}
	
	public Pet[] getPets() {
		return this.pets;
	}
	
	public HashMap<FoodType, Integer> getFood() {
		return this.food;
	}
	
	public ArrayList<Toy> getToys() {
		return this.toys;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public int getScore() {
		return this.score;
	}
	// End Getters
	
	/**
	 * Changes the amount of money the player has by a given amount.
	 * @param amount
	 * The amount of money to change the player's balance by (positive to give money to player, negative to take money away).
	 */
	public void changeMoney(int amount) {
		money += amount;
	}
	
	/**
	 * Changes the score of the player by a given value.
	 * @param value
	 * The value to change the player's score by (positive to add score).
	 */
	public void changeScore(int value) {
		score += value;
	}
	
	/**
	 * Adds one copy of the given food to the player�s inventory.
	 * @param foodToAdd
	 * A reference to the FoodType object you wish to add one of.
	 */
	public void addFood(FoodType foodToAdd) {
		if (food.containsKey(foodToAdd))
			food.put(foodToAdd, food.get(foodToAdd) + 1);
		else
			food.put(foodToAdd, 1);
	}

	/**
	 * Adds the given toy to the player�s inventory.
	 * @param toyToAdd
	 * A reference to the Toy object you want to add to the player's inventory.
	 */
	public void addToy(Toy toy) {
		toys.add(toy);
	}
	
	/**
	 * The player feeds a pet a food. The food is consumed and removed from the player's inventory.
	 * @param pet
	 * The pet to be fed
	 * @param foodType
	 * The food to feed the pet
	 */
	public void feed(Pet pet, FoodType foodType) {
		pet.eat(foodType);
		food.put(foodType, food.get(foodType)-1);
		if (food.get(foodType) == 0)
			food.remove(foodType);
	}
	
	/**
	 * The player plays with a pet using a toy. The toy is damaged and possibly broken by this.
	 * @param pet
	 * The pet to be played with
	 * @param toy
	 * The toy object to use for playing
	 * @return
	 * True if the toy does not break, false otherwise
	 */
	public boolean playWith(Pet pet, Toy toy) {
		pet.play(toy);
		if (toy.getDurability() <= 0) {
			toys.remove(toy);
			return false;
		}
		return true;
	}
}
