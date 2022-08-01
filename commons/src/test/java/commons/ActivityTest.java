package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    @Test
    public void emptyConstructorTest() {
        Activity activity1 = new Activity();
        assertNotNull(activity1);
    }

    @Test
    public void constructorTest() {
        Activity activity1 = new Activity("a-1", "a", "asd", 1, "www.google.com");
        assertNotNull(activity1);
    }

    @Test
    public void equalsHashCode() {
        Activity activity1 = new Activity("a-1", "a", "asd", 1, "www.google.com");
        Activity activity2 = new Activity("a-1", "a", "asd", 1, "www.google.com");
        assertEquals(activity1, activity2);
        assertEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        Activity activity1 = new Activity("a-1", "a", "asd", 1, "www.google.com");
        Activity activity2 = new Activity("a-12", "b", "asd", 1, "www.google.com");
        assertNotEquals(activity1, activity2);
        assertNotEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void hasToString() {
        String activity1 = new Activity("a-1", "a", "asd", 1, "www.google.com").toString();
        assertTrue(activity1.contains("title"));
        assertTrue(activity1.contains("consumption_in_wh"));
    }
}

