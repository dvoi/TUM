import java.io.*;
import java.util.*;

public class D {

    static class Road {

        int h1;
        int h2;

        public Road(int h1, int h2) {
            this.h1 = h1;
            this.h2 = h2;
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int k = 0; k < t; k++) {

            int n = Integer.parseInt(bufferedReader.readLine());

            HashMap<Integer, HashSet<Integer>> roads = new HashMap<>();

            for (int i = 0; i < n - 1; i++) {
                String[] xy = bufferedReader.readLine().split(" ");

                int x = Integer.parseInt(xy[0]);
                int y = Integer.parseInt(xy[1]);

                if (!roads.containsKey(x)) {
                    roads.put(x, new HashSet<>());
                }

                if (!roads.containsKey(y)) {
                    roads.put(y, new HashSet<>());
                }

                roads.get(x).add(y);
                roads.get(y).add(x);
            }

            // empty line between tests
            if (k < t - 1) {
                bufferedReader.readLine();
            }

            ArrayList<Integer> path = longestPath(roads, new HashSet<>(), longestPath(roads, new HashSet<>(), new Random().nextInt(n) + 1).get(0));

            bufferedWriter.write("Case #" + (k + 1) + ": " + path.get(path.size() / 2));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static ArrayList<Integer> longestPath(HashMap<Integer, HashSet<Integer>> roads, HashSet<Integer> visited, int h1) {

        visited.add(h1);

        ArrayList<Integer> path = new ArrayList<>();

        for (Integer h2 : roads.get(h1)) {
            if (!visited.contains(h2)) {
                ArrayList<Integer> dfs = longestPath(roads, visited, h2);
                if (dfs.size() > path.size()) {
                    path = dfs;
                }
            }
        }

        path.add(h1);

        return path;
    }
}