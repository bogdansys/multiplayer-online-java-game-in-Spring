package client.scenes;

import client.Player;
import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WaitingRoomControllerTest {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private WaitingRoomController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.controller = new WaitingRoomController(mainCtrl, server);
        controller.player = Mockito.mock(Player.class);
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showMainMenuTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void showSettingsTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void registerForGameStartMessagesTest() {
        controller.registerForGameStartMessages();
        verify(server, times(1)).registerForMessages(anyString(), any(), any());
    }

    @Test
    void registerForJokerMessagesTest() {
        controller.registerForJokerMessages();
        verify(server, times(1)).registerForMessages(anyString(), any(), any());
    }

    @Test
    void startGameTest() {
        controller.startGame();
        verify(server, times(1)).send("/app/startGame/" + controller.player.lobbyID, true);
    }

    @Test
    void startPlayingTest() {
        //Runs on a separate thread, so shouldn't be tested
    }

    @Test
    void setGameIDTest() {
        //Affects UI, so shouldn't be tested
    }
}