import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class E {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            String[] nm = bufferedReader.readLine().split(" ");

            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            ArrayList<String>[] human = new ArrayList[n];
            ArrayList<String>[] mice = new ArrayList[m];

            for (int i = 0; i < n; i++) {
                String[] humanSequences = bufferedReader.readLine().split("");

                human[i] = new ArrayList<>();

                for (String letter : humanSequences) {
                    human[i].add(letter);
                }
            }

            for (int i = 0; i < m; i++) {
                String[] miceSequences = bufferedReader.readLine().split("");

                mice[i] = new ArrayList<>();

                for (String letter : miceSequences) {
                    mice[i].add(letter);
                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // BioScore:
            // https://www.topcoder.com/thrive/articles/Greedy%20is%20Good
            int score = 0;
            Integer[] scores = new Integer[10];
            Integer[] combinations = new Integer[10];

            for (int i = 0; i < 10; i++) {
                combinations[i] = 0;
            }

            for (ArrayList<String> humanSequence : human) {
                for (ArrayList<String> miceSequence : mice) {
                    for (int i = 0; i < humanSequence.size(); i++) {
                        combinations[index(humanSequence.get(i) + miceSequence.get(i))]++;
                    }
                }
            }

            // sort in DESCENDING ORDER
            Arrays.sort(combinations, 4, combinations.length, Collections.reverseOrder());

            for (int A = 0; A < 11; A++) {
                for (int C = 0; C < 11; C++) {
                    for (int T = 0; T < 11; T++) {
                        for (int G = 0; G < 11; G++) {

                            if ((A + C + T + G) % 2 != 0) {
                                continue;
                            }

                            int newScore = 0;

                            scores[0] = A;
                            scores[1] = C;
                            scores[2] = T;
                            scores[3] = G;

                            scores[4] = 10;
                            scores[5] = 10;

                            scores[6] = 10 - ((A + C + T + G) / 2);

                            scores[7] = -10;
                            scores[8] = -10;
                            scores[9] = -10;

                            for (int i = 0; i < 10; i++) {
                                newScore += combinations[i] * scores[i];
                            }

                            score = Math.max(score, newScore);
                        }
                    }
                }
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + score);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }


    private static int index(String combination) {
        switch (combination) {
            case "AA": return 0;
            case "CC": return 1;
            case "TT": return 2;
            case "GG": return 3;
            case "AC": case "CA": return 4;
            case "AT": case "TA": return 5;
            case "AG": case "GA": return 6;
            case "CT": case "TC": return 7;
            case "CG": case "GC": return 8;
            case "TG": case "GT": return 9;
            default: return -1;
        }
    }
}
