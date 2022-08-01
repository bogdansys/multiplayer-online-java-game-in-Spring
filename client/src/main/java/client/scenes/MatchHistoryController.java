package client.scenes;

import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Gamer;
import commons.LeaderBoardEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MatchHistoryController {

    public final MainCtrl mainCtrl;

    public Gamer[] matchHistory;

    @FXML
    TableView<LeaderBoardEntity> tableView;
    @FXML
    TableColumn<LeaderBoardEntity, Integer> rankColumn;
    @FXML
    TableColumn<LeaderBoardEntity, Integer> scoreColumn;
    @FXML
    TableColumn<LeaderBoardEntity, Integer> positionColumn;

    /**
     * Constructor.
     * @param mainCtrl
     */
    @Inject
    public MatchHistoryController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Shows main menu scene.
     */
    public void showMainMenu() {
        mainCtrl.showMainMenu();
    }

    /**
     * Updates the UI of match history table.
     */
    public void addToTable() {

        ObservableList<LeaderBoardEntity> data = FXCollections.observableArrayList();

        for (int i = 0; i < matchHistory.length; i++) {
            data.add(new LeaderBoardEntity(i + 1, matchHistory[i].score, matchHistory[i].position));
        }

        tableView.setItems(data);

        rankColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, Integer>("rank"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, Integer>("score"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardEntity, Integer>("position"));
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}