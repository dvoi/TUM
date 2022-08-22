import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

public class D {

    static class Tournament {

        private final int start;
        private final int end;
        private final int prize;

        public Tournament(int start, int end, int prize) {
            this.start = start;
            this.end = end;
            this.prize = prize;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int getPrize() {
            return prize;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            int n = Integer.parseInt(bufferedReader.readLine());
            Tournament[] tournaments = new Tournament[n];

            for (int i = 0; i < n; i++) {
                String[] abp = bufferedReader.readLine().split(" ");

                int a = Integer.parseInt(abp[0]);
                int b = Integer.parseInt(abp[1]);
                int p = Integer.parseInt(abp[2]);

                tournaments[i] = new Tournament(a, b, p);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            Arrays.sort(tournaments, Comparator.comparing(Tournament::getEnd));
            int latestEndTime = tournaments[n - 1].getEnd() + 1;

            int[][] dp = new int[n][latestEndTime];

            int firstEnd = tournaments[0].getEnd();
            int firstPrize = tournaments[0].getPrize();

            // Longest Common Sequence:
            // https://www.programiz.com/dsa/longest-common-subsequence
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < latestEndTime; j++) {
                    if (i == 0 || j == 0) {

                        // cells for the first intermediate dp results
                        if (j >= firstEnd) {
                            dp[0][j] = firstPrize;
                        } else {
                            dp[i][j] = 0;
                        }

                    } else if (j >= tournaments[i].getEnd()) {

                        int currentStart = tournaments[i].getStart();
                        int currentPrize = tournaments[i].getPrize();

                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][currentStart - 1] + currentPrize);

                    } else {

                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + dp[tournaments.length - 1][latestEndTime - 1]);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
