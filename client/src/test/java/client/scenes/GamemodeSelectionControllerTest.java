package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GamemodeSelectionControllerTest {

    private MainCtrl mainCtrl;
    private GamemodeSelectionController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.controller = new GamemodeSelectionController(mainCtrl);
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showMainMenuTest() {
        controller.showMainMenu();
        verify(mainCtrl, times(1)).showMainMenu();
    }

    @Test
    void startSingleplayerTest() {
        controller.startSingleplayer();
        verify(mainCtrl, times(1)).showSinglePlayerNameInput();
    }

    @Test
    void joinLobbyTest() {
        controller.joinLobby();
        verify(mainCtrl, times(1)).showGameIDInput();
    }

    @Test
    void createLobbyTest() {
        controller.createLobby();
        verify(mainCtrl, times(1)).showNameInput();
    }
}