package playerData;

/**
 * @author Alex Tompkins (ato47)
 *
 */
public class Toy {
	private ToyType toyType;
	private int durability;
	
	/**
	 * Initialises a new Toy object with properties based on the given values.
	 * 
	 * @param toyType
	 * The type of the toy. Used to access generic statistics that all members of this toy�s type share, 
	 * e.g. the base amount of happiness a toy gains from playing with this toy.
	 * @param durability
	 * A number representation of the durability or �health� of this specific toy. 
	 * Used to determine how much more damage a toy can take before breaking. 
	 */
	public Toy(ToyType toyType) {
		this.toyType = toyType;
		this.durability = 100;
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
	 * Changes the durability of this toy by a given value.
	 * @param value
	 * The amount to change the durability by (would normally be negative to show damage to toy).
	 */
	public void changeDurability(int value) {
		durability += value;
	}
	
	public String toString() {
		return String.format("%s (%d%%)", toyType.getName(), durability);
	}
}
