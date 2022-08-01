package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleTest {

    @Test
    void constructorTest1() {
        Tuple tuple = new Tuple("Evan", 5);
        assertNotNull(tuple);
    }

    @Test
    void constructorTest2() {
        Tuple tuple = new Tuple("Evan", 32325, 1);
        assertNotNull(tuple);
    }

    @Test
    void emptyConstructorTest() {
        Tuple tuple = new Tuple();
        assertNotNull(tuple);
    }

    @Test
    void equalsTestEqual() {
        Tuple tuple1 = new Tuple("Evan", 32325);
        Tuple tuple2 = new Tuple("Evan", 32325);
        assertEquals(tuple1, tuple2);
    }

    @Test
    void equalsTestEqualSame() {
        Tuple tuple1 = new Tuple("Evan", 32325);
        Tuple tuple2 = tuple1;
        assertEquals(tuple1, tuple2);
    }

    @Test
    void equalsTestNotEqualDifferentType() {
        Tuple tuple1 = new Tuple("Evan", 32325);
        String string = "test";
        assertNotEquals(tuple1, string);
    }

    @Test
    void equalsTestNotEqual() {
        Tuple tuple1 = new Tuple("Evan", 32325);
        Tuple tuple2 = new Tuple("Vic", 32325);
        Tuple tuple3 = new Tuple("Evan", 11111);
        assertNotEquals(tuple1, tuple2);
        assertNotEquals(tuple1, tuple3);
    }

    @Test
    void hashCodeTestEqual() {
        Tuple tuple1 = new Tuple("Evan", 32325);
        Tuple tuple2 = new Tuple("Evan", 32325);
        assertEquals(tuple1.hashCode(), tuple2.hashCode());
    }
}