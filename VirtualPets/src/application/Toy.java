package application;

/**
 * A class to represent a toy with a type and a durability.
 * @author Alex Tompkins (ato47)
 */
public class Toy {
	private ToyType toyType;
	private int durability;
	
	/**
	 * Initialises a new Toy object with properties based on the given ToyType.
	 * 
	 * @param toyType
	 * The type of the toy. Used to access generic statistics that all members of this toy�s type share, 
	 * e.g. the base amount of happiness a toy gains from playing with this toy.
	 */
	public Toy(ToyType toyType) {
		this.toyType = toyType;
		this.durability = 100;
	}
	
	/**
	 * Initialises a new Toy object with properties based on the given ToyType and a durability
	 * as given. Should only be used when loading from a saved game.
	 * @param toyType
	 * The type of the toy. Used to access generic statistics that all members of this toy�s type share, 
	 * e.g. the base amount of happiness a toy gains from playing with this toy.
	 * @param durability
	 * The percentage of durability, or 'health', that this toy has remaining.
	 */
	public Toy(ToyType toyType, int durability) {
		this.toyType = toyType;
		this.durability = durability;
	}
	
	// Getters
	public ToyType getToyType() {
		return this.toyType;
	}
	
	public int getDurability() {
		return this.durability;
	}
	// End Getters
	
	/**
	 * Sets the ToyType of the toy. Should only be used when loading saved games.
	 * @param toyType
	 * The ToyType to set this toy to.
	 */
	public void setToyType(ToyType toyType) {
		this.toyType = toyType;
	}
	
	/**
	 * Changes the durability of this toy by a given value.
	 * @param value
	 * The amount to change the durability by (would normally be negative to show damage to toy).
	 */
	public void changeDurability(int value) {
		durability += value;
		if (durability > 100)
			durability = 100;
	}
}
