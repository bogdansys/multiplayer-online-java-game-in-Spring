package server.api;

import commons.Gamer;
import commons.Tuple;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import server.database.GamerRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/gamer")
public class GamerController {

    //database for the gamers to be stored
    private final GamerRepository repo;

    /**
     * Constructor for the GamerController.
     *
     * @param repo database for all the gamers
     */
    public GamerController(GamerRepository repo) {

        this.repo = repo;
    }

    /**
     * post a Gamer to be stored in the database.
     *
     * @param user a Gamer object that will be stored
     * @return a boolean
     */
    @PostMapping(path = {"", "/"})
    public boolean add(@RequestBody Gamer user) {
        if (user == null) return false;
        Gamer saved = repo.save(user);
        return true;
    }

    /**
     * post mapping that updates a gamer's score based on his unique id in the database (auto generated)
     * unique from game id or gamer's name.
     *
     * @return
     */
    @PostMapping(path = {"updateScore", "/updateScore"})
    public void updateScore(@RequestBody Tuple data) {
        List<Gamer> gamers = repo.findAll();
        for (int i = 0; i < gamers.size(); i++) {
            Gamer gamer = gamers.get(i);
            if (gamer.gameID == data.gameID && gamer.name.equals(data.name)) {
                if (data.score == 0) {
                    gamer.score = 1;
                } else {
                    gamer.score = data.score;
                }
                repo.save(gamer);
                return;
            }
        }
    }

    /**
     * post a username that will be used to create a gamer that then will be stored in the database.
     *
     * @param name a username that will be used in game
     * @return a long ID
     */
    @PostMapping(path = {"{username}"})
    public Long newGamer(@PathVariable("username") String name) {

        Gamer gamer = new Gamer(name);
        Long id = repo.save(gamer).Id;
        return id;
    }

    /**
     * returns all the gamers that are in the database.
     *
     * @return List of gamers
     */
    @GetMapping(path = {"", "/"})
    public List<Gamer> getAll() {

        return repo.findAll();
    }

    /**
     * method that sorts by score.
     *
     * @return a list of gamers sorted by score
     */
    @GetMapping(path = {"/matchhistory/{username}"})
    public Gamer[] matchHistory(@PathVariable("username") String username) {

        Sort.Direction sortDirection = Sort.Direction.DESC;
        String sortBy = "score";
        Sort sort = Sort.by(sortDirection, sortBy);

        List<Gamer> gamers = repo.findAll(sort);
        gamers.removeIf(g -> !g.name.equals(username));
        gamers.removeIf(g -> g.score == -1);

        Gamer[] result = new Gamer[gamers.size()];
        for (int i = 0; i < gamers.size(); i++) {
            Gamer gamer = gamers.get(i);
            Gamer[] inGame = getPlayersInGameById(gamer.gameID);
            for (int j = 0; j < inGame.length; j++) {
                if (inGame[j].name.equals(gamer.name)) {
                    gamer.position = j + 1;
                }
            }
            result[i] = gamer;
        }
        return result;
    }

    /**
     * Gets all the gamers in a lobby.
     *
     * @param lobbyID the ID of the lobby to get the gamers from
     * @return a list of gamers in a specified lobby
     */
    public List<Gamer> getGamers(@PathVariable("id") int lobbyID) {
        List<Gamer> gamers = repo.findAll();
        List<Gamer> gamersInLobby = new ArrayList<Gamer>();
        for (Gamer x : gamers) {
            if (x.gameID == lobbyID) gamersInLobby.add(x);
        }
        return gamersInLobby;
    }

    /**
     * singleplayer leaderboard.
     *
     * @return
     */
    @GetMapping(path = {"/allSinglePlayer"})
    public Gamer[] SinglePlayerLeaderboard() {

        Sort.Direction sortDirection = Sort.Direction.DESC;
        String sortBy = "score";
        Sort sort = Sort.by(sortDirection, sortBy);

        List<Gamer> gamers = repo.findAll(sort);
        gamers.removeIf(gamer -> getGamers(gamer.gameID).size() > 1 || gamer.score < 0);
        gamers.removeIf(g -> g.score == -1);

        Gamer[] result = new Gamer[gamers.size()];
        for (int i = 0; i < gamers.size(); i++) {
            result[i] = gamers.get(i);
        }

        return result;
    }

    /**
     * get gamers in a game by gameID.
     *
     * @param gameID the game to fetch an array of players from
     * @return an array containing all gamers in a specified game
     */
    @PostMapping(path = {"/playersingameById"})
    public Gamer[] getPlayersInGameById(@RequestBody int gameID) {

        List<Gamer> gamers = repo.findAll();
        List<Gamer> temp = new ArrayList<>();

        for (Gamer walker : gamers) {
            if (walker.gameID == gameID) {
                temp.add(walker);
            }
        }
        temp.removeIf(g -> g.score == -1);

        temp.sort(new Comparator<Gamer>() {
            @Override
            public int compare(Gamer o1, Gamer o2) {
                if (o1.score > o2.score) return -1;
                if (o1.score < o2.score) return 1;
                return 0;
            }
        });

        Gamer[] array = new Gamer[temp.size()];
        int m = 0;

        for (Gamer walker : temp) {
            array[m++] = walker;
        }

        return array;
    }

    /**
     * Disconnects a player from a lobby.
     *
     * @param data a tuple containing the lobbyID and name of the gamer to disconnect
     * @return true if it's successful, false if not
     */
    @PostMapping(path = {"/disconnect"})
    public boolean disconnect(@RequestBody Tuple data) {
        List<Gamer> gamers = repo.findAll();
        for (int i = 0; i < gamers.size(); i++) {
            Gamer gamer = gamers.get(i);
            if (gamer.gameID == data.value && gamer.name.equals(data.name) && gamer.score == 0) {
                gamer.score = -1;
                repo.save(gamer);
                return true;
            }
        }
        return false;
    }
}
