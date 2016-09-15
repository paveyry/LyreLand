package training.tools;

import analysis.harmonic.ChordDegree;

public class Pair<S, T> {
    private S leftValue_;
    private T rightValue_;

    public Pair(S leftValue, T rightValue) {
        leftValue_ = leftValue;
        rightValue_ = rightValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Pair))
            return false;
        return leftValue_ == ((Pair) other).getLeft() && rightValue_ == ((Pair) other).getRight();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(leftValue_).append(", ").append(rightValue_).append(")");
        return sb.toString();
    }

    // GETTERS / SETTERS

    /**
     * @return the leftValue of the Pair.
     */
    public S getLeft() {
        return leftValue_;
    }

    /**
     * @return the rightValue of the Pair.
     */
    public T getRight() {
        return rightValue_;
    }

    /**
     * This function set the leftValue of the pair with the given parameter.
     * @param newValue
     */
    public void setLeft(S newValue) {
        leftValue_ = newValue;
    }

    /**
     * This function set the rightValue of the pair with the given parameter.
     * @param newValue
     */
    public void setRight(T newValue) {
        rightValue_ = newValue;
    }
}
