package jaccard3Refined;

import java.util.Scanner;

public class jaccard3Refined {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double T = scanner.nextDouble();
        int N = scanner.nextInt();
        scanner.nextLine(); 
        int[][] documents = new int[N][];

        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine().toLowerCase(); // Convert to lowercase
            documents[i] = splitAndHash(line);
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (jaccardSimilarity(documents[i], documents[j], T)) {
                    count++;
                }
            }
        }

        // Print the result
        System.out.println(count);

        scanner.close();
    }
////////////////////////////////////////////////////////////////////
    private static int[] splitAndHash(String line) {
        int[] words = new int[1000]; 
        int count = 0;
        boolean[] seen = new boolean[65536]; 

        int start = 0;
        for (int i = 0; i <= line.length(); i++) {
            if (i == line.length() || line.charAt(i) == ' ') {
                if (start < i) {
                    String word = line.substring(start, i);
                    int hash = hash(word);
                    if (!seen[hash]) {
                        seen[hash] = true;
                        words[count++] = hash;
                    }
                }
                start = i + 1;
            }
        }

        int[] result = new int[count];
        System.arraycopy(words, 0, result, 0, count); // Efficient array copy
        return result;
    }
////////////////////////////////////////////////////////////////////
///
///
   
    private static boolean jaccardSimilarity(int[] doc1, int[] doc2, double threshold) {
        int intersection = 0;
        boolean[] seen = new boolean[65536]; 
        int uniqueCount = 0;


        for (int hash : doc1) {
            if (!seen[hash]) {
                seen[hash] = true;
                uniqueCount++;
            }
        }

        for (int hash : doc2) {
            if (seen[hash]) {
                intersection++;
            } else {
                seen[hash] = true;
                uniqueCount++;
            }

            if ((double) intersection / uniqueCount <= threshold) {
                return false;
            }
        }

        return (double) intersection / uniqueCount > threshold;
    }

    private static int hash(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) {
            h = (h << 5) - h + s.charAt(i); 
        }
        return (h < 0) ? -h % 65536 : h % 65536;
    }
}