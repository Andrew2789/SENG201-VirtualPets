package application;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testFood() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 4);
		Pet testPet = new Pet("Test Pet", species, toy, food);
		Player testPlayer = new Player("Test Player", new Pet[] {testPet}, 50);

		testPlayer.addFood(food);
		assertTrue(testPlayer.getFood().get(food).equals(1));
		testPlayer.addFood(food);
		assertTrue(testPlayer.getFood().get(food).equals(2));
		
		testPlayer.feed(testPet, food);
		assertTrue(testPlayer.getFood().get(food).equals(1));
	}
	
	@Test
	public void testToy() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 4);
		Pet testPet = new Pet("Test Pet", species, toy, food);
		Player testPlayer = new Player("Test Player", new Pet[] {testPet}, 50);

		testPlayer.addToy(new Toy(toy));
		assertEquals(testPlayer.getToys().get(0).getDurability(), 100);
		testPlayer.addToy(new Toy(toy));
		testPlayer.playWith(testPet, testPlayer.getToys().get(0));
		assertTrue(testPlayer.getToys().get(0).getDurability() < 100);
		assertTrue(testPlayer.getToys().get(1).getDurability() == 100);
	}
}
