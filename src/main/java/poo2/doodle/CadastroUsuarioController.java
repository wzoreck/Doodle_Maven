package poo2.doodle;

import javafx.fxml.FXML;

public class CadastroUsuarioController {
	
	public CadastroUsuarioController() {
		App.setResizable(false);
	}

	@FXML
	private void voltar() {
		App.setRoot("login");
	}
}
