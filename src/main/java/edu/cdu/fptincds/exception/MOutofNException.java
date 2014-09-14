package edu.cdu.fptincds.exception;

public class MOutofNException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MOutofNException(){
		
	}
	public MOutofNException(String msg){
		super(msg);
	}
}
