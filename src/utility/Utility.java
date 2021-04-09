package utility;

import java.util.*;

public class Utility {

    public static HashMap<Integer, Float> sortMap(HashMap<Integer, Float> hm) {
        List<Map.Entry<Integer, Float>> list = new LinkedList<Map.Entry<Integer, Float>>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Float>>() {
            public int compare(Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<Integer, Float> temp = new LinkedHashMap<Integer, Float>();
        for (Map.Entry<Integer, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }

    public static long max3(long a, long b, long c) {
        return Math.max(Math.max(a, b), c);
    }

    public static int max3(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}
