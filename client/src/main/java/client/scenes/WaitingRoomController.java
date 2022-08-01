package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Gamer;
import commons.Tuple;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomController extends EmoteController implements Initializable {

    public final MainCtrl mainCtrl;

    public ObservableList<Gamer> listOfGamers;

    public Timer timer;

    @FXML
    public Label gameID;
    @FXML
    private TableView<Gamer> playerTable;
    @FXML
    private TableColumn<Gamer, SimpleStringProperty> gamersInLobby;

    /**
     * Constructor for WaitingRoomController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     */
    @Inject
    public WaitingRoomController(MainCtrl mainCtrl, ServerUtils server) {
        super(mainCtrl, server);
        this.mainCtrl = mainCtrl;
        listOfGamers = FXCollections.observableArrayList();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewList.add(emojiView0);
        viewList.add(emojiView1);
        viewList.add(emojiView2);
        viewList.add(emojiView3);
        viewList.add(emojiView4);
        viewList.add(emojiView5);
        viewList.add(emojiView6);
        viewList.add(emojiView7);
        viewList.add(emojiView8);
        viewList.add(emojiView9);
    }

    /**
     * Shows the main menu screen and disconnects the player from the lobby.
     * Also stops all the websocket connections.
     */
    public void showMainMenu() {
        String name = player.name;
        int lobbyID = player.lobbyID;
        Tuple tuple = new Tuple(name, lobbyID);

        boolean leave = server.leaveLobby(tuple);
        player.disconnect();
        if (leave) mainCtrl.showMainMenu();
        server.unsubscribeAll();
        timer.cancel();
    }

    /**
     * Shows the settings screen and disconnects the player from the lobby.
     */
    public void showSettings() {
        mainCtrl.showSettings();
        player.disconnect();
        timer.cancel();
    }

    /**
     * Starts a timer that refreshes the gamers in lobby table every 2 seconds.
     */
    public void lobbyGamersTimer() {
        gamersInLobby.setCellValueFactory(new PropertyValueFactory<Gamer, SimpleStringProperty>("name"));
        listOfGamers.remove(0, listOfGamers.size());
        playerTable.setItems(listOfGamers);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateLobbyGamers());
            }
        }, 0, 2000);
    }

    /**
     * Updates the list of gamers in the lobby.
     */
    public void updateLobbyGamers() {
        listOfGamers.remove(0, listOfGamers.size());
        listOfGamers.addAll(server.getGamersInLobby(player.lobbyID));
    }

    /**
     * Registers the user for receiving game start boolean for the game they are in.
     * Calls startPlaying() method in when true is received
     */
    public void registerForGameStartMessages() {
        server.registerForMessages("/topic/startGame/" + player.lobbyID, Boolean.class, b -> startPlaying());
    }

    /**
     * Registers the user for receiving information about jokers for the game they are in.
     * Calls player.reduceTime() method when a joker message is received.
     */
    public void registerForJokerMessages() {
        server.registerForMessages("/topic/jokers/" + player.lobbyID, Tuple.class, j -> {
            Platform.runLater(() -> player.reduceTime(j));
            Platform.runLater(() -> SoundUtils.playSFX("joker"));
        });
    }

    /**
     * Triggers the game start for all the players.
     * The method is called when the player click "Start button".
     */
    public void startGame() {
        server.send("/app/startGame/" + player.lobbyID, true);
    }

    /**
     * Starts the game and displays the first question.
     */
    public void startPlaying() {
        Platform.runLater(mainCtrl::startGame);
    }

    /**
     * Sets the gameID.
     */
    public void setGameID() {
        this.gameID.setText("GameID: " + player.lobbyID);
    }
}
