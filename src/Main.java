import java.util.Set;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    Dictionary dict = null;
    Mistakes   mist = null;
    WordList   candidates;
    String     dictFile, fileToCorrect;
    Long       delta;

    if (args.length < 2) {
      System.err.println("Not enough arguments");
      System.err.println("usage: jspell <dict> <file>");
      System.exit(1);
    }

    dictFile      = args[0];
    fileToCorrect = args[1];

    try {
      System.out.print("Building dictionary... ");
      Bench.start();
      dict = new Dictionary(dictFile);
      delta = Bench.stop();
      System.out.println("Done, indexing " + dict.getWordCount() +
                         " words took " + delta + "ms");

    } catch (FileNotFoundException e) {
      System.err.println("File not found");
      System.exit(1);

    } catch (IOException e) {
      System.err.println("Read error");
      System.exit(1);
    }

    try {
      System.out.print("Reading mistake file... ");
      Bench.start();
      mist = new Mistakes(fileToCorrect);
      delta = Bench.stop();
      System.out.println("Done, took " + delta + "ms");

    } catch (FileNotFoundException e) {
      System.err.println("File not found");
      System.exit(1);

    } catch (IOException e) {
      System.err.println("Read error");
      System.exit(1);
    }

    Set<String> ws = mist.words();
    int success = 0, oneshot = 0, count = ws.size();
    Bench.start();
    for (String w : ws) {
      candidates = new Spelling(w).check(dict);
      String c = mist.correction(w);
      if (candidates.contains(c)) {
        if (candidates.getFirst().equals(c)) {
          oneshot++;
        } else {
        }
        success++;
      } else {
      }
    }
    delta = Bench.stop();
    System.out.printf("%d words out of %d corrected [%d%%], took %dms\n",
                      success, count, success * 100 / count, delta);

  }

}
