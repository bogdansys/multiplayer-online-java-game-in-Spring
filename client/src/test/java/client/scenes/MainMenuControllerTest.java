package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MainMenuControllerTest {

    private MainCtrl mainCtrl;
    private ServerUtils server;
    private MainMenuController controller;

    @BeforeEach
    public void setup() {
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        this.server = Mockito.mock(ServerUtils.class);
        this.controller = new MainMenuController(mainCtrl, server);
        controller.timer = new Timer();
    }

    @Test
    void constructorTest() {
        assertNotNull(controller);
    }

    @Test
    void showGamemodeSelectionTest() {
        controller.showGamemodeSelection();
        verify(mainCtrl, times(1)).showGamemodeSelection();
    }

    @Test
    void showNameInputMatchHistoryTest() {
        controller.showNameInputMatchHistory();
        verify(mainCtrl, times(1)).showNameInputMatchHistory();
    }

    @Test
    void showSettingsTest() {
        controller.showSettings();
        verify(mainCtrl, times(1)).showSettings();
    }

    @Test
    void staticsTimerTest() {
        //Runs on a separate thread, so shouldn't be tested
    }

    @Test
    void updateStatisticsTest() {
        //Affects UI, so shouldn't be tested
    }
}