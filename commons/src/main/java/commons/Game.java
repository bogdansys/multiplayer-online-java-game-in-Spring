package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Game {

    @Id
    public int id;

    public boolean started;


    /**
     * Create a game.
     */
    public Game() {
        Random random = new Random();
        id = random.nextInt(99999 - 10000) + 10000;
        started = false;
    }

    /**
     * adds a gamer to this game.
     *
     * @param gamer
     */
    public void addGamer(Gamer gamer) {
        gamer.gameID = (int) this.id;
    }

    /**
     * Compares obj with this game.
     *
     * @param obj any object to compare to this activity
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Changes this into a hashcode.
     *
     * @return hashcode value of this
     */
    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Changes this object into a readable format.
     *
     * @return the readable format of this object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
