package tme3;

import java.io.IOException;

import graph.GraphArray;

public class GraphGeneratorTest {

	public static void main(String[] args) {
		GraphGenerator generator;
		GraphArray graph;
		final int V = 400; // nb of vertices
		float p = 0.6f, q = 0.1f;
		int partsNB = 4;
		generator = new GraphGenerator(V, p, q);

		generator.genRandomGraph(partsNB);
		graph = generator.getGraph();
		try {
			generator.genPlotData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int sameClusterCount = 0, linkedSameClusterCount = 0;
		int notSameClusterCount = 0, linkedNSameClusterCount = 0;
		int diffSameCluster, diffNotSameCluster;

		for (int i = 0; i < V; i++) {
			for (int j = i + 1; j < V; j++) {
				boolean linked = graph.hasLink(i, j);
				if (generator.sameCluster(i, j)) {
					sameClusterCount++;
					if (linked)
						linkedSameClusterCount++;
				} else {
					notSameClusterCount++;
					if (linked)
						linkedNSameClusterCount++;
				}
			}
		}

		diffSameCluster = sameClusterCount - linkedSameClusterCount;
		diffNotSameCluster = notSameClusterCount - linkedNSameClusterCount;

		System.out.printf("diff(Nodes in same cluster, linked _) : %d\n", diffSameCluster);
		System.out.printf("diff(Nodes not in same cluster, linked _) : %d\n", diffNotSameCluster);
	}
}
