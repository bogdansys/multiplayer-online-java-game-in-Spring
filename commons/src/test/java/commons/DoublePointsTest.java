package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoublePointsTest {

    @Test
    void constructorTest() {
        DoublePoints DPT1 = new DoublePoints();
        assertNotNull(DPT1);
    }

    @Test
    void testHashCode() {
        DoublePoints DPT1 = new DoublePoints();
        DoublePoints DPT2 = new DoublePoints();
        DPT2.use();
        assertNotEquals(DPT1.hashCode(), DPT2.hashCode());
    }

    @Test
    void testEquals() {
        DoublePoints DPT1 = new DoublePoints();
        DoublePoints DPT2 = new DoublePoints();
        assertEquals(DPT1, DPT2);
    }

    @Test
    void testEqualsSame() {
        DoublePoints DPT1 = new DoublePoints();
        DoublePoints DPT2 = DPT1;
        assertEquals(DPT1, DPT2);
    }

    @Test
    void testNotEquals() {
        DoublePoints DPT1 = new DoublePoints();
        DoublePoints DPT2 = new DoublePoints();
        DPT2.use();
        assertNotEquals(DPT1, DPT2);
    }

    @Test
    void testNotEqualsNull() {
        DoublePoints DPT1 = new DoublePoints();
        DoublePoints DPT2 = null;
        assertNotEquals(DPT1, DPT2);
    }

    @Test
    void testGetMultiplier() {
        DoublePoints DPT1 = new DoublePoints();
        assertEquals(DPT1.getMultiplier(), 1);
        DPT1.use();
        assertEquals(DPT1.getMultiplier(), 2);
    }

    @Test
    public void testToString() {
        DoublePoints DPT1 = new DoublePoints();
        assertEquals("DoublePoints{used=false,multiplier=1}", DPT1.toString());
        DPT1.use();
        assertEquals("DoublePoints{used=true,multiplier=2}", DPT1.toString());
        DPT1.setMultiplier1();
        assertEquals("DoublePoints{used=true,multiplier=1}", DPT1.toString());
        DPT1.reset();
        assertEquals("DoublePoints{used=false,multiplier=1}", DPT1.toString());
    }
}