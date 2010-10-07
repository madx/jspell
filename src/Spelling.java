public class Spelling {

  private String word;

  /**
   * Creates a new spelling object for a given word.
   */
  public Spelling(String word) {
    this.word = '$' + word + '$';
  }

  /**
   * Checks a word agains a given dictionary
   */
  public WordList check(Dictionary dict) {
    WordList     trigrams;
    WordList     candidates;
    FrequencyMap common;

    trigrams   = Trigram.decompose(word);
    candidates = new WordList();
    common     = new FrequencyMap();

    for (String trigram : trigrams) {
      WordList words = dict.wordsWith(trigram);

      for (String word : words) {
        if (common.containsKey(word)) {
          common.put(word, common.get(word) +  1);
        } else {
          common.put(word, new Float(1));
        }
      }
    }

    for (String candidate : common.keySet()) {
      Float commonCount, jaccard;

      commonCount = Float.valueOf(common.get(candidate));
      jaccard     = commonCount /
                    (word.length() + candidate.length() - commonCount);
      if (jaccard > 0.2)
        candidates.add(candidate);
    }
    System.out.println(candidates);

    return new WordList();
  }
}
