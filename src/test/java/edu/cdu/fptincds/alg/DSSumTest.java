package edu.cdu.fptincds.alg;

import org.junit.Ignore;
import org.junit.Test;

public class DSSumTest {
	// private Logger log = LogUtil.getLogger(DSSumTest.class);
	@Ignore
	@Test
	public void testRun() {
		DSSum dsSum = new DSSum();
		dsSum.run();
	}


	@Test
	public void sum() {
		String message = "sum 100";
		int numOfVertices = 100;
		float ratio = 0.3f;
		DSSum dsSum = new DSSum();
		dsSum.generateValidGraph(message, numOfVertices, ratio);

	}

	

}
