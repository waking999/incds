package edu.cdu.fptincds.alg;

import java.util.List;

import org.apache.log4j.Logger;

import edu.cdu.fptincds.io.FileInfo;
import edu.cdu.fptincds.io.FileOperation;
import edu.cdu.fptincds.util.LogUtil;

public class TestUtil {
	private static Logger log = LogUtil.getLogger(TestUtil.class);
	
	public static void printResult(String message,List<Integer> solution){
		log.debug(message);
		StringBuffer sb = new StringBuffer("(").append(solution.size()).append(
				"):");
		for (Integer i : solution) {
			sb.append(i).append(" ");
		}
		log.debug(sb);
	}
	
	public static List<String[]> getAMFromFile(String inputFile){
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile(inputFile);

		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();

		List<String[]> am = fileOperation.getAdjacencyMatrix();
		
		return am;
	}
}
