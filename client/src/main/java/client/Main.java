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

package client;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Launches the client.
     * @param args
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws java.lang.Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        var addActivity = FXML.load(AddActivityController.class, "client", "scenes", "AddActivity.fxml");
        var adminPanel = FXML.load(AdminPanelController.class, "client", "scenes", "AdminPanel.fxml");
        var closeConfirmation = FXML.load(CloseConfirmationController.class, "client", "scenes", "CloseConfirmation.fxml");
        var gameIDInput = FXML.load(GameIDInputController.class, "client", "scenes", "GameIDInput.fxml");
        var gamemodeSelection = FXML.load(GamemodeSelectionController.class, "client", "scenes", "GamemodeSelection.fxml");
        var mainMenu = FXML.load(MainMenuController.class, "client", "scenes", "MainMenu.fxml");
        var matchHistory = FXML.load(MatchHistoryController.class, "client", "scenes", "MatchHistory.fxml");
        var multiplayerEndLeaderboard = FXML.load(MultiplayerEndLeaderboardController.class, "client", "scenes", "MultiplayerEndLeaderboard.fxml");
        var nameInput = FXML.load(NameInputController.class, "client", "scenes", "NameInput.fxml");
        var nameInputMatchHistory = FXML.load(NameInputMatchHistoryController.class, "client", "scenes", "NameInputMatchHistory.fxml");
        var closedQuestion = FXML.load(ClosedQuestionController.class, "client", "scenes", "ClosedQuestion.fxml");
        var openQuestion = FXML.load(OpenQuestionController.class, "client", "scenes", "OpenQuestion.fxml");
        var settings = FXML.load(SettingsController.class, "client", "scenes", "Settings.fxml");
        var singleplayerEndLeaderboard = FXML.load(SingleplayerEndLeaderboardController.class, "client", "scenes", "SingleplayerEndLeaderboard.fxml");
        var waitingRoom = FXML.load(WaitingRoomController.class, "client", "scenes", "WaitingRoom.fxml");
        var singlePlayerNameInput = FXML.load(SinglePlayerNameInputController.class, "client", "scenes", "SinglePlayerNameInput.fxml");
        var nameInputJoinLobby = FXML.load(NameInputJoinLobbyController.class, "client", "scenes", "nameInputJoinLobby.fxml");
        var tutorial1 = FXML.load(Tutorial1Controller.class, "client", "scenes", "tutorial1.fxml");
        var tutorial2 = FXML.load(Tutorial2Controller.class, "client", "scenes", "tutorial2.fxml");
        var tutorial3 = FXML.load(Tutorial3Controller.class, "client", "scenes", "tutorial3.fxml");
        var tutorial4 = FXML.load(Tutorial4Controller.class, "client", "scenes", "tutorial4.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, addActivity, adminPanel, closeConfirmation,
                singlePlayerNameInput, gameIDInput, gamemodeSelection, mainMenu, matchHistory,
                multiplayerEndLeaderboard, nameInput, nameInputMatchHistory,
                closedQuestion, openQuestion, settings, singleplayerEndLeaderboard, waitingRoom,
                nameInputJoinLobby, tutorial1, tutorial2, tutorial3, tutorial4);
    }
}