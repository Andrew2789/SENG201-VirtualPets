package application;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Instances of this class represent a pet. The pet has a species and name, and has attributes
 * that must be maintained (hunger, energy, happiness, and weight). The pet may become unhealthy,
 * misbehave, or die. The pet also has a single favourite toy and food. Pets are interacted with 
 * through a set of actions: eat, play, rest, go to toilet, be cured, be disciplined, get revived.
 * @author Andrew Davidson (ada130)
 */
public class Pet {
	private String name;
	private Species species;
	
	private boolean healthy = true;
	private boolean behaving = true;
	private boolean alive = true;
	private boolean revivable = true;
	
	private int hunger = 20;
	private int energy = 80;
	private int happiness = 80;
	private int weight;
	private int actionPoints = 2;
	
	private ToyType favouriteToy;
	private FoodType favouriteFood;
	
	/**
	 * Initialises all variables used by a pet instance.
	 * @param name
	 * The name of the pet.
	 * @param species
	 * The species of the pet. This species contains species specific information about the pet, 
	 * such as how much it damages toys, and how much it gains hunger per turn.
	 * @param favouriteToy
	 * The pet's favourite toy. Randomly chosen in the game environment.
	 * @param favouriteFood
	 * The pet's favourite food. Randomly chosen in the game environment.
	 */
	public Pet(String name, Species species, ToyType favouriteToy, FoodType favouriteFood) {
		this.name = name;
		this.species = species;
		this.weight = species.getOptimumWeight();
		this.favouriteToy = favouriteToy;
		this.favouriteFood = favouriteFood;
	}
	
	/**
	 * Sets all variables used by a pet instance - this overloaded constructor should be used
	 * when loading from a saved game.
	 * @param name
	 * The name of the pet.
	 * @param species
	 * The species of the pet. This species contains species specific information about the pet, 
	 * such as how much it damages toys, and how much it gains hunger per turn.
	 * @param healthy
	 * Whether the pet is healthy or not.
	 * @param behaving
	 * Whether the pet is behaving or not.
	 * @param alive
	 * Whether the pet is alive.
	 * @param revivable
	 * Whether the pet has not already died.
	 * @param hunger
	 * The pet's hunger level (higher means hungrier).
	 * @param energy
	 * The pet's energy level (higher means more energetic).
	 * @param happiness
	 * The pet's happiness level (higher means happier).
	 * @param weight
	 * The weight of the pet.
	 * @param actionPoints
	 * The number of actions the pet has left for this turn.
	 * @param favouriteToy
	 * The pet's favourite toy. Randomly chosen in the game environment.
	 * @param favouriteFood
	 * The pet's favourite food. Randomly chosen in the game environment.
	 */
	public Pet(String name, Species species, boolean healthy, boolean behaving, boolean alive, boolean revivable, int hunger, 
			int energy, int happiness, int weight, int actionPoints, ToyType favouriteToy, FoodType favouriteFood) {
		this.name = name;
		this.species = species;
		this.healthy = healthy;
		this.behaving = behaving;
		this.alive = alive;
		this.revivable = revivable;
		this.hunger = hunger;
		this.energy = energy;
		this.happiness = happiness;
		this.weight = weight;
		this.actionPoints = actionPoints;
		this.favouriteToy = favouriteToy;
		this.favouriteFood = favouriteFood;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public Species getSpecies() {
		return species;
	}
	
	public boolean isHealthy() {
		return healthy;
	}
	
	public boolean isBehaving() {
		return behaving;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isRevivable() {
		return revivable;
	}
	
	public int getHunger() {
		return hunger;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getHappiness() {
		return happiness;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getActionPoints() {
		return actionPoints;
	}
	
	public ToyType getFavouriteToy() {
		return favouriteToy;
	}
	
	public FoodType getFavouriteFood() {
		return favouriteFood;
	}
	// End Getters
	
	// Private variable modifiers
	/**
	 * Sets the species of a pet to the specified species - should only be used when loading from 
	 * a saved game.
	 * @param species
	 * The species to use.
	 */
	public void setSpecies(Species species) {
		this.species = species;
	}
	
	/**
	 * Sets the favourite ToyType of a pet to the specified ToyType - should only be used when 
	 * loading from a saved game.
	 * @param toy
	 * The ToyType to set as favourite.
	 */
	public void setFavouriteToy(ToyType toy) {
		this.favouriteToy = toy;
	}
	
	/**
	 * Sets the favourite FoodType of a pet to the specified FoodType - should only be used when 
	 * loading from a saved game.
	 * @param food
	 * The FoodType to set as favourite.
	 */
	public void setFavouriteFood(FoodType food) {
		this.favouriteFood = food;
	}
	
	/**
	 * Changes the hunger level of the pet.
	 * @param amount
	 * An integer amount to change the hunger by (negative or positive). Hunger cannot go below 0
	 * or above 100, this function will change out of bound values to be on the relevant boundary.
	 */
	private void changeHunger(int amount) {
		hunger += amount;
		if (hunger > 100)
			hunger = 100;
		else if (hunger < 0)
			hunger = 0;
	}
	
	/**
	 * Changes the energy level of the pet.
	 * @param amount
	 * An integer amount to change the energy by (negative or positive). Energy cannot go below 0
	 * or above 100, this function will change out of bound values to be on the relevant boundary.
	 */
	private void changeEnergy(int amount) {
		energy += amount;
		if (energy > 100)
			energy = 100;
		else if (energy < 0)
			energy = 0;
	}
	
	/**
	 * Changes the happiness level of the pet.
	 * @param amount
	 * An integer amount to change the happiness by (negative or positive). Happiness cannot go below 
	 * 0 or above 100, this function will change out of bound values to be on the relevant boundary.
	 */
	private void changeHappiness(int amount) {
		happiness += amount;
		if (happiness > 100)
			happiness = 100;
		else if (happiness < 0)
			happiness = 0;
	}
	
	/**
	 * Changes the weight of the pet.
	 * @param amount
	 * An integer amount to change the weight by (negative or positive). Weight cannot go below optimum
	 * apart from by starvation, and weight must always be between 1/3 optimum and 5/3 optimum
	 * @param starving
	 * This is true for starvation induced weight changes, allows them to decrease weight below optimum
	 */
	private void changeWeight(int amount, boolean starving) {
		if (starving) {
			weight += amount;
			if (weight < species.getOptimumWeight()*1/3)
				weight = species.getOptimumWeight()*1/3;
		}
		else if (amount > 0) {
			weight += amount;
			if (weight > species.getOptimumWeight()*5/3)
				weight = species.getOptimumWeight()*5/3;
		}
		else if (weight > species.getOptimumWeight()) {
			weight += amount;
			if (weight < species.getOptimumWeight())
				weight = species.getOptimumWeight();
		}
	}
	// End private variable modifiers
	
	
	// Pet actions
	
	/**
	 * Makes the pet eat a food of the specified food type. Increases happiness and decreases hunger.
	 * Happiness increases more if the food type is the pet's favourite food type.
	 * @param food
	 * The type of the food to be eaten.
	 */
	public void eat(FoodType food) {
		changeHappiness(food.getTastiness());
		changeHunger(-food.getNutrition());
		changeWeight(food.getWeight(), false);
		if (food == favouriteFood)
			changeHappiness(food.getTastiness()/2);
		actionPoints -= 1;
	}
	
	/**
	 * Makes the pet play with a toy. This damages the toy, increases the pet's happiness, decreases
	 * the pet's energy, and increases the pet's hunger. Happiness increases more if the toy's type 
	 * is the pet's favourite toy type.
	 * @param toy
	 * The toy to be played with
	 */
	public void play(Toy toy) {
		toy.changeDurability(-species.genToyDamage());
		changeHappiness(toy.getToyType().getHappinessGain());
		changeEnergy(-10);
		changeHunger(5);
		if (toy.getToyType() == favouriteToy)
			changeHappiness(toy.getToyType().getHappinessGain()/2);
		actionPoints -= 1;
	}
	
	/**
	 * Makes the pet go to the toilet. Decreases the pet's weight.
	 */
	public void goToToilet() {
		changeWeight(-(int)Math.round(((double)species.getOptimumWeight()/6)), false);
		actionPoints -= 1;
	}
	
	/**
	 * Makes the pet sleep. Increases the pet's energy.
	 */
	public void sleep() {
		changeEnergy(30);
		actionPoints -= 1;
	}
	
	/**
	 * Cures the pet, making it happy and curing its sickness. The player should have some money
	 * deducted.
	 */
	public void cure() {
		healthy = true;
		changeHappiness(20);
		actionPoints -= 1;
	}
	
	/**
	 * Disciplines the pet, making it sad but also making it stop misbehaving.
	 */
	public void discipline() {
		behaving = true;
		changeHappiness(-30);
		actionPoints -= 1;
	}
	
	/**
	 * Revives the pet, setting it to reasonably healthy stats and setting attributes such that
	 * the pet cannot be revived again.
	 */
	public void revive() {
		revivable = false;
		hunger = 35;
		energy = 65;
		happiness = 65;
		weight = species.getOptimumWeight()*9/10;
		healthy = true;
		behaving = true;
		alive = true;
	}
	// End pet actions
	
	/**
	 * Calculates score based on pet's stats.
	 * 
	 * Generates random events - the pet has a chance to become sick, start misbehaving, or die.
	 * 
	 * Increments hunger by the pet's species hunger gain stat and decrements energy and happiness by the 
	 * species energy loss and happiness loss stats respectively. Resets action points.
	 * 
	 * Decerements stats if the pet is unhealthy, misbehaving, or starving.
	 * 
	 * Chance to become unhealthy:
	 * +0-100% for hunger 65-85
	 * +0-100% for energy 30-20
	 * +0-100% for weight deviation 2/6-3/6
	 * 
	 * Chance to start misbehaving:
	 * +0-100% for hunger 70-90
	 * +0-100% for happiness 40-20
	 * 
	 * Chance to die:
	 * +0-100% for hunger 90-100
	 * +0-100% for energy 10-0
	 * 
	 * Starts starving when hunger greater/equal 90
	 * 
	 * @return
	 * The score for this pet
	 */
	public int finishTurn() {
		if (alive) {
			//Generate score for this round based on stats before adjusting them and generating random events
			int score = happiness + energy + (100-hunger) + Math.abs(weight-species.getOptimumWeight())*2;
			if (!healthy)
				score -= 50;
			if (!behaving)
				score -= 50;
			
			//Generate random events - if stats are sufficiently low, pet may get sick, start to misbehave, or die
			double optWeight = species.getOptimumWeight();
			int chance = 0;
			
			//Chance to die
			if (energy < 10)
				chance += (10-energy)*10;
			if (Math.abs(weight-optWeight) > optWeight/2)
				chance += ((double)Math.abs(weight-optWeight) - optWeight/2)/(optWeight/6)*100;
			if (ThreadLocalRandom.current().nextInt(0, 100) < chance) {
				alive = false;
				behaving = true;
				healthy = true;
				hunger = 0;
				energy = 0;
				happiness = 0;
				weight = 0;
				actionPoints = 0;
			}
			//Only continue if the pet did not die
			else {
				//Chance to get sick
				if (healthy) {
					chance = 0;
					if (hunger > 65)
						chance += (hunger-65)*5;
					if (energy < 30)
						chance += (30-energy)*10;
					if (Math.abs(weight-optWeight) > optWeight/3)
						chance += ((double)Math.abs(weight-optWeight) - optWeight/3)/(optWeight/6)*100;
					if (ThreadLocalRandom.current().nextInt(0, 100) < chance) {
						healthy = false;
					}
				}
				
				//Chance to start misbehaving
				if (behaving) {
					chance = 0;
					if (hunger > 70)
						chance += (90-hunger)*5;
					if (happiness < 40)
						chance += (40-happiness)*5;
					if (ThreadLocalRandom.current().nextInt(0, 100) < chance) {
						behaving = false;
					}
				}
				
				//Change stats by attribute values
				changeHunger(species.getHungerGain());
				changeEnergy(-species.getEnergyLoss());
				changeHappiness(-species.getHappinessLoss());
				actionPoints = 2;
				
				//If the pet is starving, sharply decrease stats
				if (hunger >= 90) {
					changeWeight(-species.getOptimumWeight()/3, true);
					changeEnergy(-25);
					changeHappiness(-35);
				}
				
				//If pet is sick, decrease energy and happiness
				if (!healthy) {
					changeEnergy(-10);
					changeHappiness(-10);
				}
				
				//If pet is misbehaving, decrease happiness
				if (!behaving)
					changeHappiness(-20);
			}
			return score;
		}
		//Dead pets generate no score
		else
			return 0;
	}
}
