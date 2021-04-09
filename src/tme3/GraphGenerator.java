package tme3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import graph.GraphArray;
import graph.GraphList;

/**
 * 
 * Generator of random benchmarks to test community detection alogrithms
 *
 */
public class GraphGenerator {
	private final int V; // number of vertices
	private float p; // probability of connection of nodes in the same cluster
	private float q; // probability of connection of nodes not in the same cluster
	private GraphArray graph;
	private GraphList graphList;
	private int[] cluster;

	public GraphGenerator(int V, float p, float q) {
		this.V = V;
		this.p = p;
		this.q = q;
		graph = new GraphArray(V);
		graphList = new GraphList();
		cluster = new int[V];
	}

	private void partitionNodes(int partsNB) {
		int partsLen = V / partsNB;
		for (int i = 0; i < V; i++) {
			cluster[i] = i / partsLen + 1;
		}
	}

	public boolean sameCluster(int n1, int n2) {
		return cluster[n1] == cluster[n2];
	}

	public boolean genRandomConnection(int n1, int n2) {
		float r = (float) Math.random();

		return sameCluster(n1, n2) ? (r < p) : (r < q);
	}

	public void genRandomGraph(int partsNB) {
		partitionNodes(partsNB);
		for (int i = 0; i < V; i++) {
			for (int j = i + 1; j < V; j++) {
				boolean connected = genRandomConnection(i, j);
				if (connected) {
					graph.addEdge(i, j);
					graph.addEdge(j, i);
					graphList.addEdge(i, j);
				}
			}
		}
	}

	public void genPlotData() throws IOException {
		System.out.println("Writing plot data...");
		BufferedWriter outputWriter;
		String filename = "src/tme3/data/clusterGraph-" + p + "-" + q + ".txt";
		outputWriter = new BufferedWriter(new FileWriter(filename));
		outputWriter.write(String.valueOf(p) + " " + String.valueOf(q) + " \n");
		for (GraphList.Edge e : graphList.getEdges()) {
			outputWriter.write(e.s + " " + e.t + " \n");
		}
		outputWriter.close();
	}

	public void setProbability(float p, float q) {
		this.p = p;
		this.q = q;
	}

	public GraphArray getGraph() {
		return graph;
	}
	
	public GraphList getGraphList() {
		return graphList;
	}

}
