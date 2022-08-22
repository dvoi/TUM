import java.io.*;
import java.util.Arrays;

public class A {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] nc = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nc[0]); // number of coin/note values
            int c = Integer.parseInt(nc[1]); // amount to be spent

            int[] values = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            int[] dp = new int[c + 1];
            int[] used = new int[c + 1];
            int[] result = new int[n];

            for (int i = 0; i < c + 1; i++) {
                for (int j = 0; j < n; j++) {
                    int k = i + values[j];

                    if (k < c + 1) {
                        if (dp[k] == 0 || dp[k] > dp[i] + 1) {

                            dp[k] = dp[i] + 1;
                            used[k] = j;
                        }
                    }
                }
            }

            while (c > 0) {
                int index = used[c];
                result[index]++;
                c -= values[index];
            }

            StringBuilder sb = new StringBuilder();
            for (int value : result) {
                sb.append(value).append(" ");
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + sb);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
