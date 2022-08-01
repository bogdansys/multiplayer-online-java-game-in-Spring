package server.api;

import commons.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/joker")
public class JokerController {
    public SimpMessagingTemplate template;

    /**
     * Constructor for JokerController.
     * @param template a SimpMessagingTemplate object
     */
    @Autowired
    public JokerController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Receives a time reduction joker for a particular gameId and sends it to all gamers in that game.
     * The endpoints should only be used for time reduction joker.
     *
     * @param gameId id of the game
     * @param joker  joker sent to the server
     */
    @MessageMapping("/jokers/{gameId}")
    public void receiveAndSendEmote(@DestinationVariable int gameId, Tuple joker) {
        this.template.convertAndSend("/topic/jokers/" + gameId, joker);
    }
}
