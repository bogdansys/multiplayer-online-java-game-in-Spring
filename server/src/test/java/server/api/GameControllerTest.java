package server.api;

import commons.Game;
import commons.Gamer;
import commons.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;

public class GameControllerTest {

    private TestGamerRepository repo;

    private TestGameRepository gameRepo;

    private GameController controller;

    private SimpMessagingTemplate template;

    @BeforeEach
    public void setup() {
        repo = new TestGamerRepository();
        gameRepo = new TestGameRepository();
        controller = new GameController(template, repo, gameRepo);
    }

    @Test
    public void timeReductionTest() {
        Tuple tuple = new Tuple("a", 3);
        var actual = controller.timeReduction(tuple);
        assertEquals(OK, actual.getStatusCode());
    }

    @Test
    public void testGetAll() {
        Game game = new Game();
        gameRepo.save(game);
        assertNotNull(controller.getAll());
    }

    @Test
    public void addGameTest() {
        Game game = new Game();
        controller.addGame(game);
        assertNotNull(gameRepo.findAll());
    }

    @Test
    public void getGamesOngoingTest() {
        Game game = new Game();
        game.started = true;
        controller.addGame(game);
        assertEquals(controller.getGamesOngoing(), 1);
    }

    @Test
    public void getGameTest() {
        Game game = new Game();
        controller.addGame(game);
        int x = gameRepo.findAll().get(0).id;
        assertEquals(controller.getGame(x), game);
    }

    @Test
    public void getGamersTest() {
        Gamer gamer = new Gamer();
        repo.save(gamer);
        assertEquals(controller.getGamers(gamer.gameID).size(), 1);
    }

    @Test
    public void getPlayersInGameTest() {
        Gamer gamer = new Gamer();
        repo.save(gamer);
        assertEquals(controller.getGamers(gamer.gameID).size(), 1);
    }

    @Test
    void reset() {
        Gamer gamer = new Gamer();
        repo.save(gamer);
        Game game = new Game();
        gameRepo.save(game);
        assertEquals(1, gameRepo.count());
        assertEquals(1, repo.count());
        controller.reset();
        assertEquals(0, gameRepo.count());
        assertEquals(0, repo.count());
    }

    @Test
    void newSingleplayerGamerNull() {
        assertEquals(0, controller.newSingleplayerGamer(null));
    }

    @Test
    void newSinglePlayerGamer() {
        // can't test, uses QuestionMaker
    }

    @Test
    void createLobby() {
        // can't test, uses QuestionMaker
    }

    @Test
    void joinLobby() {
        Gamer gamer = new Gamer("Dave", 0, 12345);
        repo.save(gamer);
        assertEquals(1, repo.count());
        Tuple tuple = new Tuple("Dan", 12345);
        controller.joinLobby(tuple);
        assertEquals(2, repo.count());
    }

    @Test
    void getGamers() {
        Gamer gamer1 = new Gamer("Gijs", 0, 12345);
        Gamer gamer2 = new Gamer("Vic", 0, 12345);
        repo.save(gamer1);
        repo.save(gamer2);
        assertEquals(controller.getGamers(12345), List.of(gamer1, gamer2));
    }

    @Test
    void testCreateLobby() {
        Game game1 = new Game();
        Game game2 = new Game();
        game2.started = true;
        gameRepo.save(game1);
        gameRepo.save(game2);
        assertEquals(0, controller.createLobby(game1.id));
        assertEquals(2, controller.createLobby(game2.id));
        assertEquals(1, controller.createLobby(1));
    }

    @Test
    void receiveAndSendStartGame() {
        // can't test
    }

    @Test
    void leaveLobby() {
        Tuple tuple = new Tuple("Gijs", 12345);
        Gamer gamer = new Gamer("Gijs", 0, 12345);
        Game game = new Game();
        game.id = 12345;
        repo.save(gamer);
        assertEquals(1, repo.count());
        controller.leaveLobby(tuple);
        assertEquals(0, repo.count());
    }

    @Test
    void getPlayersInGame() {
        Gamer gamer1 = new Gamer("Gijs", 0, 12345);
        Gamer gamer2 = new Gamer("Vic", 0, 12345);
        Gamer gamer3 = new Gamer("Evan", 0, 54321);
        repo.save(List.of(gamer1, gamer2, gamer3));
        assertEquals(controller.getPlayersInGame(12345), 2);
    }

    @Test
    void getGamesOngoing() {
        Game game = new Game();
        Game game2 = new Game();
        Game game3 = new Game();
        gameRepo.save(List.of(game, game2, game3));
        game.started = true;
        game3.started = true;
        assertEquals(2, controller.getGamesOngoing());
    }

    @Test
    void getPlayersOnline() {
        Gamer gamer1 = new Gamer("Gijs", 0, 12345);
        Gamer gamer2 = new Gamer("Vic", 0, 12345);
        Gamer gamer3 = new Gamer("Evan", 234, 54321);
        repo.save(List.of(gamer1, gamer2, gamer3));
        assertEquals(2, controller.getPlayersOnline());
    }

    @Test
    void disableGame() {
        Game game = new Game();
        Game game2 = new Game();
        gameRepo.save(List.of(game, game2));
        game2.started = true;
        assertFalse(controller.disableGame(game.id));
        assertTrue(controller.disableGame(game2.id));
    }

    @Test
    void disconnect() {
        Game game = new Game();
        Gamer gamer = new Gamer("Gijs", 0, 12345);
        Gamer gamer2 = new Gamer("Vic", 0, 12345);
        repo.save(List.of(gamer, gamer2));
        gameRepo.save(game);
        game.id = 12345;
        game.addGamer(gamer);
        game.addGamer(gamer2);
        assertFalse(controller.disconnect(12345));
        gamer2.gameID = 54321;
        assertTrue(controller.disconnect(12345));
    }

    @Test
    void getQuestionByIndex() {
        // can't test, refers to Main
    }

    @Test
    void receiveAndSendPlayerAnswered() {
        // can't test, websockets
    }

    @Test
    void receiveAndSendTimeRanOut() {
        // can't test, websockets
    }

    @Test
    void receiveAndSendPlayerDisconnected() {
        // can't test, websockets
    }
}
