package poo2.doodle.controllers;

import javafx.fxml.FXML;
import poo2.doodle.App;

public class CadastroCursoController {
	
	@FXML
	private void voltar() {
		App.setRoot("professorMain");
	}
}
