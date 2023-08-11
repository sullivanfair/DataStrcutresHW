package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class BadgerTest
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
		Badger b = new Badger(plain, 4, 5, 2);
		plain.grid[4][5] = b;
		
		assertEquals(4, b.row);
		assertEquals(5, b.column);
		assertEquals(2, b.age);
		assertEquals(State.BADGER, plain.grid[4][5].who());
	}
	
	@Test
	public void TestNextScenarios() throws FileNotFoundException
	{
		plain = new Plain("BadgerNextCycleA.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
	
		plain = new Plain("BadgerNextCycleB.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.FOX, nextPlain.grid[1][1].who());
		
		plain = new Plain("BadgerNextCycleC.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
		
		plain = new Plain("BadgerNextCycleD.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.BADGER, nextPlain.grid[1][1].who());
		assertEquals(2, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age updated
	}
}
