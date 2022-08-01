package commons;

import java.util.Objects;

public class DoublePoints extends Joker {

    private int multiplier;

    /**
     * Constructor for this object.
     * Creates a new DoublePoints joker
     */
    public DoublePoints() {
        super();
        multiplier = 1;
    }

    /**
     * Uses the joker and updates the multiplier.
     */
    @Override
    public void use() {
        multiplier = 2;
        used = true;
    }

    /**
     * Resets the joker and its multiplier.
     */
    @Override
    public void reset() {
        super.reset();
        multiplier = 1;
    }

    /**
     * Resets the multiplier to 1.
     */
    public void setMultiplier1() {
        multiplier = 1;
    }

    /**
     * Generates a human-readable string representation of this Joker.
     *
     * @return a string containing this Joker's attributes
     */
    @Override
    public String toString() {
        return "DoublePoints{" +
                "used=" + used +
                ",multiplier=" + multiplier +
                '}';
    }
    /**
     * getter or setter.
     * @return
     */


    public int getMultiplier() {
        return multiplier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DoublePoints that = (DoublePoints) o;
        return multiplier == that.multiplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), multiplier);
    }
}
