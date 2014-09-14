package edu.cdu.fptincds.alg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;
import edu.uci.ics.jung.graph.Graph;

public class DSSum {
	private Logger log = LogUtil.getLogger(DSSum.class);

	private Map<Integer, String[]> map;

	public DSSum() {
		map = new HashMap<Integer, String[]>();

		 int v100 = 100;
		 String[] v100InputFiles = {
		 "src/test/resources/100_testcase_20140524_a.csv",
		 "src/test/resources/100_testcase_20140524_b.csv" };
		
		 map.put(v100, v100InputFiles);

		
		
//		 int v400 = 400;
//		 String[] v400InputFiles = {
//		 "src/test/resources/400_testcase_20140524_a.csv",
//		 "src/test/resources/400_testcase_20140524_b.csv" };
//		
//		 map.put(v400, v400InputFiles);
//
//		
//		 int v600 = 600; 
//		 String[] v600InputFiles = {
//		  "src/test/resources/600_testcase_20140525_a.csv",
//		  "src/test/resources/600_testcase_20140525_b.csv" };
//		 
//		  map.put(v600, v600InputFiles);
//		
//
//		int v800 = 800;
//		String[] v800InputFiles = {
//				"src/test/resources/800_0.15_testcase_a.csv",
//				"src/test/resources/800_0.15_testcase_b.csv" };
//
//		map.put(v800, v800InputFiles);
	}

	public void run() {
		Set<Integer> keySet = map.keySet();
		for (Integer key : keySet) {
			String[] vInputFiles = map.get(key);

			dsGreedy(vInputFiles[1]);
			//dsHillClimbing(vInputFiles[1]);
			incdsFpt(vInputFiles);
		}
	}

	private void dsGreedy(String inputFile) {
		FileOperation fileOperation = AlgorithmUtil.getProblemInfo(inputFile);

		List<String[]> am = fileOperation.getAdjacencyMatrix();
		Graph<Integer, Integer> g = AlgorithmUtil.prepareGraph(am);
		DSGreedy ag = new DSGreedy(g);
		long start = System.nanoTime();
		ag.computing();
		long end = System.nanoTime();
		List<Integer> ds = ag.getDominatingSet();
		int dsLen = ds.size();
		StringBuffer msg = new StringBuffer();
		msg.append("By Greedy, we find a DS solution of size ").append(dsLen)
				.append(" for a graph with size of ").append(am.size())
				.append(".");
		msg.append("It spends " + (end - start) + " ns.");

		log.debug(msg);
	}
	
//	private void dsHillClimbing(String inputFile){
//		
//		
//		//greedy get ds1
//		FileOperation fileOperation = AlgorithmUtil.getProblemInfo(inputFile);
//		List<String[]> am = fileOperation.getAdjacencyMatrix();
//
//		DSGreedy ag = new DSGreedy(am);
//		ag.computing();
//		List<Integer> ds = ag.getDominatingSet();
//		
//		//hill climbing get ds2
//		DSHillClimbing ahc= new DSHillClimbing(am,ds);
//		long start = System.nanoTime();
//		ahc.computing();
//		long end = System.nanoTime();
//		List<Integer> ds2=ahc.getDominatingSet();
//		
//		int ds2Len = ds2.size();
//		StringBuffer msg = new StringBuffer();
//		msg.append("By Hill Climbing, we find a DS solution of size ")
//				.append(ds2Len).append(" for a graph with size of ")
//				.append(am.size()).append(".");
//		msg.append("It spends " + (end - start) + " ns.");
//
//		log.debug(msg);
//		
//	}

	private void incdsFpt(String[] inputFiles) {

		// greedy to get ds1
		FileOperation fileOperation1 = AlgorithmUtil
				.getProblemInfo(inputFiles[0]);
		List<String[]> am1 = fileOperation1.getAdjacencyMatrix();
		DSGreedy ag1 = new DSGreedy(am1);
		ag1.computing();
		List<Integer> ds1 = ag1.getDominatingSet();

	
		// incdsfpt to get ds2
		FileOperation fileOperation2 = AlgorithmUtil
				.getProblemInfo(inputFiles[1]);
		List<String[]> am2 = fileOperation2.getAdjacencyMatrix();
		Graph<Integer, Integer> g2 = AlgorithmUtil.prepareGraph(am2);
		int r2 = fileOperation2.getR();

		IncDSFPT aIncDSFPT = new IncDSFPT(g2, ds1, r2);
		long start = System.nanoTime();
		try {
			aIncDSFPT.computing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.nanoTime();
		List<Integer> ds2 = aIncDSFPT.getDs2();

		int ds2Len = ds2.size();
		StringBuffer msg = new StringBuffer();
		msg.append("By IncDSFPT, we find a DS solution of size ")
				.append(ds2Len).append(" for a graph with size of ")
				.append(am2.size()).append(".");
		msg.append("It spends " + (end - start) + " ns.");

		log.debug(msg);

	}
	
	public void generateValidGraph(String message,int numOfVertices,float ratio){
		log.debug(message);
		// generate the graph 1 and calculate its dominating set by greedy
		List<String[]> am1 = AlgorithmUtil.generateRandGraph(numOfVertices,
				ratio);
		String fileName1 = FileOperation.saveAgjacencyMatrixToFile(am1,ratio);
		Graph<Integer, Integer> g1 = AlgorithmUtil.prepareGraph(am1);
		DSGreedy ag1 = new DSGreedy(g1);
		ag1.computing();
		List<Integer> ds1 = ag1.getDominatingSet();
		int ds1Len = ds1.size();
		//TestUtil.printResult(message, ds1);
		/*
		 * generate graph 2 according to g1 and calculate its dominating set by
		 * greedy
		 */
		int hammingDistance = ds1Len/3;

		HEdit hEdit = AlgorithmUtil.hEdit(am1, g1, ds1, hammingDistance);

		List<String[]> am2 = hEdit.getOutputAdjacencyMatrix();

		am1 = TestUtil.getAMFromFile(fileName1);

		boolean equals =  AlgorithmUtil.isAMEquals(am1, am2);
		log.debug(equals);
		DSGreedy ag2 = new DSGreedy(am2);
		ag2.computing();

		List<Integer> ds2 = ag2.getDominatingSet();
		int ds2Len = ds2.size();
		//TestUtil.printResult(message, ds2);
		// compare ds1 and ds2, if distance
		int dsDistance21 = ds2Len - ds1Len;
//		log.debug("hammingDistance=" + hammingDistance + ",dsDistance21="
//				+ dsDistance21);

		// calculate g2's ds by incds
		Graph<Integer, Integer> g2 = AlgorithmUtil.prepareGraph(am2);

		try {
			IncDSFPT aIncDSFPT = new IncDSFPT(g2, ds1, dsDistance21 - 1);
			aIncDSFPT.computing();
			List<Integer> ds3 = aIncDSFPT.getDs2();
			int ds3Len = ds3.size();
			//TestUtil.printResult(message, ds3);
			int dsDistance31 = ds3Len - ds1Len;
			log.debug("dsDistance31=" + dsDistance31);

			// if (ds3Len > 0 && ds3Len < ds2Len) {
			//
			// FileOperation.saveAgjacencyMatrixToFile(am2);
			//
			// }
			FileOperation.deleteFile(fileName1);
		} catch (Exception e) {

			FileOperation.saveAgjacencyMatrixToFile(am2,ratio);
		}
	}
}
