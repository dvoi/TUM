import java.io.*;
import java.util.*;

public class A {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            int n = Integer.parseInt(bufferedReader.readLine());

            char[][] startedPainting = new char[n + 1][n + 1];

            for (int i = 1; i < n + 1; i++) {
                char[] line = bufferedReader.readLine().toCharArray();

                System.arraycopy(line, 0, startedPainting[i], 1, n + 1 - 1);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            // inspired by previously solved exercise:
            // https://leetcode.com/problems/n-queens/
            List<List<String>> res = new ArrayList<>();
            dfs(startedPainting, 1, res);

            StringBuilder sb = new StringBuilder();
            if (res.isEmpty()) {
                sb.append("impossible\n");
            } else {
                if (res.get(0).size() > 0) {
                    for (int i = 1; i < n + 1; i++) {
                        sb.append(res.get(0).get(i), 1, n + 1).append("\n");
                    }
                }
            }

            bufferedWriter.write("Case #" + (t + 1) + ":\n");
            bufferedWriter.write(sb.toString());
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static void dfs(char[][] board, int colIndex, List<List<String>> res) {

        if (!res.isEmpty()) {
            return;
        }

        if (colIndex == board.length) {

            List<String> result = new LinkedList<>();
            for (char[] chars : board) {
                String s = new String(chars);
                result.add(s);
            }

            res.add(result);
            return;
        }

        for (int i = 1; i < board.length; i++) {

            boolean queenAlready = board[i][colIndex] == 'x';

            if (validate(board, i, colIndex)) {
                board[i][colIndex] = 'x';
                dfs(board, colIndex + 1, res);
                if (!queenAlready) {
                    board[i][colIndex] = '.';
                }
            }
        }
    }

    private static boolean validate(char[][] board, int x, int y) {

        // vertical and horizontal
        for (int i = 0; i < board.length; i++) {
            if ((board[x][i] == 'x' && i != y) || (board[i][y] == 'x' && i != x)) {
                return false;
            }
        }

        // diagonal left-up
        for (int k = 1; k <= Math.min(x, y); k++) {
            if (board[x - k][y - k] == 'x') {
                return false;
            }
        }

        // diagonal right-up
        for (int k = 1; k < Math.min(x, board.length - y); k++) {
            if (board[x - k][y + k] == 'x') {
                return false;
            }
        }

        // diagonal left-down
        for (int k = 1; k < Math.min(board.length - x, y); k++) {
            if (board[x + k][y - k] == 'x') {
                return false;
            }
        }

        // diagonal right-down
        for (int k = 1; k < Math.min(board.length - x, board.length - y); k++) {
            if (board[x + k][y + k] == 'x') {
                return false;
            }
        }

        return true;
    }

}

