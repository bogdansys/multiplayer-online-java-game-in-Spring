package client.scenes;

import client.Player;
import client.utils.ServerUtils;
import commons.Emote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EmoteControllerTest {

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
    void displayTest() {
        //Affects UI, so shouldn't be tested
    }

    @Test
    void registerForEmoteMessagesTest() {
        controller.registerForEmoteMessages();
        verify(server, times(1)).registerForMessages(anyString(), any(), any());
    }

    @Test
    void useEmoteTest() {
        controller.useEmote(new Emote("poop"));
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("poop"));
    }

    @Test
    void usePoopTest() {
        controller.usePoop();
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("poop"));
    }

    @Test
    void useSunGlassesTest() {
        controller.useSunGlasses();
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("sun glasses"));
    }

    @Test
    void useHeartTest() {
        controller.useHeart();
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("heart"));
    }

    @Test
    void useAngryTest() {
        controller.useAngry();
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("angry"));
    }

    @Test
    void useCryingTest() {
        controller.useCrying();
        verify(server, times(1)).send("/app/emotes/" + controller.player.lobbyID, new Emote("crying"));
    }
}