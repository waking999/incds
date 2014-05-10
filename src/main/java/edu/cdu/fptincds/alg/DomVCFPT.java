package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

import edu.uci.ics.jung.graph.Graph;

public class DomVCFPT {
	private Graph<Integer, Integer> g;

	private List<Integer> dominatingVertexCoverSet;

	// private int numOfVertices;
	//
	// private List<String[]> adjacencyMatrix;

	// public void setAdjacencyMatrix(List<String[]> adjacencyMatrix) {
	// this.adjacencyMatrix = adjacencyMatrix;
	// }

	public List<Integer> getDominatingVertexCoverSet() {
		return dominatingVertexCoverSet;
	}

	private List<Integer> vertexCover;

	public DomVCFPT(Graph<Integer, Integer> g, List<Integer> vertexCover) {
		this.g = g;
		this.vertexCover = vertexCover;
		this.dominatingVertexCoverSet = new ArrayList<Integer>();
	}

	public void computing() {
		Collection<Integer> vertices = g.getVertices();
		List<Integer> independentSet = (List<Integer>) CollectionUtils
				.subtract(vertices, vertexCover);

		int vertexCoverSize = vertexCover.size();

		while (vertexCoverSize > 0) {
			Integer v = vertexCover.get(0);

			Collection<Integer> neighboursOfV = g.getNeighbors(v);
			List<Integer> intsec = null;
			if (neighboursOfV != null && independentSet != null) {
				intsec = (List<Integer>) CollectionUtils.intersection(
						neighboursOfV, independentSet);
			}
			if (intsec != null && intsec.size() > 0) {

				AlgorithmUtil.addElementToList(this.dominatingVertexCoverSet,
						intsec.get(0));
				vertexCover.remove(v);
			} else {
				AlgorithmUtil
						.addElementToList(this.dominatingVertexCoverSet, v);
				vertexCover.remove(v);
			}
			vertexCoverSize = vertexCover.size();
		}

		for (Integer v : dominatingVertexCoverSet) {
			Collection<Integer> neighboursOfV = g.getNeighbors(v);
			if (neighboursOfV != null) {
				int neighboursOfVSize = neighboursOfV.size();
				for (Integer u : this.dominatingVertexCoverSet) {
					if (u != v) {
						Collection<Integer> neighboursOfU = g.getNeighbors(u);
						int neighboursOfUSize = neighboursOfU.size();
						if (neighboursOfVSize > 0
								&& neighboursOfUSize > 0
								&& CollectionUtils.isEqualCollection(
										neighboursOfV, neighboursOfU)) {
							this.dominatingVertexCoverSet.remove(u);
						}
					}
				}
			}

		}
	}

}
