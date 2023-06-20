package edu.iastate.cs228.hw4;



/**
 * 
 * @author Sullivan Fair
 *
 */

import java.util.Stack;

public class MsgTree 
{
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	
	/**
	 * Builds a tree from a String
	 * 
	 * @param encodingString retrieved from file
	 */
	public MsgTree(String encodingString)
	{
		if(encodingString == null || encodingString.length() < 2)
		{
			return;
		}
		
		Stack<MsgTree> stack = new Stack<>();
		int index = 0;
		this.payloadChar = encodingString.charAt(index++);
		stack.push(this);
		MsgTree current = this;
		String last = "in";
		
		while(index < encodingString.length())
		{
			MsgTree node = new MsgTree(encodingString.charAt(index++));
			
			if(last.equals("in"))
			{
				current.left = node;
				
				if(node.payloadChar == '^')
				{
					current = stack.push(node);
					last = "in";
				}
				else
				{
					if(!stack.isEmpty())
					{
						current = stack.pop();
						last = "out";
					}
				}
			}
			else
			{
				current.right = node;
				
				if(node.payloadChar == '^')
				{
					current = stack.push(node);
					last = "in";
				}
				else
				{
					if(!stack.isEmpty()) 
					{
						current = stack.pop();
						last = "out";
					}
				}
			}
		}
	}
	
	/**
	 * Creates node w/ both children null
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar)
	{
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}
	
	private static String charCode; //Used in printCodes() and getCode()
	
	/**
	 * Prints characters w/ their respective codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code)
	{
		System.out.println("character code\n-------------------------");
		
		for(int i = 0; i < code.length(); i++)
		{
			char ch = code.charAt(i);
			getCode(root, ch, charCode = "");
			System.out.println("    " + (ch == '\n' ? "\\n" : ch + " ") + "    " + charCode);
		}
		
		System.out.println();
	}
	
	/**
	 * Gets code and call itself to set character values
	 * 
	 * @param root
	 * @param ch
	 * @param path
	 * @return
	 */
	private static boolean getCode(MsgTree root, char ch, String path)
	{
		if(root != null)
		{
			if(root.payloadChar == ch)
			{
				charCode = path;
				return true;
			}
			else
			{
				return getCode(root.left, ch, path + "0") || getCode(root.right, ch, path + "1");
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Decodes message using character values from codes
	 * 
	 * @param codes
	 * @param message
	 */
	public void decode(MsgTree codes, String message)
	{
		System.out.println("MESSAGE:");
		
		MsgTree current = codes;
		String msg = "";
		
		for(int i = 0; i < message.length(); i++)
		{
			char ch = message.charAt(i);
			current = (ch == '0' ? current.left : current.right);
			
			if(current.payloadChar != '^')
			{
				getCode(codes, current.payloadChar, charCode = "");
				msg += current.payloadChar;
				current = codes;
			}
		}
		
		System.out.println(msg + '\n');
		getStats(message, msg);
	}
	
	/**
	 * Gets data from encodedString and decodedString and prints it
	 * 
	 * @param encodedString
	 * @param decodedString
	 */
	private void getStats(String encodedString, String decodedString)
	{
		System.out.println("STATISTICS:");
		System.out.println("Avg bits/char:       " + String.format("%.1f", encodedString.length() / (double) decodedString.length()));
		System.out.println("Total Characters:    " + decodedString.length());
		System.out.println("Space Savings:       " + String.format("%.1f%%", (1d - decodedString.length() / (double) encodedString.length()) * 100));
	}
}
