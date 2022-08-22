import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class F {

    static class Time {

        private final int ht;
        private final int hu;
        private final int mt;
        private final int mu;

        public Time(int ht, int hu, int mt, int mu) {
            this.ht = ht;
            this.hu = hu;
            this.mt = mt;
            this.mu = mu;
        }

        public int getHt() {
            return ht;
        }

        public int getHu() {
            return hu;
        }

        public int getMt() {
            return mt;
        }

        public int getMu() {
            return mu;
        }
    }



    public static byte n0 = (1 << 0) + (1 << 1) + (1 << 2) + (0 << 3) + (1 << 4) + (1 << 5) + (1 << 6);
    public static byte n1 = (0 << 0) + (0 << 1) + (1 << 2) + (0 << 3) + (0 << 4) + (1 << 5) + (0 << 6);
    public static byte n2 = (1 << 0) + (0 << 1) + (1 << 2) + (1 << 3) + (1 << 4) + (0 << 5) + (1 << 6);
    public static byte n3 = (1 << 0) + (0 << 1) + (1 << 2) + (1 << 3) + (0 << 4) + (1 << 5) + (1 << 6);
    public static byte n4 = (0 << 0) + (1 << 1) + (1 << 2) + (1 << 3) + (0 << 4) + (1 << 5) + (0 << 6);
    public static byte n5 = (1 << 0) + (1 << 1) + (0 << 2) + (1 << 3) + (0 << 4) + (1 << 5) + (1 << 6);
    public static byte n6 = (1 << 0) + (1 << 1) + (0 << 2) + (1 << 3) + (1 << 4) + (1 << 5) + (1 << 6);
    public static byte n7 = (1 << 0) + (0 << 1) + (1 << 2) + (0 << 3) + (0 << 4) + (1 << 5) + (0 << 6);
    public static byte n8 = (1 << 0) + (1 << 1) + (1 << 2) + (1 << 3) + (1 << 4) + (1 << 5) + (1 << 6);
    public static byte n9 = (1 << 0) + (1 << 1) + (1 << 2) + (1 << 3) + (0 << 4) + (1 << 5) + (1 << 6);



    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            List<Time> result = new ArrayList<>();
            List<Time> times = new ArrayList<>();

            int[][] sevenSegments = new int[4][7];
            for (int i = 0; i < 4; i++) {
                Arrays.fill(sevenSegments[i], -1);
            }

            for (int i = 0; i < n; i++) {
                String[] xxxx = bufferedReader.readLine().split("");

                int ht = Integer.parseInt(xxxx[0]);
                int hu = Integer.parseInt(xxxx[1]);
                int mt = Integer.parseInt(xxxx[3]);
                int mu = Integer.parseInt(xxxx[4]);

                times.add(new Time(ht, hu, mt, mu));
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            findTime(result, times, new LinkedList<>(), sevenSegments, n, 0);

            bufferedWriter.write("Case #" + (t + 1) + ":\n");

            if (result.isEmpty()) {
                bufferedWriter.write("none");
                bufferedWriter.newLine();
            }

            for (Time time : result) {
                bufferedWriter.write(time.getHt() + "" + time.getHu() + ":" + time.getMt() + "" + time.getMu());
                bufferedWriter.newLine();
            }
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    public static List<Integer> confusedWith(int n) {
        switch (n) {
            case 0: return List.of(0, 8);
            case 1: return List.of(0, 1, 3, 4, 7, 8, 9);
            case 2: return List.of(2, 8);
            case 3: return List.of(3, 8, 9);
            case 4: return List.of(4, 8, 9);
            case 5: return List.of(5, 6, 8, 9);
            case 6: return List.of(6, 8);
            case 7: return List.of(0, 3, 7, 8, 9);
            case 8: return List.of(8);
            default: return List.of(8, 9);
        }
    }

    static List<Integer> bcd(int n) {
        switch (n) {
            case 0: return List.of(0, 1, 2, 4, 5, 6);
            case 1: return List.of(2, 5);
            case 2: return List.of(0, 2, 3, 4, 6);
            case 3: return List.of(0, 2, 3, 5, 6);
            case 4: return List.of(1, 2, 3, 5);
            case 5: return List.of(0, 1, 3, 5, 6);
            case 6: return List.of(0, 1, 3, 4, 5, 6);
            case 7: return List.of(0, 2, 5);
            case 8: return List.of(0, 1, 2, 3, 4, 5, 6);
            default: return List.of(0, 1, 2, 3, 5, 6);
        }
    }

    public static void findTime(List<Time> result, List<Time> times, LinkedList<Time> currentTimes, int[][] sevenSegments, int n, int pos) {

        if (pos == n) {
            Time t = currentTimes.peek();
            for (Time time : result) {
                if (time.getHt() == t.getHt() && time.getHu() == t.getHu() && time.getMt() == t.getMt() && time.getMu() == t.getMu()) {
                    return;
                }
            }
            result.add(t);
            return;
        }

        Time now = times.get(pos);
        int[] digits = new int[]{now.getHt(), now.getHu(), now.getMt(), now.getMu()};

        List<List<Integer>> alternatives = new ArrayList<>();
        ArrayList<Integer> possibleAlternative = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            possibleAlternative.add(0);
        }

        for (int l : confusedWith(digits[0])) {
            possibleAlternative.set(0, l);
            for (int k : confusedWith(digits[1])) {
                possibleAlternative.set(1, k);
                for (int j : confusedWith(digits[2])) {
                    possibleAlternative.set(2, j);
                    for (int i : confusedWith(digits[3])) {
                        possibleAlternative.set(3, i);
                        List<Integer> temp = List.copyOf(possibleAlternative);
                        alternatives.add(temp);
                    }
                }
            }
        }

        for (List<Integer> alternative : alternatives) {

            Time time = new Time(alternative.get(0), alternative.get(1), alternative.get(2), alternative.get(3));

            if (isValid(time, currentTimes)) {

                List<List<Integer>> assumptions = new ArrayList<>();

                currentTimes.add(time);
                boolean valid = true;

                for (int i = 0; i < 4; i++) {

                    List<Integer> assumption = new ArrayList<>();

                    if (alternative.get(i) != digits[i]) {

                        List<Integer> first = bcd(alternative.get(i));
                        List<Integer> second = bcd(digits[i]);

                        for (Integer bar : first) {

                            assumption.add(i);
                            assumption.add(bar);
                            assumption.add(sevenSegments[i][bar]);
                            assumptions.add(assumption);
                            assumption = new ArrayList<>();

                            if (!second.contains(bar)) {

                                if (sevenSegments[i][bar] == 1) {
                                    set(currentTimes, assumptions, sevenSegments);
                                    valid = false;
                                    break;
                                }

                                sevenSegments[i][bar] = 0;

                            } else {

                                if (sevenSegments[i][bar] == 0) {
                                    set(currentTimes, assumptions, sevenSegments);
                                    valid = false;
                                    break;
                                }

                                sevenSegments[i][bar] = 1;

                            }
                        }

                    } else {

                        List<Integer> segment = bcd(digits[i]);

                        for (Integer bar : segment) {

                            assumption.add(i);
                            assumption.add(bar);
                            assumption.add(sevenSegments[i][bar]);
                            assumptions.add(assumption);

                            assumption = new ArrayList<>();

                            if (sevenSegments[i][bar] == 0) {
                                set(currentTimes, assumptions, sevenSegments);
                                valid = false;
                                break;
                            }

                            sevenSegments[i][bar] = 1;

                        }
                    }

                    if (!valid) {
                        break;
                    }

                }

                if (valid) {
                    findTime(result, times, currentTimes, sevenSegments, n, pos + 1);
                    set(currentTimes, assumptions, sevenSegments);
                }

            }
        }
    }

    static boolean isValid(Time time, LinkedList<Time> current) {

        if (time.getHt() * 10 + time.getHu() > 23 || time.getMt() * 10 + time.getMu() > 59) {
            return false;
        }

        if (current.isEmpty()) {
            return true;
        }

        Time difference = current.peekLast();

        if ((difference.getHt() * 10 + difference.getHu()) == (time.getHt() * 10 + time.getHu()) && (time.getMt() * 10 + time.getMu()) == (difference.getMt() * 10 + difference.getMu() + 1)) {
            return true;
        }

        if (time.getHt() * 10 + time.getHu() == difference.getHt() * 10 + difference.getHu() + 1 && time.getMt() * 10 + time.getMu() == 0 && difference.getMt() * 10 + difference.getMu() == 59) {
            return true;
        }

        return time.getHt() * 10 + time.getHu() != 0 && time.getMt() * 10 + time.getMu() != 0 && difference.getHt() * 10 + difference.getHu() == 23 && difference.getMt() * 10 + difference.getMu() == 59;
    }

    static void set(LinkedList<Time> current, List<List<Integer>> assumptions, int[][] sevenSegments) {

        for (List<Integer> assumption : assumptions) {
            sevenSegments[assumption.get(0)][assumption.get(1)] = assumption.get(2);
        }

        current.pollLast();
    }


}
