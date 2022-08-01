package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

//database storage class

@Entity
public class Gamer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    public String name;
    public int score;
    public int gameID;
    @Transient
    public int position;

    /**
     * for object maper.
     */
    public Gamer() {
        // for object mappers
    }

    /**
     * Creates a gamer.
     */
    public Gamer(String name, int score, int gameID) {
        this.gameID = gameID;
        this.name = name;
        this.score = score;
        this.position = 0;
    }

    /**
     * Creates a gamer (name only).
     */
    public Gamer(String name) {
        this.gameID = 0;
        this.name = name;
        this.score = 0;
        this.position = 0;
    }

    /**
     * Getter for name.
     *
     * @return name of the gamer
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name name of the gamer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares obj with this gamer.
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
