package tme4;

import graph.GraphArray;
import graph.GraphLoader;

public class GraphMiner {

	private static final String pathRoot = "src/tme4/benchmarks/";
	
	
	public static void mineScholar() {
		String scholarIDFile = pathRoot + "scholar/ID.txt";
		String scholarNetFile = pathRoot + "scholar/net.txt";
		GraphLoader loader = new GraphLoader(scholarNetFile, false);
		loader.parseGraph();
		loader.initGraphArray();
		GraphArray graph = loader.getGraphArray();
		System.out.println("Google Scholar Network:");
		System.out.println("Number of vertices: " + graph.getV());
		System.out.println("Number of edges: " + graph.getE()/2);
		Core core = new Core(graph);

		System.out.println("Calculating core value of the graph...");
		int cv = core.coreDecomposition();
		System.out.printf("...core value: %d\n", cv);
		
		
	}
	
	public static void main(String[] args) {
	
		mineScholar();
		
	}
}
