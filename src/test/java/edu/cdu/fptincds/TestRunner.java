package edu.cdu.fptincds;

import org.apache.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import edu.cdu.fptincds.util.LogUtil;



/**
 * It is a entrance to run all available test classes
 * 
 * @author Kai Wang
 * 
 * 
 */
public class TestRunner {
	static Logger log = LogUtil.getLogger(TestRunner.class);

	/**
	 * the main method to run all available tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuit.class);
		// log the result
		for (Failure failure : result.getFailures()) {
			log.info(failure.toString());
		}
	}
}
