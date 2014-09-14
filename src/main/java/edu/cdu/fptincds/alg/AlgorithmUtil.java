package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections15.CollectionUtils;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class AlgorithmUtil {
	
	public static final String CONNECTED = "1";
	public static final String UNCONNECTED = "0";

	
	// used for vertex covers to show if they are dominated or not
	public final static byte MARKED = 1;
	public final static byte UNMARKED = 0;
	
	
	/**
	 * generate an instance of Graph with internal parameters
	 * 
	 * @param adjacencyMatrix
	 *            , adjacency matrix of a graph
	 * @return a graph
	 */
	public static Graph<Integer, Integer> prepareGraph(
			List<String[]> adjacencyMatrix) {

		int numOfVertices = adjacencyMatrix.size();
		Graph<Integer, Integer> g = new SparseMultigraph<Integer, Integer>();
		for (int i = 0; i < numOfVertices; i++) {
			g.addVertex(i);
		}

		for (int i = 0; i < numOfVertices; i++) {
			String[] rowArr = adjacencyMatrix.get(i);
			for (int j = i + 1; j < numOfVertices; j++) {
				if (CONNECTED.equals(rowArr[j].trim())) {
					// the label of edge is decided by the label of the two
					// endpoints
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
	 * @param adjacentRatio
	 *            ,a real number between 0 and 1. the purpose of this ratio is
	 *            to reduce the number of neighbors of a vertex in order to
	 *            increase the number of elements in ds. Otherwise, a vertex may
	 *            link to all other vertices and become the only element in the
	 *            ds.
	 * @return an adjancy matrix of the random graph
	 */
	public static List<String[]> generateRandGraph(int numOfVertices,
			float adjacentRatio) {
		List<String[]> adjacencyMatrix = null;

		adjacencyMatrix = new ArrayList<String[]>(numOfVertices);
		for (int i = 0; i < numOfVertices; i++) {
			String[] row = new String[numOfVertices];
			// initialize every cell in the row to be UNCONNECTED,
			Arrays.fill(row, UNCONNECTED);
			// generate a random number of the vertices linking to the current
			// row no.
			int adjacentNum = (int) Math.round(Math.random() * numOfVertices);
			// make use of the ratio to reduce density
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
	 * do hedit on a graph to generate a new graph
	 * 
	 * @param am1
	 *            , the adjacency matrix of graph 1
	 * @param g1
	 *            , graph 1
	 * @param ds1
	 *            , a domininating set of graph 1
	 * @param r
	 *            , the hamming distance between ds1 (for g1) and ds2 (for g2)
	 * @return HEdit, containing the adjacency matrix of the new graph and the
	 *         edge edit operation list
	 */
	public static HEdit hEdit(List<String[]> am1, Graph<Integer, Integer> g1,
			List<Integer> ds1, int r) {
		List<String[]> operationList = new ArrayList<String[]>();

		// generate a copy of adjacency matrix 1
		List<String[]> am2 = new ArrayList<String[]>(am1);
		

		Collection<Integer> vertices1 = g1.getVertices();
		// get the complementary set of dominating set 1 in graph 1
		List<Integer> complementOfSByVertices1 = (List<Integer>) CollectionUtils
				.subtract(vertices1, ds1);
		int complementOfSByVertices1Size = complementOfSByVertices1.size();

		for (int i = 0; i < r; i++) {
			// get an arbitrary vertex in the complementary set
			int complementPosition = (int) Math.floor(Math.random()
					* complementOfSByVertices1Size);
			int complementV = complementOfSByVertices1.get(complementPosition);
			// get the arbitrary vertex's neighbours in g1
			Collection<Integer> neighboursOfComplementV = g1
					.getNeighbors(complementV);
			Iterator<Integer> neighboursOfComplementVIterator = neighboursOfComplementV
					.iterator();
			// unconnect the arbitrary vertex and its neighbours
			while (neighboursOfComplementVIterator.hasNext()) {
				int tmpNeighbour = neighboursOfComplementVIterator.next();

				// change adjacency matrix
				am2.get(complementV)[tmpNeighbour] = UNCONNECTED;
				am2.get(tmpNeighbour)[complementV] = UNCONNECTED;

				// change operation list
				String[] operation = { UNCONNECTED,
						Integer.toString(complementV),
						Integer.toString(tmpNeighbour) };

				addElementToList(operationList, operation);
			}
		}

		HEdit hEdit = new HEdit();
		hEdit.setOperationList(operationList);
		hEdit.setOutputAdjacencyMatrix(am2);

		// in addition, add some edge add operations, which are harmless
		hEdit = generateEdgeAddOperation(hEdit);

		return hEdit;
	}
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInRang(int min,int max){
		Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static float randomInRang(float min,float max){
		Random random = new Random();
        float s = random.nextFloat()*(max-min) + min;
        return s;
	}

	/**
	 * generate some edge add operations, which are harmless, into hEdit's edge
	 * edit operation list.
	 * 
	 * @param hEdit
	 * @return hEdit
	 */
	private static HEdit generateEdgeAddOperation(HEdit hEdit) {

		List<String[]> operationList = hEdit.getOperationList();
		List<String[]> am = hEdit.getOutputAdjacencyMatrix();
		int numOfVertices = am.size();

		int maxEdgeOperationNum = numOfVertices;
		Random random = new Random();
		int operationRandomNum = Math.abs(random.nextInt()
				% maxEdgeOperationNum);
		for (int i = 0; i < operationRandomNum; i++) {
			String[] operation = new String[3];
			operation[0] = CONNECTED;
			int v1 = Math.abs(random.nextInt() % numOfVertices);
			int v2 = Math.abs(random.nextInt() % numOfVertices);

			// change adjacency matrix
			am.get(v1)[v2] = CONNECTED;
			am.get(v2)[v1] = CONNECTED;

			// change operation list
			operation[1] = Integer.toString(v1);
			operation[2] = Integer.toString(v2);

			addElementToList(operationList, operation);

		}
		hEdit.setOperationList(operationList);
		hEdit.setOutputAdjacencyMatrix(am);

		return hEdit;
	}

	/**
	 * avoid adding duplicated elements into a list
	 * 
	 * @param list
	 * @param e
	 * @return
	 */
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
	 *            , a potential dominating set
	 * @return boolean, is dominating set or not
	 */
	public static boolean isDS(Graph<Integer, Integer> g, List<Integer> ds) {
		Collection<Integer> vertices = g.getVertices();
		Collection<Integer> complementaryDS = CollectionUtils.subtract(
				vertices, ds);

		for (Integer v : ds) {
			// get neighbours of the vertices in dominating set
			Collection<Integer> neighborsOfV = g.getNeighbors(v);
			// remove the neighbours from the complementary set
			complementaryDS = CollectionUtils.subtract(complementaryDS,
					neighborsOfV);
		}
		/*
		 * if the complementary set is not empty, it means there are some
		 * vertices are not dominated and in turn the input set is not a
		 * dominating set for the graph
		 */
		if (!complementaryDS.isEmpty()) {
			return false;
		}
		return true;

	}

	
			
	/**
	 * judge if it is a solution of set dominating a vertex cover
	 * 
	 * @param g
	 *            , a graph
	 * @param domVC
	 *            , a potential set dominating a vertex cover
	 * @param vc
	 *            , a vertex cover
	 * @return boolean, is a solution or not
	 */

	public static boolean isDomVCSet(Graph<Integer, Integer> g,
			List<Integer> domVC, List<Integer> vc) {

		for (Integer v : domVC) {
			/*
			 * get neighbours of the vertices in the potential set dominating
			 * the vertex cover
			 */
			Collection<Integer> neighborsOfV = g.getNeighbors(v);
			// remove the neighbours from the vertex cover
			vc = (List<Integer>) CollectionUtils.subtract(vc, neighborsOfV);
		}
		/*
		 * vc intersect domVC might not be empty because some verterices in vc
		 * may dominate other vertices in vc as well
		 */
		vc = (List<Integer>) CollectionUtils.subtract(vc, domVC);

		/*
		 * if the vertex cover is not empty, it means there are some vertices
		 * are not dominated and in turn the input set is not a set dominating
		 * vc for the graph
		 */
		if (!vc.isEmpty()) {
			return false;
		}
		return true;

	}

	/**
	 * calculate the decimal number according to the 0/1 array. For example,the
	 * result of {1,0,1,1} will be 2^3+2^2+2^0=8+4+1=13 because the value in
	 * postion 0,2,3 is 1.
	 * 
	 * this can be used to show if any two 0/1 array are the same
	 * 
	 * @param ruler
	 *            , an 0/1 array
	 * @return
	 */
	public static double getDecFromBinArray(byte[] ruler) {
		int k = ruler.length;
		double rulerNum = 0;
		for (int i = 0; i < k; i++) {
			if (ruler[i] == 1) {
				rulerNum += (Math.pow(2, i));
			}
		}

		return rulerNum;
	}

	/**
	 * get the number of 1s in the array
	 * 
	 * this can be used to show the number of 1s in the array
	 * 
	 * @param rulerr
	 *            , an 0/1 array
	 * @return
	 */
	public static int getSumFromBinArray(byte[] ruler) {
		int k = ruler.length;
		int rulerSum = 0;
		for (int i = 0; i < k; i++) {
			rulerSum += ruler[i];
		}
		return rulerSum;
	}

	public static byte[] arrayOr (byte[] ruler1, byte[] ruler2) {
		int ruler1Len = ruler1.length;
		int ruler2Len = ruler2.length;
		if (ruler1Len != ruler2Len) {
			return null;
		}
		
		for (int i = 0; i < ruler1Len; i++) {
//			if ((ruler1[i] == MARKED) || (ruler2[i] == MARKED)) {
//				ruler1[i] = MARKED;
//			}
			ruler1[i] =(byte)( ruler1[i] | ruler2[i]);
		}

		return ruler1;
	}

	/**
	 * get neighbours of vertices in a set
	 * 
	 * @param g
	 *            , a graph
	 * @param S
	 *            , a set of vertices
	 * @return
	 */
	public static List<Integer> getNeighborsOfS(Graph<Integer, Integer> g,
			List<Integer> S) {
		List<Integer> ngs = new ArrayList<Integer>();
		for (Integer s : S) {
			Collection<Integer> col = g.getNeighbors(s);
			if (col != null) {
				ngs = (List<Integer>) CollectionUtils.union(ngs, col);
			}
		}
		return ngs;
	}

	/**
	 * @param inputFile
	 * @return
	 */
	public static FileOperation getProblemInfo(String inputFile) {

		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(inputFile);
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

		return fileOperation;
	}
	
	public static boolean isAMEquals(List<String[]> am1, List<String[]> am2) {

		int n = am1.size();
		for (int i = 0; i < n; i++) {

			String[] am1row = am1.get(i);
			String[] am2row = am2.get(i);
			boolean tempEq = Arrays.equals(am1row, am2row);
			if (!tempEq) {
				return false;
			}

		}
		return true;
	}
	

}

class HEdit {
	/**
	 * the edge edit operation list
	 */
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

	public void setOutputAdjacencyMatrix(
			List<String[]> outputAdjacencyMatrix) {
		this.outputAdjacencyMatrix = outputAdjacencyMatrix;
	}

	/**
	 * the adjacency matrix after hedit
	 */
	private List<String[]> outputAdjacencyMatrix;
}
