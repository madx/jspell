public class Word {

  public static final WordList decompose(String word) {
    WordList trigrams = new WordList();
    int limit = word.length() - 2;

    for (int i = 0; i < limit; i++)
      trigrams.add(word.substring(i, i+3));

    return trigrams;
  }

  public static final double levenshtein(String first, String second) {
    int m = first.length();
    int n = second.length();
    int[][] L = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) L[i][0] = i; // deletion
    for (int j = 0; j <= n; j++) L[0][j] = j; // insertion
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (first.charAt(i - 1) == second.charAt(j - 1)) L[i][j] = L[i - 1][j - 1];
            else L[i][j] = min3(L[i - 1][j] + 1, // deletion
                                L[i][j - 1] + 1, // insertion
                                L[i - 1][j - 1] + 1); // substitution
    return 1.0 - L[m][n]*1.0/Math.max(m, n);
  }

  private static final int min3(int a, int b, int c) {
    if (a < b) return (a < c) ? a : c;
    else       return (b < c) ? b : c;
  }
}

