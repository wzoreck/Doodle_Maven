package poo2.doodle;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void logout() {
        try {
			App.setRoot("login");
		} catch (IOException e) {
			System.err.println("Erro ao carregar a janela de Login");
		}
    }
}
