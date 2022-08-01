package server.api;

import commons.Emote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EmoteControllerTest {
    private SimpMessagingTemplate simpMessagingTemplate;
    private EmoteController emoteController;

    @BeforeEach
    void setup() {
        simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        emoteController = new EmoteController(simpMessagingTemplate);
    }

    @Test
    void constructorTest() {
        assertEquals(simpMessagingTemplate, emoteController.template);
    }

    @Test
    void receiveAndSendEmote() {
        Emote emote = new Emote("cry");
        emoteController.receiveAndSendEmote(12345, emote);
        verify(simpMessagingTemplate, times(1)).convertAndSend("/topic/emotes/12345", emote);
    }
}