package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class AlgorithmUtil {
	public static final String CONNECTED = "1";
	public static final String UNCONNECTED = "0";

	/**
	 * generate an instance of Graph with internal parameters
	 * 
	 * @param adjacencyMatrix
	 *            , adjacency matrix of a graph
	 * @return a graph
	 */
	public static Graph<Integer, Integer> prepareGraph(
			List<String[]> adjacencyMatrix) {
		// prepare graph
		int numOfVertices = adjacencyMatrix.size();
		Graph<Integer, Integer> g = new SparseMultigraph<Integer, Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			g.addVertex(i);
		}

		for (int i = 0; i < numOfVertices; i++) {
			String[] rowArr = adjacencyMatrix.get(i);
			for (int j = i + 1; j < numOfVertices; j++) {
				if (CONNECTED.equals(rowArr[j])) {

					int edge = i * numOfVertices + j;
					g.addEdge(edge, i, j);

				}
			}
		}
		return g;
	}

	/**
	 * get a list of sorted vertices with their degrees from a graph
	 * 
	 * @param g
	 *            , an instance of Graph,
	 * @param numberOfVertex
	 *            , number of vertices in graph
	 * @return List<VertexDegree>, a list of vertices with their degrees
	 */
	public static List<VertexDegree> sortVertexAccordingToDegree(
			Graph<Integer, Integer> g) {

		// get the sorted vertex according their degree
		List<VertexDegree> vertexDegreeList = new ArrayList<VertexDegree>();
		Collection<Integer> vertices = g.getVertices();
		for (int i : vertices) {
			int degree = g.degree(i);
			vertexDegreeList.add(new VertexDegree(i, degree));
		}
		Collections.sort(vertexDegreeList);
		return vertexDegreeList;
	}

	/**
	 * generate a random graph
	 * 
	 * @param numOfVertices
	 *            , the number of vertices in the graph
	 * @return adjancy matrix
	 */
	public static List<String[]> generateRandGraph(int numOfVertices) {
		List<String[]> adjacencyMatrix = null;
		float adjacentRatio = 0.4f; // the purpose of this ratio is to reduce
									// the number of neighbors of a vertex in
									// order to increase the number of elements
									// in ds. otherwise, a vertex may link to
									// all other vertices and become the only
									// element in the ds.

		adjacencyMatrix = new ArrayList<String[]>(numOfVertices);
		for (int i = 0; i < numOfVertices; i++) {
			String[] row = new String[numOfVertices];
			Arrays.fill(row, UNCONNECTED);

			int adjacentNum = (int) Math.round(Math.random() * numOfVertices);
			adjacentNum = Math.round(adjacentRatio * adjacentNum);
			for (int j = 0; j < adjacentNum; j++) {
				int position;

				position = (int) Math.floor(Math.random() * numOfVertices);

				if (CONNECTED.equals(row[position])) {
					j--;
				} else {
					row[position] = CONNECTED;
				}
			}

			row[i] = CONNECTED;
			adjacencyMatrix.add(row);
		}

		return adjacencyMatrix;

	}


	
	public static HEdit hEdit(List<String[]> am1,Graph<Integer,Integer> g1,List<Integer> s1, int k){
		List<String[]> operationList=new ArrayList<String[]>();

		List<String[]> am2 = new ArrayList<String[]>(am1);
		Collections.copy(am2, am1);
		
		Collection<Integer> vertices1 =g1.getVertices();
		List<Integer> complementOfSByVertices1 = (List<Integer>)CollectionUtils.subtract(vertices1, s1);
		int complementOfSByVertices1Size = complementOfSByVertices1.size();
		
		for(int i=0;i<k;i++){
			int complementPosition = (int) Math.floor(Math.random() * complementOfSByVertices1Size);
			int complementV  = complementOfSByVertices1.get(complementPosition);
			Collection<Integer> neighboursOfComplementV = g1.getNeighbors(complementV);
			Iterator<Integer> neighboursOfComplementVIterator= neighboursOfComplementV.iterator();
			while(neighboursOfComplementVIterator.hasNext()){
				int tmpNeighbour = neighboursOfComplementVIterator.next();
				am2.get(complementV)[tmpNeighbour]=UNCONNECTED;
				am2.get(tmpNeighbour)[complementV]=UNCONNECTED;
				
				operationList.add(new String[]{UNCONNECTED,Integer.toString(complementV),Integer.toString(tmpNeighbour)});
			}
		}
		
		HEdit hEdit = new HEdit();
		hEdit.setOperationList(operationList);
		hEdit.setOutputAdjacencyMatrix(am2);
		return hEdit;
	}

//	public static List<String[]> generateRandOperation(int numOfVertices){
//		List<String[]> operationList= new ArrayList<String[]>();
//		
//		int maxEdgeNum = numOfVertices*(numOfVertices-1)/2;
//		Random random = new Random();
//		int operationRandomNum =  Math.abs(random.nextInt()%maxEdgeNum);
//		
//		for(int i=0;i< operationRandomNum;i++){
//			String[] operation=new String[3];
//			Boolean bool = random.nextBoolean();
//			operation[0]=bool?"1":"0";
//			
//			int v1 = Math.abs(random.nextInt()%numOfVertices);
//			int v2= Math.abs(random.nextInt()%numOfVertices);
//			
//			operation[1] = Integer.toString(v1);
//			operation[2] = Integer.toString(v2);
//			
//			operationList.add(operation);
//		}
//		
//		return operationList;
//	}
	
//	public static void generateRandGraph400() {
//		int numOfVertices = 400;
//
//		for (int i = 0; i < 10000; i++) {
//			List<String[]> am1 = AlgorithmUtil.generateRandGraph(numOfVertices);
//			AlgorithmGreedy ag1 = new AlgorithmGreedy(am1);
//			ag1.computing();
//			List<Integer> ds1 = ag1.getDominatingSet();
//			int ds1Size = ds1.size();
//
//			List<String[]> am2 = AlgorithmUtil.generateRandGraph(numOfVertices);
//			AlgorithmGreedy ag2 = new AlgorithmGreedy(am2);
//			ag2.computing();
//			List<Integer> ds2 = ag2.getDominatingSet();
//			int ds2Size = ds2.size();
//
//			Collection<Integer> intsec = CollectionUtils.intersection(ds1, ds2);
//			int intsecSize = intsec.size();
//			if (intsecSize >= Math.min(ds1Size, ds2Size) / 2) {
//				AlgorithmUtil.saveAgjacencyMatrixToFile(am1);
//				am1 = null;
//				AlgorithmUtil.saveAgjacencyMatrixToFile(am2);
//				am2 = null;
//				break;
//			}
//			am1 = null;
//			am2 = null;
//
//		}
//
//	}
//
//	public static void generateRandGraph400_100000() {
//		int numOfVertices = 400;
//		int intsecSize = -1;
//		int ds1Size = 0;
//		int ds2Size = 0;
//		List<Integer> ds1;
//		List<Integer> ds2;
//		List<String[]> am1 = null;
//		List<String[]> am2 = null;
//		while (intsecSize != Math.min(ds1Size, ds2Size)) {
//
//			am1 = AlgorithmUtil.generateRandGraph(numOfVertices);
//			AlgorithmGreedy ag1 = new AlgorithmGreedy(am1);
//			ag1.computing();
//			ds1 = ag1.getDominatingSet();
//			ds1Size = ds1.size();
//
//			am2 = AlgorithmUtil.generateRandGraph(numOfVertices);
//			AlgorithmGreedy ag2 = new AlgorithmGreedy(am2);
//			ag2.computing();
//			ds2 = ag2.getDominatingSet();
//			ds2Size = ds2.size();
//
//			Collection<Integer> intsec = CollectionUtils.intersection(ds1, ds2);
//
//			intsecSize = intsec.size();
//
//		}
//
//		AlgorithmUtil.saveAgjacencyMatrixToFile(am1);
//
//		AlgorithmUtil.saveAgjacencyMatrixToFile(am2);
//
//	}
}

class HEdit{
	private List<String[]> operationList;
	public List<String[]> getOperationList() {
		return operationList;
	}
	public void setOperationList(List<String[]> operationList) {
		this.operationList = operationList;
	}
	public List<String[]> getOutputAdjacencyMatrix() {
		return outputAdjacencyMatrix;
	}
	public void setOutputAdjacencyMatrix(List<String[]> outputAdjacencyMatrix) {
		this.outputAdjacencyMatrix = outputAdjacencyMatrix;
	}
	private List<String[]> outputAdjacencyMatrix;
}

