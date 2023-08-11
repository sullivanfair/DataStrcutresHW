package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class GrassTest 
{
	/**
	 * Test Plain
	 */
	Plain plain;
	
	/**
	 * Empty plain to update plain to
	 */
	Plain nextPlain;
	
	@Test
	public void TestConstructorANDWho() throws FileNotFoundException
	{
		plain = new Plain("TestPlain.txt");
		Grass g = new Grass(plain, 4, 5);
		plain.grid[4][5] = g;
		
		assertEquals(4, g.row);
		assertEquals(5, g.column);
		assertEquals(State.GRASS, plain.grid[4][5].who());
	}
	
	@Test
	public void TestNextScenarios() throws FileNotFoundException
	{
		plain = new Plain("GrassNextCycleA.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
	
		plain = new Plain("GrassNextCycleB.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.RABBIT, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("GrassNextCycleC.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.GRASS, nextPlain.grid[1][1].who());		
	}
}
