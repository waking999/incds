package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class DSGreedy2Test {
	private Logger log = LogUtil.getLogger(DSGreedy2Test.class);
	
	@Test
	public void testComputing_2() {

		String message = "DSGreedy2 testcase1";
		String inputFile = "src/test/resources/testcase2.csv";

		List<Integer> ds = testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_50() {

		String message = "DSGreedy2 testcase50";
		String inputFile = "src/test/resources/testcase50.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_100() {

		String message = "DSGreedy2 testcase100";
		String inputFile = "src/test/resources/100_0.3_testcase_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_400() {

		String message = "DSGreedy2 testcase400";
		String inputFile = "src/test/resources/400_testcase_20140524_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_600() {

		String message = "DSGreedy2 testcase600";
		String inputFile = "src/test/resources/600_testcase_20140525_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_800() {

		String message = "DSGreedy2 testcase600";
		String inputFile = "src/test/resources/800_testcase_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	private List<Integer> testComputingWithDifferentData(String message,
			String inputFile) {

		log.debug(message);
		// read adjacency matrix from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(inputFile);

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

		int numOfV = fileOperation.getNumOfVertices();
		log.debug("number of Vertices:"+numOfV);
		List<String[]> am = fileOperation.getAdjacencyMatrix();

		DSGreedy2 ag = new DSGreedy2(am);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		log.debug("number of Vertices in dominating set:"+ds.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		//log.debug(sb);

		return ds;
	}
}
