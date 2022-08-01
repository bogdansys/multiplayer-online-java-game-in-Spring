package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Gamer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class NameInputMatchHistoryController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final MatchHistoryController matchHistoryController;
    public final Preferences pref;

    @FXML
    private TextField enterName;
    @FXML
    private Label labelOneWord;
    @FXML
    private Label labelNoGames;

    /**
     * Constructor for NameInputJoinLobbyController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param matchHistoryController a MatchHistoryController object
     */
    @Inject
    public NameInputMatchHistoryController(MainCtrl mainCtrl, ServerUtils server, MatchHistoryController matchHistoryController) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.matchHistoryController = matchHistoryController;
        this.pref = Preferences.userNodeForPackage(MainCtrl.class);
    }

    /**
     * Shows the MainMenuScene.
     */
    public void showMainMenu() {
        labelOneWord.setVisible(false);
        labelNoGames.setVisible(false);
        mainCtrl.showMainMenu();
        if (enterName != null) {
            pref.put("username", enterName.getText());
        }
    }

    /**
     * Validates user's name.
     * Unless the name is empty, too long, or already taken, shows waiting room scene.
     */
    public void confirm() {
        labelOneWord.setVisible(false);
        labelNoGames.setVisible(false);
        String name = enterName.getText();
        if (name.equals("")) return;
        if (name.contains(" ")) {
            labelOneWord.setVisible(true);
            return;
        }

        Gamer[] matchHistory = server.getMatchHistoryByName(name);
        if (matchHistory.length == 0) {
            labelNoGames.setVisible(true);
            return;
        }
        matchHistoryController.matchHistory = matchHistory;
        pref.put("username", name);
        mainCtrl.showMatchHistory();
    }

    /**
     * Clears the gameIDInput FXML field.
     */
    public void clearField() {
        enterName.clear();
        pref.put("username", enterName.getText());
        enterName.requestFocus();
    }

    /**
     * Puts recent username used by the player in the name input text field.
     */
    public void updateNameTextField() {
        String userNamePreference = pref.get("username", "");
        enterName.setText(userNamePreference);
    }

    /**
     * Creates the clicksound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
