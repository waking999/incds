package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
/**
 * make use of greedy algorithm to get a vertex cover of a graph
 * 
 * @author Kai Wang
 *
 */
public class VCGreedy {
	/**
	 * the graph
	 */
	private Graph<Integer, Integer> g;
	/**
	 * a sorted vertices with their degree (from highest degree to the lowest)
	 */
	private List<VertexDegree> vertexDegreeList;
	/**
	 * the desired vertex cover
	 */
	List<Integer> vertexCover;

	public List<Integer> getVertexCover() {
		return vertexCover;
	}

	/**
	 * the adjacency matrix of the graph
	 */
	private List<String[]> adjacencyMatrix;
	/**
	 * number of vertices in the graph
	 */
	private int numOfVertices;

	public VCGreedy(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);
	}

	public VCGreedy(Graph<Integer, Integer> g) {
		this.g = g;
		this.numOfVertices = g.getVertexCount();
	}

	/**
	 * the major function do the computing to get the desired solution. In this
	 * case, the desired result is a vertex cover
	 */
	public void computing() {

		initialization();
		greedy();

	}

	private void initialization() {
		this.vertexDegreeList = AlgorithmUtil.sortVertexAccordingToDegree(g);
		this.vertexCover = new ArrayList<Integer>();
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
		int numOfEdges = g.getEdgeCount();
		while (numOfEdges > 0) {
			//get the vertex with the highest degree
			Integer v = T.get(0);

			Collection<Integer> incidentEdgesOfV = g.getIncidentEdges(v);
			/*
			 * if a vertex is isolated, which is not incident any edge, it is
			 * not an element in the solution
			 */
			if (incidentEdgesOfV.size() > 0) {
				vertexCover.add(v);
			}

			T.remove(v);

			g.removeVertex(v);

			numOfEdges = g.getEdgeCount();

		}

	}

}
