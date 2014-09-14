package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class AlgorithmUtilTest {
	private Logger log = LogUtil.getLogger(AlgorithmUtilTest.class);

	@Ignore
	@Test
	public void testIsDS() {
		String message = "test isDs";
		String inputFile = "src/test/resources/testcase1.csv";

		log.debug(message);
		// read adjacency matrix from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(inputFile);

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();
		Graph<Integer, Integer> g = AlgorithmUtil.prepareGraph(am);

		DSGreedy ag = new DSGreedy(g);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();

		boolean isDS = AlgorithmUtil.isDS(g, ds);

		Assert.isTrue(isDS);

		ds.remove(0);

		isDS = AlgorithmUtil.isDS(g, ds);
		Assert.isTrue(!isDS);
	}

	@Ignore
	@Test
	public void testAddElementToList() {
		List<Integer> list = new ArrayList<Integer>();

		AlgorithmUtil.addElementToList(list, new Integer(1));
		AlgorithmUtil.addElementToList(list, new Integer(1));
		AlgorithmUtil.addElementToList(list, new Integer(2));

		StringBuffer sb = new StringBuffer();
		for (Integer i : list) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}

	@Ignore
	@Test
	public void testGenerateRandGraph_100_03() {
		String message = "Graph:100 vertices;radio:0.3";
		int numOfVertex = 100;
		float radio = 0.3f;

		testGenerateRandomGraphWithDifferentData(message, numOfVertex, radio);
	}
	@Ignore
	@Test
	public void testGenerateRandGraph_800_03() {
		String message = "Graph:800 vertices;radio:0.3";
		int numOfVertex = 800;
		float radio = 0.3f;

		testGenerateRandomGraphWithDifferentData(message, numOfVertex, radio);
	}

	@Ignore
	@Test
	public void testArrayEquals() {
		byte[] a1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };
		byte[] a2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };

		Assert.isTrue(Arrays.equals(a1, a2));
	}

	private void testGenerateRandomGraphWithDifferentData(String message,
			int numOfVertex, float radio) {
		log.debug(message);
		List<String[]> adjacencyMatrix = AlgorithmUtil.generateRandGraph(
				numOfVertex, radio);
		DSGreedy ag = new DSGreedy(adjacencyMatrix);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();

		StringBuffer sb = new StringBuffer().append("ds(").append(ds.size())
				.append("):");
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		FileOperation.saveAgjacencyMatrixToFile(adjacencyMatrix,1);
	}

	@Ignore
	@Test
	public void testGenerateRandGraph_50_01() {
		String message = "Graph:50 vertices;radio:0.1";
		int numOfVertex = 50;
		float radio = 0.1f;

		testGenerateRandomGraphWithDifferentData(message, numOfVertex, radio);
	}

	@Ignore
	@Test
	public void testGenerateRandGraph_50_03() {
		String message = "Graph:50 vertices;radio:0.3";
		int numOfVertex = 50;
		float radio = 0.1f;

		testGenerateRandomGraphWithDifferentData(message, numOfVertex, radio);
	}

	@Ignore
	@Test
	public void testHEdit_400() {
		String graph1InputFile = "src/test/resources/20140510_testcase400_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}
@Ignore
	@Test
	public void testHEdit_100() {
		String graph1InputFile = "src/test/resources/20140524_testcase100_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}

	@Ignore
	@Test
	public void testHEdit_50() {
		String graph1InputFile = "src/test/resources/20140523_testcase50_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}

	@Ignore
	@Test
	public void testHEdit_600() {

		String graph1InputFile = "src/test/resources/20140511_testcase600_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}
	@Ignore
	@Test
	public void testHEdit_800() {

		String graph1InputFile = "src/test/resources/20140524_testcase800_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}

	@Ignore
	@Test
	public void testHEdit_1000() {
		

		String graph1InputFile = "src/test/resources/20140511_testcase1000_a.csv";

		testHEditWithDifferentData(graph1InputFile);
	}

	@Ignore
	@Test
	public void testGetDecFromBinArray() {
		byte[] bin1 = { 1, 0, 1, 1 };
		double dec1 = AlgorithmUtil.getDecFromBinArray(bin1);
		Assert.isTrue(Math.abs(dec1 - 13) < 0.001);
		byte[] bin2 = { 0, 0, 1, 0, 1, 1, 0 };
		double dec2 = AlgorithmUtil.getDecFromBinArray(bin2);
		Assert.isTrue(Math.abs(dec2 - 52) < 0.001);

	}

	@Ignore
	@Test
	public void testArrayOr() {
		byte[] bin1 = { 1, 0, 1, 1 };
		byte[] bin2 = { 1, 1, 0, 1 };
		bin1 = AlgorithmUtil.arrayOr(bin1, bin2);
		for (int i = 0; i < bin1.length; i++) {
			System.out.print(bin1[i] + " ");
		}

		byte[] bin3 = { 1, 0, 0, 1 };
		byte[] bin4 = { 1, 1, 0, 1 };
		bin1 = AlgorithmUtil.arrayOr(bin3, bin4);
		for (int i = 0; i < bin1.length; i++) {
			System.out.print(bin1[i] + " ");
		}

		byte[] bin5 = { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0,
				0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };
		for (int i = 0; i < bin5.length; i++) {
			System.out.print(bin5[i] + " ");
		}
		System.out.println();
		byte[] bin6 = { 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0,
				0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1,
				0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 };
		double bin5Num = AlgorithmUtil.getDecFromBinArray(bin5);
		double bin6Num = AlgorithmUtil.getDecFromBinArray(bin5);
		for (int i = 0; i < bin6.length; i++) {
			System.out.print(bin6[i] + " ");
		}
		System.out.println();

		bin5 = AlgorithmUtil.arrayOr(bin5, bin6);
		for (int i = 0; i < bin5.length; i++) {
			System.out.print(bin5[i] + " ");
		}
		System.out.println();
		double bin5Num1 = AlgorithmUtil.getDecFromBinArray(bin5);
		log.debug(bin5Num);
		log.debug(bin6Num);
		log.debug(bin5Num1);
	}

	
	@Test
	public void differenceOfTwoGraph_400(){
		String msg="400";
		String graph1InputFile = "src/test/resources/400_testcase_20140524_a.csv";
		String graph2InputFile = "src/test/resources/400_testcase_20140524_b.csv";
		
		log.debug(msg);
		int k =  getDifferentEdgeNumber(graph1InputFile, graph2InputFile);
		log.debug(k);
	}

	@Test
	public void differenceOfTwoGraph_600(){
		String msg="600";
		String graph1InputFile = "src/test/resources/600_testcase_20140525_a.csv";
		String graph2InputFile = "src/test/resources/600_testcase_20140525_b.csv";
		
		log.debug(msg);
		int k =  getDifferentEdgeNumber(graph1InputFile, graph2InputFile);
		log.debug(k);
	}
	
	@Test
	public void differenceOfTwoGraph_800(){
		String msg="800";
		String graph1InputFile = "src/test/resources/800_0.15_testcase_a.csv";
		String graph2InputFile = "src/test/resources/800_0.15_testcase_b.csv";
		
		log.debug(msg);
		int k =  getDifferentEdgeNumber(graph1InputFile, graph2InputFile);
		log.debug(k);
	}
	
	private int getDifferentEdgeNumber(String graph1InputFile,
			String graph2InputFile) {
		
		FileOperation fileOperation1=AlgorithmUtil.getProblemInfo(graph1InputFile);
		List<String[]> am1= fileOperation1.getAdjacencyMatrix();
		
		FileOperation fileOperation2=AlgorithmUtil.getProblemInfo(graph2InputFile);
		List<String[]> am2= fileOperation2.getAdjacencyMatrix();
		
		int k=0;
		int amLen = am1.size();
		for(int i=0;i<amLen;i++){
			String[] row1=am1.get(i);
			String[] row2=am2.get(i);
			for(int j=0;j<i;j++){
				String cell1=row1[j];
				String cell2=row2[j];
				if(!cell1.equals(cell2)){
					k++;
				}
			}
		}
		return k;
	}
	// @Test
	// public void testNChooseM(){
	// List<String> elements=new ArrayList<String>();
	// elements.add("1");
	// elements.add("2");
	// elements.add("3");
	// elements.add("4");
	// elements.add("5");
	// elements.add("6");
	//
	// int n=elements.size();
	// int m = 3;
	//
	// List<byte[]> results= AlgorithmUtil.nChooseM(n, m);
	// int resultsSize = results.size();
	// System.out.println("----------"+resultsSize);
	//
	// for(byte[] ruler:results){
	// for(int i=0;i<n;i++){
	// if(ruler[i]==AlgorithmUtil.CHOSEN){
	// System.out.print(elements.get(i));
	// }
	// }
	// System.out.println();
	// }
	//
	// }

	private void testHEditWithDifferentData(String graph1InputFile) {

		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(graph1InputFile);

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		Graph<Integer, Integer> g = AlgorithmUtil.prepareGraph(am);

		DSGreedy ag = new DSGreedy(am);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		int dsSize = ds.size();

		StringBuffer sb = new StringBuffer("g1's ds(").append(dsSize).append(
				"):");
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		int r = dsSize / 2;

		HEdit hEdit = AlgorithmUtil.hEdit(am, g, ds, r);

		List<String[]> am2 = hEdit.getOutputAdjacencyMatrix();

		DSGreedy ag2 = new DSGreedy(am2);

		ag2.computing();

		List<Integer> ds2 = ag2.getDominatingSet();
		int dsSize2 = ds2.size();

		StringBuffer sb2 = new StringBuffer("g2's ds(").append(dsSize2).append(
				"):");
		for (Integer i : ds2) {
			sb2.append(i).append(" ");
		}
		log.debug(sb2);

		List<String[]> operationList = hEdit.getOperationList();

		FileOperation.saveAgjacencyMatrixToFile(am2,1);
		FileOperation.saveOperationToFile(operationList);
	}
}
