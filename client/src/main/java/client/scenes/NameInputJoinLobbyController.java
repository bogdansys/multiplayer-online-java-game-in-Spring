package client.scenes;

import client.utils.ServerUtils;
import client.utils.SoundUtils;
import com.google.inject.Inject;
import commons.Tuple;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class NameInputJoinLobbyController {

    public final MainCtrl mainCtrl;
    public final ServerUtils server;
    public final WaitingRoomController waitingRoomController;
    public final Preferences pref;

    @FXML
    private TextField enterName;
    @FXML
    private Label labelTaken1;
    @FXML
    private Label labelTaken2;
    @FXML
    private Label labelOneWord;

    /**
     * Constructor for NameInputJoinLobbyController.
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     * @param waitingRoomController a WaitingRoomController object
     */
    @Inject
    public NameInputJoinLobbyController(MainCtrl mainCtrl, ServerUtils server, WaitingRoomController waitingRoomController) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.waitingRoomController = waitingRoomController;
        this.pref = Preferences.userNodeForPackage(MainCtrl.class);
    }

    /**
     * Shows the GameModeSelectionScene.
     */
    public void showGamemodeSelection() {
        resetErrorLabels();
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
        resetErrorLabels();
        String name = enterName.getText();
        if (name.equals("")) return;
        if (name.contains(" ")) {
            labelOneWord.setVisible(true);
            return;
        }
        int lobbyID = mainCtrl.player.lobbyID;
        boolean x = server.sendNameAndLobbyIDToServer_MultiPlayer(new Tuple(name, lobbyID));
        pref.put("username", enterName.getText());

        if (!x) {
            labelTaken1.setVisible(true);
            labelTaken2.setVisible(true);
        } else {
            mainCtrl.player.name = name;

            waitingRoomController.registerForGameStartMessages();
            mainCtrl.registerForEmoteMessages();
            waitingRoomController.registerForJokerMessages();

            mainCtrl.showWaitingRoom();
        }
    }

    /**
     * Makes all the error labels invisible.
     */
    public void resetErrorLabels() {
        labelTaken1.setVisible(false);
        labelTaken2.setVisible(false);
        labelOneWord.setVisible(false);
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