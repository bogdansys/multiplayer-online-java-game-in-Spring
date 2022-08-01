package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NameInputControllerTest {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private WaitingRoomController waitingRoomController;
    private NameInputController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.waitingRoomController = Mockito.mock(WaitingRoomController.class);
        this.controller = new NameInputController(mainCtrl, server, waitingRoomController);
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showGamemodeSelectionTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void confirmTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void clearFieldTest() {
        //Affects UI, so shouldn't be tested
    }
}