package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class DomVCFPTTest {
	private Logger log = LogUtil.getLogger(DomVCFPTTest.class);

	@Test
	public void testComputing1() {
		String message = "DomVCFPTTest testcase1";
		String inputFile = "src/test/resources/testcase1.csv";

		testComputingWithDifferentData(message, inputFile);
	}

	@Test
	public void testComputing2() {
		String message = "DomVCFPTTest testcase2";
		String inputFile = "src/test/resources/testcase2.csv";
		

		testComputingWithDifferentData(message, inputFile);
	}
	
	

	@Ignore
	@Test
	public void testComputing100() {
		String message = "DomVCFPTTest testcase100";
		String inputFile = "src/test/resources/testcase100.csv";
		

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
		Graph<Integer, Integer> g = AlgorithmUtil.prepareGraph(am);
		int r = fileOperation.getR();

		// make use of greedy to get its vertex cover
		VCGreedy ag = new VCGreedy(g);
		ag.computing();

		List<Integer> vc = ag.getVertexCover();

		StringBuffer sb = new StringBuffer().append("vertex cover(")
				.append(vc.size()).append("):");
		for (Integer i : vc) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

		// compute to get the dominating set of the vertex cover
		g = AlgorithmUtil.prepareGraph(am);

		DomVCFPT ag1 = new DomVCFPT(g, vc, r);
		try {
			ag1.computing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<List<Integer>> possibleDomVCSet = ag1.getPossibleDomVCSets();
		for (List<Integer> domVC : possibleDomVCSet) {
			int domVCSize = domVC.size();

			sb.setLength(0);
			sb.append("dominating a vertex cover set(").append(domVCSize)
					.append("<=").append(r).append("):");
			for (Integer i : domVC) {
				sb.append(i).append(" ");
			}
			log.debug(sb);

			boolean isDomVc = AlgorithmUtil.isDomVCSet(g, domVC, vc);

			Assert.isTrue(isDomVc);
			Assert.isTrue(domVCSize <= r);
		}

	}
}
