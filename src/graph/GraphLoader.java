package graph;

import java.io.*;

import utility.FastReader;

public class GraphLoader {
	private String path;
	private GraphList graphList;
	private GraphArray graphArray;
	private GraphMatrix graphMatrix;
	private boolean isDirected;

	public GraphLoader(String path, boolean isDirected) {
		this.path = path;
		this.isDirected = isDirected;
		graphList = new GraphList();
	}

	public void printGraph() {
		System.out.println(graphList.getEdges());
		graphArray.printGraph();
		graphMatrix.printGraph();
	}

	public void initGraphArray() {
		int V = graphList.getNumberOfVertices() + 1;
		graphArray = new GraphArray(V);
		for (GraphList.Edge edge : graphList.getEdges()) {
			graphArray.addEdge(edge.s, edge.t);
			if (!isDirected)
				graphArray.addEdge(edge.t, edge.s);
		}
	}

	public void initGraphMatrix() {
		int V = graphList.getNumberOfVertices() + 1;
		graphMatrix = new GraphMatrix(V);
		for (GraphList.Edge edge : graphList.getEdges()) {
			graphMatrix.addEdge(edge.s, edge.t);
			if (!isDirected)
				graphArray.addEdge(edge.t, edge.s);
		}
	}

	public void parseGraph() {
		int V = 0;
		long E = 0;
		File file = new File(path);
		int progress = 0, gap = 100_000, countP = 0;

		try {
			FastReader in = new FastReader(file);

			String line;
			System.out.println("Loading graph...");
			System.out.print("progress : [");
			while ((line = in.nextLine()) != null) {

				if (line.isEmpty() || line.contains("#")) {
					continue;
				}
				String[] values = line.split("\\s+");
				int s = Integer.parseInt(values[0]), t = Integer.parseInt(values[1]);

				graphList.addEdge(s, t);

				E++;
				progress++;
				if (progress > gap) {
					System.out.print("#");
					progress = 0;
					countP++;
				}

			}

			System.out.printf("] ~ %d x %d edges\n", countP, gap);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public GraphList getGraphList() {
		return graphList;
	}

	public GraphArray getGraphArray() {
		return graphArray;
	}

	public GraphMatrix getGraphMatrix() {
		return graphMatrix;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void serializeGraphList() {
		File file = new File("./objects/gl-data");

		if (file.exists())
			return;

		try (FileOutputStream fos = new FileOutputStream("./objects/gl-data");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(graphList);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public GraphList deserializeGraphList() {
		String path = "./objects/gl-data";
		if (!(new File(path).exists())) {
			return null;
		}
		GraphList graphListDeser = null;
		try (FileInputStream fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis)) {
			graphListDeser = (GraphList) ois.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("The file : " + path + " cannot be found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("The file : " + path + " cannot be read.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("The object class you have tried to deserialize doesn't exist");
			e.printStackTrace();
		}
		return graphListDeser;
	}

}
