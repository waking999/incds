package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;

import edu.cdu.fptincds.util.ListSizeComparator;
import edu.uci.ics.jung.graph.Graph;

/**
 * This class implements hill climbing algorithm
 * 
 * @author Kai Wang
 * 
 * 
 */

public class DSHillClimbingGreedy {
	public Set<List<Integer>> getDominatingSetSet() {
		return dominatingSetSet;
	}

	private Graph<Integer, Integer> g;
	// private int numOfVertices;
	private List<String[]> adjacencyMatrix;
	private State state;
	private Set<List<Integer>> dominatingSetSet;
	
	public List<Integer> getDominatingSet(){
		return state.getDs();
	}

	public DSHillClimbingGreedy(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		// this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);
	}

	private void initialization() {

		DSGreedy ag = new DSGreedy(g);
		ag.computing();

		List<Integer> dominatingSet = ag.getDominatingSet();
		this.state = new State();
		state.setDs(dominatingSet);

		dominatingSetSet = new TreeSet<List<Integer>>(new ListSizeComparator());
	}

	public DSHillClimbingGreedy(Graph<Integer, Integer> g) {
		this.g = g;
		// this.numOfVertices = g.getVertexCount();
	}


	public void computing() {

		initialization();
		hillClimbing();
	}

	/**
	 * implement the hill climbing algorithm
	 * 
	 * @param g
	 *            , graph
	 */
	private void hillClimbing() {

		List<Integer> aDS = state.getDs();
		dominatingSetSet.add(aDS);

		int aDSLen = aDS.size();

		// take use of hill climbing idea based on the generated dominating set
		int minDSLen = aDSLen;

		Collection<Integer> vertices = g.getVertices();

		List<Integer> acDS = (List<Integer>) CollectionUtils.subtract(vertices,
				aDS);
		int aCDSLen = acDS.size();

		for (int i = 0; i < aCDSLen; i++) {
			List<Integer> aDSBk = new ArrayList<Integer>(aDS);

			List<Integer> acDSBk = new ArrayList<Integer>(acDS);

			Integer temp = acDS.get(i);
			aDSBk.add(temp);
			acDSBk.remove(temp);
			Collection<Integer> neighborsOfTemp = g.getNeighbors(temp);
			for (Integer neig : neighborsOfTemp) {
				if (aDSBk.contains(neig)) {
					acDSBk.add(neig);
					aDSBk.remove(neig);
				}
			}

			// verify certainDSBk is a dominating set
			boolean isDS = AlgorithmUtil.isDS(g, aDSBk, acDSBk);
			if (isDS) {
				int aDSBkLen = aDSBk.size();
				if (minDSLen > aDSBkLen) {

					minDSLen = aDSBkLen;

					
					List<Integer> existingDs = state.getDs();
					
					state.setPrevDs(existingDs);					
					state.setDs(aDSBk);
					dominatingSetSet.add(state.getDs());
					
					aDS = new ArrayList<Integer>(aDSBk);
					acDS = (List<Integer>) CollectionUtils.subtract(vertices,
							aDS);
					aCDSLen =acDS.size();
					i=-1;

				}
			}
		}
		
	

	}

}
