public class SinePod extends Pod {
    public SinePod(String name, double a, double b, double c) {
        super(name, t -> a * Math.sin(b * t) + c);
    }
}
