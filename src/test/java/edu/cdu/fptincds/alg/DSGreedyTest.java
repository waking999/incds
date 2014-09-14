package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class DSGreedyTest {
	private Logger log = LogUtil.getLogger(DSGreedyTest.class);

	@Ignore
	@Test
	public void testComputing_1() {

		String message = "DSGreedy testcase1";
		String inputFile = "src/test/resources/testcase1.csv";

		List<Integer> ds = testComputingWithDifferentData(message, inputFile);
		
		

	}
	
	
	@Test
	public void testComputing_2() {

		String message = "DSGreedy testcase2";
		String inputFile = "src/test/resources/testcase2.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Test
	public void testComputing_50() {

		String message = "DSGreedy testcase50";
		String inputFile = "src/test/resources/testcase50.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	
	@Test
	public void testComputing_100() {

		String message = "DSGreedy testcase100";
		String inputFile = "src/test/resources/100_0.3_testcase_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	@Test
	public void testComputing_400() {

		String message = "DSGreedy testcase400";
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
		String inputFile = "src/test/resources/600_testcase_20140525_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	
	
	
	@Ignore
	@Test
	public void testComputing_50_b() {

		String message = "DSGreedy testcase50b";
		String inputFile = "src/test/resources/20140523_testcase50_b.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	

	

//	@Ignore
//	@Test
//	public void testComputing_600() {
//
//		String message = "DSGreedy testcase600";
//		String inputFile = "src/test/resources/testcase600.csv";
//
//		testComputingWithDifferentData(message, inputFile);
//
//	}
	@Ignore
	@Test
	public void testComputing_600_a() {

		String message = "DSGreedy testcase600a";
		String inputFile = "src/test/resources/20140511_testcase600_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	@Ignore
	@Test
	public void testComputing_600_b() {

		String message = "DSGreedy testcase600b";
		String inputFile = "src/test/resources/20140511_testcase600_b.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Ignore
	@Test
	public void testComputing_1000_a() {
		String message = "DSGreedy testcase1000a";
		String inputFile = "src/test/resources/20140511_testcase1000_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}
	@Ignore
	@Test
	public void testComputing_1000_b() {
		String message = "DSGreedy testcase1000b";
		String inputFile = "src/test/resources/20140511_testcase1000_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Ignore
	@Test
	public void testComputing_400a() {

		String message = "DSGreedy testcase400a";
		String inputFile = "src/test/resources/20140510_testcase400_a.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Ignore
	@Test
	public void testComputing_400b() {

		String message = "DSGreedy testcase400b";
		String inputFile = "src/test/resources/20140510_testcase400_b.csv";

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

		DSGreedy ag = new DSGreedy(am);

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

	@Ignore
	@Test
	public void testComputing_400fpt() {

		testComputing_400a();

		testComputing_400b();

	}
}
