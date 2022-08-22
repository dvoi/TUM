import java.io.*;

public class E {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferedReader.readLine());
        long[] ans = new long[t];

        for (int i = 0; i < t; i++) {

            String s = bufferedReader.readLine();

            if (s.matches("\\d*")) {
                ans[i] = Integer.parseInt(s);
                continue;
            }

            String[] operations = s.replaceAll("[^a-z]", " ").trim().split("\\s+");
            String[] operands = s.replaceAll("[^0-9]", " ").split("\\s+");

            long result = Integer.parseInt(operands[0]);

            for (int j = 0; j < operations.length; j++) {

                long operand = Integer.parseInt(operands[j + 1]);
                String operation = operations[j];

                switch (operation) {
                    case "plus":
                        result += operand;
                        break;
                    case "minus":
                        result -= operand;
                        break;
                    case "times":
                        result *= operand;
                        break;
                    case "tothepowerof":
                        result = (long) Math.pow(result, operand);
                        break;
                }
            }

            ans[i] = result;
        }

        for (int i = 0; i < ans.length; i++) {
            bufferedWriter.write("Case #" + (i + 1) + ": " + ans[i]);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

}
