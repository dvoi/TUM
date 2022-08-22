import java.io.*;
import java.util.*;

public class D {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());
        List<List<Integer[]>> ans = new ArrayList<>();

        for (int i = 0; i < t; i++) {
            ans.add(new ArrayList<>());
            int n = Integer.parseInt(bufferedReader.readLine());

            for (int k = 0; k < n; k++) {
                String[] s = bufferedReader.readLine().split(" ");
                ans.get(i).add(new Integer[]{
                        Integer.parseInt(s[0]),
                        Integer.parseInt(s[1]),
                        Integer.parseInt(s[2]),
                        Integer.parseInt(s[3]),
                        Integer.parseInt(s[4])
                });
            }
            if (i < t - 1) {
                bufferedReader.readLine();
            }
        }

        for (List<Integer[]> an : ans) {
            for (Integer[] ints : an) {
                Arrays.sort(ints, Collections.reverseOrder());
            }
            an.sort(D::compareSchools);
            Collections.reverse(an);
        }


        for (int i = 0; i < ans.size(); i++) {
            bufferedWriter.write("Case #" + (i + 1) + ": ");
            bufferedWriter.newLine();
            for (Integer[] ints : ans.get(i)) {
                bufferedWriter.write(Arrays.toString(ints)
                              .replace("[", "")
                              .replace("]", "")
                              .replace(", ", " ")
                );
                bufferedWriter.newLine();
            }
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    public static int compareSchools(Integer[] a, Integer[] b) {
        for (int i = 0; i < 5; i++) {
            if (a[i] > b[i]) {
                return 1;
            }
            if (a[i] < b[i]) {
                return -1;
            }
        }
        return 0;
    }


}
