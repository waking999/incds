package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class VCGreedyTest {
	private Logger log = LogUtil.getLogger(VCGreedyTest.class);
	@Test
	public void testComputing(){
		log.debug("VCGreedy testcase1");
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase1.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		// initilize algorithm
		VCGreedy ag = new VCGreedy(am);
		ag.computing();

		List<Integer> vc = ag.getVertexCover();
		log.debug(vc.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : vc) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}
}
