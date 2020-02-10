public class PiEstimator {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Exactly one argument required");
            }
            int darts = Integer.parseInt(args[0]);
          System.out.println(estimate(darts));
        } catch (NumberFormatException e) {
              System.err.println("Argument must be an integer");
            }
         catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static double estimate(int darts) {
      if (darts < 1){
        throw new IllegalArgumentException("At least one dart required");
      }
        double dartsThrown = 0;

        for (var i = 0; i <= darts; i++){
          if (Math.hypot(Math.random(), Math.random())<= 1){
            dartsThrown++;
          }
        }
        return 4 * ((double)dartsThrown) / darts;
    }
}
