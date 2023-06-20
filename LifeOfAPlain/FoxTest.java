package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class FoxTest 
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
		Fox f = new Fox(plain, 4, 5, 3);
		plain.grid[4][5] = f;
		
		assertEquals(4, f.row);
		assertEquals(5, f.column);
		assertEquals(3, f.age);
		assertEquals(State.FOX, plain.grid[4][5].who());
	}
	
	@Test
	public void TestNextScenarios() throws FileNotFoundException
	{
		plain = new Plain("FoxNextCycleA.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
	
		plain = new Plain("FoxNextCycleB.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.BADGER, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("FoxNextCycleC.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
		
		plain = new Plain("FoxNextCycleD.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.FOX, nextPlain.grid[1][1].who());
		assertEquals(3, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age updated
		
	}
}
