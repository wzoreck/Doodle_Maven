package poo2.doodle;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setScene(FXMLUtil.loadScene("login"));
		stage.show();
	}

	static void setRoot(String fxml) {
		stage.setScene(FXMLUtil.loadScene(fxml));
	}

	public static void main(String[] args) {
		launch();
	}

}