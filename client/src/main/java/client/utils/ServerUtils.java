/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import commons.Activity;
import commons.Gamer;
import commons.Question;
import commons.Tuple;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * A method that resets the tuple at the path to null and 0 (name and time) when called.
     */
    public void timeReductionReset() {
        Tuple data = new Tuple(null, 0);
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/timeReduction") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(data, APPLICATION_JSON), Tuple.class);
    }

    /**
     * receives a list sorted by score of all gamers.
     *
     * @return array of gamers
     */
    public Gamer[] getMatchHistoryByName(String name) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(String.format("/api/gamer/matchhistory/%s", name)) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Gamer[]>() {
                });
    }

    /**
     * Sends to the server a name, and it creates both a game and a gamer from that
     * name, sends back the game id.
     * It also stores the gamer
     * and the score of gamer will be updated
     * after the gameSessionFinishes.
     *
     * @param name name of the player
     * @return game ID
     */
    public int sendNameToServer_SinglePlayer(String name) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/singlePlayer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(name, APPLICATION_JSON), Integer.class);
    }

    /**
     * Sends an array of 2 elements, an id and a score
     * for the server to later update on
     * the database.
     */
    public void updateScoreOfGamer(Tuple data) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/gamer/updateScore") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(data, APPLICATION_JSON));
    }

    /**
     * Informs the server that it can disable the game.
     * @param gameID ID of the game to be disabled
     */
    public void disableGame(int gameID) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/disablegame") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(gameID, APPLICATION_JSON), boolean.class);
    }

    private StompSession session = connect("ws://localhost:8080/websocket");
    private List<StompSession.Subscription> subscriptions = new ArrayList<>();

    /**
     * Initializes StompSession.
     * @param url URL to connect to
     * @return StompSession object
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    /**
     * Registers for websocket messages.
     * @param destination path
     * @param type type of expected message object
     * @param consumer method that is executed immediately after a message is received
     * @param <T>
     */
    public <T> void registerForMessages(String destination, Class<T> type, Consumer<T> consumer) {
        subscriptions.add(session.subscribe(destination, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        }));
    }

    /**
     * Unsubscribes from all websocket messages.
     */
    public void unsubscribeAll() {
        for (StompSession.Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
    }

    /**
     * Sends a websocket message.
     * @param destination path
     * @param o object to send
     */
    public void send(String destination, Object o) {
        session.send(destination, o);
    }

    /**
     * Sends name to the server.
     * @param name name of the player
     * @return lobby ID
     */
    public int sendNameToServer_MultiPlayer(String name) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/createLobby") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(name, APPLICATION_JSON), int.class);
    }

    /**
     * Sends name and lobby ID to the server.
     * @param pair Tuple of name and lobby ID
     * @return true iff the combination doesn't exist in the database yet
     */
    public boolean sendNameAndLobbyIDToServer_MultiPlayer(Tuple pair) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/joinLobby") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(pair, APPLICATION_JSON), Boolean.class);
    }

    /**
     * Sends lobby ID to the server.
     * @param id lobby ID
     * @return 0 if game exists and hasn't started,
     * 1 if there is no game with the specified id,
     * 2 if the game has already started
     */
    public int sendLobbyIDToServer_MultiPlayer(int id) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/checkLobbyID") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(id, APPLICATION_JSON), Integer.class);
    }


    /**
     * Gets players by game ID.
     * @param gameId ID of the game
     * @return an array of Gamer objects
     */
    public Gamer[] getPlayersByGameId(int gameId) {

        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/gamer/playersingameById") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(gameId, APPLICATION_JSON), Gamer[].class);
    }

    /**
     * Returns an array of singleplayer Gamer objects.
     * @return an array of Gamer objects
     */
    public Gamer[] getSinglePlayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(String.format("/api/gamer/allSinglePlayer")) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Gamer[]>() {
                });
    }

    /**
     * Gets the number of players in game by game ID.
     * @param gameID ID of the game
     * @return the number of players in game
     */
    public int getPlayersInGame(int gameID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(String.format("/api/game/playersingame/%s", gameID)) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Integer>() {
                });
    }

    /**
     * Returns the number of players online.
     * @return the number of players online
     */
    public int getPlayersOnline() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/game/playersonline") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Integer>() {
                });
    }

    /**
     * Returns the number of games ongoing.
     * @return the number of games ongoing
     */
    public int getGamesOngoing() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/game/gamesongoing") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Integer>() {
                });
    }

    /**
     * Lets the server know that the player has left the lobby.
     * @param pair Tuple of name and game ID
     * @return true if deleting the gamer was successful
     */
    public boolean leaveLobby(Tuple pair) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/leaveLobby") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(pair, APPLICATION_JSON), boolean.class);
    }

    /**
     * Disconnects the player.
     * @param data Tuple of name and game ID
     */
    public void disconnect(Tuple data) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/disconnect") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(data.value, APPLICATION_JSON), boolean.class);

        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/gamer/disconnect") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(data, APPLICATION_JSON), boolean.class);
    }

    /**
     * Gets one multiplayer question.
     * @param data Tuple of question number and game ID
     * @return Question object
     */
    public Question getMultiplayerQuestions(Tuple data) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/getQuestionByIndex") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(data, APPLICATION_JSON), new GenericType<Question>() {
                });

    }

    /**
     * Returns a list of players in the lobby.
     * @param id game ID
     * @return a list of players in the lobby
     */
    public List<Gamer> getGamersInLobby(int id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/game/getGamers/" + Integer.toString(id)) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Gamer>>() {
                });
    }

    /**
     * Posts Activity to the server.
     * @param activity Activity to be posted
     * @return a boolean that is true if the activity has been successfully added
     */
    public boolean postActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activity/addActivity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(activity, APPLICATION_JSON), boolean.class);
    }

    /**
     * Deletes an Activity.
     * @param activity Activity to be deleted
     * @return a boolean that is true if the activity has been successfully deleted
     */
    public boolean deleteActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activity/removeActivity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(activity, APPLICATION_JSON), boolean.class);
    }

    /**
     * Returns all the activities.
     * @return a list of all activities
     */
    public List<Activity> getActivities() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Activity>>() {
                });
    }
}