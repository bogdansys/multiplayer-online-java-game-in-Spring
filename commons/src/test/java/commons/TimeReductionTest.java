package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeReductionTest {
    private TimeReduction tr1;

    @BeforeEach
    void setup() {
        tr1 = new TimeReduction();
    }

    @Test
    void constructorTest() {
        assertNotNull(tr1);
    }

    @Test
    void testHashCode() {
        TimeReduction tr2 = new TimeReduction();
        tr2.used = true;
        assertNotEquals(tr1.hashCode(), tr2.hashCode());
    }

    @Test
    void testEquals() {
        TimeReduction tr2 = new TimeReduction();
        assertEquals(tr1, tr2);
    }

    @Test
    void testNotEquals() {
        TimeReduction tr2 = new TimeReduction();
        tr2.used = true;
        assertNotEquals(tr1, tr2);
    }

    @Test
    void testToString() {
        assertEquals("TimeReduction{used=false}", tr1.toString());
        tr1.use();
        assertEquals("TimeReduction{used=true}", tr1.toString());
    }
}