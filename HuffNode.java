/*	Author: Benjamin Pipes

 * 	A node for holding the characters used in the huffman encoding algorithm
 * 	stores the character as a string to represent special characters
 * 	(blank space = "BS",	Carriage return = "CR")
 * 	Also stores links to a nodes' left and right child to build a tree.
 * 
 * 	Variable Dictionary
 * 		ch = the character, or characters that the node represents
 * 		freq = number of times it occurs throughout the text
 * 		chrCode = the code that is assigned to the character through the huffman scheme
 * 		indivCount = the number of bits needed for that particular character's code
 * 		left & right = links to children.
 */
public class HuffNode {
	public String ch;
	public int freq;
	public String chrCode;
	public int indivCount;
	public HuffNode left = null;
	public HuffNode right = null;
	
	
	public HuffNode(String c, int freq){
		ch = c;
		this.freq = freq;
	}

	
}
