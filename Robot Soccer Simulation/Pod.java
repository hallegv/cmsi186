import java.util.function.Function;

public class Pod {
    private String name;
    private Function<Double, Double> velocityFunction;

    public Pod(String name, Function<Double, Double> velocityFunction) {
        this.name = name;
        this.velocityFunction = velocityFunction;
    }

    public String getName() {
        return name;
    }

    public double v(double t) {
        return velocityFunction.apply(t);
    }

    public double distanceTraveled(double startTime, double endTime, double slices) {
      if (slices < 1) {
        throw new IllegalArgumentException("At least one slice required");
      }
      var distance = 0.0;
      var timeSlice = (endTime - startTime) / slices;
      for (var i = 0; i < slices; i++) {
        var t = startTime + i * timeSlice;
        distance += v(t) * timeSlice;
      }
      return distance;
    }
}
