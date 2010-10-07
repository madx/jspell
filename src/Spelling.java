import java.util.Collections;
import java.util.Comparator;

public class Spelling {

  private String word;
  static public final int Candidates = 20;

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
    WordList   trigrams;
    CoupleList candidates;
    FloatMap   common;

    trigrams   = Word.decompose(word);
    candidates = new CoupleList();
    common     = new FloatMap();

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
      Couple couple;

      commonCount = Float.valueOf(common.get(candidate));
      jaccard     = commonCount /
                    (word.length() + candidate.length() - commonCount);
      if (jaccard > 0.2) {
        couple = new Couple(candidate, Word.levenshtein(candidate, word));
        candidates.add(couple);
      }
    }

    Collections.sort(candidates, new InvCoupleComparator());
    while (candidates.size() > Spelling.Candidates)
      candidates.removeLast();
    /* Collections.sort(candidates, new FrequencyComparator(dict)); */

    return candidates.members();
  }

  private class InvCoupleComparator implements Comparator<Couple> {
    public int compare(Couple c1, Couple c2) {
      if (c1.snd > c2.snd)
        return -1;
      else if (c1.snd < c2.snd)
        return 1;
      else
        return 0;
    }
  }

  private class FrequencyComparator implements Comparator<Couple> {
    private Dictionary dict;

    public FrequencyComparator(Dictionary dict) {
      this.dict = dict;
    }

    public int compare(Couple c1, Couple c2) {
      double f1 = dict.freqFor(c1.fst),
             f2 = dict.freqFor(c2.fst);

      if (f1 > f2)
        return -1;
      else if (f1 < f2)
        return 1;
      else
        return 0;
    }
  }
}
