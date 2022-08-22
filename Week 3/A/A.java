import java.io.*;
import java.util.*;

public class A {

    private static class Edge {

        int source;
        int dest;
        int weight;

        Edge(int source, int dest, int weight) {
            this.source = source;
            this.dest = dest;
            this.weight = weight;
        }

        int getSource() {
            return source;
        }

        int getDest() {
            return dest;
        }
    }

    private static void union(int[] parent, int[] size, int a, int b) {

        // improvement from O(n) to O(log n):
        // https://dou.ua/lenta/articles/union-find/

        int rootA = root(parent, a);
        int rootB = root(parent, b);

        if (rootA == rootB) {
            return;
        }

        if (size[rootA] < size[rootB]) {
            parent[rootA] = rootB;
            size[rootB] += size[rootA];
        } else {
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        }

    }

    private static int root(int[] parent, int i) {
        if (i == -1) {
            return -1;
        }

        if (parent[i] == i) {
            return i;
        }

        return root(parent, parent[i]);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int k = 0; k < t; k++) {

            int n = Integer.parseInt(bufferedReader.readLine());

            int[] parent = new int[n];
            int[] size = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }

            ArrayList<Edge> graph = new ArrayList<>(n);
            ArrayList<Edge> mst = new ArrayList<>(n); // minimum spanning tree

            for (int i = 0; i < n; i++) {
                String[] line = bufferedReader.readLine().split(" ");
                for (int j = 0; j < line.length; j++) {
                    if (j > i) {
                        graph.add(new Edge(i, j, Integer.parseInt(line[j])));
                    }
                }
            }

            graph.sort(Comparator.comparingInt(edge -> edge.weight));

            for (Edge egde : graph) {
                if (root(parent, egde.source) != root(parent, egde.dest)) {
                    union(parent, size, egde.source, egde.dest);
                    mst.add(egde);
                }
            }

            mst.sort(Comparator.comparingInt(Edge::getSource).thenComparing(Edge::getDest));

            bufferedWriter.write("Case #" + (k + 1) + ":\n");
            for (Edge edge : mst) {
                bufferedWriter.write((edge.source + 1) + " " + (edge.dest + 1));
                bufferedWriter.newLine();
            }

            // empty line between tests
            if (k < t - 1) {
                bufferedReader.readLine();
            }

        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
