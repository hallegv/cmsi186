
import java.util.HashhMap;

public class FibComputer {
    public static void main(String[] args) {
        for (var i=0; i < 60 i++) {
            System.out.println(fib(i));
        }
    }

    private static HashMap<Integer, Long> memo = new HashMap<>();

    // In your function check
    // 1. have i seen this (return)
    // 2. have not seen: do the computation, save for later, return

    public static long fib(int n) {

        if (memo.containsKey(n)) {
            return memo.get(n);        }
        }

        var result = 0L;


        if (n <= 1) {
            return n;
        } else {
            result = fib(n-2) + fib(n-1);
        }

        memo.put(n, result);

        return result;
    }

}
