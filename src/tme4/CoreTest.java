package tme4;


import graph.GraphArray;
import graph.GraphLoader;

public class CoreTest {

	public static void testCDA(GraphArray graph) {

		Core core = new Core(graph);
		int[] deg = new int[graph.getV()];
		long t1, t2;
		System.out.println("Calculating core value of the graph...");
		t1 = System.currentTimeMillis();
		int cv = core.coreDecomposition();
		t2 = System.currentTimeMillis();

		System.out.printf("...execution time: %dms\n", t2 - t1);

		int sz = core.getDensestSubGraphSize();
		System.out.printf("core value: %d\n", cv);
		System.out.printf("degree density: %f\n", core.getDegDensity(true));
		System.out.printf("edge density: %f\n", core.getEdgeDensity(true));
		System.out.printf("densest prefix size : %d\n", sz);
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
