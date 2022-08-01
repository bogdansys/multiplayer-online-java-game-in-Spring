package commons;

import java.util.Objects;

public abstract class Joker {
    public boolean used;

    /**
     * Constructor method for the Joker classl.
     * Constructs a new joker with the 'used' boolean set to false
     */
    public Joker() {
        used = false;
    }

    /**
     * Changes used to true.
     */
    public void use() {
        used = true;
    }

    /**
     * Changes used to true.
     */
    public void reset() {
        used = false;
    }

    /**
     * Compares obj with this joker.
     *
     * @param o the object to compare with
     * @return true iff the object is of type Joker and has the same attributes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joker joker = (Joker) o;
        return used == joker.used;
    }

    /**
     * Generates a hash code for this Joker.
     *
     * @return hashcode value of this Joker
     */
    @Override
    public int hashCode() {
        return Objects.hash(used);
    }
}
