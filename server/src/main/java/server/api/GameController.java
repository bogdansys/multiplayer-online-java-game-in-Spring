package server.api;

import commons.Game;
import commons.Gamer;
import commons.Question;
import commons.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import server.Main;
import server.database.GameRepository;
import server.database.GamerRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/game")
public class GameController {
    private SimpMessagingTemplate template;
    private final GamerRepository repo;
    private final GameRepository gameRepo;
    private Tuple data;

    /**
     * The constructor for the GameController.
     * @param template a SimpMessagingTemplate object
     * @param repo a database that stores all the gamers
     * @param gameRepo a database that stores all the games
     */
    @Autowired
    public GameController(SimpMessagingTemplate template, GamerRepository repo, GameRepository gameRepo) {
        this.template = template;
        this.repo = repo;
        this.gameRepo = gameRepo;
    }

    /**
     * Adds a game to the repository.
     *
     * @param game the game to add to the repository
     * @return a 'ok' responseEntity
     */
    @PostMapping(path = {",", "/"})
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        gameRepo.save(game);
        return ResponseEntity.ok(game);
    }

    /**
     * Retrieves a game from the repository.
     *
     * @param id the ID of the game to retrieve
     * @return the Game object matching the provided ID
     */
    @GetMapping(path = {",", ""})
    public Game getGame(@RequestBody int id) {
        List<Game> games = gameRepo.findAll();
        for (Game game : games) {
            if (game.id == id) return game;
        }
        return null;
    }

    /**
     * Retrieves a list of all games in the repository.
     *
     * @return a List containing all games saved to the repository
     */
    @GetMapping(path = {"/GetAll"})
    public List<Game> getAll() {
        return gameRepo.findAll();
    }

    /**
     * Resets the entire game and gamer repositories.
     */
    @GetMapping(path = {"/reset"})
    public void reset() {
        gameRepo.deleteAll();
        repo.deleteAll();
    }


    /**
     * creates a game object with a unique id and then adds
     * a gamer to it (which will later be saved to DB)
     * because it s singleplayer, this particular game won't wait for others to join
     * + sends the unique id of the gamer back (to be able to easily update score at the end).
     *
     * @param name
     */
    @PostMapping(path = {"/singlePlayer"})
    public int newSingleplayerGamer(@RequestBody String name) {

        if (name == null) return 0;

        Game game = new Game();
        Gamer gamer = new Gamer(name);
        game.addGamer(gamer);
        game.started = true;

        Question[] questionList = new Question[21];
        for (int i = 0; i < 21; i++) {
            questionList[i] = (QuestionMaker.getQuestion());
        }
        Main.map.put(game.id, questionList);

        //will update score at end of the gameSession

        addGame(game);
        repo.save(gamer);
        return game.id;

    }

    /**
     * posts the name (and time to be reduced although right now it has no use) of the player who used a time red. joker to the path specified
     *
     * @param data the tuple holding the name and time values
     * @return a ResponseEntiity with the OK status, and the data tuple as the response body
     */
    @PostMapping(path = {"/timeReduction"})
    public ResponseEntity timeReduction(@RequestBody Tuple data) {
        this.data = data;
        return ResponseEntity.ok(data);
    }

    /**
     * Posts a username, saves it in the database and creates a lobby.
     *
     * @param name the name of the gamer
     * @return the lobbyID
     */
    @PostMapping(path = {"/createLobby"})
    public int createLobby(@RequestBody String name) {
        Game game = new Game();
        Gamer gamer = new Gamer(name);
        gamer.gameID = game.id;
        repo.save(gamer);
        gameRepo.save(game);
        addGame(game);

        Question[] questionList = new Question[21];
        for (int i = 0; i < 21; i++) {
            questionList[i] = (QuestionMaker.getQuestion());
        }
        Main.map.put(game.id, questionList);

        return gamer.gameID;
    }

    /**
     * Joins the lobby if the name of the user does not already exist in combination with the lobbyID
     * And saves it in the database.
     *
     * @param data the Tuple that consists of the name and lobbyID
     * @return a boolean depending on if the combination already exists in the database
     */
    @PostMapping(path = {"/joinLobby"})
    public boolean joinLobby(@RequestBody Tuple data) {
        String name = (String) data.name;
        int lobbyID = (int) data.value;
        Gamer gamer = new Gamer(name);
        List<Gamer> gamers = repo.findAll();
        gamer.gameID = lobbyID;
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).gameID == lobbyID && gamers.get(i).name.equals(name)) {
                return false;
            }
        }
        repo.save(gamer);
        return true;
    }

    /**
     * Gets all the gamers that are in your lobby from the database.
     *
     * @param lobbyID the id of your lobby
     * @return a list of gamers
     */
    @GetMapping(path = {"/getGamers/{id}"})
    public List<Gamer> getGamers(@PathVariable("id") int lobbyID) {
        List<Gamer> gamers = new ArrayList<Gamer>();
        gamers = repo.findAll();
        List<Gamer> gamersInLobby = new ArrayList<Gamer>();
        for (Gamer x : gamers) {
            if (x.gameID == lobbyID) gamersInLobby.add(x);
        }
        return gamersInLobby;
    }

    /**
     * checks if this is a valid lobby ID.
     *
     * @param id the id for the lobby
     * @return 0 if game exists and hasn't started,
     * 1 if there is no game with the specified id,
     * 2 if the game has already started
     */
    @PostMapping(path = {"/checkLobbyID"})
    public int createLobby(@RequestBody int id) {

        List<Game> games = gameRepo.findAll();
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.id == id)
                if (!game.started) return 0;
                else return 2;
        }
        return 1;
    }

    /**
     * Receives a boolean if game started for a particular gameId and sends it to all gamers in that game.
     *
     * @param gameId      id of the game
     * @param gameStarted boolean if game started (should always be true)
     */
    @MessageMapping("/startGame/{gameId}")
    public void receiveAndSendStartGame(@DestinationVariable int gameId, boolean gameStarted) {
        List<Game> games = gameRepo.findAll();
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.id == gameId) {
                game.started = true;
                gameRepo.save(game);
            }
        }
        this.template.convertAndSend("/topic/startGame/" + gameId, gameStarted);
    }

    /**
     * Leaves a lobby by removing the player from the database.
     *
     * @param data the name and lobbyID of the gamer that wants to leave
     * @return a boolean to check if deleting the gamer was successful
     */
    @PostMapping(path = {"/leaveLobby"})
    public boolean leaveLobby(@RequestBody Tuple data) {

        String name = (String) data.name;
        int lobbyID = (int) data.value;
        Gamer gamer = new Gamer(name);
        List<Gamer> gamers = repo.findAll();
        gamer.gameID = lobbyID;
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).gameID == lobbyID && gamers.get(i).name.equals(name)) {
                repo.delete(gamers.get(i));
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of all players in a game by comparing game IDs.
     *
     * @param gameID the ID of the game to fetch players from
     * @return a count of players in a certain game
     */
    @GetMapping(path = {"/playersingame/{gameID}"})
    public int getPlayersInGame(@PathVariable("gameID") int gameID) {
        int counter = 0;

        List<Gamer> gamers = repo.findAll();

        for (int i = 0; i < gamers.size(); i++) {
            Gamer gamer = gamers.get(i);
            if (gamer.gameID == gameID && gamer.score == 0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * returns the number of games that are ongoing.
     *
     * @return number of ongoing games
     */
    @GetMapping(path = {"", "/gamesongoing"})
    public int getGamesOngoing() {
        List<Game> games = gameRepo.findAll();
        int counter = 0;

        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).started) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * returns the number of gamers that are online atm.
     *
     * @return number of players that are currently in a game
     */
    @GetMapping(path = {"", "/playersonline"})
    public int getPlayersOnline() {
        List<Gamer> gamers = repo.findAll();
        int counter = 0;

        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).score == 0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Sets a game's 'started' boolean to false if it's been started.
     *
     * @param gameID the ID of the game to disable
     * @return true if it's succeeded, false if the game wasn't started.
     */
    @PostMapping(path = {"/disablegame"})
    public boolean disableGame(@RequestBody int gameID) {
        List<Game> games = gameRepo.findAll();
        Game game = null;
        for (Game g : games) {
            if (g.id == gameID) {
                game = g;
            }
        }
        if (game.started) {
            game.started = false;
            gameRepo.save(game);
            return true;
        }
        return false;
    }

    /**
     * If the last player leaves a game, this sets the game's started boolean to false and saves it into the repository.
     *
     * @param gameID the gameID to stop
     * @return true if it's succeeded, false if there are more players left in the game
     */
    @PostMapping(path = {"/disconnect"})
    public boolean disconnect(@RequestBody int gameID) {
        if (getPlayersInGame(gameID) == 1) {
            List<Game> games = gameRepo.findAll();
            Game game = null;
            for (Game g : games) {
                if (g.id == gameID) {
                    game = g;
                }
            }
            game.started = false;
            gameRepo.save(game);
            return true;
        }
        return false;
    }

    /**
     * Gets a question from an arraylist of questions which is in turn received from a map defined in Main.
     *
     * @param data a tuple containing the index of the question and the gameID of the game
     * @return the question at the specified index
     */
    @PostMapping(path = {"/getQuestionByIndex"})
    public Question getQuestionByIndex(@RequestBody Tuple data) {
        int index = Integer.parseInt(data.name);
        int id = data.value;
        Question[] questions = Main.map.get(id);
        return questions[index];
    }

    /**
     * Receives and sends information that the sender has answered the current question.
     *
     * @param gameId         id of the game
     * @param playerAnswered boolean if the player answered (should always be true)
     */
    @MessageMapping("/playerAnswered/{gameId}")
    public void receiveAndSendPlayerAnswered(@DestinationVariable int gameId, boolean playerAnswered) {
        this.template.convertAndSend("/topic/playerAnswered/" + gameId, playerAnswered);
    }

    /**
     * Receives and sends information that the time has ran out.
     *
     * @param gameId id of the game
     * @param timeRanOut boolean if the time ran out
     */
    @MessageMapping("/timeRanOut/{gameId}")
    public void receiveAndSendTimeRanOut(@DestinationVariable int gameId, boolean timeRanOut) {
        this.template.convertAndSend("/topic/timeRanOut/" + gameId, timeRanOut);
    }

    /**
     * Receives and sends information that the sender has disconnected.
     *
     * @param gameId             id of the game
     * @param playerDisconnected boolean if the player disconnected (should always be true)
     */
    @MessageMapping("/playerDisconnected/{gameId}")
    public void receiveAndSendPlayerDisconnected(@DestinationVariable int gameId, boolean playerDisconnected) {
        this.template.convertAndSend("/topic/playerDisconnected/" + gameId, playerDisconnected);
    }
}
