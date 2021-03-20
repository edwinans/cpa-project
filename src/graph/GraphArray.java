package graph;

import java.util.*;

/**
 * undirected graph - adjacency list structure
 */

public class GraphArray {
	private int V; // number of vertices
	private long E; // number of edges

	private LinkedList<Integer> adj[];

	@SuppressWarnings("unchecked")
	public GraphArray(int v) {
		V = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList<>();
	}

	public void addEdge(int v, int w) {
		E++;
		adj[v].add(w);
	}

	public boolean isIndirNode(int node) {
		return (adj[node].size() > 0);
	}

	public boolean hasLink(int v, int w) {
		return (adj[v].contains(w));
	}

	public void printGraph() {
		for (int i = 0; i < adj.length; i++) {
			System.out.println(i + " -> " + adj[i]);
		}
	}

	public LinkedList<Integer>[] getAdj() {
		return adj;
	}

	public int getV() {
		return V;
	}

	public long getE() {
		return E;
	}

}
