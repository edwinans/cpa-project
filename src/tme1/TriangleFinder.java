package tme1;

import java.util.*;
import graph.*;

public class TriangleFinder {
    GraphLoader loader;
    GraphArray graph;
    GraphList graphList;

    public TriangleFinder(GraphLoader loader) {
        this.loader = loader;
        loader.parseGraph();
        loader.initGraphArray();
        graphList = loader.getGraphList();
        graph = loader.getGraphArray();

    }

    public static void main(String[] args) {
		String rootPath = "src/tme1/benchmarks/";
		String defaultPath = rootPath + "test1.txt";
        String path = args.length > 0 ? rootPath + args[0] : defaultPath;
        GraphLoader loader = new GraphLoader(path, false);
        long t1, t2;
        t1 = System.currentTimeMillis();

        TriangleFinder tFinder = new TriangleFinder(loader);
        System.out.println("Counting triangles...");
        // System.out.println(tFinder.getTriangles());
        System.out.println("NB of Triangles:" + tFinder.countTriangles());

        loader.initGraphArray();
        t2 = System.currentTimeMillis();
        System.out.printf("Triangles counting time : %dms\n", t2 - t1);

    }

    private class Triangle {
        int u, v, w;

        public Triangle(int u, int v, int w) {
            int[] sorted = { u, v, w };
            Arrays.sort(sorted);
            this.u = sorted[0];
            this.v = sorted[1];
            this.w = sorted[2];
        }

        @Override
        public boolean equals(Object obj) {
            Triangle t = (Triangle) obj;
            return (t.u == this.u && t.v == this.v && t.w == this.w);
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v, w);
        }

        @Override
        public String toString() {
            return "(" + u + "," + v + "," + w + ")";
        }

    }

    // modify the graph
    public void sortAndTruncateNbrs(int u) {

        Collections.sort(graph.getAdj()[u], new Comparator<Integer>() {
            public int compare(Integer v1, Integer v2) {
                int d1 = graph.getAdj()[v1].size();
                int d2 = graph.getAdj()[v2].size();
                if (d1 < d2)
                    return 1;
                else if (d1 == d2)
                    return 0;
                else
                    return -1;
            };
        });

        LinkedList<Integer> adj = graph.getAdj()[u];

        if (adj.isEmpty())
            return;

        int degree = adj.size();
        int x = adj.getFirst();

        while (graph.getAdj()[x].size() > degree) {
            adj.removeFirst();
            if (adj.isEmpty())
                break;
            x = adj.getFirst();
        }

    }

    // l1 and l2 are sorted in non-increasing order
    public HashSet<Integer> intersect(List<Integer> l1, List<Integer> l2) {
        HashSet<Integer> res = new HashSet<>(l1);
        HashSet<Integer> set2 = new HashSet<>(l2);
        res.retainAll(set2);

        return res;
    }

    public long countTriangles() {
        return getTriangles().size();
    }

    public HashSet<Triangle> getTriangles() {
        HashSet<Triangle> triangles = new HashSet<>();

        //complexity in ~ O(E.log(V))
        for (int i = 0; i < graph.getV(); i++) {
            if (!graph.getAdj()[i].isEmpty()) {
                sortAndTruncateNbrs(i);
            }
        }

        //complexity:  < O(E * V) ??
        for (GraphList.Edge e : graphList.getEdges()) {
            if (e.s == e.t)
                continue;

            HashSet<Integer> commonNbrs = intersect(graph.getAdj()[e.s], graph.getAdj()[e.t]);
            for (int w : commonNbrs) {
                if (w != e.s && w != e.t) {
                    Triangle t = new Triangle(e.s, e.t, w);
                    triangles.add(t);
                }
            }
        }

        return triangles;
    }

}
