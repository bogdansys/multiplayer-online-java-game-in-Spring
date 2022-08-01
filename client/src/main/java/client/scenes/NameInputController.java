package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class NameInputController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final WaitingRoomController waitingRoomController;
    public final Preferences pref;

    @FXML
    private TextField enterName;
    @FXML
    private Label labelOneWord;

    /**
     * Constructor for NameInputController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param waitingRoomController a WaitingRoomController object
     */
    @Inject
    public NameInputController(MainCtrl mainCtrl, ServerUtils server, WaitingRoomController waitingRoomController) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.waitingRoomController = waitingRoomController;
        this.pref = Preferences.userNodeForPackage(MainCtrl.class);
    }

    /**
     * Shows the GameModeSelectionScene.
     */
    public void showGamemodeSelection() {
        labelOneWord.setVisible(false);
        mainCtrl.showGamemodeSelection();
        if (enterName != null) {
            pref.put("username", enterName.getText());
        }
    }

    /**
     * Validates user's name.
     * Unless the name is empty, too long, or already taken, shows waiting room scene.
     * Also starts all websockets connections.
     */
    public void confirm() {
        labelOneWord.setVisible(false);
        String name = enterName.getText();
        if (name.equals("")) return;
        if (name.contains(" ")) {
            labelOneWord.setVisible(true);
            return;
        }
        mainCtrl.player.lobbyID = server.sendNameToServer_MultiPlayer(name);
        mainCtrl.player.name = name;

        waitingRoomController.registerForGameStartMessages();
        mainCtrl.registerForEmoteMessages();
        waitingRoomController.registerForJokerMessages();
        mainCtrl.player.name = name;
        pref.put("username", enterName.getText());
        mainCtrl.showWaitingRoom();
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