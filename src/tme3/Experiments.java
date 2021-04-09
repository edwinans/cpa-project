package tme3;
import java.util.Map;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.GraphArray;
import graph.GraphList.Edge;

public class Experiments {
	public static void main(String[] args) {
		LabelPropagator lpa;
		GraphGenerator generator;
		GraphArray graph;
		final int V = 400; // nb of vertices
		float p = 0.5f, q = 0.04f;
		int partsNB = 4;
		generator = new GraphGenerator(V, p, q);
		
		generator.genRandomGraph(partsNB);
		graph = generator.getGraph();

		lpa = new LabelPropagator(graph);
		lpa.propagate();
		int labelsCount = lpa.countLabels();
		System.out.println("LABEL COUNTS : " + labelsCount);
		UndirectedSparseGraph<Integer, String> sparseGraph = new UndirectedSparseGraph<>();

		for (Edge e : generator.getGraphList().getEdges()) {
			sparseGraph.addEdge("e" + e.toString(), e.s, e.t);

		}
		
		Map<Integer,Double> coefMap = Metrics.clusteringCoefficients(sparseGraph);
		System.out.println(coefMap);
	}
}
