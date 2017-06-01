/*	Author: Benjamin Pipes
 * 	
 * 	Defined the compare method to be used for priority queue operations.
 *  Compares the frequency values. 	
 * 
 */

import java.util.Comparator;
public class DoubleComparator implements Comparator<HuffNode> {

public int compare(HuffNode arg0, HuffNode arg1) {

        if (arg0.freq < arg1.freq)
        {
            return -1;
        }
        if (arg0.freq > arg1.freq)
        {
            return 1;
        }
        return 0;
    }

	}

