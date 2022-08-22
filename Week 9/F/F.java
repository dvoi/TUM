import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class F {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            int n = Integer.parseInt(bufferedReader.readLine());
            int[] candies = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            HashSet<Integer> combinations = new HashSet<>();

            for (int j = 0; j < Math.pow(2, n); j++) {
                int total = 1;

                for (int i = 0; i < n; i++) {
                    if (1 == ((j >> i) & 1)) {
                        total += candies[i];
                    }
                }

                combinations.add(total);
            }

            long res = 1;
            for (Integer combination : combinations) {
                res = lcm(res, combination);
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // Euclidean Algorithm from the slides
    static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Lowest Common Multiple:
    // https://proofwiki.org/wiki/Product_of_GCD_and_LCM
    static long lcm(long a, long b) {
        return a * (b / gcd(a, b)); // gotta be careful next time
    }
}
