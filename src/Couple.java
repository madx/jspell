public class Couple {
  public String fst;
  public double snd;

  public Couple(String fst, double snd) {
    this.fst = fst;
    this.snd = snd;
  }

  public String toString() {
    return "<"+ fst +","+ snd +">";
  }
}
