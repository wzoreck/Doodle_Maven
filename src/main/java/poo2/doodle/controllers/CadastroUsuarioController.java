package poo2.doodle.controllers;

import javafx.fxml.FXML;
import poo2.doodle.App;

public class CadastroUsuarioController {
	
	public CadastroUsuarioController() {
		App.setResizable(false);
	}

	@FXML
	private void voltar() {
		App.setRoot("login");
	}
}
