package poo2.doodle.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import poo2.doodle.entidades.Aluno;
import poo2.doodle.entidades.Curso;
import poo2.doodle.entidades.Pessoa;
import poo2.doodle.ihc.AlertaFX;

public class AlunoDAO implements InterfaceDAO<Aluno> {

	@Override
	public void adicionar(Aluno aluno, ArrayList<Integer> id) {
		try {
			String queryPessoa = "INSERT INTO pessoa VALUES (" + aluno.getId() + ", '" + aluno.getNome() + "', '"
					+ aluno.getEmail() + "', '" + aluno.getDataNascimento() + "', '" + aluno.getLogin() + "', '"
					+ aluno.getPasswd() + "')";

			UtilBD.alterarBd(queryPessoa);

		} catch (SQLException e1) {
			AlertaFX.erro("Falaha ao inserir Pessoa no banco de dados");
		}

		try {
			String queryAluno = "INSERT INTO aluno (id_aluno)\n" + "SELECT (id_pessoa) FROM pessoa\n"
					+ "WHERE login = '" + aluno.getLogin() + "'";

			UtilBD.alterarBd(queryAluno);

		} catch (SQLException e2) {
			AlertaFX.erro("Falaha ao inserir Aluno no banco de dados");
		}

	}

	@Override
	public ArrayList<Aluno> listar(int aux) {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato da data
		try {
			String querySelectAlunos = "SELECT * FROM pessoa INNER JOIN aluno ON pessoa.id_pessoa = aluno.id_aluno";
			ResultSet resultSet = UtilBD.consultarBD(querySelectAlunos);
			while (resultSet.next()) {
				int id = resultSet.getInt("id_pessoa");
				String nome = resultSet.getString("nome");
				String email = resultSet.getString("email");
				String data = resultSet.getString("data_nascimento");
				String login = resultSet.getString("login");
				String passwd = resultSet.getString("passwd");
				boolean matriculado = resultSet.getBoolean("matriculado");
				aluno = new Aluno(nome, email, sdf.parse(data), login, passwd, false);
				aluno.setId(id);
				aluno.setMatriculado(matriculado);
				alunos.add(aluno);
			}
			resultSet.getStatement().close();
			sdf.clone();
		} catch (SQLException e) {
			AlertaFX.erro("Não foi possível buscar os Alunos no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao transformar String para Data - AlunoDAO");
		}
		return alunos;
	}

	@Override
	public void atualizar(Aluno aluno, int aux) {
		try {
			String queryUpdatePessoa = "UPDATE pessoa SET " + "nome = '" + aluno.getNome() + "', email = '"
					+ aluno.getEmail() + "', data_nascimento = '" + aluno.getDataNascimento() + "', login = '"
					+ aluno.getLogin() + "', passwd = '" + aluno.getPasswd() + "' WHERE id_pessoa = " + aluno.getId();
			UtilBD.alterarBd(queryUpdatePessoa);
			String queryUpdateAluno = "UPDATE aluno SET " + "matriculado = " + aluno.isMatriculado()
					+ " WHERE id_aluno = " + aluno.getId();
			UtilBD.alterarBd(queryUpdateAluno);
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao realizar o update de Pessoa-Aluno");
		}
	}

	@Override
	public void remover(Aluno aluno) {
		try {
			String queryDeleteAluno = "DELETE FROM aluno WHERE id_aluno = '" + aluno.getId() + "'";
			UtilBD.alterarBd(queryDeleteAluno);
			String queryDeletePessoa = "DELETE FROM pessoa WHERE id_pessoa = '" + aluno.getId() + "'";
			UtilBD.alterarBd(queryDeletePessoa);
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao remover Pessoa-Aluno do banco de dados");
		}
	}

	// Adaptado somente para o LoginFX
	public Pessoa get(String loginUsuario) {
		Pessoa pessoa = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String querySelectAluno = "SELECT * FROM pessoa" + " WHERE login = '" + loginUsuario + "'";
			ResultSet resultSet = UtilBD.consultarBD(querySelectAluno);
			while (resultSet.next()) {
				int id = resultSet.getInt("id_pessoa");
				String nome = resultSet.getString("nome");
				String email = resultSet.getString("email");
				String data = resultSet.getString("data_nascimento");
				String login = resultSet.getString("login");
				String passwd = resultSet.getString("passwd");
				pessoa = new Aluno(nome, email, sdf.parse(data), login, passwd, false);
				pessoa.setId(id);
			}
			resultSet.getStatement().close();
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao buscar Aluno no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data AlunoDAO");
		}
		return pessoa;
	}

	public Aluno get(int idAluno) {
		Aluno aluno = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String querySelectAluno = "SELECT * FROM pessoa INNER JOIN aluno ON pessoa.id_pessoa = aluno.id_aluno"
					+ " WHERE id_pessoa = " + idAluno;
			ResultSet resultSet = UtilBD.consultarBD(querySelectAluno);
			while (resultSet.next()) {
				int id = resultSet.getInt("id_pessoa");
				String nome = resultSet.getString("nome");
				String email = resultSet.getString("email");
				String data = resultSet.getString("data_nascimento");
				String login = resultSet.getString("login");
				String passwd = resultSet.getString("passwd");
				boolean matriculado = resultSet.getBoolean("matriculado");
				aluno = new Aluno(nome, email, sdf.parse(data), login, passwd, false);
				aluno.setId(id);
				aluno.setMatriculado(matriculado);
			}
			resultSet.getStatement().close();
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao buscar Pessoa no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data AlunoDAO");
		}
		return aluno;
	}

	public void matricularAluno(Aluno aluno, Curso curso) {
		// id_aluno, id_curso
		try {
			String queryMatricula = "INSERT INTO matricula_curso VALUES (" + aluno.getId() + ", " + curso.getID() + ")";
			UtilBD.alterarBd(queryMatricula);

			String queryMatriculaAluno = "UPDATE aluno SET matriculado = true WHERE id_aluno = " + aluno.getId();
			UtilBD.alterarBd(queryMatriculaAluno);
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao inserir Matricula no banco de dados");
		}
	}

	public void desMatricularAluno(Aluno aluno, Curso curso) {
		try {
			String queryDeleteMatriculaAluno = "DELETE FROM matricula_curso WHERE id_aluno = " + aluno.getId()
					+ " AND id_curso = " + curso.getID();
			UtilBD.alterarBd(queryDeleteMatriculaAluno);

		} catch (SQLException e) {
			AlertaFX.erro("Falha ao remover Matricula-Aluno do banco de dados");
		}

	}

	public ArrayList<Aluno> getMatriculaAlunos(Curso curso) {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		Aluno aluno = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato da data
		try {
			String querySelectMatriculas = "SELECT * FROM matricula_curso"
					+ " INNER JOIN pessoa ON pessoa.id_pessoa = matricula_curso.id_aluno"
					+ " INNER JOIN aluno ON pessoa.id_pessoa = aluno.id_aluno" + " WHERE id_curso = " + curso.getID()
					+ " AND aluno.id_aluno = pessoa.id_pessoa";
			ResultSet resultSet = UtilBD.consultarBD(querySelectMatriculas);
			while (resultSet.next()) {
				int idAluno = resultSet.getInt("id_aluno");
				String nome = resultSet.getString("nome");
				String email = resultSet.getString("email");
				String data = resultSet.getString("data_nascimento");
				String login = resultSet.getString("login");
				String passwd = resultSet.getString("passwd");

				aluno = new Aluno(nome, email, sdf.parse(data), login, passwd, false);
				aluno.setId(idAluno);
				alunos.add(aluno);
			}
			resultSet.getStatement().close();
			sdf.clone();
		} catch (SQLException e) {
			AlertaFX.erro("Não foi possível buscar os Matriculas no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data CursoDAO");
		}
		return alunos;
	}

	public ArrayList<Curso> getCursos(int aux) {
		ProfessorDAO professorDAO = new ProfessorDAO();
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Curso curso = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato da data
		try {
			String querySelectCursos = "SELECT * FROM curso"
					+ " INNER JOIN matricula_curso ON curso.id_curso = matricula_curso.id_curso" + " WHERE id_aluno = "
					+ aux;
			ResultSet resultSet = UtilBD.consultarBD(querySelectCursos);
			while (resultSet.next()) {
				int id = resultSet.getInt("id_curso");
				String nome = resultSet.getString("nome");
				String dataInicio = resultSet.getString("data_inicio");
				int idProfessor = resultSet.getInt("id_professor");

				curso = new Curso(professorDAO.get(idProfessor), nome, sdf.parse(dataInicio));
				curso.setID(id);
				cursos.add(curso);
			}
			resultSet.getStatement().close();
			sdf.clone();
		} catch (SQLException e) {
			AlertaFX.erro("Não foi possível buscar os Cursos-Aluno no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data AlunoDAO");
		}
		return cursos;
	}

}
