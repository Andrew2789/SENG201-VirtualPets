
/**
 * @author Alex Tompkins (ato47)
 *
 */
public class ToyType {
	private String name;
	private double price;
	private int happinessGain;
	
	/**
	 * Initialises a new ToyType instance based on given values.
	 * 
	 * @param name
	 * The name of this type of toy. 
	 * @param price
	 * The base price of this type of toy.
	 * @param happinessGain
	 * A number representation of the base amount of happiness playing with this toy will give a pet.
	 */
	public ToyType(String name, double price, int happinessGain) {
		this.name = name;
		this.price = price;
		this.happinessGain = happinessGain;
	}
	
	// Getters
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getHappinessGain() {
		return this.happinessGain;
	}
	// End Getters
}
