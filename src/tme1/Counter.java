package tme1;

import java.io.*;
import java.util.*;

//count number of distinct nodes and all edges
public class Counter {

    public static void main(String[] args) {
    	String rootPath = "src/tme1/benchmarks/";
    	String defaultPath = rootPath + "test1.txt";
        long n = 0;
        long m = 0;
        File f = new File(args.length > 0 ? rootPath + args[0] : defaultPath);
        HashSet<Integer> mark = new HashSet<>();

        try (Scanner in = new Scanner(f)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.isEmpty() || line.contains("#")) {
                    continue;
                }
                String[] v = line.split("\\s+");
                mark.add(Integer.parseInt(v[0]));
                mark.add(Integer.parseInt(v[1]));
                m++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        n = mark.size();

        System.out.printf("number of vertices : %d\nnumber of edges : %d \n", n, m);
    }
}