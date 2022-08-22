import java.io.*;
import java.util.Arrays;

public class G {

    // Manchester with working mirror
    // https://leetcode.com/problems/longest-palindromic-substring/discuss/388610/Java-solution-using-Manacher's-algorithm
    public static String preProcess(String s) {
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append('^');
        for (int i = 0; i < length; i++) {
            sb.append('#');
            sb.append(s.charAt(i));
        }
        sb.append("#$");
        return sb.toString();
    }

    public static int[] manchester(String s) {
        String t = preProcess(s);
        int length = t.length();
        int[] len = new int[length];
        int maxLength = 0, index = 0, left = 0, right = 0;
        int C = 0, R = 0;
        for (int i = 1; i < length - 1; i++) {
            // avoid (2 * C - i) overflow
            int j = C - (i - C);
            if (i < R)
                len[i] = Math.min(len[j], R - i);
            while (t.charAt(i + len[i]) == t.charAt(i - len[i]))
                len[i]++;
            if (i >= R) {
                C = i;
                R = i + len[i];
            }
            if (len[i] > maxLength) {
                maxLength = len[i] - 1;
                index = i;
            }
        }
        left = (index - maxLength - 1) / 2;
        right = left + maxLength;

        return new int[]{left, right};
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var np = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = np[0];
            int p = np[1];

            String s = bufferedReader.readLine();

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            var res = manchester(s);

            int a = res[0];
            int b = res[1];

            bufferedWriter.write("Case #" + (t + 1) + ": " + (b - a < p ? "none" : ((a + 1) + " " + b)));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
