import java.io.*;
import java.util.Arrays;

public class E {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            int[] friends = new int[n + 1];
            int[] enemies = new int[n + 1];
            int[] size = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                friends[i] = i;
                enemies[i] = -1;
                size[i] = 1;
            }

            for (int i = 0; i < m; i++) {

                String[] relation = bufferedReader.readLine().split(" ");

                int countryA = Integer.parseInt(relation[1]);
                int countryB = Integer.parseInt(relation[2]);

                int aRoot = root(friends, countryA);
                int bRoot = root(friends, countryB);

                int aEn = root(friends, enemies[aRoot]);
                int bEn = root(friends, enemies[bRoot]);

                if (relation[0].charAt(0) == 'F') {

                    union(friends, size, aRoot, bRoot);

                    if (aEn == -1) {
                        enemies[aRoot] = bEn;
                    }

                    if (bEn == -1) {
                        enemies[bRoot] = aEn;
                    }

                    if (aEn != -1 && bEn != -1) {
                        union(friends, size, aEn, bEn);
                    }

                } else {

                    if (enemies[aRoot] == -1) {
                        enemies[aRoot] = bRoot;
                    }

                    if (enemies[bRoot] == -1) {
                        enemies[bRoot] = aRoot;
                    }

                    if (enemies[aRoot] != -1 && enemies[bRoot] != -1) {
                        union(friends, size, enemies[aRoot], bRoot);
                        union(friends, size, enemies[bRoot], aRoot);
                    }

                }
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            int counter = 1;

            int rootLea = root(friends, 1);
            for (int i = 2; i <= n; i++) {
                if (rootLea == root(friends, i)) {
                    counter++;
                }
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + (counter > n / 2 ? "yes" : "no"));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();

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

    private static void union(int[] parent, int[] size, int countryA, int countryB) {

        // improvement from O(n) to O(log n):
        // https://dou.ua/lenta/articles/union-find/

        int rootA = root(parent, countryA);
        int rootB = root(parent, countryB);

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
}
