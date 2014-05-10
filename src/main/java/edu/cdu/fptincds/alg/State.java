package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * a java bean to contain states
 * 
 * @author Kai Wang
 * 
 */
public class State implements Cloneable {
	/*
	 * dominating set
	 */
	private List<Integer> ds = null;
//	/*
//	 * complementary set of dominating set
//	 */
//	private List<Integer> complementaryDs = null;
	/*
	 * previous dominating set
	 */
	private List<Integer> prevDs = null;
//	/*
//	 * complementary set of previous dominating set
//	 */
//	private List<Integer> prevCplDs = null;

	public void setDs(List<Integer> ds) {
		if (this.ds == null) {
			this.ds = new ArrayList<Integer>();
			this.ds.addAll(ds);
		} else {
			this.ds.clear();
			this.ds.addAll(ds);
		}
	}

//	public void setComplementaryDs(List<Integer> cplDs) {
//
//		if (this.complementaryDs == null) {
//			this.complementaryDs = new ArrayList<Integer>();
//			this.complementaryDs.addAll(cplDs);
//		} else {
//			this.complementaryDs.clear();
//			this.complementaryDs.addAll(cplDs);
//		}
//	}

	public void setPrevDs(List<Integer> prevDs) {
		if (this.prevDs == null) {
			this.prevDs = new ArrayList<Integer>();
			this.prevDs.addAll(prevDs);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(prevDs);
		}
	}

//	public void setPrevCplDs(List<Integer> prevCplDs) {
//		if (this.prevCplDs == null) {
//			this.prevCplDs = new ArrayList<Integer>();
//			this.prevCplDs.addAll(prevCplDs);
//		} else {
//			this.prevCplDs.clear();
//			this.prevCplDs.addAll(prevCplDs);
//		}
//	}

	public List<Integer> getPrevDs() {
		return prevDs;
	}

//	public List<Integer> getPrevCplDs() {
//		return prevCplDs;
//	}

	public State() {

	}

	public State(List<Integer> ds) {
		this.ds = ds;
		//this.complementaryDs = cplDs;

		if (this.prevDs == null) {
			this.prevDs = new ArrayList<Integer>();
			this.prevDs.addAll(ds);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(ds);
		}
//		if (this.prevCplDs == null) {
//			this.prevCplDs = new ArrayList<Integer>();
//			this.prevCplDs.addAll(cplDs);
//		} else {
//			this.prevCplDs.clear();
//			this.prevCplDs.addAll(cplDs);
//		}
	}

	public State(List<Integer> ds, List<Integer> prevDs) {
		this.ds = ds;
		//this.complementaryDs = cplDs;
		this.prevDs = prevDs;
		//this.prevCplDs = prevCplDs;
	}

	public List<Integer> getDs() {
		return ds;
	}

//	public List<Integer> getComplementaryDs() {
//		return this.complementaryDs;
//	}

	public Object clone() {
		State copy = new State();
		copy.setDs(this.ds);
		//copy.setComplementaryDs(this.complementaryDs);
		//copy.setPrevCplDs(this.prevCplDs);
		copy.setPrevDs(this.prevDs);

		return copy;
	}

}
