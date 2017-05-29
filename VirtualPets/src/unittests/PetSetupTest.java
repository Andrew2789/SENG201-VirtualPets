package unittests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Font;
import javax.swing.ImageIcon;

import gui.GuiRunner;
import gui.PetSetup;

import model.FoodType;
import model.Pet;
import model.Species;
import model.ToyType;

/**
 * Tests that the PetSetup GUI component can correctly generate a new pet based on its fields and inputs.
 * @author Andrew Davidson (ada130)
 */
public class PetSetupTest {

	@Test
	public void test() {
		GuiRunner fontLoader = new GuiRunner();
		Font testFont = fontLoader.loadFont(PetSetupTest.class.getResourceAsStream("/fonts/Poppins/Poppins-Regular.ttf")).deriveFont(14f);
		Species[] species = new Species[] {new Species("test species", new ImageIcon(), 40, 20, 20, 20, 5, 15)};
		ToyType[] toyTypes = new ToyType[] {new ToyType("test toy type", new ImageIcon(), 20, 30)};
		FoodType[] foodTypes = new FoodType[] {new FoodType("test food type", new ImageIcon(), 20, 20, 20, 5)};
		PetSetup testPetSetup = new PetSetup(species, new String[] {species[0].getName()}, toyTypes, foodTypes, testFont, testFont, 0);
	
		assertTrue(!testPetSetup.fieldsFilled());
		Pet generated = testPetSetup.generatePet();
		assertEquals(generated.getFavouriteFood(), foodTypes[0]);
		assertEquals(generated.getFavouriteToy(), toyTypes[0]);
		assertEquals(generated.getSpecies(), species[0]);
		assertEquals(generated.getName(), "");
	}
}
