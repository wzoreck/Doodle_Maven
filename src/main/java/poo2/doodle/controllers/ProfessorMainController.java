package poo2.doodle.controllers;

import javafx.fxml.FXML;
import poo2.doodle.App;

public class ProfessorMainController {

	@FXML
	private void sair() {
		App.setRoot("login");
	}

	@FXML
	private void acessarCurso() {
		App.setRoot("curso");
	}

	@FXML
	private void criarCurso() {
		App.setRoot("cadastroCurso");
	}
}
