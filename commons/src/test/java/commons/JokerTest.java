package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JokerTest {

    private Joker joker1 = new TimeReduction();
    private Joker joker2 = new TimeReduction();
    private EliminateAnswer eliminateAnswer = new EliminateAnswer();
    private DoublePoints doublePoints = new DoublePoints();

    @Test
    void constructorTest() {
        TimeReduction joker = new TimeReduction();
        assertNotNull(joker);
    }

    @Test
    void use() {
        joker1.use();
        assertTrue(joker1.used);
    }

    @Test
    void reset() {
        joker1.reset();
        assertFalse(joker1.used);
    }

    @Test
    void testEquals() {
        assertEquals(joker1, joker1);
        assertEquals(joker1, joker2);
    }

    @Test
    void testNotEquals() {
        joker2.use();
        assertNotEquals(joker1, joker2);
        assertNotEquals(joker1, null);
        assertNotEquals(joker1, doublePoints);
        assertNotEquals(doublePoints, eliminateAnswer);
        assertNotEquals(eliminateAnswer, joker1);
    }

    @Test
    void testHashCode() {
        assertEquals(joker1.hashCode(), joker2.hashCode());
    }
}