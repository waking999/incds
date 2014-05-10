package edu.cdu.fptincds.alg;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class AlgorithmUtilTest {
	private Logger log = LogUtil.getLogger(AlgorithmUtilTest.class);

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

	
	@Test
	/**
	 * test for generate random graph
	 */
	public void testGenerateRandGraph() {
		int numOfVertex = 1000;

		List<String[]> adjacencyMatrix = AlgorithmUtil.generateRandGraph(numOfVertex);

		
		FileOperation.saveAgjacencyMatrixToFile(adjacencyMatrix); 
	}
	@Ignore
	@Test
	public void testHEdit_400() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/20140510_testcase400_a.csv");

		testHEditWithDifferentData(fileInfo);
	}
	@Ignore
	@Test
	public void testHEdit_600() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/20140511_testcase600_a.csv");

		testHEditWithDifferentData(fileInfo);
	}

	private void testHEditWithDifferentData(FileInfo fileInfo) {
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

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

		FileOperation.saveAgjacencyMatrixToFile(am2);
		FileOperation.saveOperationToFile(operationList);
	}
}
