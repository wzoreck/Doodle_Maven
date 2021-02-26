package poo2.doodle.entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import poo2.doodle.bd.AlunoDAO;
import poo2.doodle.bd.CursoDAO;
import poo2.doodle.bd.ForumDAO;
import poo2.doodle.forum.Forum;

public class Curso {
	private int id;
	private String nome;
	private Date dataInicio;
	private Professor professor;
	private ArrayList<Aluno> alunos;
	private ArrayList<Conteudo> conteudos;
	private int vagas = 40;
	// Valor inicial baseado na quantidade de INSERTs iniciais em UtilBD
	private static int proxID = 2;

	public Curso(Professor professor, String nome, Date dataInicio) {
		this.professor = professor;
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.alunos = new ArrayList<Aluno>();
		this.conteudos = new ArrayList<Conteudo>();
		Curso.proxID++;
		this.id = proxID;
	}

	public void adicionaAluno(Aluno aluno) {
		if (this.alunos.size() >= this.vagas)
			return;

		this.alunos.add(aluno);
		aluno.setMatriculado(true);
		AlunoDAO alunoDAO = new AlunoDAO();
		alunoDAO.matricularAluno(aluno, this);
		aluno.adicionaCursos();
	}

	public void removeAluno(Aluno aluno) {
		AlunoDAO alunoDAO = new AlunoDAO();
		alunoDAO.desMatricularAluno(aluno, this);
	}

	public void listaAlunos() {
		AlunoDAO alunoDAO = new AlunoDAO();
		alunos = alunoDAO.getMatriculaAlunos(this);
		for (int i = 0; i < this.alunos.size(); i++) {
			System.out.println("\nNome: " + this.alunos.get(i).getNome());
			System.out.println("Email: " + this.alunos.get(i).getEmail());
			System.out.println("Data de nascimento: " + this.alunos.get(i).getDataNascimento());
			System.out.println(this.alunos.get(i).isMatriculado() ? "Está matriculado!" : "Não está matriculado!");
		}
	}
	
	public void adicionaConteudo(Conteudo conteudo) {
		this.conteudos.add(conteudo);
	}

	public void adicionaForum(String titulo, String descricao, Date data) {
		for (int i = 0; i < conteudos.size(); i++)
			if (this.conteudos.get(i).getTitulo().contentEquals(titulo))
				return;

		Forum f = new Forum(titulo, descricao, data, true, true);
		this.conteudos.add(f);

		CursoDAO cursoDAO = new CursoDAO();
		cursoDAO.adicinarConteudo((Conteudo) f, this.id);
		ForumDAO forumDAO = new ForumDAO();
		ArrayList<Integer> id = new ArrayList<Integer>();
		id.add(f.getId());
		forumDAO.adicionar(f, id);
	}

	public void atualizarForum(int idForum, String titulo, String descricao) {
		ForumDAO forumDAO = new ForumDAO();
		ArrayList<Forum> foruns = forumDAO.listar(this.getID());
		Date data = new Date();

		for (Forum forum : foruns) {
			if (forum.getIDForum() == idForum) {
				Forum f = new Forum(titulo, descricao, data, false, false);
				f.setId(forum.getId());
				f.setIDForum(forum.getIDForum());
				forumDAO.atualizar(f, 0);
				return;
			}
		}
	}

	public void removeConteudo(int id) {
		ForumDAO forumDAO = new ForumDAO();
		
		for (Forum forum : this.getConteudos()) {
			if (forum.getIDForum() == id) {
				forumDAO.remover(forum);
				return;
			}
		}
	}

	public void informacaoCurso() {
		System.out.println("ID: " + this.id);
		System.out.println("Nome: " + this.nome);
		System.out.println("Criado em: " + getDataInicio());
		System.out.println("Professor: " + this.professor.getNome());
		System.out.println("Contato: " + this.professor.getEmail());
	}

	public void listaConteudos() {
		ForumDAO forumDAO = new ForumDAO();
		ArrayList<Forum> foruns = new ArrayList<Forum>();
		foruns = forumDAO.listar(this.getID());

		for (Forum forum : foruns) {
			System.out.println("\nID Forum: " + forum.getIDForum());
			System.out.println("Titulo: " + forum.getTitulo());
			System.out.println("Descrição: " + forum.getDescricao());
			System.out.println("Data Pubicação: " + forum.getDataPublicacao());
			forum.listar();
		}
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataInicio() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dataInicio);
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Professor getProfessor() {
		return professor;
	}

	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}

	public ArrayList<Forum> getConteudos() {
		ForumDAO forumDAO = new ForumDAO();
		return forumDAO.listar(this.getID());
	}

}
