import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());

        for (int j = 0; j < t; j++) {

            String[] abc = bufferedReader.readLine().split(" ");
            int a = Integer.parseInt(abc[0]);
            int b = Integer.parseInt(abc[1]); // number of family relations
            int c = Integer.parseInt(abc[2]); // number of marriages

            // 1-indexed, so 0 = 1, 1 = 2 and so on!
            String[] money = bufferedReader.readLine().split(" ");

            int[] parent = new int[a + 1];
            Arrays.fill(parent, -1);

            boolean[] isMarried = new boolean[a + 1];
            Arrays.fill(isMarried, false);

            for (int i = 0; i < b; i++) {

                String[] relation = bufferedReader.readLine().split(" ");

                int first = Integer.parseInt(relation[0]);
                int second = Integer.parseInt(relation[1]);

                union(parent, first, second);
            }

            for (int i = 0; i < c; i++) {

                String[] marriage = bufferedReader.readLine().split(" ");

                int first = Integer.parseInt(marriage[0]);
                int second = Integer.parseInt(marriage[1]);

                union(parent, first, second);

                isMarried[first] = true;
                isMarried[second] = true;
            }

            // empty line between tests
            if (j < t - 1) {
                bufferedReader.readLine();
            }

            int maxMoney = -1;

            int p = find(parent, a);

            for (int i = 1; i < a; i++) {

                int toMarry = find(parent, i);

                if (toMarry != p && !isMarried[i]) {
                    maxMoney = Math.max(maxMoney, Integer.parseInt(money[i - 1]));
                }
            }

            bufferedWriter.write("Case #" + (j + 1) + ": " + (maxMoney > -1 ? maxMoney : "impossible"));
            bufferedWriter.newLine();

        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // https://algorithmist.com/wiki/Union_find
    public static int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }

    public static void union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rootX != rootY) {
            parent[rootX] = rootY;
        }
    }
}
