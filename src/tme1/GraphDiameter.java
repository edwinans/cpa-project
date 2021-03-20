package tme1;

import java.util.*;
import graph.*;

public class GraphDiameter {
    GraphLoader loader;
    GraphArray graph;
    DiameterData lowerBoundData;
    int upperBound;
    final int T;

    public GraphDiameter(GraphLoader gLoader, int T) {
        loader = gLoader;
        loader.parseGraph();
        loader.initGraphArray();
        graph = loader.getGraphArray();
        this.T = T;
    }

    public void BFS() {
        int V = graph.getV();
        boolean visited[] = new boolean[V];

        for (int i = 0; i < V; i++) {
            // suppose that we don't have an isolated vertex i.e deg = 0
            if (!visited[i] && !graph.getAdj()[i].isEmpty()) {
                BFSAux(i, visited);
                System.out.println();
            }
        }
    }

    private class DiameterData {
        int source;
        int fartestNode;
        int dist;
        int[] pi;

        public DiameterData(int source, int fartestNode, int dist, int[] pi) {
            this.source = source;
            this.fartestNode = fartestNode;
            this.dist = dist;
            this.pi = pi;
        }
    }

    public int getLowerBound() {
        if (lowerBoundData == null)
            lowerBoundData = calcLowerBound(T);
        return lowerBoundData.dist;
    }

    public int getUpperBound() {
        if (upperBound == 0)
            upperBound = calcUpperBound();
        return upperBound;
    }

    public int calcUpperBound() {
        getLowerBound();
        int d = lowerBoundData.dist / 2;
        int[] pi = lowerBoundData.pi.clone();
        int src = lowerBoundData.source;
        int midNode = lowerBoundData.fartestNode;
        while (d-- > 0 && midNode != src) {
            midNode = pi[midNode];
        }

        int V = graph.getV();
        int[] distance = new int[V];
        int firstNode = getFartestNode(midNode, distance, pi);
        int max1 = distance[firstNode];
        int secondNode = 0;
        int max2 = 0;
        for (int i = 0; i < V; i++) {
            if (i != firstNode) {
                if (distance[i] > max2) {
                    max2 = distance[i];
                    secondNode = i;
                }
            }
        }
        
        getFartestNode(secondNode, distance, pi);
        max2 = distance[midNode];

        return max1 + max2;
    }

    public DiameterData calcLowerBound(int t) {
        int V = graph.getV();
        int[] distance = new int[V];
        int[] pi = new int[V];

        int s = 0;
        boolean[] mark = new boolean[V];
        int fartestSourceNode = s;
        int fartestNode = s;
        int fartestDistance = 0;

        while (t > 0) {
            if (!mark[s] && !graph.getAdj()[s].isEmpty()) {
                mark[s] = true;

                t--;
                int node = getFartestNode(s, distance, pi);
                int dist = distance[node];
                if (dist > fartestDistance) {
                    fartestSourceNode = s;
                    fartestNode = node;
                    fartestDistance = dist;
                }

                s = node;
            } else {
                s = (int) (Math.random() * V);
            }

        }

        return new DiameterData(fartestSourceNode, fartestNode, fartestDistance, pi.clone());
    }

    public int getFartestNode(int s, int[] distance, int[] pi) {
        boolean[] visited = new boolean[graph.getV()];

        LinkedList<Integer> queue = new LinkedList<Integer>();
        int maxDistance = 0;
        int fartestNode = s;

        visited[s] = true;
        queue.add(s);
        distance[s] = 0;
        pi[s] = s;

        while (queue.size() != 0) {
            s = queue.poll();

            for (int nbr : graph.getAdj()[s]) {
                if (!visited[nbr]) {
                    pi[nbr] = s;
                    distance[nbr] = distance[s] + 1;
                    if (distance[nbr] > maxDistance) {
                        maxDistance = distance[nbr];
                        fartestNode = nbr;
                    }
                    visited[nbr] = true;
                    queue.add(nbr);
                }
            }
        }

        return fartestNode;
    }

    public void BFSAux(int s, boolean[] visited) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();
            System.out.print(s + " ");

            for (int nbr : graph.getAdj()[s]) {
                if (!visited[nbr]) {
                    visited[nbr] = true;
                    queue.add(nbr);
                }
            }
        }
    }

}
