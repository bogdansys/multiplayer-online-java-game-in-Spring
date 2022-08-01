package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void emptyConstructorTest() {
        Question question = new Question();
        assertNotNull(question);
    }

    @Test
    public void constructorTest() {
        ArrayList<String> images = new ArrayList<>();
        images.add("asd");
        images.add("bsf");
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        Question question1 = new Question("a", list, "c", images);
        assertNotNull(question1);
    }

    @Test
    public void equalsHashCode() {
        ArrayList<String> images = new ArrayList<>();
        images.add("asd");
        images.add("bsf");
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        Question question1 = new Question("a", list, "c", images);
        Question question2 = new Question("a", list, "c", images);
        assertEquals(question1, question2);
        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        ArrayList<String> images = new ArrayList<>();
        images.add("asd");
        images.add("bsf");
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        Question question1 = new Question("a", list, "c", images);
        Question question2 = new Question("a", list, "b", images);
        assertNotEquals(question1, question2);
        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    public void hasToString() {
        ArrayList<String> images = new ArrayList<>();
        images.add("asd");
        images.add("bsf");
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        String question1 = new Question("a", list, "c", images).toString();
        assertTrue(question1.contains("question"));
        assertTrue(question1.contains("answers"));
        assertTrue(question1.contains("correctAnswer"));
    }
}
