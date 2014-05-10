/**
 * @author : Kai
 * @Time : 11:15:27 AM
 */
package edu.cdu.fptincds.util;

import java.util.Comparator;
import java.util.List;

/**
 * a comparator for lists
 * 
 * @author Kai Wang
 * 
 */
public class ListSizeComparator implements Comparator<List<Integer>> {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(List<Integer> l1, List<Integer> l2) {
		return l2.size() - l1.size();
	}
}
