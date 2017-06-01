/*	Author: Benjamin Pipes
 * 	
 * 	The purpose of this assignment is to take a list of characters and their
 * 	frequencies and implement a huffman encoding scheme for its respective
 * 	text file. The file given is the Constitution. The program builds a 
 * 	huffman tree with each leaf being a character used in the text file. The
 * 	expected output should be a fewer amount of bits needed compared to
 * 	a fixed-bit encoding scheme.
 * 
 * 	Variable Dictionary:
 * 		bitCount = accumulator for total bits needed for huffman scheme	
 * 		comp = comparator, defined for comparing character frequencies
 *		pq = priority queue. holds the nodes used in huffman algorithm
 *		the rest is just variables for file parsing.
 *
 *	Input text file is formatted as:
 *	<character1> <frequency1>
 *	<character2> <freqeuncy2>
 *	...
 *	<characterN> <frequencyN>
 *
 *	Output is rather large, see external text file.
 *
 *	Files needed: HuffmanB.java, HuffNode.java, DoubleComparator.java,
 *					 txt file that holds character frequencies.
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

public class HuffmanEncoder {

static int bitCount = 0;	
	
	public static void main(String[] args) throws FileNotFoundException {

		Comparator<? super HuffNode> comp = new DoubleComparator();
		PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>(70,comp);
		String line;
		String ch;
		String freqs;
		Integer freqi;
		Scanner in  = new Scanner(new File("constitution_freq.txt"));
	
		while(in.hasNext()){
			//parse text file, store character and frequency in a node
			line = in.nextLine();
			ch = line.substring(0, line.indexOf(" "));
			freqs = line.substring(line.indexOf(" ") + 1);
			
			freqi = Integer.parseInt(freqs);
			HuffNode hn = new HuffNode(ch,freqi);
			
			//enqueue node to priority queue
			pq.add(hn);
		}
		
		//algorithm for creating huffman tree
		while(pq.size() > 1){
			//while pq contains 2 or more nodes.
			
			//dequeue node and make it left child for new parent.
			HuffNode left = pq.remove();
			
			//dequeue node and make it right child for new parent.
			HuffNode right = pq.remove();
			
			//create new node, the frequency of it equals
			//sum of frequency of left & right child.
			HuffNode parent = new HuffNode("PRN",left.freq + right.freq);
			
			//add the left and right children.
			parent.left = left;
			parent.right = right;
			
			//enqueue new parent
			pq.add(parent);
		}
		
		//****calculate and display information****
		
		//dequeue the last node, gives the huffman tree.
		HuffNode tree = pq.remove();
		int charCount = tree.freq;
		
		//output the total char count
		System.out.println("Total Characters used: " + charCount);
		System.out.println("bits needed with fixed bit size(ascii):   " + 8 * charCount);
		
		//traverse through tree, set code values for each char
		//begin with empty string, method takes care of rest.
		traversalCode(tree, "");
		
		//traverse tree, multiply frequency * bit count
		inOrderBitCount(tree);
		
		System.out.println("bits needed with variable bit size: \t  " + bitCount);

		//display list of characters and their bit code
		System.out.println("\n**character codes & additional info**");
		inOrderTraversal(tree);
		
		System.out.println("execution complete");
		in.close();
	}//main

	/*	Traverses through tree and builds a bit code for each leaf (a character)
	 * 	when it moves left, it adds a 0 to code
	 * 	when it moves right, it adds a 1 to code
	 * 	if it has reached a leaf, it stores the bitCode for that leaf.
	 * 
	 */
	public static void traversalCode(HuffNode node, String code){
		
			 if(node.left == null && node.right == null){
				 //if at leaf, store charcode in node
				 //store number of bits used for that code.
				 node.chrCode = code;
				 node.indivCount = code.length();
			 }
			 if (node.left != null){
				 traversalCode(node.left, code + '0');
			 }
			 if (node.right != null){
		    traversalCode(node.right, code + '1');
			 }
	}
	
	/*	Traverses through tree and multiplies character frequency times
	 * 	the number of bits needed for that character's code.
	 * 	Accumulates to global variable
	 */
	public static void inOrderBitCount(HuffNode node){
		
		 if (node != null) {
			 
			inOrderBitCount(node.left); 
		    if (node.ch.compareTo("PRN") != 0){
		    	//if node isn't a parent.
		    	bitCount += (node.freq * node.indivCount);
		    } 
		    inOrderBitCount(node.right);   
		 }
	}
	
	/*	Traverses through tree and displays additional information
	 * 	regarding each node and it's contents.
	 */
	public static void inOrderTraversal(HuffNode node){
		
		 if (node != null) {
				 
			inOrderTraversal(node.left);
			 if (node.ch.compareTo("PRN") != 0){
				    System.out.println(node.ch + " (" + node.chrCode + ") " + node.indivCount + " bits * "
				    					+ node.freq + " occurrences: " + (node.freq * node.indivCount) + "bits");
				    } 
		    inOrderTraversal(node.right);
		   
		 }
	}
	
}//class

