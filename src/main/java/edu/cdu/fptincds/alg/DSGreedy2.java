package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

public class DSGreedy2 {
	/**
	 * the graph
	 */
	private Graph<Integer, Integer> g;
	/**
	 * 
	 * a sorted vertices with their degree (from highest degree to the lowest)
	 */
	private List<VertexDegree> vertexDegreeList;
	/**
	 * the desired dominating set
	 */
	List<Integer> dominatingSet;

	public List<Integer> getDominatingSet() {
		return dominatingSet;
	}

	/**
	 * number of vertices
	 */
	private int numOfVertices;

	/**
	 * the adjacency matrix of the graph
	 */
	private List<String[]> adjacencyMatrix;

	public DSGreedy2(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);

	}

	/**
	 * the major function do the computing to get the desired solution. In this
	 * case, the desired result is a dominating set
	 */
	public void computing() {

		initialization();
		greedy();

	}

	private void initialization() {
		this.vertexDegreeList = AlgorithmUtil.sortVertexAccordingToDegree(g);
		this.dominatingSet = new ArrayList<Integer>();
	}

	private void greedy() {
		Map<Integer, Integer> utilityMap = new HashMap<Integer, Integer>();
		for (VertexDegree vd : this.vertexDegreeList) {
			utilityMap.put(vd.getVertex(), vd.getDegree());
		}

		for (VertexDegree vd : this.vertexDegreeList) {
			Integer v = vd.getVertex();
			if (utilityMap.get(v) != null) {

				Collection<Integer> neighOfV = g.getNeighbors(v);
				neighOfV.add(v);

				Integer u = getVetexWithHighestUtilityInNeighbors(neighOfV,
						utilityMap);

				if (u != null) {
					this.dominatingSet.add(u);
				}

				utilityMap.remove(u);
				Collection<Integer> neighOfU = g.getNeighbors(u);
				if (neighOfU != null) {
					for (Integer w : neighOfU) {
						utilityMap = reduceUtility(w, utilityMap);
						utilityMap.remove(w);
					}
				}
			}

		}

	}

	private Integer getVetexWithHighestUtilityInNeighbors(
			Collection<Integer> neigh, Map<Integer, Integer> utilityMap) {
		Integer maxV = null;
		int maxDegree = 0;

		for (Integer v : neigh) {

			Integer tempDegree = utilityMap.get(v);
			if (tempDegree != null) {
				if (tempDegree > maxDegree) {
					maxV = v;
					maxDegree = tempDegree;
				}
			}
		}

		return maxV;
	}

	private Map<Integer, Integer> reduceUtility(Integer w,
			Map<Integer, Integer> utilityMap) {

		Collection<Integer> neighOfW = g.getNeighbors(w);
		for (Integer u : neighOfW) {
			Integer degree = utilityMap.get(u);
			if (degree != null) {
				utilityMap.put(u, degree - 1);
			}
		}

		return utilityMap;
	}
}
