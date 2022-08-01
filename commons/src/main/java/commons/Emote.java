package commons;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Emote {
    public String type;
    public int counter;

    /**
     * object mapper.
     */
    public Emote() {
        // for object mappers
    }

    /**
     * Constructor for the Emote object.
     *
     * @param type the type of emote
     *             Creates a new Emote object with given type
     */
    public Emote(String type) {
        this.type = type;
        counter = 300;
    }

    /**
     * Every second decreases the counter by 100 until it reaches 0 (or below 0).
     */
    public void startCounter() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter -= 100;
                if (counter <= 0) {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    /**
     * Compares an object to this Emote.
     *
     * @param o the object to compare with
     * @return true iff the other object is of type Emote and has matching attributes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emote emote = (Emote) o;
        return counter == emote.counter && type.equals(emote.type);
    }

    /**
     * Generates a hash code value for this emote.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, counter);
    }

    /**
     * Generates a human-readable string representation of this Emote.
     *
     * @return a string containing this Emote's attributes
     */
    @Override
    public String toString() {
        return "Emote{" +
                "type='" + type + '\'' +
                ", counter=" + counter +
                '}';
    }
}
