package edu.cdu.fptincds.view;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * A class that shows the minimal work necessary to load and visualize a graph.
 */
public class GraphView 
{

//    public static void main(String[] args) throws IOException 
//    {
//       
//        Graph<Integer,Integer> g = getGraph();
//        presentGraph(jf, g);
//    }
//    /**
//     * Generates a graph: in this case, reads it from the file
//     * "samples/datasetsgraph/simple.net"
//     * @return A sample undirected graph
//     */
//    public static Graph<Integer,Integer> getGraph() throws IOException 
//    {
//    	FileInfo fileInfo = new FileInfo();
//		fileInfo.setInputFile("src/test/resources/testcase400.csv");
//
//		FileOperation fileOperation = new FileOperation();
//		fileOperation.setFileInfo(fileInfo);
//		fileOperation.retriveAdjacencyInfo();
//
//		List<String[]> am = fileOperation.getAdjacencyMatrix();
//		
//		Graph<Integer,Integer> g = AlgorithmUtil.prepareGraph(am);
//		return g;
//    }

	public static void presentGraph(Graph<Integer, Integer> g) {
		JFrame jf = new JFrame();
		VisualizationViewer<Integer,Integer> vv = new VisualizationViewer<Integer,Integer>(new FRLayout<Integer,Integer>(g));
        jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
	}
}
