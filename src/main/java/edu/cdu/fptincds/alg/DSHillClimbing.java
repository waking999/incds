package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;

import edu.uci.ics.jung.graph.Graph;

public class DSHillClimbing {
	//private List<String[]> am;
	private Graph<Integer,Integer> g;
	private List<Integer> ds1;
	private State state;

	
	public List<Integer> getDominatingSet(){
		return state.getDs();
	}
	public DSHillClimbing(List<String[]> am,List<Integer> ds1){
		//this.am=am;
		this.g=AlgorithmUtil.prepareGraph(am);
		this.ds1=ds1;		
	}
	
	public DSHillClimbing(Graph<Integer,Integer> g,List<Integer> ds1){
		this.g=g;
		this.ds1=ds1;		
	}
	
	private void initlization(){
		state = new State(ds1);
		
	}
	
	public void computing() {
		initlization();
		hillClimbing();
	}

	private void hillClimbing() {
		
		

		List<Integer> aDs = state.getDs();		

		int aDsLen = aDs.size();

		

		// take use of hill climbing idea based on the generated dominating set
		int minDSLen = aDsLen;

		Collection<Integer> vertices = g.getVertices();
		
		Collection<Integer> compDs = CollectionUtils.subtract(vertices, aDs);
		
		

		for (Integer tmpV:compDs) {
			aDs = state.getDs();		
			compDs = CollectionUtils.subtract(vertices, aDs);
			List<Integer> aDsBk = new ArrayList<Integer>(aDs);
			List<Integer> comDsBk = new ArrayList<Integer>(compDs);
	
			aDsBk.add(tmpV);
			comDsBk.remove(tmpV);
			Collection<Integer> neighborsOfTmpV = g.getNeighbors(tmpV);
			for(Integer neig:neighborsOfTmpV){
				if (aDsBk.contains(neig)) {
					comDsBk.add(neig);
					aDsBk.remove(neig);
				}
			}
			
			
		

			// verify certainDSBk is a dominating set
			boolean isDS = AlgorithmUtil.isDS(g, aDsBk);
			
			if (isDS) {
				int aDsBkLen = aDsBk.size();
				if (minDSLen > aDsBkLen) {

					minDSLen = aDsBkLen;

					// dominatingSetSet.add(certainDSBk);
					List<Integer> existingDs = state.getDs();
					
					state.setPrevDs(existingDs);
					state.setDs(aDsBk);

				}
			}
		}
		

		
	}
}
