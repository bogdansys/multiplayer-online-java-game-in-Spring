package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EliminateAnswerTest {
    @Test
    void constructorTest() {
        EliminateAnswer ea1 = new EliminateAnswer();
        assertNotNull(ea1);
    }

    @Test
    void testHashCode() {
        EliminateAnswer ea1 = new EliminateAnswer();
        EliminateAnswer ea2 = new EliminateAnswer();
        ea2.used = true;
        assertNotEquals(ea1.hashCode(), ea2.hashCode());
    }

    @Test
    void testEquals() {
        EliminateAnswer ea1 = new EliminateAnswer();
        EliminateAnswer ea2 = new EliminateAnswer();
        assertEquals(ea1, ea2);
    }

    @Test
    void testNotEquals() {
        EliminateAnswer ea1 = new EliminateAnswer();
        EliminateAnswer ea2 = new EliminateAnswer();
        ea2.used = true;
        assertNotEquals(ea1, ea2);
    }

    @Test
    public void testToString() {
        String ea1 = new EliminateAnswer().toString();
        assertTrue(ea1.contains("used"));
    }
}