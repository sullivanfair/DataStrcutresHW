package edu.iastate.cs228.hw1;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
import java.io.FileNotFoundException;

/**
 * 
 * @author Sullivan Fair
 *
 */

public class AnimalTest 
{	
	@Test
	public void TestMyAge() throws FileNotFoundException
	{
		/**
		 * Test plain used to create Animals
		 */
		Plain plain = new Plain("TestPlain.txt");
		
		/**
		 * Create test Animals to test MyAge()
		 */
		Badger b = new Badger(plain, 0, 0, 0);
		Fox f = new Fox(plain, 2, 2, 2);
		Rabbit r = new Rabbit(plain, 3, 3, 3);
		
		assertEquals(0, b.myAge());
		assertEquals(2, f.myAge());
		assertEquals(3, r.myAge());
	}
	
	
}
