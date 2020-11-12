import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

public class PodRace {
    public static Set<Pod> race(double distance, Set<Pod> racers, double timeSlice, double timeLimit) {
        if (distance <=0) {
            throw new IllegalArgumentException("Distance must be greater than zero.");
        }
        if (timeLimit < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }
        if (timeSlice > 10) {
            throw new IllegalArgumentException("Time slices must be a positive value less than 10.");
        }

        var distances = new HashMap<Pod, Double>();

        var winners = new HashSet<Pod>();
        for (var t = 0; t < timeLimit; t += timeSlice) {
            for (var p: racers) {
                var distanceForThisTimeSlice = p.distanceTraveled(t, t+timeSlice, 1);
                distances.put(p, distances.getOrDefault(p, 0.0) + distanceForThisTimeSlice);
                if (distances.get(p) >= distance) {
                    winners.add(p);
                }
            }
            if (winners.isEmpty() != true) {
                return winners;
            }
        }
        return winners;
    }
}
