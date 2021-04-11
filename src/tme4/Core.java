package tme4;

import java.util.Arrays;

import graph.GraphArray;

/**
 * 
 * An O(m) Algorithm for Cores Decomposition of Networks where m is the number
 * of edges in the undirected graph
 * 
 * @see https://arxiv.org/abs/cs/0310049
 */

public class Core {
	private final int V; // number of vertices + 1
	private final long E; // number of edges
	private GraphArray graph;
	private int coreValue;
	private int[] deg;
	private int[] cores;

	public Core(GraphArray graph) {
		this.graph = graph;
		V = graph.getV();
		E = graph.getE() / 2; // undirected graph
		deg = new int[V];
		cores = new int[V];
		coreValue = 0;
	}

	public int[] getDeg() {
		return deg.clone();
	}

	public int[] getCores() {
		return cores.clone();
	}

	public int getCoreValue() {
		return coreValue;
	}

	public int getDensestSubGraphSize() {
		int ct = (int) Arrays.stream(cores).filter(x -> x == coreValue).count();
		return ct;
	}

	public double getDegDensity(boolean countAll) {
		long nbNodes = 0;
		for (int i = 0; i < V; i++) {
			if (graph.isIndirNode(i))
				nbNodes++;
		}

		return (double) E / (countAll ? V : nbNodes);

	}

	public double getEdgeDensity(boolean countAll) {
		long nbNodes = V;
		for (int i = 0; i < V && !countAll; i++) {
			if (graph.isIndirNode(i))
				nbNodes++;
		}

		double maxE = nbNodes * (nbNodes - 1) / 2;

		return (double) E / maxE;
	}

	public int coreDecomposition() {
		int[] vert = new int[V + 1];
		int[] pos = new int[V + 1];
		int[] bin;
		boolean[] visited = new boolean[V];
		int n = V;
		int md = 0;
		for (int v = 0; v < n; v++) {
			deg[v] = graph.getAdj()[v].size();
			cores[v] = deg[v];
			md = Math.max(md, deg[v]);
		}

		bin = new int[md + 1];
		for (int v = 0; v < n; v++) {
			bin[cores[v]]++;
		}

		int start = 1;
		for (int d = 0; d <= md; d++) {
			int num = bin[d];
			bin[d] = start;
			start += num;
		}

		for (int v = 0; v < n; v++) {
			pos[v] = bin[cores[v]];
			vert[pos[v]] = v;
			bin[cores[v]]++;
		}

		for (int d = md; d >= 1; d--) {
			bin[d] = bin[d - 1];
		}
		bin[0] = 1;

		int coreValue = 0;
		for (int i = 1; i < n; i++) {
			int v = vert[i];
			visited[v] = true;
			coreValue = Math.max(coreValue, cores[v]);
			for (int u : graph.getAdj()[v]) {
				if (visited[u])
					continue;

				if (cores[u] > cores[v]) {
					int du = cores[u], pu = pos[u];
					int pw = bin[du], w = vert[pw];
					if (u != w) {
						pos[u] = pw;
						vert[pu] = w;
						pos[w] = pu;
						vert[pw] = u;
					}
					bin[du]++;
					cores[u]--;
				}
			}
		}
		this.coreValue = coreValue;
		return coreValue;
	}

}
