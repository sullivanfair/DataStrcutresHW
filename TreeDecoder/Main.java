package edu.iastate.cs228.hw4;

/**
 * 
 * @author Sullivan Fair
 *
 */

import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		System.out.print("Please enter file name to decode: ");
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
		scanner.close();
		System.out.println();
		
		Deriver deriver = new Deriver(fileName);
		deriver.deriveMessage();
		MsgTree tree = new MsgTree(deriver.getTree());
		String code = deriver.getCode();
		String charList = deriver.deriveCharList();
		
		MsgTree.printCodes(tree, charList);
		tree.decode(tree, code);
	}
}
