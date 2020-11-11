import java.util.*;

public class SharedBirthday {

    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException("Exactly three arguments required");
            }
            int people = Integer.parseInt(args[0]);
            int days = Integer.parseInt(args[1]);
            int trials = Integer.parseInt(args[2]);
            System.out.println(probabilityEstimate(people, days, trials));
        } catch (NumberFormatException e) {
            System.err.println("Arguments must all be integers");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static double probabilityEstimate(int people, int days, int trials) {
        if (people < 2) {
            throw new IllegalArgumentException("At least two people required");
        }
        if (trials < 1) {
            throw new IllegalArgumentException("At least one trial required");
        }
        if (days < 1) {
            throw new IllegalArgumentException("At least one day required");
        };
        double success = 0;
        for (var i = 0; i < trials; i++) {
            var birthdays = new HashSet<Integer>();            
            for (var j = 0; j < people; j++) {
                int birthday = (int)Math.floor(Math.random() * days);              
                if (birthdays.contains(birthday)) {
                    success++;
                    break;
                }
                birthdays.add(birthday);
            }
        }
        return success / (double)trials;
    }
}
