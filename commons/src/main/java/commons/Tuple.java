package commons;

import java.util.Objects;

public class Tuple {
    public String name;
    public Integer value = 0;
    public Integer gameID;
    public Integer score;

    /**
     * Blank Constructor class needed for POST requests.
     */
    public Tuple() {
    }

    /**
     * A Constructor class for the Tuple class.
     *
     * @param name  a String containing the name of a player
     * @param value an Integer containing a certain value
     */
    public Tuple(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    /**
     * A Constructor class for the Tuple class.
     *
     * @param name   a String containing the name of a player
     * @param gameID the id of the game
     * @param score  the score of the player
     */
    public Tuple(String name, Integer gameID, Integer score) {
        this.name = name;
        this.gameID = gameID;
        this.score = score;
        this.value = 0;
    }

    /**
     * for testing.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Objects.equals(name, tuple.name) && Objects.equals(value, tuple.value);
    }

    /**
     * auromated generated.
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}