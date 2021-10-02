package SmallerBetter;

abstract class Equation {

    public abstract long evaluate(long x);
}

class QuadraticEquation extends Equation {

    private long secondPolynomial;

    private long firstPolynomial;

    private long constant;

    /**
     * Constructs a quadratic equation in the form of:
     * f(x) = secondPolynomial * x^2 + firstPolynomial * x + constant
     *
     * @param secondPolynomial the parameter for the second degree polynomial
     * @param firstPolynomial the parameter for the first degree polynomial
     * @param constant the parameter for the constant
     */
    public QuadraticEquation(long secondPolynomial, long firstPolynomial, long constant) {
        this.secondPolynomial = secondPolynomial;
        this.firstPolynomial = firstPolynomial;
        this.constant = constant;
    }

    /**
     * Evaluates the equation with the given x.
     * @param x value used to evaluate
     * @return the result of the equation1
     */
    public long evaluate(long x) {
        return secondPolynomial * x * x + firstPolynomial * x + constant;
    }
}
