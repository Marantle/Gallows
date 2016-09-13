package com.marantle.gallows.client.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

public class StartClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loginLoader = new FXMLLoader();
			loginLoader.setLocation(getClass().getResource("loginview.fxml"));
			BorderPane loginPane = loginLoader.load();
			LoginController loginController = loginLoader.getController();


			Scene loginScene = new Scene(loginPane,400,400);
			Stage loginStage = new Stage();
			loginController.setStage(loginStage);
			loginStage.setScene(loginScene);
			loginStage.showAndWait();

			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(getClass().getResource("mainview.fxml"));
			BorderPane root = rootLoader.load();
			MainController mainController = rootLoader.getController();
			mainController.setPort(loginController.getPort());
			mainController.setAddress(loginController.getAddress());
			mainController.setStage(primaryStage);
			mainController.connectToServer();
			Scene scene = new Scene(root, 700, 700);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
