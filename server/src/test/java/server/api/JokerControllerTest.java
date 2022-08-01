package server.api;

import commons.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class JokerControllerTest {
    private SimpMessagingTemplate simpMessagingTemplate;
    private JokerController jokerController;

    @BeforeEach
    void setup() {
        simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        jokerController = new JokerController(simpMessagingTemplate);
    }

    @Test
    void constructorTest() {
        assertEquals(simpMessagingTemplate, jokerController.template);
    }

    @Test
    void receiveAndSendEmote() {
        Tuple joker = new Tuple("player1", 5);
        jokerController.receiveAndSendEmote(12345, joker);
        verify(simpMessagingTemplate, times(1)).convertAndSend("/topic/jokers/12345", joker);
    }
}