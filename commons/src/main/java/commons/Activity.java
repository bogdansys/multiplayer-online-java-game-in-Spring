package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {


    @Id
    public String id;

    public String image_path;

    public String title;

    public long consumption_in_wh;

    public String source;

    /**
     * Constructs an activity.
     *
     * @param title             name of the activity
     * @param consumption_in_wh amount of energy the activity consumes
     */
    public Activity(String id, String image_path, String title, long consumption_in_wh, String source) {

        this.id = id;
        this.title = title;
        this.image_path = image_path;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    /**
     * object mapper.
     */
    public Activity() {
        // for object mappers
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
