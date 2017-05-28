package application;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Test;

/**
 * Tests the only non-getter/setter method of Toy: durability change.
 * @author Andrew Davidson (ada130)
 * @author Alex Tompkins (ato47)
 */
public class ToyTest {
	
	@Test
	public void testDurabilityDecrease() {
		ToyType toyType = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		Toy toy = new Toy(toyType);
		
		assertEquals(toy.getDurability(), 100);
		toy.changeDurability(-15);
		assertEquals(toy.getDurability(), 85);
	}

	@Test
	public void testDurabilityIncrease() {
		ToyType toyType = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		Toy toy = new Toy(toyType);
		
		toy.changeDurability(-15);

		toy.changeDurability(5);
		assertEquals(toy.getDurability(), 90);
		toy.changeDurability(500);
		assertEquals(toy.getDurability(), 100);
	}

}
