/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.Player;
import client.utils.QuestionUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class MainCtrl {

    private Stage primaryStage;
    private Stage popup;

    private AddActivityController addActivityController;
    private Scene addActivity;

    private AdminPanelController adminPanelController;
    private Scene adminPanel;

    private CloseConfirmationController closeConfirmationController;
    private Scene closeConfirmation;

    private GameIDInputController gameIDInputController;
    private Scene gameIDInput;

    private GamemodeSelectionController gamemodeSelectionController;
    private Scene gamemodeSelection;

    private MainMenuController mainMenuController;
    private Scene mainMenu;

    private MatchHistoryController matchHistoryController;
    private Scene matchHistory;

    private MultiplayerEndLeaderboardController multiplayerEndLeaderboardController;
    private Scene multiplayerEndLeaderboard;

    private NameInputController nameInputController;
    private Scene nameInput;

    private NameInputMatchHistoryController nameInputMatchHistoryController;
    private Scene nameInputMatchHistory;

    private ClosedQuestionController closedQuestionController;
    private Scene closedQuestion;

    private OpenQuestionController openQuestionController;
    private Scene openQuestion;

    private SettingsController settingsController;
    private Scene settings;

    private SingleplayerEndLeaderboardController singleplayerEndLeaderboardController;
    private Scene singleplayerEndLeaderboard;

    private WaitingRoomController waitingRoomController;
    private Scene waitingRoom;

    private SinglePlayerNameInputController singlePlayerNameInputController;
    private Scene singlePlayerNameInput;

    private NameInputJoinLobbyController nameInputJoinLobbyController;
    private Scene nameInputJoinLobby;

    private Tutorial1Controller tutorial1Controller;
    private Scene tutorial1;

    private Tutorial2Controller tutorial2Controller;
    private Scene tutorial2;

    private Tutorial3Controller tutorial3Controller;
    private Scene tutorial3;

    private Tutorial4Controller tutorial4Controller;
    private Scene tutorial4;

    public QuestionUtils questionUtils;
    public Player player;
    public List<Scene> scenes;
    public double oldWidth;
    public double oldHeight;
    public boolean listenToSizeChanges;

    /**
     * Constructor for questionUtils object.
     * @param questionUtils a QuestionUtils object
     */
    @Inject
    public MainCtrl(QuestionUtils questionUtils) {
        this.questionUtils = questionUtils;
    }

    /**
     * Initializes all the following params.
     * @param primaryStage
     * @param addActivity
     * @param adminPanel
     * @param closeConfirmation
     * @param singlePlayerNameInput
     * @param gameIDInput
     * @param gamemodeSelection
     * @param mainMenu
     * @param matchHistory
     * @param multiplayerEndLeaderboard
     * @param nameInput
     * @param nameInputMatchHistory
     * @param closedQuestion
     * @param openQuestion
     * @param settings
     * @param singleplayerEndLeaderboard
     * @param waitingRoom
     * @param nameInputJoinLobby
     * @param tutorial1
     * @param tutorial2
     * @param tutorial3
     * @param tutorial4
     */
    public void initialize(Stage primaryStage, Pair<AddActivityController, Parent> addActivity,
                           Pair<AdminPanelController, Parent> adminPanel,
                           Pair<CloseConfirmationController, Parent> closeConfirmation,
                           Pair<SinglePlayerNameInputController, Parent> singlePlayerNameInput,
                           Pair<GameIDInputController, Parent> gameIDInput,
                           Pair<GamemodeSelectionController, Parent> gamemodeSelection,
                           Pair<MainMenuController, Parent> mainMenu,
                           Pair<MatchHistoryController, Parent> matchHistory,
                           Pair<MultiplayerEndLeaderboardController, Parent> multiplayerEndLeaderboard,
                           Pair<NameInputController, Parent> nameInput,
                           Pair<NameInputMatchHistoryController, Parent> nameInputMatchHistory,
                           Pair<ClosedQuestionController, Parent> closedQuestion,
                           Pair<OpenQuestionController, Parent> openQuestion,
                           Pair<SettingsController, Parent> settings,
                           Pair<SingleplayerEndLeaderboardController, Parent> singleplayerEndLeaderboard,
                           Pair<WaitingRoomController, Parent> waitingRoom,
                           Pair<NameInputJoinLobbyController, Parent> nameInputJoinLobby,
                           Pair<Tutorial1Controller, Parent> tutorial1,
                           Pair<Tutorial2Controller, Parent> tutorial2,
                           Pair<Tutorial3Controller, Parent> tutorial3,
                           Pair<Tutorial4Controller, Parent> tutorial4) {

        this.addActivityController = addActivity.getKey();
        this.addActivity = new Scene(addActivity.getValue());

        this.adminPanelController = adminPanel.getKey();
        this.adminPanel = new Scene(adminPanel.getValue());

        this.closeConfirmationController = closeConfirmation.getKey();
        this.closeConfirmation = new Scene(closeConfirmation.getValue());
        this.closeConfirmation.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.closeConfirmation.getRoot().setStyle("-fx-border-color: black;" +
                "-fx-border-width: 5px;");

        this.singlePlayerNameInputController = singlePlayerNameInput.getKey();
        this.singlePlayerNameInput = new Scene(singlePlayerNameInput.getValue());

        this.gameIDInputController = gameIDInput.getKey();
        this.gameIDInput = new Scene(gameIDInput.getValue());

        this.gamemodeSelectionController = gamemodeSelection.getKey();
        this.gamemodeSelection = new Scene(gamemodeSelection.getValue());

        this.mainMenuController = mainMenu.getKey();
        this.mainMenu = new Scene(mainMenu.getValue());

        this.matchHistoryController = matchHistory.getKey();
        this.matchHistory = new Scene(matchHistory.getValue());

        this.multiplayerEndLeaderboardController = multiplayerEndLeaderboard.getKey();
        this.multiplayerEndLeaderboard = new Scene(multiplayerEndLeaderboard.getValue());

        this.nameInputController = nameInput.getKey();
        this.nameInput = new Scene(nameInput.getValue());

        this.nameInputMatchHistoryController = nameInputMatchHistory.getKey();
        this.nameInputMatchHistory = new Scene(nameInputMatchHistory.getValue());

        this.settingsController = settings.getKey();
        this.settings = new Scene(settings.getValue());

        this.singleplayerEndLeaderboardController = singleplayerEndLeaderboard.getKey();
        this.singleplayerEndLeaderboard = new Scene(singleplayerEndLeaderboard.getValue());

        this.closedQuestionController = closedQuestion.getKey();
        this.closedQuestion = new Scene(closedQuestion.getValue());

        this.openQuestionController = openQuestion.getKey();
        this.openQuestion = new Scene(openQuestion.getValue());

        this.waitingRoomController = waitingRoom.getKey();
        this.waitingRoom = new Scene(waitingRoom.getValue());

        this.nameInputJoinLobbyController = nameInputJoinLobby.getKey();
        this.nameInputJoinLobby = new Scene(nameInputJoinLobby.getValue());

        this.tutorial1Controller = tutorial1.getKey();
        this.tutorial1 = new Scene(tutorial1.getValue());

        this.tutorial2Controller = tutorial2.getKey();
        this.tutorial2 = new Scene(tutorial2.getValue());

        this.tutorial3Controller = tutorial3.getKey();
        this.tutorial3 = new Scene(tutorial3.getValue());

        this.tutorial4Controller = tutorial4.getKey();
        this.tutorial4 = new Scene(tutorial4.getValue());

        popup = new Stage();
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setAlwaysOnTop(true);

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Quizzzz!");
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image("/client/scenes/Images/logo.png"));

        questionUtils.openQuestionController = openQuestionController;
        questionUtils.closedQuestionController = closedQuestionController;

        initializeFields();
        addEventListeners();
        showMainMenu();
        primaryStage.show();
    }

    /**
     * Adds necessary listeners.
     */
    public void addEventListeners() {
        addOnCloseListeners();
        addStageResizeListeners();
        addKeyPressListeners();
        addPopupListeners();
        addMouseListeners();
    }

    /**
     * Adds listeners that listen to scene switch to move the cursor a bit.
     * If it didn't happen, the style of the cursor would be set to default.
     */
    private void addMouseListeners() {
        primaryStage.sceneProperty().addListener((obs, oldVal, newVal) -> {
            Robot robot = new Robot();
            robot.mouseMove(robot.getMouseX(), robot.getMouseY() + 1);
        });
        popup.sceneProperty().addListener((obs, oldVal, newVal) -> {
            Robot robot = new Robot();
            robot.mouseMove(robot.getMouseX(), robot.getMouseY() + 1);
        });
    }

    /**
     * Adds listeners that listen to stage resizing or maximizing.
     * The listeners ensure that switching scenes preserves the window size.
     */
    public void addStageResizeListeners() {
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (listenToSizeChanges) {
                resizeScenes(primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
            }
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (listenToSizeChanges) {
                resizeScenes(primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
            }
        });
        primaryStage.maximizedProperty().addListener((obs, oldVal, newVal) -> {
            listenToSizeChanges = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    listenToSizeChanges = true;
                }
            }, 50);
            if (!primaryStage.maximizedProperty().get()) {
                resizeScenes(oldWidth, oldHeight);
                return;
            }
            oldWidth = primaryStage.getScene().getWidth();
            oldHeight = primaryStage.getScene().getHeight();
            Scene currentScene = primaryStage.getScene();
            Number width = Screen.getPrimary().getBounds().getMaxX();
            double titleHeight = currentScene.getWindow().getHeight() - currentScene.getHeight();
            Number height = Screen.getPrimary().getBounds().getMaxY() - titleHeight;
            resizeScenes(width, height);
        });
    }

    /**
     * Used to add popup listeners.
     */
    public void addPopupListeners() {
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> centerPopup());
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> centerPopup());
        primaryStage.xProperty().addListener((observable, oldValue, newValue) -> centerPopup());
        primaryStage.yProperty().addListener((observable, oldValue, newValue) -> centerPopup());
        popup.showingProperty().addListener((observable, oldValue, newValue) -> centerPopup());
    }

    /**
     * Used to create a popup in the center of the screen.
     */
    public void centerPopup() {
        double stageWidth = popup.getWidth();
        popup.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - stageWidth / 2);
        double stageHeight = popup.getHeight();
        popup.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - stageHeight / 2);
    }

    /**
     * Adds listeners that listen to close events.
     * The listeners manage closing the application.
     */
    public void addOnCloseListeners() {
        popup.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                closeApplication();
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                showCloseConfirmation();
            }
        });
    }

    /**
     * Resizes all the scenes based on width and height parameters.
     *
     * @param width  desired width of the scenes
     * @param height desired height of the scenes
     */
    public void resizeScenes(Number width, Number height) {
        closedQuestionController.resizeUIElements(width);
        openQuestionController.resizeUIElements(width);
        for (Scene scene : scenes) {
            GridPane root = (GridPane) scene.getRoot();
            root.setPrefSize(width.doubleValue(), height.doubleValue());
        }
        primaryStage.show();
    }

    /**
     * Used to enter games.
     */
    public void addKeyPressListeners() {
        this.singlePlayerNameInput.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                singlePlayerNameInputController.startSinglePlayer();
            }
        });

        this.gameIDInput.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                gameIDInputController.join();
            }
        });

        this.nameInput.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                nameInputController.confirm();
            }
        });

        this.nameInputJoinLobby.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                nameInputJoinLobbyController.confirm();
            }
        });

        showMainMenu();
        primaryStage.show();
    }

    /**
     * Initializes the Player and fields in some controllers.
     * Initializes scenes list.
     */
    public void initializeFields() {
        player = new Player("placeholder", this, closedQuestionController, closedQuestionController.server);
        closedQuestionController.player = this.player;
        openQuestionController.player = this.player;
        waitingRoomController.player = this.player;
        multiplayerEndLeaderboardController.player = this.player;
        closedQuestionController.openQuestionController = openQuestionController;
        openQuestionController.closedQuestionController = closedQuestionController;

        listenToSizeChanges = true;
        scenes = Arrays.stream(MainCtrl.class.getDeclaredFields())
                .map(field -> {
                    try {
                        return field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(object -> object instanceof Scene)
                .map(object -> (Scene) object)
                .filter(object ->
                        object != closeConfirmation &&
                                object != tutorial1 &&
                                object != tutorial2 &&
                                object != tutorial3 &&
                                object != tutorial4)
                .collect(Collectors.toList());
    }

    /**
     * Switches scenes to addActivityScene.
     */
    public void showAddActivity() {
        addActivityController.resetErrors();
        primaryStage.setScene(addActivity);
    }

    /**
     * Adds an activity to the database.
     */
    public void addedSuccessfully() {
        adminPanelController.showAddedSuccessfully(true);
    }

    /**
     * Shows the adminPanelScene.
     */
    public void showAdminPanel() {
        adminPanelController.showActivities();
        primaryStage.setScene(adminPanel);
        adminPanelController.showAddedSuccessfully(false);
    }

    /**
     * Closes the application.
     */
    public void closeApplication() {
        Preferences.userNodeForPackage(MainCtrl.class).remove("gameId");
        player.disconnect();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Shows the closeConfirmationPopup.
     */
    public void showCloseConfirmation() {
        popup.setScene(closeConfirmation);
        popup.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                closeApplication();
            }
        });
        closeConfirmationController.displayRejoinMsg(primaryStage.getScene().getRoot() == closedQuestion.getRoot());
        popup.show();
    }

    /**
     * Closes the popup.
     */
    public void closePopup() {
        popup.close();
    }

    /**
     * Switches to the gameIDInputScene.
     */
    public void showGameIDInput() {
        primaryStage.setScene(gameIDInput);
        gameIDInputController.updateGameIdTextField();
    }

    /**
     * Shows the gamemodeSelectionScene.
     */
    public void showGamemodeSelection() {
        primaryStage.setScene(gamemodeSelection);
    }

    /**
     * Starts a timer to refresh the statistics on the main menu.
     */
    public void showMainMenu() {
        mainMenuController.staticsTimer();
        primaryStage.setScene(mainMenu);
    }

    /**
     * Shows the matchHistoryScene.
     */
    public void showMatchHistory() {
        matchHistoryController.addToTable();
        primaryStage.setScene(matchHistory);
    }

    /**
     * Shows the multiplayerEndLeaderbordScene.
     */
    public void showMultiplayerEndLeaderboard() {
        multiplayerEndLeaderboardController.score = player.score;
        multiplayerEndLeaderboardController.gameId = player.lobbyID;
        multiplayerEndLeaderboardController.showLeaderBoard();
        primaryStage.setScene(multiplayerEndLeaderboard);
    }

    /**
     * Sets the question type to multiplayer.
     */
    public void showNameInput() {
        primaryStage.setScene(nameInput);
        nameInputController.updateNameTextField();
        closedQuestionController.setMultiplayer();
        openQuestionController.setMultiplayer();
    }

    /**
     * Shows the nameInputJoinLobbyScene.
     */
    public void showNameInputJoinLobby() {
        primaryStage.setScene(nameInputJoinLobby);
        nameInputJoinLobbyController.updateNameTextField();
    }

    /**
     * Shows the nameInputMatchHistoryScene.
     */
    public void showNameInputMatchHistory() {
        primaryStage.setScene(nameInputMatchHistory);
        nameInputMatchHistoryController.updateNameTextField();
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        primaryStage.setScene(closedQuestion);
        player.startRound();
    }

    /**
     * Calls a method to set up everything to make the question work.
     */
    public void showClosedQuestion() {
        primaryStage.setScene(closedQuestion);
        closedQuestionController.updateQuestion();
        closedQuestionController.resizeUIElements(primaryStage.getWidth());
    }

    /**
     * Shows the openQuestionScene.
     */
    public void showOpenQuestion() {
        primaryStage.setScene(openQuestion);
        openQuestionController.updateQuestion();
        openQuestionController.resizeUIElements(primaryStage.getWidth());
    }

    /**
     * Shows the settingsScene.
     */
    public void showSettings() {
        primaryStage.setScene(settings);
    }

    /**
     * Shows the singlePlayerEndLeaderboardScene.
     */
    public void showSingleplayerEndLeaderboard() {
        singleplayerEndLeaderboardController.score = player.score;
        singleplayerEndLeaderboardController.gameId = player.lobbyID;
        singleplayerEndLeaderboardController.showLeaderBoard();
        primaryStage.setScene(singleplayerEndLeaderboard);
    }

    /**
     * Sets the question type to singleplayer.
     */
    public void showSinglePlayerNameInput() {
        primaryStage.setScene(singlePlayerNameInput);
        closedQuestionController.setSingleplayer();
        openQuestionController.setSingleplayer();
        singlePlayerNameInputController.updateNameTextField();
    }

    /**
     * Sets the gameID, so it gets displayed right
     * And sets the question type to multiplayer.
     */
    public void showWaitingRoom() {
        primaryStage.setScene(waitingRoom);
        waitingRoomController.setGameID();
        waitingRoomController.lobbyGamersTimer();
        closedQuestionController.setMultiplayer();
        openQuestionController.setMultiplayer();
    }

    /**
     * Shows the Tutorial1Scene.
     */
    public void showTutorial1() {
        primaryStage.setScene(tutorial1);
        primaryStage.setResizable(false);
    }

    /**
     * Shows the Tutorial2Scene.
     */
    public void showTutorial2() {
        primaryStage.setScene(tutorial2);
    }

    /**
     * Shows the Tutorial3Scene.
     */
    public void showTutorial3() {
        primaryStage.setScene(tutorial3);
    }

    /**
     * Shows the Tutorial4Scene.
     */
    public void showTutorial4() {
        primaryStage.setScene(tutorial4);
    }

    /**
     * Returns the width of ClosedQuestion scene.
     *
     * @return width of ClosedQuestion scene
     */
    public int getClosedQuestionSceneWidth() {
        return (int) closedQuestion.getWidth();
    }

    /**
     * Sets the resizable property of the primary stage.
     *
     * @param resizable boolean if the stage should be resizable
     */
    public void setResizable(boolean resizable) {
        primaryStage.setResizable(resizable);
    }

    /**
     * Registers the user to receive and send emotes.
     */
    public void registerForEmoteMessages() {
        waitingRoomController.registerForEmoteMessages();
        closedQuestionController.registerForEmoteMessages();
        openQuestionController.registerForEmoteMessages();
        multiplayerEndLeaderboardController.registerForEmoteMessages();
    }

    /**
     * Checks wether emotes are enabled or disabled.
     * @return a boolean
     */
    public boolean areEmotesOn() {
        return settingsController.emotes;
    }
}
