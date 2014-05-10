package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

import edu.uci.ics.jung.graph.Graph;

public class DSGreedy {
	private Graph<Integer, Integer> g;
	private List<VertexDegree> vertexDegreeList;
	List<Integer> dominatingSet; 
	public List<Integer> getDominatingSet() {
		return dominatingSet;
	}

	/*
	 * number of vertices shown in the input file, generally in the first line
	 */
	private int numOfVertices;


	/*
	 * the adjacency matrix shown in the input file
	 */
	private List<String[]> adjacencyMatrix;
	
	
	public void computing(){
	
		initialization();
		greedy();
		
	}
	
	public DSGreedy(List<String[]> adjacencyMatrix){
		this.adjacencyMatrix=adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgorithmUtil.prepareGraph(this.adjacencyMatrix);	
		this.vertexDegreeList = AlgorithmUtil
				.sortVertexAccordingToDegree(g);
	}
	
	public DSGreedy(Graph<Integer, Integer> g){
		this.g=g;
		this.numOfVertices = g.getVertexCount();
		this.vertexDegreeList = AlgorithmUtil
				.sortVertexAccordingToDegree(g);
	}
	
	/**
	 * 
	 */
	private  void initialization() {
			
		
		this.dominatingSet = new ArrayList<Integer>();
	}

	private void greedy() {
		
		List<Integer> T = new ArrayList<Integer>();
		
		for (int j = 0; j < numOfVertices; j++) {
			T.add(vertexDegreeList.get(numOfVertices - 1 - j)
					.getVertex());
		}

		// idea: Take all vertices of the highest degree as an approximate
		// solution
		
		while (!T.isEmpty()) {
			Integer v = T.get(0);
			
			dominatingSet.add(v);
			T.remove(v);
			

			Collection<Integer> neighborsOfV = g.getNeighbors(v);
			
			//remove v's neighbours from T
			T=(List<Integer>)CollectionUtils.subtract(T, neighborsOfV);

			
			
		}
		
		
	}
}
