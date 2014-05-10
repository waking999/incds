package edu.cdu.fptincds.alg;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

/**
 * This test class works for test of Algorithm in Hill Climbing idea
 * 
 * @author : Kai Wang
 * 
 */
public class DSHillClimbingGreedyTest {

	private Logger log = LogUtil.getLogger(DSHillClimbingGreedyTest.class);

	@Ignore
	@Test
	/**
	 * test with a already existing test case of a graph of 400 vertices
	 */
	public void takeUseOfGeneratedBigRandGraphToCompute_400() {

		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/20140510_testcase400_a.csv");

		testComputingWithDifferentData(fileInfo);
	}

	@Test
	/**
	 * test with a already existing test case of a graph of 600 vertices
	 */
	public void takeUseOfGeneratedBigRandGraphToCompute_600() {

		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase600.csv");

		testComputingWithDifferentData(fileInfo);
	}

	private void testComputingWithDifferentData(FileInfo fileInfo) {
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		DSHillClimbingGreedy ah = new DSHillClimbingGreedy(am);

		ah.computing();

		Set<List<Integer>> dsSet = ah.getDominatingSetSet();

		for (List<Integer> ds : dsSet) {

			StringBuffer sb = new StringBuffer(ds.size() + ":");
			for (Integer i : ds) {
				sb.append(i).append(" ");
			}
			log.debug(sb);
		}
	}

}
