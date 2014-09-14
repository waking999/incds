package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class VCGreedyTest {
	private Logger log = LogUtil.getLogger(VCGreedyTest.class);
	@Ignore
	@Test
	public void testComputing1(){
		String message = "VCGreedy testcase1";
		String inputFile = "src/test/resources/testcase1.csv";
		
		testComputingWithDifferentData(message, inputFile);
	}
	@Ignore
	@Test
	public void testComputing2(){
		String message = "VCGreedy testcase2";
		String inputFile = "src/test/resources/testcase2.csv";
		
		testComputingWithDifferentData(message, inputFile);
	}
	
	private void testComputingWithDifferentData(String message, String inputFile) {
		log.debug(message);
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(inputFile);

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

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
