package poo2.doodle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import poo2.doodle.bd.ProfessorDAO;
import poo2.doodle.entidades.Pessoa;
import poo2.doodle.entidades.Professor;
import poo2.doodle.ihc.AlertaFX;

public class LoginController {
	
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPasswd;
	
	private ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
	private ArrayList<Professor> professores = new ArrayList<Professor>();
	private Professor professor = null;
	private ProfessorDAO professorDAO = new ProfessorDAO();
	
	// "realizar login" altera para a tela principal
	@FXML
	private void login() {
		try {
			
			buscarUsuariosBD();
			
			if (txtUsuario.getText().isBlank()) {
				AlertaFX.alerta("Campo usuário em branco");
				return;
			}

			if (txtPasswd.getText().isBlank()) {
				AlertaFX.alerta("Campo senha em branco");
				return;
			}

			for (Pessoa pessoa : pessoas) {
				if (pessoa.getLogin().contentEquals(txtUsuario.getText())) {
					if (pessoa.getPasswd().contentEquals(txtPasswd.getText())) {
						
						if (pessoa.getTipoPessoa().contentEquals("professor")) {
							professor = (Professor) pessoa;
							
							System.out.println("Encontrou");
							// Ver com passar objeto ainda!!
//							ProfessorMainController p = new ProfessorMainController(professor);
							App.setRoot("professorMain");
							break;
						}
					}
				}
			}
			
			System.out.println("Nao encontrou");
			
		} catch (IOException e) {
			System.err.println("Erro ao carregar a janela professorMain");
		}
	}
	
	@FXML
	private void sair() {
		Platform.exit();
	}
	
	@FXML
	private void novoUsuario() {
		try {
			App.setRoot("cadastroUsuario");
		} catch (IOException e) {
			System.err.println("Erro ao carregar a janela cadastroUsuario");
		}
	}
	
	private void buscarUsuariosBD() {
		professores = professorDAO.listar(0);
		pessoas = new ArrayList<Pessoa>();
		for (Professor p : professores)
			pessoas.add(p);
	}
}
