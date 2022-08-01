package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Gamer;
import commons.LeaderBoardEntity;
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

public class MultiplayerEndLeaderboardController extends EmoteController implements Initializable {

    public final MainCtrl mainCtrl;

    public int gameId;
    public int score;

    @FXML
    private TableView<LeaderBoardEntity> tableView;
    @FXML
    private TableColumn<LeaderBoardEntity, Integer> rankColumn;
    @FXML
    private TableColumn<LeaderBoardEntity, String> nameColumn;
    @FXML
    private TableColumn<LeaderBoardEntity, Integer> scoreColumn;
    @FXML
    private Label playerPosition;

    /**
     * Constructor.
     * @param mainCtrl
     * @param server
     */
    @Inject
    public MultiplayerEndLeaderboardController(MainCtrl mainCtrl, ServerUtils server) {
        super(mainCtrl, server);
        this.mainCtrl = mainCtrl;
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
     * Shows the main menu screen and stops all websockets connections
     * Also stops all the websocket connections.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
        server.unsubscribeAll();
    }

    /**
     * Shows the leaderboard.
     */
    public void showLeaderBoard() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gamer[] leaderboard = server.getPlayersByGameId(this.gameId);

        ObservableList<LeaderBoardEntity> data = FXCollections.observableArrayList();

        for (int i = 0; i < leaderboard.length; i++) {
            data.add(new LeaderBoardEntity(i + 1, leaderboard[i].name, leaderboard[i].score));
        }

        tableView.setItems(data);

        rankColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, Integer>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, String>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, Integer>("score"));

        playerPosition.setText("Your Score: " + score);
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
