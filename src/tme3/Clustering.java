package tme3;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.*;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.GraphArray;
import graph.GraphList;
import graph.GraphList.Edge;

/**
 * EdgeBetweennessClusterer:\ Running time is: O(kmn) where k is the number of
 * edges to remove, m is the total number of edges, and n is the total number of
 * vertices. For very sparse graphs the running time is closer to O(kn^2) and
 * for graphs with strong community structure, the complexity is even lower.
 * Girvan�Newman algorithm
 * 
 *
 */
public class Clustering {
	public static void main(String[] args) {

		GraphGenerator generator;
		GraphList graphList;
		final int V = 100; // nb of vertices
		float p = 0.1f, q = 0.01f;
		int ratio = (int) (p / q);
		int partsNB = 4;
		final int K = 10; // nb of edges to remove for edge-betweenness-clusterer
		generator = new GraphGenerator(V, p, q);

		generator.genRandomGraph(partsNB);
		graphList = generator.getGraphList();

		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		EdgeBetweennessClusterer<Integer, String> ebc = new EdgeBetweennessClusterer<>(K);
		BicomponentClusterer<Integer, String> bc = new BicomponentClusterer<>();

		for (Edge e : graphList.getEdges()) {
			graph.addEdge("e" + e.toString(), e.s, e.t);

		}

		// To use Bicomponent Clusterer
		// System.out.println(graph);
		// Set<Set<Integer>> set2 = bc.transform(graph);



		System.out.printf("NB of Nodes : %d, NB of Edges : %d, NB of Actual communities : %d\n", V,
				generator.getGraphList().getNumberOfEdges(), partsNB);
		System.out.printf("Connection Probability of (same)/(not same) community nodes : (%.5f)/(%.5f) = (%d)\n", p, q,
				ratio);
		
		System.out.println("Running EdgeBetweennessClusterer...");
		Set<Set<Integer>> set = ebc.transform(graph);
		
		System.out.printf("...NB of communities found using Girvan-Newman algorithm\n(" + "*removing %d edges) : %d\n", K,
				set.size());
	}
}
