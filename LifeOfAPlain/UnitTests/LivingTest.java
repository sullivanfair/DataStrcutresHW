package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class LivingTest 
{
	@Test
	public void TestCensus() throws FileNotFoundException
	{
		Plain plain = new Plain("CensusTest.txt");
		int[] population = new int[5];
		plain.grid[1][1].census(population);
		
		assertEquals(1, population[0]);
		assertEquals(2, population[1]);
		assertEquals(2, population[2]);
		assertEquals(2, population[3]);
		assertEquals(2, population[4]);
	}
}
