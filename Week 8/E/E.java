import java.io.*;
import java.util.*;

public class E {

    private static class Pair {

        Integer index;
        Integer distance;

        Pair(Integer index, Integer distance) {
            this.index = index;
            this.distance = distance;
        }

        public Integer getIndex() {
            return index;
        }

        Integer getDistance() {
            return distance;
        }
    }

    private static class Path {

        int distance;
        int objects;

        public Path(int distance, int objects) {
            this.distance = distance;
            this.objects = objects;
        }

        public int getDistance() {
            return distance;
        }

        public int getObjects() {
            return objects;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nmg = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nmg[0]);
            int m = Integer.parseInt(nmg[1]);
            int g = Integer.parseInt(nmg[2]); //Lea's clone position

            ArrayList<Integer> objectsPerGrave = new ArrayList<>();
            objectsPerGrave.add(0);

            HashMap<Integer, HashSet<Integer>> neighbours = new HashMap<>();

            for (String s : bufferedReader.readLine().split(" ")) {
                objectsPerGrave.add(Integer.parseInt(s));
            }

            int[][] graph = new int[n + 1][n + 1];
            int[][] graphFutureLea = new int[n + 1][n + 1];

            for (int i = 0; i < m; i++) {
                String[] xyl = bufferedReader.readLine().split(" ");

                // 0-indexed
                int x = Integer.parseInt(xyl[0]);
                int y = Integer.parseInt(xyl[1]);
                int l = Integer.parseInt(xyl[2]);

                if (graphFutureLea[x][y] < l && graphFutureLea[x][y] > 0) {
                    continue;
                }

                if (!neighbours.containsKey(x)) {
                    neighbours.put(x, new HashSet<>());
                }
                neighbours.get(x).add(y);

                if (!neighbours.containsKey(y)) {
                    neighbours.put(y, new HashSet<>());
                }
                neighbours.get(y).add(x);

                if (x > y) {
                    graph[x][y] = l;
                } else {
                    graph[y][x] = l;
                }

                graphFutureLea[x][y] = l;
                graphFutureLea[y][x] = l;
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            //////////////////////////////////////
            // Dijkstra SSSP
            int[] futureLeaDist = new int[n + 1];
            Arrays.fill(futureLeaDist, Integer.MAX_VALUE);
            futureLeaDist[g] = 0;

            PriorityQueue<Pair> q = new PriorityQueue<>(Comparator.comparing(Pair::getDistance));

            for (int i = 0; i < n; i++) {
                q.add(new Pair(i, futureLeaDist[i]));
            }

            while (!q.isEmpty()) {
                int current = q.poll().getIndex();

                for (int neighbour : neighbours.get(current)) {
                    if (futureLeaDist[current] + graphFutureLea[current][neighbour] < futureLeaDist[neighbour]) {
                        futureLeaDist[neighbour] = futureLeaDist[current] + graphFutureLea[current][neighbour];
                        q.add(new Pair(neighbour, futureLeaDist[neighbour]));
                    }
                }
            }
            //////////////////////////////////////

            HashSet<Path>[] dp = new HashSet[n + 1];
            for (int i = 0; i < n + 1; i++) {
                dp[i] = new HashSet<>();
            }

            dp[n].add(new Path(0, objectsPerGrave.get(n))); // objects at Lea's initial position

            int res = -1;
            // from Lea's position to the exit
            for (int i = n - 1; i > -1; i--) {

                // all nodes larger than the current one
                for (int j = n; j > i; j--) {

                    // only interested in direction from larger nodes to smaller
                    if (graph[j][i] > 0) {

                        for (Path path : dp[j]) {

                            int newDistance = graph[j][i] + path.getDistance();
                            int newObjects = path.getObjects() + objectsPerGrave.get(i);

                            // we consider the new path only if it takes us less time (shorter distance) than Lea's clone
                            if (newDistance < futureLeaDist[i]) {
                                dp[i].add(new Path(newDistance, newObjects));

                                // if reached the exit node, find a maximum of all paths leading to it
                                if (i == 0) {
                                    res = Math.max(res, path.getObjects());
                                }
                            }
                        }
                    }
                }
            }

            StringBuilder sb = new StringBuilder();

            if (res < 0) {
                sb.append("impossible");
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + (sb.toString().isEmpty() ? res : sb));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
