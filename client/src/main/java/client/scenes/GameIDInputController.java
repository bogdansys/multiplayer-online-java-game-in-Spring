package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class GameIDInputController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final Preferences pref;

    @FXML
    public TextField gameIDInput;

    @FXML
    public Label errorExist;
    @FXML
    public Label errorNumber;
    @FXML
    public Label errorStarted;

    /**
     * Constructor.
     * @param mainCtrl
     * @param server
     */
    @Inject
    public GameIDInputController(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.pref = Preferences.userNodeForPackage(MainCtrl.class);
    }

    /**
     * Returns to the gamemode selection menu and makes the error labels invisible.
     */
    public void showGamemodeSelection() {
        errorExist.setVisible(false);
        errorStarted.setVisible(false);
        errorNumber.setVisible(false);
        mainCtrl.showGamemodeSelection();
        pref.put("gameId", gameIDInput.getText());
    }

    /**
     * Validates the game ID entered by the user.
     * If the game ID exists, shows name input scene.
     */
    public void join() {
        int id;
        try {
            id = Integer.parseInt(gameIDInput.getText());
        } catch (NumberFormatException e) {
            errorExist.setVisible(false);
            errorStarted.setVisible(false);
            errorNumber.setVisible(true);
            return;
        }
        int error = server.sendLobbyIDToServer_MultiPlayer(id);
        switch (error) {
            case 0:
                mainCtrl.player.lobbyID = id;
                mainCtrl.showNameInputJoinLobby();
                pref.put("gameId", gameIDInput.getText());
                break;
            case 1:
                errorExist.setVisible(true);
                errorStarted.setVisible(false);
                errorNumber.setVisible(false);
                break;
            case 2:
                errorExist.setVisible(false);
                errorStarted.setVisible(true);
                errorNumber.setVisible(false);
                break;
            default:
                errorExist.setVisible(false);
                errorStarted.setVisible(false);
                errorNumber.setVisible(false);
                break;
        }
    }

    /**
     * Clears the gameIDInput FXML field.
     */
    public void clearField() {
        gameIDInput.clear();
        pref.put("gameId", gameIDInput.getText());
        gameIDInput.requestFocus();
    }

    /**
     * Puts recent game id entered by the player in the game id input text field.
     */
    public void updateGameIdTextField() {
        String userNamePreference = pref.get("gameId", "");
        gameIDInput.setText(userNamePreference);
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}