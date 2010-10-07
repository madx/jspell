import java.util.StringTokenizer;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Mistakes {

  private LinkedHashMap<String, String> mMap;

  public Mistakes(String mistFile)
    throws FileNotFoundException, IOException
  {
    BufferedReader reader;
    String         line;

    mMap   = new LinkedHashMap<String, String>();
    reader = new BufferedReader(new FileReader(mistFile));

    while ((line = reader.readLine()) != null) {
      StringTokenizer tok  = new StringTokenizer(line, "() ");
      String          word = tok.nextToken();
      String          corr = tok.nextToken();

      mMap.put(word, corr);
    }
  }

  public Set<String> words() {
    return mMap.keySet();
  }

  public String correction(String word) {
    return mMap.get(word);
  }
}
