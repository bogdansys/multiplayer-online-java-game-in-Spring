package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class ActivityEntity {

    public String name = null;
    public Long WH = Long.parseLong(String.valueOf(0));

    /**
     * Constructor for the ActivityEntity class.
     *
     * @param name the name of the activity
     * @param WH   the watt per hour the activity consumes
     */
    public ActivityEntity(String name, Long WH) {
        this.WH = WH;
        this.name = name;
    }

    /**
     * getter or setter.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getter or setter.
     * @return
     */
    public Long getWH() {
        return WH;
    }

    /**
     * Compares obj with this quote.
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
