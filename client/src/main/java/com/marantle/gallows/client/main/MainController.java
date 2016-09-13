package com.marantle.gallows.client.main;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;
import com.marantle.gallows.common.networking.Config;
import com.marantle.gallows.common.packets.GallowsRequest;
import com.marantle.gallows.common.packets.GallowsResponse;
import com.marantle.gallows.common.packets.PacketType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldMsg;

    @FXML
    private TextArea logArea;

    @FXML
    private ComboBox<PacketType> requestTypeBox;

    @FXML
    private ListView<Room> roomListView;

    @FXML
    private ListView<Player> playerListView;

    private int count = 0;
    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private int port;
    private String address;
    private Client client;
    private Stage stage;
    private Thread thread;
    private String userName;

    public void setPort(int port) {
        this.port = port;
        logger.info(String.format("Input port was [%d], port is now [%d]", port, this.port));
    }

    public void setAddress(String address) {
        this.address = address;
        logger.info(String.format("Input Address was [%s], address is now [%s]", address, this.address));
    }

    @FXML
    void initialize() {
        initClient();
        requestTypeBox.getItems().addAll(PacketType.values());
        requestTypeBox.getSelectionModel().select(PacketType.CHAT);

        textFieldMsg.setOnAction(e -> sendRequest(null));
        textFieldMsg.setText("");

        playerListView.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {

            @Override
            public ListCell<Player> call(ListView<Player> param) {
                ListCell<Player> cell = new ListCell<Player>() {

                    @Override
                    protected void updateItem(Player item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(String.format("%s[%d]", item.getPlayerName(), item.getConnectionID()));
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void initClient() {


//        buttonConnect.setDisable(true);
        client = new Client();
        thread = new Thread(client);
        thread.setDaemon(true);
        thread.start();
        Config.configure(client.getKryo());

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof GallowsResponse) {
                    GallowsResponse response = (GallowsResponse) object;
                    logger.info("server responded: " + "[" +response.getPacketType().toString() + "]: " + response.getText());
                    logArea.appendText(response.getText() + "\n");
                    switch (response.getPacketType()) {

                    case LIST_PLAYERS:
                        populatePlayerList(response);
                        break;
                    case LIST_ROOMS:
                        populateRoomList(response);
                        break;
                    case CONNECT:
                        requestPlayers();
                        requestRooms();
                        break;
                    case JOIN_ROOM:
                        requestToJoinRoom();
                        break;
                    case GUESS:
                        break;
                    case CHAT:
                        break;
                    case LEAVE_ROOM:
                        break;
                    case MESSAGE:
                        logArea.appendText(response.getText()+"\n");
                        break;
                    }
                }
            }
        });
    }

    @FXML
    void sendRequest(ActionEvent actionEvent) {
        GallowsRequest request = new GallowsRequest();
        request.setPlayerName(getUserName());
        request.setMsg(textFieldMsg.getText());

        GridPane gridPane = new GridPane();
        Optional<PacketType> requestType = Optional.of(requestTypeBox.getSelectionModel().getSelectedItem());
        request.setRoom("test");
        request.setPacketType(requestType.orElse(PacketType.LIST_PLAYERS));
        client.sendTCP(request);
    }

    private String getUserName() {
        if (!Strings.isNullOrEmpty(this.userName))
            return this.userName;

        String systemName = System.getProperty("user.name");
        List<String> chars = Splitter.fixedLength(1).splitToList(systemName);
        chars.forEach(Log::info);
        this.userName = chars.stream().reduce((p, n) -> {
            logger.info(String.format("prev is [%s] next is [%s]", p, n));
            if (p.length() == 1) {
                char temp = p.charAt(0);
                temp = (char)(((int)temp) + 1);
                p = String.valueOf(temp);
            }
            String previous = p;
            char next = n.charAt(0);
            next = (char)(((int)next) + 1);
           return Joiner.on("").join(previous, next);
        }).orElse(systemName);

        return this.userName;
    }

    void connectToServer() {

        try {
            logger.info("trying connect with " + this.address + " and " + this.port);
            client.connect(5000, this.address, this.port, this.port+1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GallowsRequest request = new GallowsRequest();
        request.setPlayerName(getUserName());
        request.setMsg("Initialize connection");
        request.setRoom("Lobby");
        request.setPacketType(PacketType.CONNECT);
        client.sendTCP(request);
    }

    private void populatePlayerList(GallowsResponse response) {
        List<Player> players = response.getPlayersList();
        players.forEach(p -> logger.info(p.toString()));
        Platform.runLater(() -> playerListView.getItems().setAll(players));
    }

    private void populateRoomList(GallowsResponse response) {
        List<Room> rooms = response.getRoomList();
        rooms.forEach(p -> logger.info(p.toString()));
        Platform.runLater(() -> roomListView.getItems().setAll(rooms));
    }

    private void requestToJoinRoom() {
        GallowsRequest request = new GallowsRequest();
        request.setPacketType(PacketType.JOIN_ROOM);
        Platform.runLater(() -> logArea.appendText("Requesting to join a room\n"));
    }

    private void requestPlayers() {
        GallowsRequest request = new GallowsRequest();
        request.setPacketType(PacketType.LIST_PLAYERS);
        Platform.runLater(() -> logArea.appendText("Requesting players\n"));
        client.sendTCP(request);
    }

    private void requestRooms() {
        GallowsRequest request = new GallowsRequest();
        request.setPacketType(PacketType.LIST_ROOMS);
        Platform.runLater(() -> logArea.appendText("Requesting rooms\n"));
        client.sendTCP(request);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(e ->{
            Platform.exit();
        });
    }
}
