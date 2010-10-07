import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    Dictionary dict = null;
    WordList   candidates;
    String     dictFile, fileToCorrect;

    if (args.length < 1) {
      System.err.println("Not enough arguments");
      System.err.println("usage: jspell <dict> <file>");
      System.exit(1);
    }

    dictFile      = args[0];
    /* fileToCorrect = args[1]; */

    try {
      System.out.print("Building dictionary... ");
      Bench.start();
      dict = new Dictionary(dictFile);
      Bench.stop();
      System.out.println("Done, indexing " + dict.getWordCount() +
                         " words took " + Bench.getDelta() + "ms");

    } catch (FileNotFoundException e) {
      System.err.println("Dictionnaire introuvable");
      System.exit(1);

    } catch (IOException e) {
      System.err.println("Erreur à la lecture du dictionnaire");
      System.exit(1);
    }

    candidates = new Spelling("acceuil").check(dict);
  }

}
