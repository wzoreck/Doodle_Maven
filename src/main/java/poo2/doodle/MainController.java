package poo2.doodle;

import javafx.fxml.FXML;

public class MainController {

	@FXML
	private void logout() {
		App.setRoot("login");
	}
}
