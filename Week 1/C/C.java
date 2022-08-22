import java.io.*;

public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());
        long[] ans = new long[t];

        for (int i = 0; i < t; i++) {
            ans[i] = Integer.parseInt(bufferedReader.readLine()) * 89875517873681764L;
        }

        for (int i = 0; i < t; i++) {
            bufferedWriter.write("Case #" + (i + 1) + ": " + ans[i]);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
