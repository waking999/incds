/**
 * @author : Kai
 * @Time : 11:15:27 AM
 */
package edu.cdu.fptincds.alg;

import java.util.Comparator;
import java.util.List;

/**
 * a comparator for lists
 * 
 * @author Kai Wang
 * 
 */
public class ListSizeComparator implements Comparator<List<Integer>> {
	
	public int compare(List<Integer> l1, List<Integer> l2) {
		return l2.size() - l1.size();
	}
}
