package tme1;
import graph.*;

public class LoaderTest {

    /**
     * LoaderTest --[mode] [path]
     * @param args
     */
    public static void main(String[] args) {
    	
    	
		String rootPath = "src/tme1/benchmarks/";
		String defaultPath = rootPath + "test1.txt";
        String path = args.length > 1 ? rootPath + args[1] : defaultPath;
        GraphLoader loader = new GraphLoader(path, false);

        long t1, t2;

        t1 = System.currentTimeMillis();
        loader.parseGraph();
        t2 = System.currentTimeMillis();
        System.out.printf("GraphList time : %dms\n", t2 - t1);

        if (args.length > 0) {
            if (args[0].contains("a")) {
                t1 = System.currentTimeMillis();
                loader.initGraphArray();
                t2 = System.currentTimeMillis();
                System.out.printf("GraphArray time : %dms\n", t2 - t1);
            }

            if (args[0].contains("m")) {
                t1 = System.currentTimeMillis();
                loader.initGraphMatrix();
                t2 = System.currentTimeMillis();
                System.out.printf("GraphMatrix time : %dms\n", t2 - t1);
            }
        }

        /*
         * amazon : GraphList time : 1934ms - FastReader : 939ms GraphArray time : 89ms
         * GraphMatrix time : java.lang.OutOfMemoryError
         * 
         * live-journal: GraphList time : 91363ms - FastReader : 31400ms GraphArray time
         * : java.lang.OutOfMemoryError
         * 
         * (4500MB of RAM) java -Xmx4500m LoaderTest ./benchmarks/com-lj.ungraph.txt
         * GraphList time : 32031ms GraphArray time : 17712ms orkut: TODO friendster:
         * TODO
         */

        // loader.printGraph();

    }
}
