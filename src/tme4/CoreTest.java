package tme4;

import graph.GraphArray;
import graph.GraphLoader;

public class CoreTest {

	public static void testCDA(GraphArray graph) {
		Core core = new Core(graph);
		int[] deg = new int[graph.getV()];
		System.out.println("Calculating core value of the graph...");
		int cv = core.coreDecomposition(deg);
		System.out.printf("...Core value: %d\n", cv);
	}

	public static void main(String[] args) {
		String rootPath = "src/tme1/benchmarks/";
		String defaultPath = rootPath + "test1.txt";

		String path = args.length > 0 ? rootPath + args[0] : defaultPath;
		GraphLoader loader = new GraphLoader(path, false);
		loader.parseGraph();
		loader.initGraphArray();

		testCDA(loader.getGraphArray());

	}
}
