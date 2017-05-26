package application;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Test;

public class SpeciesTest {

	@Test
	public void testToyDamageGeneration() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		
		int damage;
		for (int i=0; i<10000; i++) {
			damage = species.genToyDamage();
			assertTrue((damage <= 25) && (damage >= 15));
		}
	}

}
