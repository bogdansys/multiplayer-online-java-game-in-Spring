package client.scenes;

import client.Player;
import client.utils.ServerUtils;
import client.utils.SoundUtils;
import commons.Emote;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.util.*;

public abstract class EmoteController {

    public final ServerUtils server;
    public final MainCtrl mainCtrl;
    public Player player;

    @FXML
    public GridPane emotesGridPane;
    @FXML
    public ImageView emojiView0;
    @FXML
    public ImageView emojiView1;
    @FXML
    public ImageView emojiView2;
    @FXML
    public ImageView emojiView3;
    @FXML
    public ImageView emojiView4;
    @FXML
    public ImageView emojiView5;
    @FXML
    public ImageView emojiView6;
    @FXML
    public ImageView emojiView7;
    @FXML
    public ImageView emojiView8;
    @FXML
    public ImageView emojiView9;

    List<ImageView> viewList = new ArrayList<>();

    /**
     * Default constructor for an emoteController.
     *
     * @param mainCtrl a MainCtrl object
     * @param server a ServerUtils object
     */
    @Inject
    public EmoteController(MainCtrl mainCtrl, ServerUtils server) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Displays the emote in the UI.
     *
     * @param emote the emote type to be displayed
     */
    public void display(Emote emote) {
        if (!mainCtrl.areEmotesOn()) return;
        Timer timer = new Timer();
        Image image = null;
        ImageView emojiView;

        switch (emote.type) {
            case "poop":
                image = new Image("/client/scenes/Images/Icons/poo.png");
                break;
            case "heart":
                image = new Image("/client/scenes/Images/Icons/heart.png");
                break;
            case "angry":
                image = new Image("/client/scenes/Images/Icons/angry.png");
                break;
            case "sun glasses":
                image = new Image("/client/scenes/Images/Icons/cool.png");
                break;
            case "crying":
                image = new Image("/client/scenes/Images/Icons/crying.png");
                break;
            default:
                break;
        }

        emojiView = viewList.get(selectImageView());
        if (emojiView.getOpacity() < 1) {
            emojiView.setImage(null);
            emojiView.setOpacity(1);
            timer.cancel();
        }
        if (emojiView.getOpacity() == 1) {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (emote.counter <= 300 && emote.counter > 0) {
                        Platform.runLater(() -> fadeOut(emote, emojiView));
                    } else timer.cancel();
                }
            }, 10, 10);
        }

        emojiView.setImage(image);
        Random rnd = new Random();
        emotesGridPane.add(emojiView, rnd.nextInt(5), rnd.nextInt(7));
    }

    /**
     * Returns the next free image view in the list of image views.
     *
     * @return an ImageView object
     */
    private int selectImageView() {
        int temp = -1;
        for (int i = 0; i < 10; i++) {
            if (viewList.get(i).getOpacity() == 1) {
                return i;
            }
            if (temp == -1) {
                temp = i;
            } else if (viewList.get(i).getOpacity() < viewList.get(temp).getOpacity()) {
                temp = i;
            }
        }
        return temp;
    }

    /**
     * Lowers the opacity of the emote used to 0.
     *
     * @param emote          the emote used
     * @param finalImageView the ImageView the emote will be displayed on
     */
    private void fadeOut(Emote emote, ImageView finalImageView) {
        emote.counter -= 1;
        finalImageView.setOpacity(((double) emote.counter) / 300);

        if (emote.counter <= 0) {
            finalImageView.setImage(null);
            finalImageView.setOpacity(1);
        }
    }

    /**
     * Registers the user for receiving emotes for the game they are in.
     * Calls display method in the EmoteController when the emote is received.
     */
    public void registerForEmoteMessages() {
        server.registerForMessages("/topic/emotes/" + player.lobbyID, Emote.class, this::display);
    }

    /**
     * Sends the emote to the server via websocket endpoint, which will send it to other players in the game.
     *
     * @param emote Emote that was used
     */
    public void useEmote(Emote emote) {
        server.send("/app/emotes/" + player.lobbyID, emote);
    }

    /**
     * Calls the useEmote method with the emote type "poop".
     */
    public void usePoop() {
        useEmote(new Emote("poop"));
    }

    /**
     * Calls the useEmote method with the emote type "sunglasses".
     */
    public void useSunGlasses() {
        useEmote(new Emote("sun glasses"));
    }

    /**
     * Calls the useEmote method with the emote type "hearth".
     */
    public void useHeart() {
        useEmote(new Emote("heart"));
    }

    /**
     * Calls the useEmote method with the emote type "angry".
     */
    public void useAngry() {
        useEmote(new Emote("angry"));
    }

    /**
     * Calls the useEmote method with the emote type "crying".
     */
    public void useCrying() {
        useEmote(new Emote("crying"));
    }

    /**
     * Plays the click sound.
     */
    public void clickSound() {
        SoundUtils.playSFX("click");
    }
}
