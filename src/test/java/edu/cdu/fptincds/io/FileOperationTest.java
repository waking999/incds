package edu.cdu.fptincds.io;



import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FileOperationTest {

	@Test 
	public void testRetriveProblemInfo(){
		FileInfo fileInfo = new FileInfo();
		fileInfo.setInputFile("src/test/resources/testcase1.csv");
		
		
		FileOperation fileOperation = new FileOperation();
		fileOperation.setFileInfo(fileInfo);
		fileOperation.retriveProblemInfo();
		List<String[]> am= fileOperation.getAdjacencyMatrix();
		int numOfVertices = am.size();
		
		Assert.assertEquals(6, numOfVertices);
		
		Assert.assertEquals("1", am.get(0)[1]);
		Assert.assertEquals("0", am.get(2)[1]);
	
	}
	
}
