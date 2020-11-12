public class PolynomialPod extends Pod {
    public PolynomialPod(String name, double... coefficients) {
        super(name, t -> evaluatePolynomial(coefficients, t));
    }

    private static double evaluatePolynomial(double[] coefficients, double x) {
        var result = 0.0;
        for (var i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }
}
