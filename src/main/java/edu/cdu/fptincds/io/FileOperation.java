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
public class FileOperation {
	private static final String COMMA = ",";
	private static final String BLANK = " ";

	/*
	 * the input file path and name
	 */
	private FileInfo fileInfo;

	/*
	 * the adjacency matrix shown in the input file
	 */
	private List<String[]> adjacencyMatrix;

	private int numOfVertices;

	public int getNumOfVertices() {
		return numOfVertices;
	}

	public int getK() {
		return k;
	}

	public int getR() {
		return r;
	}

	private int k;
	private int r;

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	private static String saveAgjacencyMatrixToFile(String filePath,
			String fileName, List<String[]> adjacencyMatrix) {

		int numOfVertices = adjacencyMatrix.size();

		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		String filePN = filePath + fileName;
		try {

			File csvFile = new File(filePN);
			out = new FileOutputStream(csvFile);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);

			bw.write(numOfVertices + "\r\n");
			for (int i = 0; i < numOfVertices; i++) {
				String[] row = adjacencyMatrix.get(i);
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < numOfVertices; j++) {
					sb.append(row[j]).append(COMMA);
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

		return filePN;
	}

	/**
	 * save the adjacency matrix to file
	 * 
	 * @param adjacencyMatrix
	 *            , adjacency matrix
	 */
	public static String saveAgjacencyMatrixToFile(
			List<String[]> adjacencyMatrix, float ratio) {
		int numOfVertices = adjacencyMatrix.size();
		String filePath = "src/test/resources/";
		String fileName = numOfVertices + "_" + ratio + "_testcase_"
				+ System.currentTimeMillis() + ".csv";

		return saveAgjacencyMatrixToFile(filePath, fileName, adjacencyMatrix);

	}

	public static void deleteFile(String fileName) {
		File file = new File(fileName);
		file.delete();
	}

	/**
	 * retrive graph info
	 */
	public void retriveProblemInfo() {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(
					this.fileInfo.getInputFile()));

			String line = null;

			// the first line is number of vertices,some parameters such as k,r
			line = reader.readLine();

			String[] firstLine = line.split(COMMA);
			int firstLineLen = firstLine.length;
			if (firstLineLen > 0 && firstLine[0] != null) {
				numOfVertices = Integer.parseInt(firstLine[0]);
			}

			if (firstLineLen > 1 && firstLine[1] != null) {
				k = Integer.parseInt(firstLine[1]);
			}

			if (firstLineLen > 2 && firstLine[2] != null) {
				r = Integer.parseInt(firstLine[2]);
			}
			// the following lines from the 2nd line are adjacency matrix
			adjacencyMatrix = new ArrayList<String[]>();

			while ((line = reader.readLine()) != null) {
				String item[] = line.split(COMMA);
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

	public List<String[]> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	/**
	 * 
	 * @param operationList
	 */
	public static void saveOperationToFile(List<String[]> operationList) {
		String fileName = "operation" + System.currentTimeMillis() + ".csv";

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
					sb.append(row[j]).append(BLANK);
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
