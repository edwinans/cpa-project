package tme3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import graph.GraphArray;



public class LabelPropagator {
	private final int V; // number of vertices
	private GraphArray graph;
	private ArrayList<Integer> nodes;
	private int[] label;

	public LabelPropagator(GraphArray graph) {
		this.graph = graph;
		V = graph.getV();
		nodes = new ArrayList<>();
		label = new int[V];
		for (int i = 0; i < V; i++) {
			if (graph.isIndirNode(i)) {
				nodes.add(i);
				label[i] = i;
			}
		}
	}

	private int mostFreqNbr(int node) {
		int[] count = new int[V];
		int maxFreq = 0;
		int mfLabel = label[node];
		for (Integer nbr : graph.getAdj()[node]) {
			int l = label[nbr];
			count[l]++;
			if (count[l] > maxFreq) {
				maxFreq = count[l];
				mfLabel = l;
			}
		}

		return (maxFreq == count[label[node]] ? label[node] : mfLabel);
	}

	public void propagate() {
		Collections.shuffle(nodes);

		boolean updated = false;

		do {
			updated = false;
			for (Integer node : nodes) {
				int mfLabel = mostFreqNbr(node);
				if (label[node] != mfLabel) {
					label[node] = mfLabel;
					updated = true;
				}
			}
		} while (updated);

	}

	public int countLabels() {
		return new HashSet<Integer>(Arrays.stream(label).boxed().collect(Collectors.toList())).size();
	}

	public int[] getLabel() {
		return label.clone();
	}
	
	public void genPlotData(float p, float q) throws IOException {
		System.out.println("Writing data...");
		BufferedWriter outputWriter;
		String filename = "src/tme3/data/labelGraph-" + p + "-" + q + ".txt";
		outputWriter = new BufferedWriter(new FileWriter(filename));
		for (int i = 0; i < V; i++) {
			if (graph.isIndirNode(i)) {
				outputWriter.write(i + " " + label[i] + " \n");
			}
		}
			
		
		outputWriter.close();
	}

}
