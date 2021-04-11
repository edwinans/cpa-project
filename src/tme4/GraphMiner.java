package tme4;

import graph.GraphArray;
import graph.GraphLoader;

public class GraphMiner {

	private static final String pathRoot = "src/tme4/benchmarks/";

	/*
	 * Google Scholar Network: Number of vertices: 287426 Number of edges: 871001
	 */
	public static void mineScholar() {
		String scholarIDFile = pathRoot + "scholar/ID.txt";
		String scholarNetFile = pathRoot + "scholar/net.txt";
		GraphLoader loader = new GraphLoader(scholarNetFile, false);
		loader.parseGraph();
		loader.initGraphArray();
		GraphArray graph = loader.getGraphArray();
		System.out.println("Google Scholar Network:");
		System.out.println("Number of vertices: " + graph.getV());
		System.out.println("Number of edges: " + graph.getE() / 2);
		Core core = new Core(graph);
		System.out.println("Calculating core value of the graph...");
		int cv = core.coreDecomposition();
		System.out.printf("...core value: %d\n", cv);

		int[] deg = core.getDeg();
		int[] cores = core.getCores();
		System.out.println("#DEG #Coreness #ID");
		for (int v = 0; v < graph.getV(); v++) {
			if (graph.isIndirNode(v)) {
//				System.out.printf("node %d :\n", v);
//				System.out.printf("%d %d %d\n", deg[v], cores[v], v);
				int cr = cores[v];
				int d = deg[v];

				if (d == 14 && cr == 12)
					System.out.println(d + " " + cr + " " + v);

				if (d == 61 && cr == 11)
					System.out.println(d + " " + cr + " " + v);

				if (d == 70 && cr == 11)
					System.out.println(d + " " + cr + " " + v);

				if (d == 76 && cr == 9)
					System.out.println(d + " " + cr + " " + v);

				if (d == 91 && cr == 8)
					System.out.println(d + " " + cr + " " + v);

				if (d == 100 && cr == 6)
					System.out.println(d + " " + cr + " " + v);
				
				if ( cr == 14)
					System.out.println(d + " " + cr + " " + v);
			}
		}

	}

	public static void main(String[] args) {

		mineScholar();

	}
}
