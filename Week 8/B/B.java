import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class B {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] a = bufferedReader.readLine().split("");
            String[] b = bufferedReader.readLine().split("");

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            int max = 0;

            for (int i = 0; i < a.length; i++) {
                rotate(a);
                max = Math.max(max, lcs(a, b));
                max = Math.max(max, lcs(reverse(a), b));
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + max);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    static int lcs(String[] a, String[] b) {

        int[][] dp = new int[a.length + 1][b.length + 1];

        for (int i = 1; i <= a.length; i++) {
            for (int j = 1; j <= b.length; j++) {

                if (a[i - 1].equals(b[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                } else {

                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[a.length][b.length];
    }

    private static void rotate(String[] bracelet) {
        String first = bracelet[0];
        System.arraycopy(bracelet, 1, bracelet, 0, bracelet.length - 1);
        bracelet[bracelet.length - 1] = first;
    }

    private static String[] reverse(String[] bracelet) {
        ArrayList<String> list = new ArrayList<>(List.of(bracelet));
        Collections.reverse(list);
        return list.toArray(new String[0]);
    }
}