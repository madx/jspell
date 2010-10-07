public class Bench {
  private static long start; // TODO: use a stack
  private static long stop;

  public static void start() {
    start = System.currentTimeMillis();
  }

  public static void stop() {
    stop = System.currentTimeMillis();
  }

  public static long getDelta() {
    return stop - start;
  }
}
