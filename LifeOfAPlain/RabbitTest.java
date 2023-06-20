package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class RabbitTest 
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
		Rabbit r = new Rabbit(plain, 4, 5, 2);
		plain.grid[4][5] = r;
		
		assertEquals(4, r.row);
		assertEquals(5, r.column);
		assertEquals(2, r.age);
		assertEquals(State.RABBIT, plain.grid[4][5].who());
	}
	
	@Test
	public void TestNextScenarios() throws FileNotFoundException
	{
		plain = new Plain("RabbitNextCycleA.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
	
		plain = new Plain("RabbitNextCycleB.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.EMPTY, nextPlain.grid[1][1].who());
		
		plain = new Plain("RabbitNextCycleC.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.FOX, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("RabbitNextCycleD.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.BADGER, nextPlain.grid[1][1].who());
		assertEquals(0, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age is 0
		
		plain = new Plain("RabbitNextCycleE.txt");
		nextPlain = new Plain(3);
		nextPlain.grid[1][1] = plain.grid[1][1].next(nextPlain);
		assertEquals(State.RABBIT, nextPlain.grid[1][1].who());
		assertEquals(2, ((Animal) nextPlain.grid[1][1]).myAge()); //Check if age updated
	}
}
