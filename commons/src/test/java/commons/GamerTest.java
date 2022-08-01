package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamerTest {

    @Test
    void emptyConstructorTest() {
        Gamer gamer = new Gamer();
        assertNotNull(gamer);
    }

    @Test
    void constructorTest() {
        Gamer gamer = new Gamer("Gijs", 1, 12345);
        assertNotNull(gamer);
    }

    @Test
    void testEquals() {
        Gamer gamer1 = new Gamer("Gijs", 1, 12345);
        Gamer gamer2 = new Gamer("Gijs", 1, 12345);
        assertEquals(gamer1, gamer2);
    }

    @Test
    void testNotEquals() {
        Gamer gamer1 = new Gamer("Gijs", 1, 12345);
        Gamer gamer2 = new Gamer("Gijs", 1, 67890);
        assertNotEquals(gamer1, gamer2);
    }

    @Test
    void testHashCode() {
        Gamer gamer1 = new Gamer("Gijs", 1, 12345);
        Gamer gamer2 = new Gamer("Gijs", 1, 67890);
        assertNotEquals(gamer1.hashCode(), gamer2.hashCode());
    }

    @Test
    void testToString() {
        Gamer gamer = new Gamer("Gijs", 1, 12345);
        String string = gamer.toString();
        assertTrue(string.contains("name"));
        assertTrue(string.contains("score"));
        assertTrue(string.contains("gameID"));
    }

    @Test
    void testGetName() {
        Gamer gamer = new Gamer("Gijs", 1, 12345);
        assertEquals("Gijs", gamer.getName());
    }

    @Test
    void testSetName() {
        Gamer gamer = new Gamer("Gijs", 1, 12345);
        gamer.setName("Piotr");
        assertEquals("Piotr", gamer.getName());
    }
}