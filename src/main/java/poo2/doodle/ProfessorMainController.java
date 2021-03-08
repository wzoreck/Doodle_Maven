package poo2.doodle;

import javafx.fxml.FXML;

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
