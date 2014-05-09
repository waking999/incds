package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class AlgorithmFPTIncDSTest {
	private Logger log = LogUtil.getLogger(AlgorithmFPTIncDSTest.class);

	@Test
	public void testComputing_400() {
		log.debug("AlgorithmFPTIncDSTest testcase400");
		// 1. take advantage of greedy to generate dominating set of graph 1;
		FileInfo fileInfo1 = new FileInfo();
		fileInfo1.setInputFile("src/test/resources/testcase400_a.csv");

		FileOperation fileOperation1 = new FileOperation();
		fileOperation1.setFileInfo(fileInfo1);
		fileOperation1.retriveAdjacencyInfo();

		List<String[]> am1 = fileOperation1.getAdjacencyMatrix();

		AlgorithmGreedy ag1 = new AlgorithmGreedy(am1);
		ag1.computing();

		List<Integer> ds1 = ag1.getDominatingSet();
		StringBuffer sb1 = new StringBuffer();
		for (Integer i : ds1) {
			sb1.append(i).append(" ");
		}
		log.debug(sb1);

		//2. take advanteage of fptincds to generate dominating set of graph 2, which is obtained from graph 1 via operations
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile("src/test/resources/testcase400_b.csv");

		FileOperation fileOperation2 = new FileOperation();
		fileOperation2.setFileInfo(fileInfo2);
		fileOperation2.retriveAdjacencyInfo();

		List<String[]> am2 = fileOperation2.getAdjacencyMatrix();

		AlgorithmFPTIncDS afi = new AlgorithmFPTIncDS(am1, am2, ds1);
		afi.computing();

		List<Integer> ds2 = afi.getDs2();
		log.debug(ds2.size());

		StringBuffer sb2 = new StringBuffer();
		for (Integer i : ds2) {
			sb2.append(i).append(" ");
		}
		log.debug(sb2);

		//2.1 compare the solutions by fptincds for g2 and greedy for g1
		List<Integer> ds2Ds1Diff = (List<Integer>) CollectionUtils.subtract(
				ds2, ds1);
		sb2.setLength(0);
		for (Integer i : ds2Ds1Diff) {
			sb2.append(i).append(" ");
		}
		log.debug(sb2);

		//3. take advantage of greedy to generate dominating set of graph 2
		AlgorithmGreedy ag2 = new AlgorithmGreedy(am2);

		ag2.computing();

		List<Integer> ds3 = ag2.getDominatingSet();
		log.debug(ds3.size());

		StringBuffer sb3 = new StringBuffer();
		for (Integer i : ds3) {
			sb3.append(i).append(" ");
		}
		log.debug(sb3);

		//3.1 compare the solutions by greedy for g2 and greedy for g1
		List<Integer> ds3Ds1Diff = (List<Integer>) CollectionUtils.subtract(
				ds3, ds1);
		sb3.setLength(0);
		for (Integer i : ds3Ds1Diff) {
			sb3.append(i).append(" ");
		}
		log.debug(sb3);
		
		//3.2 compare the solutions by fptincds for g2 and greedy for g2
		List<Integer> ds3Ds2Diff = (List<Integer>) CollectionUtils.subtract(
				ds3, ds2);
		sb3.setLength(0);
		for (Integer i : ds3Ds2Diff) {
			sb3.append(i).append(" ");
		}
		log.debug(sb3);
	}
}
