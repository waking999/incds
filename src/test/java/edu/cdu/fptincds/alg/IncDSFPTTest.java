package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class IncDSFPTTest {
	private Logger log = LogUtil.getLogger(IncDSFPTTest.class);

	 public static void main(String[] args) {
		 IncDSFPTTest afit=new IncDSFPTTest();
		 afit.testComputing_400();
	}
	@Test
	public void testComputing_400() {
		log.debug("IncDSFPTTest testcase400");
		// 1. take advantage of greedy to generate dominating set of graph 1;
		FileInfo fileInfo1 = new FileInfo();
		fileInfo1.setInputFile("src/test/resources/20140510_testcase400_a.csv");

		FileOperation fileOperation1 = new FileOperation();
		fileOperation1.setFileInfo(fileInfo1);
		fileOperation1.retriveAdjacencyInfo();

		List<String[]> am1 = fileOperation1.getAdjacencyMatrix();

		DSGreedy ag1 = new DSGreedy(am1);
		ag1.computing();

		List<Integer> ds1 = ag1.getDominatingSet();
		StringBuffer sb = new StringBuffer();
		sb.append("The dominating set for g1 by greedy ("+ds1.size()+"):" );
		for (Integer i : ds1) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		//2. take advanteage of fptincds to generate dominating set of graph 2, which is obtained from graph 1 via operations
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile("src/test/resources/20140510_testcase400_b.csv");

		FileOperation fileOperation2 = new FileOperation();
		fileOperation2.setFileInfo(fileInfo2);
		fileOperation2.retriveAdjacencyInfo();

		List<String[]> am2 = fileOperation2.getAdjacencyMatrix();

		IncDSFPT afi = new IncDSFPT(am1, am2, ds1);
		afi.computing();

		List<Integer> ds2 = afi.getDs2();
		

		sb.setLength(0);
		sb.append("The dominating set for g2 by incdsfpt("+ds2.size()+"):");
		for (Integer i : ds2) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		//2.1 compare the solutions by fptincds for g2 and greedy for g1
		List<Integer> ds2Ds1Diff = (List<Integer>) CollectionUtils.subtract(
				ds2, ds1);
		sb.setLength(0);
		sb.append("Difference between the dominating set for g2 by incdsfpt and for g1 by greedy("+ds2Ds1Diff.size()+"):");
		for (Integer i : ds2Ds1Diff) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		//3. take advantage of greedy to generate dominating set of graph 2
		DSGreedy ag2 = new DSGreedy(am2);

		ag2.computing();

		List<Integer> ds3 = ag2.getDominatingSet();
		

		sb.setLength(0);
		sb.append("The dominating set for g2 by greedy("+ds3.size()+"):");
		for (Integer i : ds3) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		//3.1 compare the solutions by greedy for g2 and greedy for g1
		List<Integer> ds3Ds1Diff = (List<Integer>) CollectionUtils.subtract(
				ds3, ds1);
		sb.setLength(0);
		sb.append("Difference between ds solution of g1 and g2("+ds3Ds1Diff.size()+"):");
		for (Integer i : ds3Ds1Diff) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
		
		//3.2 compare the solutions by fptincds for g2 and greedy for g2
		List<Integer> ds3Ds2Diff = (List<Integer>) CollectionUtils.subtract(
				ds3, ds2);
		sb.setLength(0);
		sb.append("Difference between ds solution for g2 by fptincds and for g2 by greedy("+ds3Ds2Diff.size()+"):");
		for (Integer i : ds3Ds2Diff) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}
}
