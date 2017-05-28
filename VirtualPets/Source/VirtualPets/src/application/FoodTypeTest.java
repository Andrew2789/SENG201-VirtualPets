package application;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import javax.swing.ImageIcon;

import org.junit.Test;

public class FoodTypeTest {

	@Test
	public void test() {
		String[] names = {"Test1", "test2", "atest", "btest", "3test", ""};
		FoodType[] foodTypes = new FoodType[names.length];
		
		for (int i=0; i<names.length; i++)
			foodTypes[i] = new FoodType(names[i], new ImageIcon(), 1, 2, 3, 4);
		TreeSet<FoodType> orderedFoodTypes = new TreeSet<FoodType>(new HashSet<FoodType>(Arrays.asList(foodTypes)));
		
		
	}

}
