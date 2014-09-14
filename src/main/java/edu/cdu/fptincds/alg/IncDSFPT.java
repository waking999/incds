package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

import edu.cdu.fptincds.view.GraphView;
import edu.cdu.fptincds.view.Section;
import edu.uci.ics.jung.graph.Graph;

/**
 * make use of the fpt algorithm to get a dominating set in a graph
 * 
 * @author Kai Wang
 * 
 */
public class IncDSFPT {

	// private Logger log = LogUtil.getLogger(IncDSFPT.class);
	// /**
	// * the adjacency matrix of graph 1
	// */
	// private List<String[]> adjacencyMatrix1;
	// /**
	// * graph 1
	// */
	// private Graph<Integer, Integer> g1;
	/**
	 * a dominating set solution of graph 1
	 */
	private List<Integer> ds1;
	/**
	 * the adjacency matrix of graph 2
	 */
	private List<String[]> adjacencyMatrix2;
	/**
	 * graph 2
	 */
	private Graph<Integer, Integer> g2;
	/**
	 * the desired dominating set of graph 2
	 */
	private List<Integer> ds2;
	private int r;

	public List<Integer> getDs2() {
		return ds2;
	}

	// public IncDSFPT(List<String[]> adjacencyMatrix1,
	// List<String[]> adjacencyMatrix2, List<Integer> dominatingSet1) {
	// int numOfVertices1 = adjacencyMatrix1.size();
	// int numOfVertices2 = adjacencyMatrix2.size();
	// if (numOfVertices1 == numOfVertices2) {
	// this.adjacencyMatrix1 = adjacencyMatrix1;
	// this.adjacencyMatrix2 = adjacencyMatrix2;
	// this.ds1 = dominatingSet1;
	// this.g1 = AlgorithmUtil.prepareGraph(this.adjacencyMatrix1);
	// this.g2 = AlgorithmUtil.prepareGraph(this.adjacencyMatrix2);
	// } else {
	// log.debug("Graph 1 and 2 are of different number of vertices.");
	// }
	// }

	public IncDSFPT(List<String[]> adjacencyMatrix2,
			List<Integer> dominatingSet1, int r) {
		this.adjacencyMatrix2 = adjacencyMatrix2;
		this.ds1 = dominatingSet1;
		this.g2 = AlgorithmUtil.prepareGraph(this.adjacencyMatrix2);
		this.r = r;

	}

	public IncDSFPT(Graph<Integer, Integer> g2, List<Integer> dominatingSet1,
			int r) {
		this.ds1 = dominatingSet1;
		this.g2 = g2;
		this.r = r;
	}

	/**
	 * the major function do the computing to get the desired solution. In this
	 * case, the desired result is a dominating set of graph 2
	 */
	public void computing() throws Exception {
		initialization();
		ReducedInstance reducedInstance = reductionRulesOnGraph2();
		Collection<Integer> vertexCover = reducedInstance.getVertexCover();

		int vertexCoverSize = vertexCover.size();
		if (vertexCoverSize > 0) {

			Graph<Integer, Integer> gStar = reducedInstance.getG();

			domAVcFpt(vertexCover, gStar, r);
		} else {
			this.ds2 = ds1;
		}

	}

	private void initialization() {
		this.ds2 = new ArrayList<Integer>();
	}

	/**
	 * apply reduction rules in graph 2
	 * 
	 * @return a vertex cover in reduced graph
	 */
	private ReducedInstance reductionRulesOnGraph2() {
		/*
		 * prepare graph 2 to get B(neighboursOfDs1InG2), C(vertexCover) in
		 * order to apply the reduction rules
		 */
		Collection<Integer> verticesInG2 = g2.getVertices();
		int verticesNum = verticesInG2.size();
		List<Integer> neighboursOfDs1InG2 = AlgorithmUtil.getNeighborsOfS(g2,
				ds1);
		neighboursOfDs1InG2 = (List<Integer>) CollectionUtils.subtract(
				neighboursOfDs1InG2, ds1);
		Collection<Integer> vertexCover = CollectionUtils.subtract(
				verticesInG2, neighboursOfDs1InG2);
		vertexCover = CollectionUtils.subtract(vertexCover, ds1);
		
		List<List<Integer>> vertexSections=new ArrayList<List<Integer>>();
		vertexSections.add(ds1);
		vertexSections.add((List<Integer>)vertexCover);
		vertexSections.add(neighboursOfDs1InG2);
		//viewGraph( g2,vertexSections);
		
		// apply reduction rules
		g2 = r1(g2, ds1);
		

		g2 = r2(g2, neighboursOfDs1InG2, vertexCover);
		

		g2 = r3(g2, neighboursOfDs1InG2,verticesNum);
		
		//viewGraph( g2,vertexSections);
		
		ReducedInstance reducedInstance = new ReducedInstance(g2,
				vertexCover);
		return reducedInstance;

	}
	
	private void viewGraph(Graph<Integer,Integer> g,List<List<Integer>> vertexSections){
		float width = 1024;
		float height = 768;

		Section sec1 = new Section(0.0f, 0.0f, width / 2*0.9f, height, vertexSections.get(0));
		Section sec2 = new Section(width / 2*1.1f, 0f, width, height / 2*0.9f, vertexSections.get(1));
		Section sec3 = new Section(width / 2*1.1f, height / 2*1.1f, width, height, vertexSections.get(2));

		List<Section> sections = new ArrayList<Section>();
		sections.add(sec1);
		sections.add(sec2);
		sections.add(sec3);
		
		
		GraphView.presentGraph(g, sections, width, height);
	}

	private void domAVcFpt(Collection<Integer> vertexCover,
			Graph<Integer, Integer> gStar, int r) throws Exception {
		DomVCFPT ag = new DomVCFPT(gStar, (List<Integer>) vertexCover, r);
		ag.computing();

		if (ag.isLessR()) {
			List<Integer> SStar = ag.getDominatingVertexCoverSet();

			Collection<Integer> S2 = CollectionUtils.union(ds1, SStar);

			this.ds2 = (List<Integer>) S2;
		}
	}

	/**
	 * apply reduction rule 1:if v belongs to ds1, remove v and its incident
	 * edges from g2
	 * 
	 * @param g
	 *            , graph 2
	 * @param S
	 *            , dominating set of graph 1(ds1)
	 * @return
	 */
	private static Graph<Integer, Integer> r1(Graph<Integer, Integer> g,
			Collection<Integer> S) {
		for (Integer s : S) {
			g.removeVertex(s);
		}
		return g;
	}

	/**
	 * apply reudction rule 2: if v belongs to B, and N(v) intersect C is empty,
	 * remove v and its incident edges in g2
	 * 
	 * @param g
	 *            , the graph 2
	 * @param B
	 *            , the neighbours of ds1 in g2
	 * @param C
	 *            , the undominated vertices by ds1
	 * @return
	 */
	private static Graph<Integer, Integer> r2(Graph<Integer, Integer> g,
			Collection<Integer> B, Collection<Integer> C) {
		for (Integer b : B) {
			Collection<Integer> neiB = g.getNeighbors(b);
			Collection<Integer> intsec = null;
			if (neiB != null && C != null) {
				intsec = CollectionUtils.intersection(neiB, C);
			}

			if (intsec == null || intsec.size() == 0) {
				g.removeVertex(b);
			}
		}
		return g;
	}

	/**
	 * reduction rule 3, if(u,v) is an edge and {u,v} belongs B, remove the
	 * edge(U,v)
	 * 
	 * @param g
	 *            , the graph 2
	 * @param B
	 *            , the neighbours of ds1 in g2
	 * @return
	 */
	private static Graph<Integer, Integer> r3(Graph<Integer, Integer> g,
			Collection<Integer> B,int verticesNum) {
		
		for (Integer b1 : B) {
			for (Integer b2 : B) {
				if (!b1.equals(b2)) {
					int edge = b1 * verticesNum + b2;
					if (g.containsEdge(edge)) {
						g.removeEdge(edge);
					}
				}
			}
		}

		return g;
	}

	class ReducedInstance {
		Graph<Integer, Integer> g;
		Collection<Integer> vertexCover;

		protected Graph<Integer, Integer> getG() {
			return g;
		}

		protected Collection<Integer> getVertexCover() {
			return vertexCover;
		}

		protected ReducedInstance(Graph<Integer, Integer> g,
				Collection<Integer> vertexCover) {
			this.g = g;
			this.vertexCover = vertexCover;
		}
	}

	

}
