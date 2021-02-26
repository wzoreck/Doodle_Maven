package poo2.doodle.ihc;

import java.util.ArrayList;
import java.util.List;

import poo2.doodle.bd.ForumDAO;
import poo2.doodle.entidades.Curso;
import poo2.doodle.entidades.Professor;
import poo2.doodle.forum.Forum;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CursoFX extends Application {

	private Stage stage;
	private Pane pane;
	private Button btnCadastrarForum;
	private Button btnAlterarForum;
	private Button btnExcluirForum;
	private Button btnVoltar;
	private ListView<String> listaForuns;

	private Professor professor;
	private Curso curso;
	private List<Forum> foruns;

	public CursoFX(Professor professor, Curso curso) {
		this.professor = professor;
		this.curso = curso;
	}

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;

		initComponentes();

		configLayout();

		Scene scene = new Scene(pane);
		btnVoltar.requestFocus();

		stage.setScene(scene);
		stage.setTitle("Bem-Vindo ao curso de " + curso.getNome());
		stage.setResizable(true);
		stage.show();
	}

	private void initComponentes() {
		listaForuns = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(geraListaForuns());
		listaForuns.setItems(items);

		btnCadastrarForum = new Button("Cadastrar forum");
		btnCadastrarForum.setOnAction(cadastrarForum());

		btnAlterarForum = new Button("Alterar forum");
		btnAlterarForum.setOnAction(alterarForum());

		btnExcluirForum = new Button("Excluir forum");
		btnExcluirForum.setOnAction(excluirForum());

		btnVoltar = new Button("Voltar");
		btnVoltar.setOnAction(voltar());

		pane = new AnchorPane();
		pane.getChildren().addAll(listaForuns, btnCadastrarForum, btnAlterarForum, btnExcluirForum, btnVoltar);

	}

	private void configLayout() {
		pane.setPrefSize(640, 480);

		listaForuns.setLayoutX(10);
		listaForuns.setLayoutY(10);
		listaForuns.setPrefHeight(pane.getPrefHeight() - 55);
		listaForuns.setPrefWidth(pane.getPrefWidth() - 20);

		btnCadastrarForum.setLayoutX(pane.getPrefWidth() - 590);
		btnCadastrarForum.setLayoutY(pane.getPrefHeight() - 35);
		btnCadastrarForum.setPrefHeight(20);
		btnCadastrarForum.setPrefWidth(150);

		btnAlterarForum.setLayoutX(pane.getPrefWidth() - 430);
		btnAlterarForum.setLayoutY(pane.getPrefHeight() - 35);
		btnAlterarForum.setPrefHeight(20);
		btnAlterarForum.setPrefWidth(150);

		btnExcluirForum.setLayoutX(pane.getPrefWidth() - 270);
		btnExcluirForum.setLayoutY(pane.getPrefHeight() - 35);
		btnExcluirForum.setPrefHeight(20);
		btnExcluirForum.setPrefWidth(150);

		btnVoltar.setLayoutX(pane.getPrefWidth() - 110);
		btnVoltar.setLayoutY(pane.getPrefHeight() - 35);
		btnVoltar.setPrefHeight(20);
		btnVoltar.setPrefWidth(100);
	}

	private List<String> geraListaForuns() {
		List<String> retorno = new ArrayList<String>();
		foruns = new ForumDAO().listar(curso.getID());
		for (Forum forum : foruns)
			retorno.add(forum.getTitulo());
		return retorno;
	}

	private EventHandler<ActionEvent> cadastrarForum() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastrarForumFX(professor, curso).start(stage);
				} catch (Exception e) {
					AlertaFX.erro("Não foi possível iniciar a tela de cadastro de um forum!");
				}
			}
		};
	}

	private EventHandler<ActionEvent> alterarForum() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (listaForuns.getSelectionModel().isEmpty()) {
					AlertaFX.info("Selecione o forum a ser alterado");
					return;
				}

				for (Forum forum : foruns) {
					if (forum.getTitulo().contentEquals(listaForuns.getSelectionModel().getSelectedItem())) {
						try {
							new AlterarForumFX(professor, curso, forum).start(stage);
						} catch (Exception e) {
							AlertaFX.erro("Não foi possível iniciar a tela de alteração de um forum! " + e);
						}

						break;
					}
				}

			}
		};
	}

	private EventHandler<ActionEvent> excluirForum() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new ExcluirForumFX(foruns, professor, curso).start(stage);
				} catch (Exception e) {
					AlertaFX.erro("Não foi possível iniciar a tela de cadastro de um forum!");
				}
			}
		};
	}

	private EventHandler<ActionEvent> voltar() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new ProfessorMainFX(professor).start(stage);
				} catch (Exception e) {
					AlertaFX.erro("Não foi possível iniciar a tela principal de professor");
				}
			}
		};
	}

}
