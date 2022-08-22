import java.io.*;
import java.util.Arrays;

public class A {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            bufferedReader.readLine();

            int[] as = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int res = as[0];
            for (int i = 1; i < as.length; ++i) {
                res = gcd(res, as[i]);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + res);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    // Euclidean Algorithm from the slides
    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
