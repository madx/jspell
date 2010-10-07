public class Trigram {
  public static final WordList decompose(String word) {
    WordList trigrams = new WordList();
    int limit = word.length() - 2;

    for (int i = 0; i < limit; i++)
      trigrams.add(word.substring(i, i+3));

    return trigrams;
  }
}
