package unittests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.ImageIcon;

import model.FoodType;
import model.Pet;
import model.Player;
import model.Species;
import model.Toy;
import model.ToyType;

/**
 * Tests the non-getter/setter methods of Player: addFood, addToy, feed, playWith.
 * @author Andrew Davidson (ada130)
 * @author Alex Tompkins (ato47)
 */
public class PlayerTest {
	private Species testSpecies;
	private ToyType testToyType;
	private FoodType testFoodType;
	private FoodType testFoodType2;
	private Pet testPet;
	
	@Before
	public void setUp() {
		testSpecies = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		testToyType = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		testFoodType = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 4);
		testFoodType2 = new FoodType("Test food type2", new ImageIcon(), 4, 3, 2, 1);
		testPet = new Pet("Test Pet", testSpecies, testToyType, testFoodType);
	}
	
	@Test
	public void testFood() {
		Player testPlayer = new Player("Test Player", new Pet[] {testPet}, 50);

		testPlayer.addFood(testFoodType);
		assertTrue(testPlayer.getFood().get(testFoodType).equals(1));
		testPlayer.addFood(testFoodType);
		assertTrue(testPlayer.getFood().get(testFoodType).equals(2));
		testPlayer.addFood(testFoodType2);
		assertTrue(testPlayer.getFood().get(testFoodType).equals(2));
		assertTrue(testPlayer.getFood().get(testFoodType2).equals(1));

		testPlayer.feed(testPet, testFoodType);
		assertTrue(testPlayer.getFood().get(testFoodType).equals(1));
	}
	
	@Test
	public void testToy() {
		Player testPlayer = new Player("Test Player", new Pet[] {testPet}, 50);

		testPlayer.addToy(new Toy(testToyType));
		assertEquals(testPlayer.getToys().get(0).getDurability(), 100);
		testPlayer.addToy(new Toy(testToyType));
		testPlayer.playWith(testPet, testPlayer.getToys().get(1));
		assertTrue(testPlayer.getToys().get(1).getDurability() < 100);
		assertTrue(testPlayer.getToys().get(0).getDurability() == 100);
	}
}
