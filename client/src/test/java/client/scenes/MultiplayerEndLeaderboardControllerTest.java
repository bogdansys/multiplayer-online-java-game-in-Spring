package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MultiplayerEndLeaderboardControllerTest {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private MultiplayerEndLeaderboardController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.controller = new MultiplayerEndLeaderboardController(mainCtrl, server);
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showMainMenuTest() {
        controller.showMainMenu();
        verify(mainCtrl, times(1)).showMainMenu();
        verify(server, times(1)).unsubscribeAll();
    }
}