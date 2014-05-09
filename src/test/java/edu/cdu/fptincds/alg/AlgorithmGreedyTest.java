package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class AlgorithmGreedyTest {
	private Logger log = LogUtil.getLogger(AlgorithmGreedyTest.class);

	@Ignore
	@Test
	public void testComputing_1() {
		log.debug("AlgorithmGreedy testcase1");
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase1.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		// initilize algorithm
		AlgorithmGreedy ag = new AlgorithmGreedy(am);
		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		log.debug(ds.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

	}

	@Ignore
	@Test
	public void testComputing_400a() {
		log.debug("AlgorithmGreedy testcase400a");
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase400_a.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		AlgorithmGreedy ag = new AlgorithmGreedy(am);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		log.debug(ds.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

	}

	@Ignore
	@Test
	public void testComputing_400b() {
		log.debug("AlgorithmGreedy testcase400b");
		// read from file
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase400_b.csv");

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveAdjacencyInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();

		AlgorithmGreedy ag = new AlgorithmGreedy(am);

		ag.computing();

		List<Integer> ds = ag.getDominatingSet();
		log.debug(ds.size());

		StringBuffer sb = new StringBuffer();
		for (Integer i : ds) {
			sb.append(i).append(" ");
		}
		log.debug(sb);

	}

	@Test
	public void testComputing_400() {

		FileInfo fileInfo1 = new FileInfo();
		fileInfo1.setInputFile("src/test/resources/testcase400_a.csv");

		FileOperation fileOperation1 = new FileOperation();
		fileOperation1.setFileInfo(fileInfo1);
		fileOperation1.retriveAdjacencyInfo();

		List<String[]> am1 = fileOperation1.getAdjacencyMatrix();

		AlgorithmGreedy ag1 = new AlgorithmGreedy(am1);

		ag1.computing();

		List<Integer> ds1 = ag1.getDominatingSet();
		log.debug(ds1.size());

		// read from file
		FileInfo fileInfo2 = new FileInfo();
		fileInfo2.setInputFile("src/test/resources/testcase400_b.csv");

		FileOperation fileOperation2 = new FileOperation();
		fileOperation2.setFileInfo(fileInfo2);
		fileOperation2.retriveAdjacencyInfo();

		List<String[]> am2 = fileOperation2.getAdjacencyMatrix();

		AlgorithmGreedy ag2 = new AlgorithmGreedy(am2);

		ag2.computing();

		List<Integer> ds2 = ag2.getDominatingSet();
		log.debug(ds2.size());

		List<Integer> intsec = (List<Integer>) CollectionUtils.intersection(
				ds1, ds2);
		StringBuffer sb = new StringBuffer();
		for (Integer i : intsec) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}
}