package edu.iastate.cs228.hw1;

/**
 *  
 * @author Sullivan Fair
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{	
	public Empty (Plain p, int r, int c) 
	{
		super(p, r, c);  
	}
	
	public State who()
	{ 
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Plain pNew)
	{
		census(population);
		
		if(population[RABBIT] > 1)
		{
			return new Rabbit(pNew, row, column, 0);
		}
		else if(population[FOX] > 1)
		{
			return new Fox(pNew, row, column, 0);
		}
		else if(population[BADGER] > 1)
		{
			return new Badger(pNew, row, column, 0);
		}
		else if(population[GRASS] >= 1)
		{
			return new Grass(pNew, row, column);
		}
		else
		{
			return new Empty(pNew, row, column);
		}
		
		// See Living.java for an outline of the function. 
		// See the project description for corresponding survival rules. 
	}
}
