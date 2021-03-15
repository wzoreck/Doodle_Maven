package poo2.doodle;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

	private static Thread loadFromFileToDB;
	
	public static void setLoadFromFileToDB(Thread loadFromFileToDB) {
		App.loadFromFileToDB = loadFromFileToDB;
	}

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Doodle");
			stage.show();
			
			loadFromFileToDB.start();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Error!", "Error!", "Fail to load login.fxml", e);
			alert.showAndWait();
		}
	}
}