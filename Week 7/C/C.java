import java.io.*;
import java.util.*;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            int n = Integer.parseInt(bufferedReader.readLine());

            HashSet<Integer> firstHalf = new HashSet<>();
            HashSet<Integer> secondHalf = new HashSet<>();

            HashMap<Integer, ArrayList<Integer>> roads = new HashMap<>();

            for (int i = 1; i <= n; i++) {
                firstHalf.add(i);
                roads.put(i, new ArrayList<>());

                // first element is k
                String[] cities = bufferedReader.readLine().split(" ");

                for (int j = 1; j < cities.length; j++) {
                    int c = Integer.parseInt(cities[j]);
                    roads.get(i).add(c);
                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // Max-Cut Problem
            // https://en.wikipedia.org/wiki/Maximum_cut
            for (int i = 0; i < n; i++) {

                boolean cityInFirst = firstHalf.contains(i);

                int first = connectedNeighboursInCountry(i, firstHalf, roads);
                int second = connectedNeighboursInCountry(i, secondHalf, roads);

                if (cityInFirst) {

                    if (first > second) {
                        firstHalf.remove(i);
                        secondHalf.add(i);
                    }

                } else {

                    if (second > first) {
                        secondHalf.remove(i);
                        firstHalf.add(i);
                    }
                }
            }

            StringBuilder sb = new StringBuilder();

            for (Integer city : firstHalf) {
                sb.append(city).append(" ");
            }

            bufferedWriter.write("Case #" + (t + 1) + ":");
            bufferedWriter.newLine();

            bufferedWriter.write(sb.substring(0, sb.length() - 1));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static int connectedNeighboursInCountry(int city, HashSet<Integer> country, HashMap<Integer, ArrayList<Integer>> roads) {

        int count = 0;

        if (!roads.containsKey(city)) {
            return count;
        }

        for (Integer c : roads.get(city)) {
            if (country.contains(c)) {
                count++;
            }
        }

        return count;
    }
}
