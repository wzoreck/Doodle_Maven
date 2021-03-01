package poo2.doodle;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import poo2.doodle.entidades.Professor;

public class ProfessorMainController {
	
	Professor professor = null;
	private TextArea txtNome;
	
//	public ProfessorMainController(Professor professor) {
//		this.professor = professor;
//		txtNome.setText(professor.getNome());
//	}
	
	@FXML
	private void voltar() {
		try {
			App.setRoot("login");
		} catch (IOException e) {
			System.err.println("Erro ao retornar para janela Login");
		}
	}
}
