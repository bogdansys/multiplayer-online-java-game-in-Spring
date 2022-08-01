package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityEntityTest {

    @Test
    public void constructorTest() {
        ActivityEntity activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        assertNotNull(activity1);
    }

    @Test
    public void getName() {
        ActivityEntity activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        assertNotNull(activity1.getName());
    }

    @Test
    public void getWH() {
        ActivityEntity activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        assertEquals(activity1.getWH(), Long.parseLong(String.valueOf(15)));
    }

    @Test
    public void equalsHashCode() {
        ActivityEntity activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        ActivityEntity activity2 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        assertEquals(activity1, activity2);
        assertEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        ActivityEntity activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(14)));
        ActivityEntity activity2 = new ActivityEntity("a", Long.parseLong(String.valueOf(15)));
        assertNotEquals(activity1, activity2);
        assertNotEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void hasToString() {
        String activity1 = new ActivityEntity("a", Long.parseLong(String.valueOf(14))).toString();
        assertTrue(activity1.contains("name"));
        assertTrue(activity1.contains("WH"));
    }
}
