package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.log4j.Logger;

import edu.cdu.fptincds.util.LogUtil;
import edu.cdu.fptincds.view.GraphView;
import edu.uci.ics.jung.graph.Graph;

public class IncDSFPT {

	private Logger log = LogUtil.getLogger(IncDSFPT.class);

	private List<String[]> adjacencyMatrix1;
	private Graph<Integer, Integer> g1;
	private List<Integer> ds1;

	private List<String[]> adjacencyMatrix2;
	private Graph<Integer, Integer> g2;
	private List<Integer> ds2;

	public List<Integer> getDs2() {
		return ds2;
	}

	public IncDSFPT(List<String[]> adjacencyMatrix1,
			List<String[]> adjacencyMatrix2, List<Integer> dominatingSet1) {
		int numOfVertices1 = adjacencyMatrix1.size();
		int numOfVertices2 = adjacencyMatrix2.size();
		if (numOfVertices1 == numOfVertices2) {
			this.adjacencyMatrix1 = adjacencyMatrix1;
			this.adjacencyMatrix2 = adjacencyMatrix2;
			this.ds1 = dominatingSet1;
		} else {
			log.debug("Graph 1 and 2 are of different number of vertices.");
		}
	}

	/**
	 * 
	 */
	private void initialization() {
		this.g1 = AlgorithmUtil.prepareGraph(this.adjacencyMatrix1);
		this.g2 = AlgorithmUtil.prepareGraph(this.adjacencyMatrix2);
	}

	public void computing() {

		initialization();
		reductionRules();

	}

	private List<Integer> getNeighborsOfS(Graph<Integer, Integer> g,
			List<Integer> S) {
		List<Integer> ngs = new ArrayList<Integer>();
		for (Integer s : S) {
			Collection<Integer> col = g.getNeighbors(s);
			ngs = (List<Integer>) CollectionUtils.union(ngs, col);
		}
		return ngs;
	}

	private void reductionRules() {
		//GraphView.presentGraph(g1);
		Collection<Integer> V = g1.getVertices();
		List<Integer> B = this.getNeighborsOfS(g2, ds1);
		Collection<Integer> C = CollectionUtils.subtract(V, B);
		C = CollectionUtils.subtract(C, ds1);

		//GraphView.presentGraph(g2);
		
		Graph<Integer, Integer> gStar = r1(g2, ds1);
		//GraphView.presentGraph(gStar);
		gStar = r2(gStar, B, C);
		//GraphView.presentGraph(gStar);
		gStar = r3(gStar, B);

		//GraphView.presentGraph(gStar);
		
		DomVCFPT ag = new DomVCFPT(gStar,( List<Integer>)C);
		ag.computing();
		List<Integer> SStar = ag.getDominatingVertexCoverSet();

		Collection<Integer> S2 = CollectionUtils.union(ds1, SStar);

		this.ds2 = (List<Integer>) S2;
	}

	private Graph<Integer, Integer> r1(Graph<Integer, Integer> g,
			Collection<Integer> S) {
		for (Integer s : S) {
			g.removeVertex(s);
		}
		return g;
	}

	private Graph<Integer, Integer> r2(Graph<Integer, Integer> g,
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

	private Graph<Integer, Integer> r3(Graph<Integer, Integer> g,
			Collection<Integer> B) {
		int numOfVertices = g.getVertexCount();
		for (Integer b1 : B) {
			for (Integer b2 : B) {
				if (!b1.equals(b2)) {
					int edge = b1 * numOfVertices + b2;
					g.removeEdge(edge);
				}
			}
		}

		return g;
	}
}
