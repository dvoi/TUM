import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class A {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());
        List<String> list = new ArrayList<>();

        for (int i = 0; i < t; i++) {
            list.add(bufferedReader.readLine());
        }

        for (int i = 0; i < list.size(); i++) {
            bufferedWriter.write("Case #" + (i + 1) + ": Hello " + list.get(i) + "!");
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
