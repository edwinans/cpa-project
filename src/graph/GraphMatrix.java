package graph;

/**
 * undirected graph - adjacency matrix structure
 */

public class GraphMatrix {
    private int V; // number of vertices
    private long E; // number of edges
    private boolean[][] matrix;

    public GraphMatrix(int v) {
        V = v;
        matrix = new boolean[V][V];
    }

    public void addEdge(int s, int t) {
        E++;
        matrix[s][t] = true;
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print((matrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }
}
