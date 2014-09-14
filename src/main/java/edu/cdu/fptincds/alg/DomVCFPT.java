package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.log4j.Logger;

import edu.cdu.fptincds.exception.MOutofNException;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

/**
 * make use of the fpt algorithm to get a dominating set of a vertex cover in a
 * graph
 * 
 * @author Kai Wang
 * 
 */
public class DomVCFPT {

	private Logger log = LogUtil.getLogger(DomVCFPT.class);

	// used for n choose m to show if the element has been chosen or not
	private final static boolean CHOSEN = true;
	private final static boolean UNCHOSEN = false;
	/**
	 * the graph
	 */
	private Graph<Integer, Integer> g;
	/**
	 * the set dominating the vertex cover of the graph
	 */
	private List<Integer> dominatingVertexCoverSet;

	public List<Integer> getDominatingVertexCoverSet() {
		return dominatingVertexCoverSet;
	}

	/**
	 * the vertex cover of the graph
	 */
	private List<Integer> vertexCover;

	/**
	 * a parameter used in FPT as the size of the set dominating the vertex
	 * cover
	 */
	private int r;

	private boolean lessR;

	/**
	 * initialize the algorithm with graph and the vertex cover, which has been
	 * computed by other algorithm
	 * 
	 * @param g
	 * @param vertexCover
	 */
	public DomVCFPT(Graph<Integer, Integer> g, List<Integer> vertexCover, int r) {
		this.g = g;
		this.vertexCover = vertexCover;
		this.r = r;

	}

	private List<ISDomInfo> candidateDomVerList;
	private List<List<Integer>> possibleDomVCSets;

	private byte[] desiredRuler;

	public List<List<Integer>> getPossibleDomVCSets() {
		return possibleDomVCSets;
	}

	private boolean isDomVCSet(boolean[] chosen, int n) {
		int vertexCoverSize = vertexCover.size();
		byte[] ruler = new byte[vertexCoverSize];
		Arrays.fill(ruler, AlgorithmUtil.UNMARKED);
		for (int i = 0; i < n; i++) {
			if (chosen[i]) {
				byte[] tempRuler = candidateDomVerList.get(i).getRuler();
				ruler = AlgorithmUtil.arrayOr(ruler, tempRuler);
			}
		}

		return Arrays.equals(desiredRuler, ruler);
	}

	private void initlization() {
		this.lessR = false;
		this.dominatingVertexCoverSet = new ArrayList<Integer>();
		possibleDomVCSets = new ArrayList<List<Integer>>();

		int vertexCoverSize = vertexCover.size();

		desiredRuler = new byte[vertexCoverSize];
		Arrays.fill(desiredRuler, AlgorithmUtil.MARKED);
	}

	/**
	 * the major function do the computing to get the desired solution. In this
	 * case, the desired result is a set dominating the vertex cover
	 */
	public void computing() throws Exception {
		// the vertex cover could be empty,we will not consider it
		int vertexCoverSize = vertexCover.size();
		if (vertexCoverSize > 0) {
			initlization();

			getCandidateDomVerList();

			getAttemptRSizeSolution(r);

			if (this.possibleDomVCSets != null
					&& this.possibleDomVCSets.size() > 0) {
				this.dominatingVertexCoverSet = this.possibleDomVCSets.get(0);
			}else{
				log.debug("can't find a solution with size"+this.r);
			}
		}

	}

	public boolean isLessR() {
		return lessR;
	}

	private long nChooseMNum(int n,int m){
		long up=1;
		for(int i=n;i>n-m;i--){
			up*=i;
		}
		long down=1;
		for(int i=1;i<=m;i++){
			down *=i;
		}
		
		return up/down;
	}
	private void nChooseM(int n, int m) throws MOutofNException {

		boolean lessR = false;
		boolean isEnd = false;

		boolean[] chosen = new boolean[n];
		Arrays.fill(chosen, UNCHOSEN);
		try {
			Arrays.fill(chosen, 0, m, CHOSEN);
		} catch (Exception e) {
			String message="When choosing m out of n, it suppose m<=n. However, m="+m+",n="+n+".";
			throw new MOutofNException(message);
		}

		lessR = verifyChosen(chosen, m, n);
		if (lessR) {
			return;
		}

		long total=nChooseMNum(n,m);
		int count=0;
		do {
			int pose = 0;
			int sum = 0;
			for (int i = 0; i < (n - 1); i++) {
				if (chosen[i] == CHOSEN && chosen[i + 1] == UNCHOSEN) {
					chosen[i] = UNCHOSEN;
					chosen[i + 1] = CHOSEN;
					pose = i;
					break;
				}
			}

			lessR = verifyChosen(chosen, m, n);
			if (lessR) {
				return;
			}

			for (int i = 0; i < pose; i++) {
				if (chosen[i] == CHOSEN) {
					sum++;
				}
			}

			Arrays.fill(chosen, 0, sum, CHOSEN);
			Arrays.fill(chosen, sum, pose, UNCHOSEN);

			isEnd = true;
			for (int i = n - m; i < n; i++) {

				if (chosen[i] == UNCHOSEN) {
					isEnd = false;
					break;
				}

			}
			count++;
		} while (!isEnd || count<total);

	}

	private void getCandidateDomVerList() {

		// the vertex cover's complement set will be an independent set
		Collection<Integer> vertices = g.getVertices();
		Collection<Integer> independentSet = CollectionUtils.subtract(vertices,
				vertexCover);
		/*
		 * the vertex cover size will be the parameter k used in the fpt
		 * algorithm
		 */
		int vertexCoverSize = vertexCover.size();

		/*
		 * set is used to avoid adding duplicated elements. In this case, if two
		 * ISDomInfo are of the same 0/1 array,which present the vertices in
		 * vertex cover dominated by the vertex in the independent set, they are
		 * regarded as the same
		 */
		Set<ISDomInfo> candidateDomVerSet = new HashSet<ISDomInfo>();

		// go through the independent set
		for (Integer isv : independentSet) {
			Collection<Integer> neighOfIsv = g.getNeighbors(isv);
			/*
			 * a 0/1 array,which present the vertices in vertex cover dominated
			 * by the vertex in the independent set
			 * 
			 * the array's length will be the number of vertices in vertex cover
			 * because what we want is a dominating set of the vertex cover
			 */
			byte ruler[] = new byte[vertexCoverSize];
			// initilze the array with 0
			Arrays.fill(ruler, AlgorithmUtil.UNMARKED);
			for (Integer neig : neighOfIsv) {
				/*
				 * the position of the dominate vertex in the vertex cover will
				 * set 1
				 */
				int pos = vertexCover.indexOf(neig);
				if (pos != -1) {
					ruler[pos] = AlgorithmUtil.MARKED;
				}
			}

			ISDomInfo isDomInfo = new ISDomInfo(isv, ruler);
			/*
			 * avoid putting the same 0/1 array into the set because if there
			 * are multiple vertices of the same neighbour type then we can
			 * discard all but one of them to dominate that neighbourhood.
			 */
			candidateDomVerSet.add(isDomInfo);
		}

		/*
		 * because the vertices in the vertex cover can also dominate other
		 * vertices in the vertex cover,they are also considered
		 */
		for (Integer vcv : vertexCover) {
			Collection<Integer> neighOfVcv = g.getNeighbors(vcv);
			byte ruler[] = new byte[vertexCoverSize];
			Arrays.fill(ruler, AlgorithmUtil.UNMARKED);

			// the vertex can dominated by itself
			int pos = vertexCover.indexOf(vcv);
			ruler[pos] = AlgorithmUtil.MARKED;

			for (Integer neig : neighOfVcv) {
				/*
				 * the position of the dominate vertex in the vertex cover will
				 * set 1
				 * 
				 * a neighbour of the vertex is likely not to be in the vertex
				 * cover
				 */
				pos = vertexCover.indexOf(neig);
				if (pos != -1) {
					ruler[pos] = AlgorithmUtil.MARKED;
				}
			}

			ISDomInfo isDomInfo = new ISDomInfo(vcv, ruler);
			candidateDomVerSet.add(isDomInfo);
		}

		candidateDomVerList = new ArrayList<ISDomInfo>(candidateDomVerSet);
//		for (ISDomInfo info : candidateDomVerList) {
//			log.debug(info);
//		}
//		log.debug("");
	}

	private void getAttemptRSizeSolution(int attemptR) throws Exception {
		int candidateDomVerListSize = candidateDomVerList.size();

		/* chose m elements from n elements */
		nChooseM(candidateDomVerListSize, attemptR);

	}

	private boolean verifyChosen(boolean[] chosen, int m, int n) {

		boolean flag = isDomVCSet(chosen, n);

		if (flag) {
			/*
			 * if it is a solution, we find a solution whose size is less than r
			 */
			lessR = true;
			List<Integer> possibleDomVCSet = new ArrayList<Integer>(m);
			for (int i = 0; i < n; i++) {
				if (chosen[i] == CHOSEN) {
					possibleDomVCSet
							.add(candidateDomVerList.get(i).getVertex());
				}
			}
			possibleDomVCSets.add(possibleDomVCSet);
			return true;
		}
		return false;
	}

	class ISDomInfo {
		private byte ruler[];

		protected byte[] getRuler() {
			return ruler;
		}

		protected Integer getVertex() {
			return vertex;
		}

		private Integer vertex;

		protected ISDomInfo(Integer vertex, byte ruler[]) {
			this.vertex = vertex;
			this.ruler = ruler;
		}
		
		public int hashCode(){
			return ruler.length;
		}

		public boolean equals(Object anObject) {
			if (this == anObject) {
				return true;
			}
			if (anObject instanceof ISDomInfo) {

				if (Arrays.equals(this.ruler, ((ISDomInfo) anObject).ruler)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(this.vertex).append(":").append("\t");
			for (byte b : this.ruler) {
				sb.append(b).append(",");
			}

			return sb.substring(0, sb.length() - 1);
		}

	}

}
