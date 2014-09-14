package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class IncDSFPTTest {
	private Logger log = LogUtil.getLogger(IncDSFPTTest.class);
	@Test
	public void testComputing_100() {
		String message = "Graph:vertices:100;incdsfpt";
		String inputFile1 = "src/test/resources/100_0.3_testcase_a.csv";
		String inputFile2 = "src/test/resources/100_0.3_testcase_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}
	@Ignore
	@Test
	public void testComputing_400() {
		String message = "Graph:vertices:400;incdsfpt";
		String inputFile1 = "src/test/resources/400_testcase_20140524_a.csv";
		String inputFile2 = "src/test/resources/400_testcase_20140524_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}

	
	@Ignore
	@Test
	public void testComputing600() {
		String message = "Graph:vertices:600;incdsfpt";
		String inputFile1 = "src/test/resources/600_testcase_20140525_a.csv";
		String inputFile2 = "src/test/resources/600_testcase_20140525_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}
	
	@Ignore
	@Test
	public void testComputing800() {
		String message = "Graph:vertices:800;incdsfpt";
		String inputFile1 = "src/test/resources/800_testcase_a.csv";
		String inputFile2 = "src/test/resources/800_testcase_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}

	@Ignore
	@Test
	public void testComputing1() {
		String message = "Graph:vertices:7;incdsfpt";
		String inputFile = "src/test/resources/testcase1.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Ignore
	@Test
	public void testComputing2() {
		String message = "Graph:vertices:8;incdsfpt";
		String inputFile = "src/test/resources/testcase2.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	@Ignore
	@Test
	public void testComputing50() {
		String message = "Graph:vertices:50;greedy-incdsfpt";
		String inputFile1 = "src/test/resources/20140523_testcase50_a.csv";
		String inputFile2 = "src/test/resources/20140523_testcase50_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}

	@Ignore
	@Test
	public void testComputing100_2() {
		String message = "Graph:vertices:100;greedy-incdsfpt";
		String inputFile1 = "src/test/resources/20140524_testcase100_a.csv";
		String inputFile2 = "src/test/resources/20140524_testcase100_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}

	@Ignore
	@Test
	public void testComputing50_b_1() {
		String message = "Graph:vertices:50;incdsfpt";
		String inputFile2 = "src/test/resources/20140523_testcase50_b.csv";

		testComputingWithDifferentData(message, inputFile2);

	}

	@Ignore
	@Test
	public void testComputing100() {
		String message = "Graph:vertices:100;incdsfpt";
		String inputFile = "src/test/resources/testcase100.csv";

		testComputingWithDifferentData(message, inputFile);

	}

	// @Ignore
	// @Test
	// public void testComputing400() {
	// String message = "Graph:vertices:400;incdsfpt";
	// String inputFile1 = "src/test/resources/20140510_testcase400_a.csv";
	// String inputFile2 = "src/test/resources/20140510_testcase400_b.csv";
	//
	// testComputingWithDifferentData(message, inputFile1,inputFile2);
	//
	// }


	


	@Ignore
	@Test
	public void testComputing1000() {
		String message = "Graph:vertices:1000;incdsfpt";
		String inputFile1 = "src/test/resources/20140511_testcase1000_a.csv";
		String inputFile2 = "src/test/resources/20140511_testcase1000_b.csv";

		testComputingWithDifferentData(message, inputFile1, inputFile2);

	}

//	@Ignore
//	@Test
//	public void testComputing800() {
//		String message = "Graph:vertices:800;incdsfpt";
//		String inputFile1 = "src/test/resources/800_0.15_testcase_a.csv";
//		String inputFile2 = "src/test/resources/800_0.15_testcase_b.csv";
//
//		testComputingWithDifferentData(message, inputFile1, inputFile2);
//
//	}

	private void testComputingWithDifferentData(String message,
			String inputFile1, String inputFile2) {
		log.debug(message);

		// greedy to get ds1
		FileOperation fileOperation1 = getProblemInfo(inputFile1);
		int numOfV = fileOperation1.getNumOfVertices();
		log.debug("number of Vertices:"+numOfV);
		List<String[]> am1 = fileOperation1.getAdjacencyMatrix();

		DSGreedy ag1 = new DSGreedy(am1);
		ag1.computing();
		List<Integer> ds1 = ag1.getDominatingSet();
		//TestUtil.printResult(message, ds1);

		// greedy to get ds2
		
		FileOperation fileOperation2 = getProblemInfo(inputFile2);
		List<String[]> am2 = fileOperation2.getAdjacencyMatrix();
//		boolean eq = isAMEquals(am1, am2);
//		log.debug(eq);
		int k=differenceBetweenG1AndG2(am1,am2);
		log.debug("k:" + k);
		DSGreedy ag2 = new DSGreedy(am2);
		ag2.computing();
		List<Integer> ds2 = ag2.getDominatingSet();
		//TestUtil.printResult(message, ds2);

		// incdsfpt to get ds2
		Graph<Integer, Integer> g2 = AlgorithmUtil.prepareGraph(am2);
		int r2 = fileOperation2.getR();

		log.debug("r:" + r2);
		IncDSFPT aIncDSFPT = new IncDSFPT(g2, ds1, r2);
		try {
			aIncDSFPT.computing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> ds3 = aIncDSFPT.getDs2();
		log.debug("number of Vertices in dominating set:"+ds3.size());

		//TestUtil.printResult(message, ds3);
	}

	private void testComputingWithDifferentData(String message, String inputFile) {
		log.debug(message);
		FileOperation fileOperation = getProblemInfo(inputFile);
		List<String[]> am = fileOperation.getAdjacencyMatrix();
		int r = fileOperation.getR();
		log.debug("r:" + r);
		Graph<Integer, Integer> g2 = AlgorithmUtil.prepareGraph(am);
		/*
		 * because g is a complete graph, ds1 only contain one vertex(any one
		 * from 0 to n-1). in this case, it is the vertex with the highest
		 * degree in g2
		 */
		List<VertexDegree> vertexDegreeList = AlgorithmUtil
				.sortVertexAccordingToDegree(g2);
		List<Integer> ds1 = new ArrayList<Integer>();
		ds1.add(vertexDegreeList.get(0).getVertex());

		// call computing
		IncDSFPT aIncDSFPT = new IncDSFPT(g2, ds1, r);
		try {
			aIncDSFPT.computing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> ds2 = aIncDSFPT.getDs2();

		StringBuffer sb = new StringBuffer()
				.append("a dominating set by incdsfpt (").append(ds2.size())
				.append("<=").append(r + ds1.size()).append("):");
		log.debug(sb);
		for (Integer i : ds2) {
			sb.append(i).append(" ");
		}
		// log.debug(sb);
	}

	private FileOperation getProblemInfo(String inputFile) {

		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile(inputFile);
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo2);
		fileOperation.retriveProblemInfo();

		return fileOperation;
	}

	private boolean isAMEquals(List<String[]> am1, List<String[]> am2) {

		int n = am1.size();
		for (int i = 0; i < n; i++) {

			String[] am1row = am1.get(i);
			String[] am2row = am2.get(i);
			boolean tempEq = Arrays.equals(am1row, am2row);
			if (!tempEq) {
				return false;
			}

		}
		return true;
	}

	private int differenceBetweenG1AndG2(List<String[]> am1, List<String[]> am2) {

		int n = am1.size();
		int count = 0;
		for (int i = 0; i < n; i++) {

			String[] am1row = am1.get(i);
			String[] am2row = am2.get(i);
			count += arrayXor(am1row, am2row, n);
		}

		return count / 2;
	}

	private int arrayXor(String[] a1, String[] a2, int len) {

		int count = 0;

		for (int i = 0; i < len; i++) {
			byte a1i = Byte.parseByte(a1[i]);
			byte a2i = Byte.parseByte(a2[i]);
			if ((a1i ^ a2i) == 1) {
				count++;
			}
		}

		return count;

	}

}
