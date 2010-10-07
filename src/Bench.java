import java.util.Stack;

public class Bench {
  private static Stack<Long> stack = new Stack<Long>();

  public static void start() {
    stack.push(System.currentTimeMillis());
  }

  public static Long stop() {
    return System.currentTimeMillis() - stack.pop();
  }
}
