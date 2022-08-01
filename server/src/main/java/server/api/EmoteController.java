package server.api;

import commons.Emote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emote")
public class EmoteController {
    public SimpMessagingTemplate template;

    /**
     * Constructor for the EmoteController object.
     * @param template a SimpMessagingTemplate object
     */
    @Autowired
    public EmoteController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * Receives an emote for a particular gameId and sends it to all gamers in that game.
     *
     * @param gameId id of the game
     * @param emote  emote sent to the server
     */
    @MessageMapping("/emotes/{gameId}")
    public void receiveAndSendEmote(@DestinationVariable int gameId, Emote emote) {
        this.template.convertAndSend("/topic/emotes/" + gameId, emote);
    }
}
