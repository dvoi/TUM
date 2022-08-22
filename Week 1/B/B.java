import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class B {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());
        List<ArrayList<String>> tests = new ArrayList<>();

        for (int i = 0; i < t; i++) {
            tests.add(new ArrayList<>());
            int n = Integer.parseInt(bufferedReader.readLine());
            for (int j = 0; j < n; j++) {
                String line = bufferedReader.readLine();
                StringBuilder sb = new StringBuilder();

                for (String s : line.split(" ")) {
                    if (s.contains("entin")) {
                        s = s.replace("entin", "ierende");
                    }

                    if (s.contains("enten")) {
                        s = s.replace("enten", "ierende");
                    }

                    if (s.contains("ent")) {
                        s = s.replace("ent", "ierender");
                    }

                    sb.append(s).append(" ");
                }

                tests.get(i).add(sb.toString().trim());
            }
        }



        for (int i = 0; i < tests.size(); i++) {
            bufferedWriter.write("Case #" + (i + 1) + ":");
            bufferedWriter.newLine();
            for (String s : tests.get(i)) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
        }

        bufferedReader.close();
        bufferedWriter.close();

    }
}
