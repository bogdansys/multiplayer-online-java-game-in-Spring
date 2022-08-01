package server.api;

import commons.Gamer;
import commons.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GamerControllerTest {

    private TestGamerRepository repo;

    private GamerController controller;

    @BeforeEach
    public void setup() {
        repo = new TestGamerRepository();
        controller = new GamerController(repo);
    }

    @Test
    public void cannotAddNullGamer() {
        Gamer gamer = null;
        boolean actual = controller.add(gamer);
        assertFalse(actual);
    }

    @Test
    public void AddGamer() {
        Gamer gamer = new Gamer("a", 5, 4);
        boolean actual = controller.add(gamer);
        assertTrue(actual);
    }

    @Test
    public void AddGamerWithUsername() {
        String name = "a";
        var actual = controller.newGamer(name);
        assertNotNull(repo.findAll());
    }


    @Test
    public void repoGetAll() {
        String name = "a";
        var actual = controller.newGamer(name);
        assertEquals(controller.getAll(), repo.findAll());
    }

    @Test
    public void updateScoreTest() {
        Gamer gamer = new Gamer("a", 5, 4);
        Gamer result = new Gamer("a", 10, 4);
        repo.save(gamer);
        Tuple tuple = new Tuple("a", 4, 10);
        controller.updateScore(tuple);
        assertEquals(result, repo.findAll().get(0));
    }

    @Test
    public void matchHistoryTest() {
        Gamer gamer = new Gamer("a", 5, 4);
        gamer.position = 1;
        repo.save(gamer);
        assertNotNull(controller.getAll());
        Gamer[] leaderboard = new Gamer[1];
        leaderboard[0] = gamer;
        String name = "a";
        assertEquals(gamer, controller.matchHistory(name)[0]);
    }

    @Test
    public void getGamersTest() {
        Gamer gamer = new Gamer("a", 5, 4);
        repo.save(gamer);
        assertNotNull(controller.getAll());
        assertEquals(controller.getGamers(4).get(0), gamer);
    }

    @Test
    public void singlePlayerLeaderboardTest() {
        Gamer gamer = new Gamer("a", 5, 4);
        repo.save(gamer);
        assertNotNull(controller.getAll());
        Gamer[] leaderbord = new Gamer[1];
        leaderbord[0] = gamer;
        assertEquals(gamer, controller.SinglePlayerLeaderboard()[0]);
    }

    @Test
    void getPlayersInGameById() {
        Gamer gamer1 = new Gamer("Gijs", 0, 12345);
        Gamer gamer2 = new Gamer("Vic", 0, 12345);
        Gamer gamer3 = new Gamer("Evan", 0, 54321);
        Gamer[] arr = {gamer1, gamer2};
        repo.save(List.of(gamer1, gamer2, gamer3));
        assertTrue(Arrays.equals(controller.getPlayersInGameById(12345), arr));
    }

    @Test
    public void disconnectTest() {
        Gamer gamer = new Gamer("a", 0, 4);
        repo.save(gamer);
        assertNotNull(controller.getAll());
        Tuple tuple = new Tuple("a", 4);
        assertTrue(controller.disconnect(tuple));
    }


}
