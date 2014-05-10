package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

public class VCGreedy {
	private Graph<Integer, Integer> g;
	private List<VertexDegree> vertexDegreeList;
	List<Integer> vertexCover;

	
	public List<Integer> getVertexCover() {
		return vertexCover;
	}
	/*
	 * the adjacency matrix shown in the input file
	 */
	private List<String[]> adjacencyMatrix;
//	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix) {
//		this.adjacencyMatrix = adjacencyMatrix;
//	}
	private int numOfVertices;
	
	public void computing(){
		
		initialization();
		greedy();
		
	}
	
	public VCGreedy(List<String[]> adjacencyMatrix){
		this.adjacencyMatrix=adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);	
	}
	
	public Graph<Integer, Integer> getG() {
		return g;
	}

	public VCGreedy(Graph<Integer, Integer> g){
		this.g=g;
		this.numOfVertices = g.getVertexCount();
	}
	
	private  void initialization() {
		
		this.vertexDegreeList = AlgorithmUtil
				.sortVertexAccordingToDegree(g);
		this.vertexCover = new ArrayList<Integer>();
	}
	
private void greedy() {
		
		List<Integer> T = new ArrayList<Integer>();
		
		for (int j = 0; j < numOfVertices; j++) {
			T.add(vertexDegreeList.get(numOfVertices - 1 - j)
					.getVertex());
		}

		// idea: Take all vertices of the highest degree as an approximate
		// solution
		int numOfEdges = g.getEdgeCount();
		while (numOfEdges>0) {
			Integer v = T.get(0);
			
			
			
			T.remove(v);
			
			g.removeVertex(v);
			
			vertexCover.add(v);
			
			numOfEdges = g.getEdgeCount();
			
		}
		
		
	}
	
}
