package application;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;

/**
 * @author Andrew Davidson (ada130)
 * This class stores all relevant information about a species of pet. Each instance of pet contains a single species
 * instance used to get information about species specific behavior.
 * None of a species attributes can be changed after initialisation.
 */
public class Species {
	private String name;
	private ImageIcon icon;
	private int optimumWeight;
	
	private int hungerGain;
	private int energyLoss;
	private int happinessLoss;
	
	private int minToyDamage;
	private int maxToyDamage;
	
	/**
	 * Initialises all variables used by a species instance.
	 * 
	 * @param name 
	 * The species name.
	 * @param optimumWeight
	 * The optimal weight of a member of this species.
	 * @param hungerGain
	 * The amount of hunger a member of this species gains per turn.
	 * @param energyLoss
	 * The amount of energy a member of this species loses per turn.
	 * @param minToyDamage
	 * The minimum amount of damage a member of this species will do to a toy.
	 * @param maxToyDamage
	 * The maximum amount of damage a member of this species will do to a toy.
	 */
	public Species(String name, ImageIcon icon, int optimumWeight, int hungerGain, int energyLoss, int happinessLoss, int minToyDamage, int maxToyDamage) {
		this.name = name;
		this.icon = icon;
		this.optimumWeight = optimumWeight;
		this.hungerGain = hungerGain;
		this.energyLoss = energyLoss;
		this.happinessLoss = happinessLoss;
		this.minToyDamage = minToyDamage;
		this.maxToyDamage = maxToyDamage;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public int getOptimumWeight() {
		return optimumWeight;
	}
	
	public int getHungerGain() {
		return hungerGain;
	}

	public int getEnergyLoss() {
		return energyLoss;
	}
	
	public int getHappinessLoss() {
		return happinessLoss;
	}
	
	public int getMinToyDamage() {
		return minToyDamage;
	}
	
	public int getMaxToyDamage() {
		return maxToyDamage;
	}
	// End Getters
	
	/**
	 * Generates and returns an amount to damage a toy by.
	 * 
	 * @return A random integer amount of damage between baseToyDamage and baseToyDamage + randomToyDamage, inclusive.
	 */
	public int genToyDamage() {
		return ThreadLocalRandom.current().nextInt(minToyDamage, maxToyDamage + 1);
	}
}