import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class J {

    static class Edge {

        int from;
        int to;
        int capacity;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public int getCapacity() {
            return capacity;
        }
    }

    public static int root(int[] parent, int i) {

        int root = i;
        while (true) {
            int p = parent[root];
            if (p == root) {
                break;
            }
            root = p;
        }

        int current = i;
        while (current != root) {
            int next = parent[current];
            parent[current] = root;
            current = next;
        }

        return root;
    }

    public static void union(int[] parent, int[] size, int a, int b) {

        int rootA = root(parent, a);
        int rootB = root(parent, b);

        if (rootA == rootB) {
            return;
        }

//        if (size[rootA] < size[rootB]) {
        parent[rootA] = rootB;
//            size[rootB] += size[rootA];
//        } else {
//        parent[rootB] = rootA;
//            size[rootA] += size[rootB];
//        }

    }

    public static Integer kruskal(int n, ArrayList<Edge> edges) {
        edges.sort(Comparator.comparing(Edge::getCapacity));

        int[] parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        int totalCapacity = 0;
        int nEdges = 0;

        for (Edge edge : edges) {
            int aRoot = root(parent, edge.from);
            int bRoot = root(parent, edge.to);

            if (aRoot != bRoot) {
                union(parent, null, edge.from, edge.to);
                totalCapacity += edge.capacity;
                nEdges++;
            }
        }

        if (nEdges != n - 1) {
            return null;
        }

        return totalCapacity;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            var nm = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = nm[0];
            int m = nm[1];

            if (n - 1 > m) {

                for (int i = 0; i < m; i++) {
                    bufferedReader.readLine();
                }

                // empty line between tests
                if (t < tests - 1) {
                    bufferedReader.readLine();
                }

                bufferedWriter.write("Case #" + (t + 1) + ": " + "impossible");
                bufferedWriter.newLine();

                continue;
            }

            ArrayList<Edge> edges = new ArrayList<>();
            int total = 0;

            for (int i = 0; i < m; i++) {
                var abxl = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                int a = abxl[0] - 1;
                int b = abxl[1] - 1;
                int x = abxl[2];
                int l = abxl[3];

                int capacity = x * l;
                total += capacity;

                edges.add(new Edge(a, b, -capacity));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            Integer minCost = kruskal(n, edges);

            StringBuilder res = new StringBuilder();
            if (minCost != null) {
                res.append((total + minCost) + "");
            } else {
                res.append("impossible");
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

}
