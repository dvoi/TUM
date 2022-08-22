import java.io.*;

// Modified insert from:
// https://www.geeksforgeeks.org/trie-insert-and-search/
public class A {

    // Alphabet size (# of symbols)
    static final int ALPHABET_SIZE = 26;

    // trie node
    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;
        boolean wordIncluded;

        TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    static TrieNode root;
    static int count;

    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    static void insert(String key) {

        int level;
        int length = key.length();
        int index;

        boolean isSubword = true;

        root.wordIncluded = false;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {

            char current = key.charAt(level);

            if (level > 0) {
                index = current - 'a';
            } else {
                index = current - 'A';
            }

            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = new TrieNode();

                // adding new letters => word is not yet in a trie => can't be a subword
                isSubword = false;
            }

            pCrawl = pCrawl.children[index];

            // check parts of longer names to match included shorter
            // current letter signifies an end of another word => subword found
            if (pCrawl.isEndOfWord && !pCrawl.wordIncluded) {
                count++;
                pCrawl.wordIncluded = true;
            }
        }

        // mark last node as leaf
        pCrawl.isEndOfWord = true;

        // check shorter names in longer
        // marked as a subword and not yet counted => increment and mark
        if (isSubword && !pCrawl.wordIncluded) {
            count++;
            pCrawl.wordIncluded = true;
        }
    }

    // Returns true if key presents in trie, else false
    static boolean search(String key) {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                return false;

            pCrawl = pCrawl.children[index];
        }

        return (pCrawl.isEndOfWord);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int tests = Integer.parseInt(bufferedReader.readLine());

        for (int t = 0; t < tests; t++) {
            int n = Integer.parseInt(bufferedReader.readLine());

            root = new TrieNode();
            count = 0;

            for (int i = 0; i < n; i++) {
                String contact = bufferedReader.readLine();
                insert(contact);
            }

            // empty line between tests
            if (t < tests - 1) {
                bufferedReader.readLine();
            }

            bufferedWriter.write("Case #" + (t + 1) + ": " + count);
            bufferedWriter.newLine();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}