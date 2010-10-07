import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Dictionary {

  private FrequencyMap fMap;
  private TrigramMap   tMap;
  private int          wordCount;

  public Dictionary(String dictFile)
    throws FileNotFoundException, IOException
  {
    BufferedReader reader;
    String         line;

    fMap      = new FrequencyMap();
    tMap      = new TrigramMap();
    reader    = new BufferedReader(new FileReader(dictFile));
    wordCount = 0;

    while((line = reader.readLine()) != null) {
      StringTokenizer tok  = new StringTokenizer(line, "\t");
      String          word = '$' + tok.nextToken() + '$';
      Float           freq = Float.valueOf(tok.nextToken());

      fMap.put(word, freq);

      WordList trigrams = Trigram.decompose(word);

      for (String trigram : trigrams) {
        if (tMap.containsKey(trigram)) {
          WordList values = tMap.get(trigram);
          values.add(word);
        } else {
          WordList values = new WordList();
          values.add(word);
          tMap.put(trigram, values);
        }
      }

      wordCount++;
    }
  }

  public WordList wordsWith(String trigram) {
    if (tMap.containsKey(trigram))
      return tMap.get(trigram);
    else
      return new WordList();
  }

  public int getWordCount() {
    return wordCount;
  }
}
