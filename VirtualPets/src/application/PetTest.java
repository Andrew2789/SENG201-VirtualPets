package application;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Test;

public class PetTest {

	@Test
	public void testEat() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType favFood = new FoodType("Test favfood type", new ImageIcon(), 1, 2, 3, 4);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 4);
		Pet testPet = new Pet("Test Pet", species, toy, favFood);

		assertEquals(testPet.getHunger(), 20);
		assertEquals(testPet.getHappiness(), 80);
		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.eat(food);
		assertEquals(testPet.getHunger(), 18);
		assertEquals(testPet.getHappiness(), 83);
		assertEquals(testPet.getWeight(), 44);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet.eat(favFood);
		assertEquals(testPet.getHunger(), 16);
		assertEquals(testPet.getHappiness(), 87);
		assertEquals(testPet.getWeight(), 48);
		assertEquals(testPet.getActionPoints(), 0);
	}
	
	@Test
	public void testPlay() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType favToy = new ToyType("Test favtoy type", new ImageIcon(), 25, 30);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 4);
		Pet testPet = new Pet("Test Pet", species, favToy, food);

		testPet.discipline();
		testPet.discipline();
		assertEquals(testPet.getHunger(), 20);
		assertEquals(testPet.getHappiness(), 20);
		assertEquals(testPet.getEnergy(), 80);
		assertEquals(testPet.getActionPoints(), 0);
		
		testPet.play(new Toy(toy));
		assertEquals(testPet.getHunger(), 25);
		assertEquals(testPet.getHappiness(), 50);
		assertEquals(testPet.getEnergy(), 70);
		assertEquals(testPet.getActionPoints(), -1);
		
		testPet.play(new Toy(favToy));
		assertEquals(testPet.getHunger(), 30);
		assertEquals(testPet.getHappiness(), 95);
		assertEquals(testPet.getEnergy(), 60);
		assertEquals(testPet.getActionPoints(), -2);
	}
	
	@Test
	public void testToilet() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, toy, food);

		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 2);
		
		testPet.goToToilet();
		assertEquals(testPet.getWeight(), 40);
		assertEquals(testPet.getActionPoints(), 1);
		
		testPet.eat(food);
		testPet.goToToilet();
		assertEquals(testPet.getWeight(), 53);
	}	
	
	@Test
	public void testSleep() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, toy, food);

		testPet.play(new Toy(toy));
		testPet.play(new Toy(toy));

		assertEquals(testPet.getEnergy(), 60);
		assertEquals(testPet.getActionPoints(), 0);
		
		testPet.sleep();
		assertEquals(testPet.getEnergy(), 90);
		assertEquals(testPet.getActionPoints(), -1);
		
		testPet.sleep();
		assertEquals(testPet.getEnergy(), 100);
	}	
	
	@Test
	public void testCure() {
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, false, false, false, false, 50, 50, 50, 50, 2, toy, food);

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
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, false, false, true, true, 50, 50, 50, 50, 2, toy, food);

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
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, false, false, false, true, 50, 50, 50, 50, 2, toy, food);

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
		Species species = new Species("Test species", new ImageIcon(), 40, 10, 10, 10, 15, 25);
		ToyType toy = new ToyType("Test toy type", new ImageIcon(), 25, 30);
		FoodType food = new FoodType("Test food type", new ImageIcon(), 1, 2, 3, 20);
		Pet testPet = new Pet("Test Pet", species, toy, food);

		assertEquals(testPet.finishTurn(), 240);
		assertEquals(testPet.getHunger(), 30);
		assertEquals(testPet.getHappiness(), 70);
		assertEquals(testPet.getEnergy(), 70);
	}
}
