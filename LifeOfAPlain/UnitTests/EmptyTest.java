package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class EmptyTest 
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
		Empty e = new Empty(plain, 4, 5);
		plain.grid[4][5] = e;
		
		assertEquals(4, e.row);
		assertEquals(5, e.column);
		assertEquals(State.EMPTY, plain.grid[4][5].who());
	}
	
	@Test
	public void TestNextScenarios() throws FileNotFoundException
	{
		plain = new Plain("EmptyNextCycleA.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.RABBIT, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
	
		plain = new Plain("EmptyNextCycleB.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.FOX, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("EmptyNextCycleC.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.BADGER, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("EmptyNextCycleD.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.GRASS, nextPlain.grid[1][1].who()); //Check if age is 0
		 
		plain = new Plain("EmptyNextCycleE.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
	}
}
