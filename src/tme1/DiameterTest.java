package tme1;

import graph.GraphLoader;

public class DiameterTest {

	public static void main(String[] args) {
		String rootPath = "src/tme1/benchmarks/";
		String defaultPath = rootPath + "test1.txt";

		String path = args.length > 0 ? rootPath + args[0] : defaultPath;
		GraphLoader loader = new GraphLoader(path, false);

		int t = args.length > 1 ? Integer.parseInt(args[1]) : 15;

		System.out.println("Calculation graph diameter");
		GraphDiameter graphDiameter = new GraphDiameter(loader, t);
		// loader.getGraphArray().printGraph();

		long t1, t2;

		t1 = System.currentTimeMillis();

		System.out.println("Lower-Bound:" + graphDiameter.getLowerBound());
		t2 = System.currentTimeMillis();
		System.out.printf("Getting Diameter Lower-Bound time : %dms\n", t2 - t1);

		t1 = System.currentTimeMillis();
		System.out.println("Upper-Bound:" + graphDiameter.getUpperBound());
		t2 = System.currentTimeMillis();
		System.out.printf("Getting Diameter Upper-Bound executing Lower-Bound before time : %dms\n", t2 - t1);

		/*
		 * Amazon:\ Calculation graph diameter Loading graph... progress : [#########] ~
		 * 9 x 100000 edges Loading graph... progress : [#########] ~ 9 x 100000 edges
		 * 47 Getting Diameter Lower-Bound time : 2634ms 61 Getting Diameter Upper-Bound
		 * executing Lower-Bound before time : 224ms
		 * 
		 * Live-Journal:\ Calculation graph diameter Loading graph... progress :
		 * [############################################################################
		 * #############################################################################
		 * #############################################################################
		 * #############################################################################
		 * #######################################] ~ 346 x 100000 edges Lower-Bound:21
		 * Getting Diameter Lower-Bound time : 74739ms Upper-Bound:24 Getting Diameter
		 * Upper-Bound executing Lower-Bound before time : 10685ms
		 * 
		 */
	}

}
