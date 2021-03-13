package poo2.doodle.entidades;

import java.util.ArrayList;
import java.util.Date;

import poo2.doodle.forum.Forum;
import poo2.doodle.forum.Pergunta;

public class Aluno extends Pessoa {

	private boolean matriculado;

	public Aluno(String nome, String email, Date dataNascimento, String login, String passwd, boolean contID) {
		super(nome, email, dataNascimento, login, passwd, contID);
		this.matriculado = false;
	}
	
//	public void adicionaCursos() {
//		AlunoDAO alunoDAO = new AlunoDAO();
//		this.cursos = alunoDAO.getCursos(this.getId());
//	}
	
	public void perguntaForum(Curso curso, int idForum, String tituloPergunta, String pergunta) {
		ArrayList<Forum> foruns = null;
		foruns = curso.getConteudos();
		
		for (Forum forum : foruns) {
			if (forum.getIDForum() == idForum) {
				Date data = new Date();
				forum.adicionaPergunta(new Pergunta(this, tituloPergunta, pergunta, data, forum.getIDForum(), true));
				return;
			}
		}
	}

	public boolean isMatriculado() {
		return matriculado;
	}

	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}

	@Override
	protected String tipoPessoa() {
		return "aluno";
	}
}
