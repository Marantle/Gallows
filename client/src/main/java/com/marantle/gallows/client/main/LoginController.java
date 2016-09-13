package com.marantle.gallows.client.main;

import com.esotericsoftware.minlog.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    private ComboBox<?> serverComboBox;
    private int port;
    private String address;
    private Stage stage;

    @FXML
    void handleComboboxSelection(ActionEvent event) {

    }

    @FXML
    void handleTextfieldInput(ActionEvent event) {
        this.address = addressField.getText();
        this.port = Integer.parseInt(portField.getText());
        Log.info("address: " + this.address + " port: " + port);
        stage.close();
    }

    @FXML
    void initialize() {
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'loginview.fxml'.";
        assert portField != null : "fx:id=\"portField\" was not injected: check your FXML file 'loginview.fxml'.";
        assert serverComboBox != null : "fx:id=\"serverComboBox\" was not injected: check your FXML file 'loginview.fxml'.";

    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
