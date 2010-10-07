import java.util.LinkedList;

public class CoupleList extends LinkedList<Couple> {
  public WordList members() {
    WordList members = new WordList();

    for(Couple c : this) {
      members.add(c.fst.substring(1, c.fst.length() - 1));
    }

    return members;
  }
}
