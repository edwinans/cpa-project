package graph;

import java.io.Serializable;
import java.util.*;
import utility.*;

public class GraphList implements Serializable {

	private static final long serialVersionUID = -2586786504537118762L;

	private int V; // number of vertices
	private long E; // number of edges
	private LinkedList<Edge> edges;
	private int[] degIn;
	private int[] degOut;

	public class Edge implements Serializable {

		private static final long serialVersionUID = 5707239458772063906L;
		public int s;
		public int t;

		Edge(int s, int t) {
			this.s = s;
			this.t = t;
		}

		@Override
		public String toString() {
			return ("(" + s + "," + t + ")");
		}
	}

	public GraphList() {
		edges = new LinkedList<>();
	}

	public boolean isNode(int node) {
		return (degIn[node] > 0 || degOut[node] > 0);
	}

	public void addEdge(int s, int t) {
		E++;
		V = Utility.max3(V, s, t);
		Edge e = new Edge(s, t);
		edges.addLast(e);
	}

	public void initDegree() {
		degIn = new int[V + 1];
		degOut = new int[V + 1];
		for (Edge e : edges) {
			degOut[e.s]++;
			degIn[e.t]++;
		}
	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public int getNumberOfVertices() {
		return V;
	}

	public long getNumberOfEdges() {
		return E;
	}

	public int[] getDegIn() {
		return degIn;
	}

	public int[] getDegOut() {
		return degOut;
	}

}
