package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * a java bean to contain states
 * 
 * @author Kai Wang
 * 
 */
public class State{
	/*
	 * dominating set
	 */
	private List<Integer> ds = null;
	
	/*
	 * previous dominating set
	 */
	private List<Integer> prevDs = null;
	

	public void setDs(List<Integer> ds) {
		if (this.ds == null) {
			this.ds = new ArrayList<Integer>(ds);
		} else {
			this.ds.clear();
			this.ds.addAll(ds);
		}
	}

	

	public void setPrevDs(List<Integer> prevDs) {
		if (this.prevDs == null) {
			this.prevDs = new ArrayList<Integer>(prevDs);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(prevDs);
		}
	}

	

	public State() {

	}

	public State(List<Integer> ds) {
		this.ds = ds;
	}

	public State(List<Integer> ds, List<Integer> prevDs) {
		this.ds = ds;		
		this.prevDs = prevDs;		
	}

	public List<Integer> getDs() {
		return ds;
	}

	


}
