package unittests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.Font;
import javax.swing.ImageIcon;

import gui.GuiRunner;
import gui.PlayerSetup;

import model.FoodType;
import model.Player;
import model.Species;
import model.ToyType;

/**
 * Tests that the PlayerSetup GUI component can correctly generate a new player based on its fields and inputs.
 * @author Andrew Davidson (ada130)
 */
public class PlayerSetupTest {

	@Test
	public void test() {
		GuiRunner fontLoader = new GuiRunner();
		Font testFont = fontLoader.loadFont(PetSetupTest.class.getResourceAsStream("/fonts/Poppins/Poppins-Regular.ttf")).deriveFont(14f);
		Species[] species = new Species[] {new Species("test species", new ImageIcon(), 40, 20, 20, 20, 5, 15)};
		ToyType[] toyTypes = new ToyType[] {new ToyType("test toy type", new ImageIcon(), 20, 30)};
		FoodType[] foodTypes = new FoodType[] {new FoodType("test food type", new ImageIcon(), 20, 20, 20, 5)};
		PlayerSetup testPlayerSetup = new PlayerSetup(species, new String[] {species[0].getName()}, toyTypes, foodTypes, testFont, testFont, 0);
			
		assertTrue(!testPlayerSetup.fieldsFilled());
		Player generated = testPlayerSetup.generatePlayer(250);
		assertEquals(generated.getMoney(), 250);
		assertEquals(generated.getName(), "");
		assertEquals(generated.getPets().length, 1);
		assertEquals(generated.getPets()[0].getSpecies(), species[0]);
	}
}
