package unittests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;
import javax.swing.ImageIcon;

import model.FoodType;

public class FoodTypeTest {
	
	@Test
	public void test() {
		String[] names = {"Test1", "test2", "atest", "btest", "3test", ""};
		String[] orderedNames = {"", "3test", "Test1", "atest", "btest", "test2"};
		FoodType[] foodTypes = new FoodType[names.length];
		
		for (int i=0; i<names.length; i++)
			foodTypes[i] = new FoodType(names[i], new ImageIcon(), 1, 2, 3, 4);
		FoodType[] orderedFoodTypes = (new TreeSet<FoodType>(new HashSet<FoodType>(Arrays.asList(foodTypes)))).toArray(new FoodType[names.length]);
		
		for (int i=0; i<names.length; i++) {
			assertTrue(orderedFoodTypes[i].getName().equals(orderedNames[i]));
		}
	}

}
