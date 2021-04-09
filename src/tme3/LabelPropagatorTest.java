package tme3;

import java.io.IOException;

import graph.GraphArray;
import graph.GraphLoader;

public class LabelPropagatorTest {
	
	
	public static void amazonScaleTest() {
		GraphArray graph;
		String rootPath = "src/tme1/benchmarks/";
		String path = rootPath + "com-amazon.ungraph.txt";
		GraphLoader loader = new GraphLoader(path, false);
		loader.parseGraph();
		loader.initGraphArray();
		graph = loader.getGraphArray();
		LabelPropagator lpa = new LabelPropagator(graph);

		long t1, t2;
		
		System.out.println("Start Label Propagator...");
		t1 = System.currentTimeMillis();
		lpa.propagate();
		t2 = System.currentTimeMillis();
		
		System.out.printf("...Label propagation in amazon time : %dms\n", t2 - t1);
		
		int labelsCount = lpa.countLabels();
		System.out.printf("Number of distintct labels in the amazon graph: %d\n", labelsCount);

	}
	
	public static void randomTest() {
		GraphArray graph;
		GraphGenerator generator;
		LabelPropagator lpa;

		final int V = 400; // nb of vertices
		float p = 0.4f, q = 0.04f;
		int partsNB = 4;
		generator = new GraphGenerator(V, p, q);

		generator.genRandomGraph(partsNB);
		graph = generator.getGraph();

		lpa = new LabelPropagator(graph);
		lpa.propagate();
		int labelsCount = lpa.countLabels();
		
//		try {
//			generator.genPlotData();
//			lpa.genPlotData(p, q);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		

		System.out.printf("Number of distintct labels in the graph: %d\n", labelsCount);
	}
	
	public static void main(String[] args) {
		randomTest();
//		amazonScaleTest();
		
	}
}
