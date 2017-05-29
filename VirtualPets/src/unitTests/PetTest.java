package unitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.ImageIcon;

import model.FoodType;
import model.Pet;
import model.Species;
import model.Toy;
import model.ToyType;

/**
 * Tests the non-getter/setter methods of pet: eat, play, toilet, sleep, cure, discipline, revive, and end turn.
 * @author Andrew Davidson (ada130)
 * @author Alex Tompkins (ato47)
 */
public class PetTest {
	Species testSpecies;
	ToyType testToyType;
	FoodType testFoodType;
	ToyType testFavToyType;
	FoodType testFavFoodType;
	
	@Before
	public void setUp() {
		testSpecies = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		testToyType = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		testFoodType = new FoodType("Test food type", new ImageIcon(), 10, 12, 8, 5);
		testFavToyType = new ToyType("Test favtoy type", new ImageIcon(), 20, 15);
		testFavFoodType = new FoodType("Test favfood type", new ImageIcon(), 15, 10, 6, 4);
	}
	
	@Test
	public void testEat() {
		Pet testPet = new Pet("Test Pet", testSpecies, true, true, true, true, 50, 50, 50, 40, 2, testFavToyType, testFavFoodType);

		assertEquals(testPet.getHunger(), 50);
		assertEquals(testPet.getHappiness(), 50);
		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.eat(testFoodType);
		assertEquals(testPet.getHunger(), 38);
		assertEquals(testPet.getHappiness(), 58);
		assertEquals(testPet.getWeight(), 45);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet.eat(testFavFoodType);
		assertEquals(testPet.getHunger(), 28);
		assertEquals(testPet.getHappiness(), 67);
		assertEquals(testPet.getWeight(), 49);
		assertEquals(testPet.getActionPoints(), 0);
	}
	
	@Test
	public void testPlay() {
		Pet testPet = new Pet("Test Pet", testSpecies, true, true, true, true, 50, 50, 20, 40, 2, testFavToyType, testFavFoodType);

		assertEquals(testPet.getHunger(), 50);
		assertEquals(testPet.getHappiness(), 20);
		assertEquals(testPet.getEnergy(), 50);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.play(new Toy(testToyType));
		assertEquals(testPet.getHunger(), 55);
		assertEquals(testPet.getHappiness(), 50);
		assertEquals(testPet.getEnergy(), 40);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet.play(new Toy(testFavToyType));
		assertEquals(testPet.getHunger(), 60);
		assertEquals(testPet.getHappiness(), 72);
		assertEquals(testPet.getEnergy(), 30);
		assertEquals(testPet.getActionPoints(), 0);
	}
	
	@Test
	public void testToilet() {
		Pet testPet = new Pet("Test Pet", testSpecies, testToyType, testFoodType);

		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.goToToilet();
		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet = new Pet("Test Pet", testSpecies, true, true, true, true, 50, 50, 50, 60, 2, testFavToyType, testFavFoodType);
		testPet.goToToilet();
		assertEquals(testPet.getWeight(), 53);
	}	
	
	@Test
	public void testSleep() {
		Pet testPet = new Pet("Test Pet", testSpecies, true, true, true, true, 50, 50, 50, 40, 2, testFavToyType, testFavFoodType);

		assertEquals(testPet.getEnergy(), 50);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.sleep();
		assertEquals(testPet.getEnergy(), 80);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet.sleep();
		assertEquals(testPet.getEnergy(), 100);
	}	
	
	@Test
	public void testCure() {
		Pet testPet = new Pet("Test Pet", testSpecies, false, false, false, false, 50, 50, 50, 50, 2, testToyType, testFoodType);

		assertEquals(testPet.getHappiness(), 50);
		assertEquals(testPet.getActionPoints(), 2);
		assertEquals(testPet.isHealthy(), false);
		
		testPet.cure();
		assertEquals(testPet.getHappiness(), 70);
		assertEquals(testPet.getActionPoints(), 1);
		assertEquals(testPet.isHealthy(), true);
	}
	
	@Test
	public void testDiscipline() {
		Pet testPet = new Pet("Test Pet", testSpecies, false, false, true, true, 50, 50, 50, 50, 2, testToyType, testFoodType);

		assertEquals(testPet.getHappiness(), 50);
		assertEquals(testPet.getActionPoints(), 2);
		assertEquals(testPet.isBehaving(), false);
		
		testPet.discipline();
		assertEquals(testPet.getHappiness(), 20);
		assertEquals(testPet.getActionPoints(), 1);
		assertEquals(testPet.isBehaving(), true);
	}
	
	@Test
	public void testRevive() {
		Pet testPet = new Pet("Test Pet", testSpecies, false, false, false, true, 50, 50, 50, 50, 2, testToyType, testFoodType);

		assertEquals(testPet.isRevivable(), true);
		assertEquals(testPet.isAlive(), false);
		
		testPet.revive();
		assertEquals(testPet.isRevivable(), false);
		assertEquals(testPet.getHunger(), 35);
		assertEquals(testPet.getEnergy(), 65);
		assertEquals(testPet.getHappiness(), 65);
		assertEquals(testPet.getWeight(), testPet.getSpecies().getOptimumWeight()*9/10);
		assertEquals(testPet.isHealthy(), true);
		assertEquals(testPet.isBehaving(), true);
		assertEquals(testPet.isAlive(), true);
	}

	@Test
	public void testFinishTurn() {
		Pet testPet = new Pet("Test Pet", testSpecies, testToyType, testFoodType);

		assertEquals(testPet.finishTurn(), 240);
		assertEquals(testPet.getHunger(), 30);
		assertEquals(testPet.getHappiness(), 70);
		assertEquals(testPet.getEnergy(), 70);
	}
}
