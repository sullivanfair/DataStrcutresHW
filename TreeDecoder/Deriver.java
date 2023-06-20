package edu.iastate.cs228.hw4;

/**
 * 
 * @author Sullivan Fair
 *
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Deriver 
{
	/**
	 * Retrieved from Main and used to create tree and code
	 */
	private File file;
	
	/**
	 * MsgTree as a string
	 */
	private String tree;
	
	/**
	 * Message as string
	 */
	private String code;
	
	/**
	 * Characters contained in tree without '^'
	 */
	private String charList = "";
	
	/**
	 * Used if file has extra lines
	 */
	private String temp;
	
	/**
	 * Used to parse lines of file to put into tree and code
	 */
	private Scanner scanner;
	
	/**
	 * Creates deriver object that can create a tree, code, and charList from a file
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public Deriver(String file) throws FileNotFoundException
	{
		this.file = new File(file);
		scanner = new Scanner(this.file);
	}
	
	/**
	 * Creates a tree and code used to decode the hidden message
	 */
	public void deriveMessage()
	{
		tree = scanner.nextLine();
		code = scanner.nextLine();
				
		if(scanner.hasNextLine())
		{
			temp = code;
			code = scanner.nextLine();
			tree = tree + '\n' + temp;
		}
		
		scanner.close();
	}
	
	/**
	 * Creates a list of the non-'^' character in the tree from the file
	 * 
	 * @return charList
	 */
	public String deriveCharList()
	{
		for(int i = 0; i < tree.length(); i++)
		{
			if(tree.charAt(i) != '^')
			{
				charList += tree.charAt(i);
			}
			else
			{
				continue;
			}
		}
		
		return charList;
	}
	
	/**
	 * Accessor method for tree
	 * 
	 * @return tree
	 */
	public String getTree()
	{
		return tree;
	}
	
	/**
	 * Accessor method for code
	 * 
	 * @return code
	 */
	public String getCode()
	{
		return code;
	}
}
