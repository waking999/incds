package edu.cdu.fptincds.alg;

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
	public void testHEdit() {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase400_a.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		Graph<Integer, Integer> g = AlgorithmUtil.prepareGraph(am);

		AlgorithmGreedy ag = new AlgorithmGreedy(am);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		int dsSize = ds.size();

		StringBuffer sb = new StringBuffer("g1's ds(").append(dsSize).append(
				"):");
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		int k = dsSize / 2;

		HEdit hEdit = AlgorithmUtil.hEdit(am, g, ds, k);

		List<String[]> am2 = hEdit.getOutputAdjacencyMatrix();

		AlgorithmGreedy ag2 = new AlgorithmGreedy(am2);

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
