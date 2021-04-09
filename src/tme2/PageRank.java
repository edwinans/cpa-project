package tme2;

import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import graph.*;
import utility.*;

public class PageRank {
	final int V; // number of vertices + 1
	GraphLoader loader;
	GraphList graphList;
	float alpha;

	private static final String pathRoot = "src/tme2/benchmarks/";

	private static final String pageNamesFile = pathRoot + "id-names.txt";

	public PageRank(GraphLoader gLoader, float alpha) {
		this.alpha = alpha;
		loader = gLoader;
		// GraphList dgl = loader.deserializeGraphList();
		// if (dgl == null) {
		// loader.parseGraph();
		// graphList = loader.getGraphList();
		// } else {
		// graphList = dgl;
		// }

		// loader.serializeGraphList();

		loader.parseGraph();
		graphList = loader.getGraphList();

		V = graphList.getNumberOfVertices() + 1;
		graphList.initDegree();
	}

	public static void main(String[] args) {

		String defaultPath = pathRoot + "test1.txt";

		//set path of links file : {wiki-links}
		final String linksFile = args.length > 0 ? pathRoot + args[0] : defaultPath;
		final String pageIDCatsFile = pathRoot + "id-category_ids.txt";
		final String idCatFile = pathRoot + "category_ids-name.txt";
		final int chessCatID = 691713, boxingCatID = 738624;
		final int magnusID = 442682;
		LinkedList<Integer> catIds = new LinkedList<>();
		catIds.add(chessCatID);
		catIds.add(boxingCatID);

		LinkedList<String> subjects = new LinkedList<>(Arrays.asList("chess", "boxing"));

		GraphLoader loader = new GraphLoader(linksFile, true);
		PageRank pageRank = new PageRank(loader, 0.15f);

		System.out.println("Searching...");
		int iter = 5;
		float[] p;
		
		/* for testing; comment and un-comment following 4 PageRank instances : */
		
//		//I
//		p = pageRank.powerIteration(iter);
//		pageRank.ranks(p, 10, pageNamesFile);

//		//II
//		p = pageRank.rootedPageRank(iter, magnusID);
//		pageRank.ranks(p, 10, pageNamesFile);

//		//III
//		// first version check only if the page contains any catIds
//		p = pageRank.configPageRank(catIds, iter, idCatFile);

		//IV
		p = pageRank.configCountPageRank(subjects, iter, idCatFile, pageIDCatsFile);
		pageRank.ranks(p, 10, pageNamesFile);

		//Plotting
		// try {
		// pageRank.collectRankingPlotData(p);
		// pageRank.collectDegreePlotData(degIn, degOut);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public float[] configPageRank(LinkedList<Integer> catIds, int t, String fileName) {
		HashSet<Integer> pageIds = getPageIDByCategory(catIds, fileName);
		float[] I = new float[V];
		for (Integer id : pageIds) {
			I[id] = (float) 1 / pageIds.size();
		}

		return rootedPageRank(t, I);
	}

	// count repetitions of subject in categoris of a page by a factor of 1 and
	// check if page_name contains subjects by a factor of count_of_subject * 50
	public float[] configCountPageRank(LinkedList<String> subjects, int t, String idCatFile, String pageIDCatsFile) {
		System.out.println("Configure restart vector for PR using CountMap...");

		LinkedList<Integer> catIds = getRelatedCategoriesID(subjects, idCatFile);

		HashMap<Integer, Integer> countMap = getPageIDCountByCategoryNames(catIds, pageIDCatsFile);

		HashMap<Integer, String> pageName = getNamesByID(new LinkedList<Integer>(countMap.keySet()), pageNamesFile);

		for (Integer id : countMap.keySet()) {
			String name = pageName.get(id).toLowerCase();
			// if (subjects.stream().anyMatch(s -> name.contains(s.toLowerCase())))
			// countMap.computeIfPresent(id, (k, v) -> v + 10);
			int ct = (int) subjects.stream().filter(s -> name.contains(s.toLowerCase())).count();
			if (ct > 0)
				countMap.computeIfPresent(id, (k, v) -> v + (ct > 1 ? ct * 50 : 10));

		}

		int totalCount = countMap.values().stream().reduce(0, Integer::sum);
		System.out.println("TOTA_COUNT = " + totalCount);
		float[] I = new float[V];
		for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
			I[entry.getKey()] = (float) entry.getValue() / totalCount;
		}

		return rootedPageRank(t, I);
	}

	public LinkedList<Integer> getRelatedCategoriesID(LinkedList<String> subjects, String idCatFile) {
		File file = new File(idCatFile);
		LinkedList<Integer> res = new LinkedList<>();
		subjects.replaceAll(x -> x.toLowerCase());

		try {
			FastReader in = new FastReader(file);

			String line;
			System.out.println("Getting Related Categories ID by subjects...");
			while ((line = in.nextLine()) != null) {

				if (line.isEmpty() || line.contains("#")) {
					continue;
				}
				String[] values = line.split("\\s+");
				int catIndex = Integer.parseInt(values[0]);

				String category = String.join(" ", Arrays.copyOfRange(values, 1, values.length)).toLowerCase();

				boolean cts = subjects.stream().anyMatch(s -> category.contains(s));

				if (cts)
					res.add(catIndex);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	// count repetitions of categories in each page
	private HashMap<Integer, Integer> getPageIDCountByCategoryNames(LinkedList<Integer> catIds, String fileName) {
		HashMap<Integer, Integer> map = new HashMap<>();
		File file = new File(fileName);

		try {
			FastReader in = new FastReader(file);

			String line;
			System.out.println("Getting CountMap of Page IDs by category IDs...");
			while ((line = in.nextLine()) != null) {

				if (line.isEmpty() || line.contains("#")) {
					continue;
				}
				String[] values = line.split("\\s+");
				int pageIndex = Integer.parseInt(values[0]);

				int count = Arrays.stream(values, 1, values.length).mapToInt(Integer::parseInt).reduce(0,
						(a, b) -> a + (catIds.contains(b) ? 1 : 0));

				if (count > 0) {
					map.put(pageIndex, count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	private HashSet<Integer> getPageIDByCategory(LinkedList<Integer> catIds, String fileName) {
		HashSet<Integer> set = new HashSet<>();
		File file = new File(fileName);

		try {
			FastReader in = new FastReader(file);

			String line;
			System.out.println("Getting page IDs by category IDs...");
			while ((line = in.nextLine()) != null) {

				if (line.isEmpty() || line.contains("#")) {
					continue;
				}
				String[] values = line.split("\\s+");
				int pageIndex = Integer.parseInt(values[0]);

				for (int i = 1; i < values.length; i++) {
					int catID = Integer.parseInt(values[i]);
					if (catIds.contains(catID)) {
						set.add(pageIndex);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return set;
	}

	private HashMap<Integer, String> getNamesByID(LinkedList<Integer> ids, String fileName) {
		HashMap<Integer, String> idNames = new HashMap<>();

		File file = new File(fileName);

		try {
			FastReader in = new FastReader(file);

			String line;
			System.out.println("Getting titles...");
			while ((line = in.nextLine()) != null) {

				if (line.isEmpty() || line.contains("#")) {
					continue;
				}
				String[] values = line.split("\\s+");
				int index = Integer.parseInt(values[0]);
				if (ids.contains(index)) {
					String[] sub = Arrays.copyOfRange(values, 1, values.length);

					idNames.put(index, String.join(" ", sub));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return idNames;
	}

	public void ranks(float[] p, int nb, String fileName) {
		HashMap<Integer, Float> indexPR = new HashMap<Integer, Float>();

		for (int i = 0; i < p.length; i++) {
			if (graphList.isNode(i))
				indexPR.put(i, p[i]);
		}

		indexPR = Utility.sortMap(indexPR);
		List<Entry<Integer, Float>> entryList = new ArrayList<Map.Entry<Integer, Float>>(indexPR.entrySet());
		int len = entryList.size();

		LinkedList<Integer> idsMax = new LinkedList<>(), idsMin = new LinkedList<>();

		for (int i = len - 1; i > len - nb - 1 && i >= 0; i--) {
			Entry<Integer, Float> entry = entryList.get(i);
			float pr = entry.getValue();
			// System.out.println("MAX_VALUE + " + (len - i) + " : " + pr);
			idsMax.addLast(entry.getKey());
		}

		// all indexes in p are not nodes of the actual graph
		for (int i = 0; i < nb && i < len; i++) {
			Entry<Integer, Float> entry = entryList.get(i);

			float pr = entry.getValue();
			// System.out.println("MIN_VALUE + " + (i) + " : " + pr);

			idsMin.addLast(entry.getKey());
		}

		byte c = 1;
		HashMap<Integer, String> idMaxNames = getNamesByID(idsMax, fileName);
		System.out.printf("TOP %d: \n", nb);
		for (Integer id : idsMax) {
			System.out.printf("max %d id:%d %s\n", c++, id, idMaxNames.get(id));
		}

		System.out.println("-------------------------------------");

		c = 1;
		HashMap<Integer, String> idMinNames = getNamesByID(idsMin, fileName);
		System.out.printf("BOTTOM %d: \n", nb);
		for (Integer id : idsMin) {
			System.out.printf("min %d id:%d %s\n", c++, id, idMinNames.get(id));
		}
	}

	private HashMap<Float, Integer> indexOfPR(float[] p) {
		HashMap<Float, Integer> map = new HashMap<>();
		for (int i = 0; i < V; i++) {
			map.put(p[i], i);
		}

		return map;
	}

	private void collectRankingPlotData(float[] ranking) throws IOException {
		System.out.println("Writing ranking data...");
		BufferedWriter outputWriter;
		String filename = "data/page-rank" + alpha + ".txt";
		outputWriter = new BufferedWriter(new FileWriter(filename));
		int count = 0;
		float n = V;
		for (int i = 0; i < ranking.length; i++) {
			if (ranking[i] > 1 / n) {
				outputWriter.write(i + " " + String.valueOf(ranking[i]) + " \n");
				count++;
			}
		}
		outputWriter.close();
		System.out.println("...completed " + count);

	}

	private void collectDegreePlotData(int[] degIn, int[] degOut) throws IOException {
		System.out.println("Writing degIn and degOut data...");
		BufferedWriter outputWriterIn;
		BufferedWriter outputWriterOut;
		outputWriterIn = new BufferedWriter(new FileWriter("data/deg-in.txt"));
		outputWriterOut = new BufferedWriter(new FileWriter("data/deg-out.txt"));
		int count = 0;
		for (int i = 0; i < degIn.length; i++) {
			if (degIn[i] > 0 || degOut[i] > 0) {
				outputWriterIn.write(i + " " + Integer.toString(degIn[i]) + " \n");
				outputWriterOut.write(i + " " + Integer.toString(degOut[i]) + " \n");
				count++;
			}
		}
		outputWriterIn.close();
		outputWriterOut.close();
		System.out.println("...completed " + count);
		System.out.println("V " + V);

	}

	private float[] calcVector(float[] vector, float[] I) {
		int[] degOut = graphList.getDegOut();
		float[] b = new float[V];
		float n = V;
		for (GraphList.Edge edge : graphList.getEdges()) {
			int d = degOut[edge.s];
			float val = d == 0 ? (float) 1 / n : (float) 1 / d;
			b[edge.t] += val * vector[edge.s];
		}

		for (int i = 0; i < V; i++) {
			b[i] = (1 - alpha) * b[i] + alpha * I[i];
		}

		return b;
	}

	private float[] normalize(float[] vector) {
		float norm = 0;
		for (int i = 0; i < V; i++) {
			norm += Math.abs(vector[i]);
		}

		for (int i = 0; i < V; i++) {
			vector[i] += (1 - norm) / V;
		}

		return vector;
	}

	private float[] normalize(float[] vector, float[] P) {
		float norm = 0;
		for (int i = 0; i < V; i++) {
			norm += Math.abs(vector[i]);
		}

		for (int i = 0; i < V; i++) {
			vector[i] += P[i] * (1 - norm) / V;
		}

		return vector;
	}

	private float[] powerIteration(int t) {
		float n = V;
		float[] p = new float[V];
		Arrays.fill(p, 1 / n);
		float[] I = p.clone();

		for (int i = 0; i < t; i++) {
			p = calcVector(p, I);
			p = normalize(p);
		}

		return p;

	}

	private float[] rootedPageRank(int t, int root) {
		float n = V;
		float[] p = new float[V];
		Arrays.fill(p, 1 / n);
		float[] I = new float[V];
		I[root] = 1;

		for (int i = 0; i < t; i++) {
			p = calcVector(p, I);
			p = normalize(p, I);
		}

		return p;
	}

	private float[] rootedPageRank(int t, float[] I) {
		float n = V;
		float[] p = new float[V];
		Arrays.fill(p, 1 / n);

		for (int i = 0; i < t; i++) {
			p = calcVector(p, I);
			p = normalize(p, I);
		}

		return p;
	}

}