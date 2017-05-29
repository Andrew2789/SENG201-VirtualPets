package unitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import javax.swing.ImageIcon;

import model.Species;

/**
 * Tests the only non-getter/setter method of Species: genToyDamage.
 * @author Andrew Davidson (ada130)
 * @author Alex Tompkins (ato47)
 */
public class SpeciesTest {

	@Test
	public void testToyDamageGeneration() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		
		int damage;
		for (int i=0; i<10000; i++) {
			damage = species.genToyDamage();
			assertTrue((damage <= 25) && (damage >= 15));
		}
		
		species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 5, 80);
		for (int i=0; i<10000; i++) {
			damage = species.genToyDamage();
			assertTrue((damage <= 80) && (damage >= 5));
		}
	}

}
