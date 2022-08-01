package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Gamer;
import commons.LeaderBoardEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SingleplayerEndLeaderboardController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;

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
     * Constructs a SingleplayerEndLeaderboardController.
     *
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     */
    @Inject
    public SingleplayerEndLeaderboardController(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Switches to the main menu scene.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Gets all the singleplayer games that are known in the database and structures this information in the table.
     */
    public void showLeaderBoard() {
        Gamer[] leaderboard = server.getSinglePlayers();

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
