import java.io.*;
import java.util.Arrays;

public class E {

    // Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;

    // trie node
    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    }

    static TrieNode root;

    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    static void insert(String key) {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }

    static boolean minimax(TrieNode node, boolean maximizingPlayer) {

        if (node.isEndOfWord) {
            return maximizingPlayer;
        }

        if (maximizingPlayer) {

            for (TrieNode child : node.children) {
                if (child != null && minimax(child, false)) {
                    return true;
                }
            }
            return false;

        } else {

            for (TrieNode child : node.children) {
                if (child != null && !minimax(child, true)) {
                    return false;
                }
            }
            return true;
        }
    }


    static void scenario(boolean leaBegins, boolean winnerBegins, int n, StringBuilder sb) {

        boolean leaWon = minimax(root, leaBegins);

        for (int i = 1; i < n; i++) {
            if ((leaWon && winnerBegins) || (!winnerBegins && !leaWon)) {
                leaWon = minimax(root, true);
            } else {
                leaWon = minimax(root, false);
            }
        }

        if (leaWon) {
            sb.append("victory\n");
        } else {
            sb.append("defeat\n");
        }
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            var nw = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int n = nw[0];
            int w = nw[1];

            root = new TrieNode();

            for (int i = 0; i < w; i++) {
                String line = bufferedReader.readLine();
                insert(line);
            }

            // empty line between tests
//            if (t < tests - 1) {
//                bufferedReader.readLine();
//            }

            StringBuilder sb = new StringBuilder("Case #" + (t + 1) + ":\n");
            scenario(true, true, n, sb);
            scenario(true, false, n, sb);
            scenario(false, true, n, sb);
            scenario(false, false, n, sb);

            bufferedWriter.write(sb.toString());
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
