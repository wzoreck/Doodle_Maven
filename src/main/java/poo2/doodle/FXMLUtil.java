package poo2.doodle;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class FXMLUtil {

	public static Scene loadScene(String fxml) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
			Scene scene;
			scene = new Scene(fxmlLoader.load());
			return scene;
		} catch (IOException e) {
			System.err.println("Erro ao carregar " + fxml + ".fxml");
			return null;
		}
	}

}
