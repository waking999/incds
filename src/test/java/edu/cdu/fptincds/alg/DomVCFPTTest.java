package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class DomVCFPTTest {
	private Logger log = LogUtil.getLogger(DomVCFPTTest.class);
	@Ignore
	@Test
	public void testComputing() {
		log.debug("DomVCFPTTest testcase1");
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase1.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();
		Graph<Integer,Integer> g = AlgorithmUtil.prepareGraph(am);
		
		// initilize algorithm
		VCGreedy ag = new VCGreedy(g);
		ag.computing();
		

		List<Integer> vc = ag.getVertexCover();
		log.debug(vc.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : vc) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
		
		g = AlgorithmUtil.prepareGraph(am);
		DomVCFPT ag1=new DomVCFPT(g,vc);
		ag1.computing();
		
		List<Integer> domVC= ag1.getDominatingVertexCoverSet();
	
		
		log.debug(domVC.size());

		sb.setLength(0);
		for (Integer i : domVC) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}
}
