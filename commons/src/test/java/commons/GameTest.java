package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void constructorTest() {
        Game game = new Game();
        assertTrue(game.id > 9999 && game.id < 100000);
    }

    @Test
    void addGamer() {
        Gamer gamer = new Gamer("Gijs", 0, 12345);
        Game game = new Game();
        game.addGamer(gamer);
        assertEquals(gamer.gameID, game.id);
    }


    @Test
    void testEquals() {
        Game game1 = new Game();
        Game game2 = new Game();
        game2.id = game1.id;
        assertEquals(game1, game2);
    }

    @Test
    void testNotEquals() {
        Game game1 = new Game();
        Game game2 = new Game();
        assertNotEquals(game1, game2);
    }

    @Test
    void testHashCode() {
        Game game1 = new Game();
        Game game2 = new Game();
        assertNotEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testTosString() {
        Game game = new Game();
        String string = game.toString();
        assertTrue(string.contains("id"));
        assertTrue(string.contains("started"));
    }
}