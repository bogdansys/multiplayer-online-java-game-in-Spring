package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class Question {

    public String question;

    public ArrayList<String> answers;

    public String correctAnswer;

    public List<String> images;

    /**
     * object mapper.
     */
    public Question() {
    }

    /**
     * Creates a question.
     *
     * @param question      a string with the question
     * @param answers       a list that contains possible answers to the question, one of which is the correct one
     * @param correctAnswer a string that contains the correct answer
     */
    public Question(String question, ArrayList<String> answers, String correctAnswer, List<String> images) {

        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.images = images;
    }

    /**
     * Compares obj with this question.
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
