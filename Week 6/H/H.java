import java.io.*;

public class H {

    private static int tools;

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {

            String[] wd = bufferedReader.readLine().split(" ");

            int width = Integer.parseInt(wd[0]);
            int depth = Integer.parseInt(wd[1]);

            char[][] field = new char[depth][width];
            boolean[][] visited = new boolean[depth][width];
            tools = 0;

            // initial position
            int y = 0;
            int x = 0;

            for (int j = 0; j < depth; j++) {
                char[] fields = bufferedReader.readLine().toCharArray();

                for (int i = 0; i < width; i++) {
                    field[j][i] = fields[i];

                    if (fields[i] == 'T') {
                        tools++;
                    }

                    if (fields[i] == 'L') {
                        y = j;
                        x = i;
                        field[j][i] = '_';
                        visited[j][i] = true;
                    }

                }
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + (canWalk(field, visited, y, x, 0) ? "yes" : "no"));
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    static boolean checker(char[][] field, boolean[][] visited, int y, int x, int toolsFound) {

        if (field[y][x] == '#') {
            return false;
        }

        if (!visited[y][x]) {

            visited[y][x] = true;
            int found = field[y][x] == 'T' ? 1 : 0;

            if (canWalk(field, visited, y, x, toolsFound + found)) {
                return true;
            }

            visited[y][x] = false;
        }

        return false;
    }

    private static boolean canWalk(char[][] field, boolean[][] visited, int y, int x, int toolsFound) {

        if (toolsFound == tools) {
            return true;
        }

        // up
        if (y - 1 >= 0) {
            if (checker(field, visited, y - 1, x, toolsFound)) {
                return true;
            }
        }

        // down
        if (y + 1 < field.length) {
            if (checker(field, visited, y + 1, x, toolsFound)) {
                return true;
            }
        }

        // left
        if (x - 1 >= 0) {
            if (checker(field, visited, y, x - 1, toolsFound)) {
                return true;
            }
        }

        // right
        if (x + 1 < field[0].length) {
            if (checker(field, visited, y, x + 1, toolsFound)) {
                return true;
            }
        }

        return false;
    }

}
