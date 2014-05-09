package edu.cdu.fptincds.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class work for getting input from files
 * 
 * @author : Kai
 * 
 */
public class FileOperation  {
	private static final String DEST_FILE_SEPARATION = " ";
	
	public static String getDestFileSeparation() {
		return DEST_FILE_SEPARATION;
	}


	


	
	/*
	 * the input file path and name
	 */
	private FileInfo fileInfo;
	
	/*
	 * the adjacency matrix shown in the input file
	 */
	private List<String[]> adjacencyMatrix;
	

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	/**
	 * save the adjacency matrix to file
	 * 
	 * @param adjacencyMatrix
	 *            , adjacency matrix
	 */
	public static void saveAgjacencyMatrixToFile(List<String[]> adjacencyMatrix) {
		String fileName = "testcase"+System.currentTimeMillis() + ".csv";
		
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		
		
		try {
			
			File csvFile = new File(fileName);
			out = new FileOutputStream(csvFile);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);

			int numOfVertices = adjacencyMatrix.size();
			bw.write(numOfVertices + "\r\n");
			for (int i = 0; i < numOfVertices; i++) {
				String[] row = adjacencyMatrix.get(i);
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < numOfVertices; j++) {
					sb.append(row[j]).append(",");
				}
				bw.write(sb.subSequence(0, sb.length() - 1) + "\r\n");

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				bw.close();
				osw.close();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cdu.fpt.io.IInput#getAdjacencyInfo()
	 */
	public void retriveAdjacencyInfo() {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.fileInfo.getInputFile()));

			String line = null;

			// the first line is number of vertex
			line = reader.readLine();
			// the following lines from the 2nd line are adjacency matrix
			adjacencyMatrix = new ArrayList<String[]>();

			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");
				adjacencyMatrix.add(item);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
	

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cdu.fpt.io.IInput#getAdjacencyMatrix()
	 */
	public List<String[]> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public static void saveOperationToFile(List<String[]> operationList) {
		String fileName = "operation"+System.currentTimeMillis() + ".csv";
		
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		
		
		try {
			
			File csvFile = new File(fileName);
			out = new FileOutputStream(csvFile);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);

			int operationListSize = operationList.size();
			for (int i = 0; i < operationListSize; i++) {
				String[] row = operationList.get(i);
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < 3; j++) {
					sb.append(row[j]).append(" ");
				}
				bw.write(sb.append("\r\n").toString());

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				bw.close();
				osw.close();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}


}
