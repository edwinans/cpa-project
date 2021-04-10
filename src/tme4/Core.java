package tme4;

import graph.GraphArray;

public class Core {
	private final int V; // number of vertices + 1
	private GraphArray graph;

	public Core(GraphArray graph) {
		this.graph = graph;
		V = graph.getV();
	}

	public int coreDecomposition(int[] deg) {
//		int[] deg = new int[V];
		int[] vert = new int[V + 1];
		int[] pos = new int[V + 1];
		int[] bin;
		boolean[] visited = new boolean[V];
		int n = V;
		int md = 0;
		for (int v = 0; v < n; v++) {
			deg[v] = graph.getAdj()[v].size();
			md = Math.max(md, deg[v]);
		}

		bin = new int[md + 1];
		for (int v = 0; v < n; v++) {
			bin[deg[v]]++;
		}

		int start = 1;
		for (int d = 0; d <= md; d++) {
			int num = bin[d];
			bin[d] = start;
			start += num;
		}

		for (int v = 0; v < n; v++) {
			pos[v] = bin[deg[v]];
			vert[pos[v]] = v;
			bin[deg[v]]++;
		}

		for (int d = md; d >= 1; d--) {
			bin[d] = bin[d - 1];
		}
		bin[0] = 1;

		int coreValue = 0;
		for (int i = 1; i < n; i++) {
			int v = vert[i];
			visited[v] = true;
			coreValue = Math.max(coreValue, deg[v]);
			for (int u : graph.getAdj()[v]) {
				if (visited[u])
					continue;

				if (deg[u] > deg[v]) {
					int du = deg[u], pu = pos[u];
					int pw = bin[du], w = vert[pw];
					if (u != w) {
						pos[u] = pw;
						vert[pu] = w;
						pos[w] = pu;
						vert[pw] = u;
					}
					bin[du]++;
					deg[u]--;
				}
			}
		}

		return coreValue;
	}

}
