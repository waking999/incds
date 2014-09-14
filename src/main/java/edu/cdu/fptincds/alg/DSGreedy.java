package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

/**
 * make use of greedy algorithm to get a dominating set of a graph
 * 
 * @author Kai Wang
 * 
 */
public class DSGreedy {
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

	public DSGreedy(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);

	}

	public DSGreedy(Graph<Integer, Integer> g) {
		this.g = g;
		this.numOfVertices = g.getVertexCount();

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

		List<Integer> T = new ArrayList<Integer>();

		for (int j = 0; j < numOfVertices; j++) {
			T.add(vertexDegreeList.get(j).getVertex());
		}

		/*
		 * idea: Take all vertices of the highest degree as an approximate
		 * solution
		 */

		while (!T.isEmpty()) {
			//get the vertex with the highest degree
			Integer v = T.get(0);

			dominatingSet.add(v);
			T.remove(v);

			Collection<Integer> neighborsOfV = g.getNeighbors(v);

			// remove v's neighbours from T
			T.removeAll(neighborsOfV);

		}

	}
}
