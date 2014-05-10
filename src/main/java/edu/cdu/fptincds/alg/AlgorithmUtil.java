package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
		float adjacentRatio = 0.3f; // the purpose of this ratio is to reduce
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

	/**
	 * 
	 * @param am1
	 *            , the adjacency matrix of graph 1
	 * @param g1
	 *            , graph 1
	 * @param ds1
	 *            , a domininating set of graph 1
	 * @param r
	 *            , the hamming distance between ds1 (for g1) and ds2 (for g2)
	 * @return
	 */
	public static HEdit hEdit(List<String[]> am1, Graph<Integer, Integer> g1,
			List<Integer> ds1, int r) {
		List<String[]> operationList = new ArrayList<String[]>();
		
		List<String[]> am2 = new ArrayList<String[]>(am1);
		Collections.copy(am2, am1);

		Collection<Integer> vertices1 = g1.getVertices();
		List<Integer> complementOfSByVertices1 = (List<Integer>) CollectionUtils
				.subtract(vertices1, ds1);
		int complementOfSByVertices1Size = complementOfSByVertices1.size();

		for (int i = 0; i < r; i++) {
			int complementPosition = (int) Math.floor(Math.random()
					* complementOfSByVertices1Size);
			int complementV = complementOfSByVertices1.get(complementPosition);
			Collection<Integer> neighboursOfComplementV = g1
					.getNeighbors(complementV);
			Iterator<Integer> neighboursOfComplementVIterator = neighboursOfComplementV
					.iterator();
			while (neighboursOfComplementVIterator.hasNext()) {
				int tmpNeighbour = neighboursOfComplementVIterator.next();
				
				//change adjacency matrix
				am2.get(complementV)[tmpNeighbour] = UNCONNECTED;
				am2.get(tmpNeighbour)[complementV] = UNCONNECTED;

				//change operation list
				String[] operation = { UNCONNECTED,
						Integer.toString(complementV),
						Integer.toString(tmpNeighbour) };
				
				addElementToList(operationList, operation);
			}
		}
		//int numOfHarmOperation = operationList.size();
		HEdit hEdit = new HEdit();
		hEdit.setOperationList(operationList);
		hEdit.setOutputAdjacencyMatrix(am2);
		//hEdit.setNumOfHarmOpearion(numOfHarmOperation);

		hEdit = generateEdgeAddOperation(hEdit);

		return hEdit;
	}

	public static HEdit generateEdgeAddOperation(HEdit hEdit) {

		List<String[]> operationList = hEdit.getOperationList();
		List<String[]> am = hEdit.getOutputAdjacencyMatrix();
		int numOfVertices = am.size();

		//int maxEdgeOperationNum = numOfVertices * (numOfVertices - 1) / 2;
		
		int maxEdgeOperationNum = numOfVertices;
		Random random = new Random();
		int operationRandomNum = Math.abs(random.nextInt() % maxEdgeOperationNum);
		for (int i = 0; i < operationRandomNum; i++) {
			String[] operation = new String[3];
			operation[0] = CONNECTED;
			int v1 = Math.abs(random.nextInt() % numOfVertices);
			int v2 = Math.abs(random.nextInt() % numOfVertices);

			//change adjacency matrix
			am.get(v1)[v2] = CONNECTED;
			am.get(v2)[v1] = CONNECTED;
			
			//change operation list
			operation[1] = Integer.toString(v1);
			operation[2] = Integer.toString(v2);

			addElementToList(operationList, operation);

		}
		hEdit.setOperationList(operationList);
		hEdit.setOutputAdjacencyMatrix(am);
		
		return hEdit;
	}

	public static <E> List<E> addElementToList(List<E> list, E e) {
		if (!list.contains(e)) {
			list.add(e);
		}
		return list;
	}

	/**
	 * judge if it is a solution of dominating set
	 * 
	 * @param g
	 *            , graph
	 * @param ds
	 *            , dominating set
	 * @param complementaryDS
	 *            , the complementary set of dominating set
	 * @return boolean, is dominating set or not
	 */
	public static boolean isDS(Graph<Integer, Integer> g, List<Integer> ds,
			List<Integer> complementaryDS) {

		int acDSLen = complementaryDS.size();
		int count=0;
		for (int j = 0; j < acDSLen; j++) {
			Integer u = complementaryDS.get(j);
			Collection<Integer> neighborsOfU = g.getNeighbors(u);
			for(Integer neig: neighborsOfU){
				if (ds.contains(neig)) {
					count++;
					break;
				}
			}
			if (count == j) {
				// if there is not any vertex in the complementary set linked to
				// a vertex in the set, it is not a dominating set
				return false;
			}
		}

		return true;

	}
}

class HEdit {


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
