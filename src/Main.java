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
    int success = 0, perfects = 0, count = ws.size();
    Long totalTime = new Long(0);

    for (String w : ws) {

      Bench.start();
      candidates = new Spelling(w).check(dict);
      delta = Bench.stop();
      totalTime += delta;

      String c = mist.correction(w);
      if (candidates.contains(c)) {
        if (candidates.getFirst().equals(c)) {
          perfects++;
          System.out.println("Perfect: "+w+" ["+delta+"ms]");
        } else {
          System.out.println("Ok: "+w+ " ("+c+") ["+delta+"ms]");
        }
        success++;
      } else {
        System.out.println("Fail: "+w+" ("+c+") ["+delta+"ms] "+candidates);
      }
    }

    System.out.printf("%d (%d perfects) words out of %d corrected [%d%%], took %dms\n",
                      success, perfects, count, success * 100 / count, totalTime);

  }

}
