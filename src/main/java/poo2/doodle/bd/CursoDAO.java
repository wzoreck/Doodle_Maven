package poo2.doodle.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import poo2.doodle.entidades.Conteudo;
import poo2.doodle.entidades.Curso;
import poo2.doodle.ihc.AlertaFX;

public class CursoDAO implements InterfaceDAO<Curso> {

	@Override
	public void adicionar(Curso curso, ArrayList<Integer> id) {
		// id_curso, nome, data_inicio, id_professor (id-0)
		try {
			String queryCurso = "INSERT INTO curso VALUES (NULL, '" + curso.getNome() + "', '" + curso.getDataInicio()
					+ "', " + id.get(0) + ")";

			UtilBD.alterarBd(queryCurso);

		} catch (SQLException e) {
			AlertaFX.erro("Falha ao inserir Curso no banco de dados");
		}
	}

	@Override
	public ArrayList<Curso> listar(int aux) {
		ProfessorDAO professorDAO = new ProfessorDAO();
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		Curso curso = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato da data
		try {
			String querySelectCursos = "SELECT * FROM curso WHERE id_professor = " + aux;
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
			AlertaFX.erro("Não foi possível buscar os Cursos no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data CursoDAO");
		}
		return cursos;
	}

	@Override
	public void atualizar(Curso curso, int aux) {
		try {
			String queryUpdateCurso = "UPDATE curso SET " + "nome = '" + curso.getNome() + "', data_inicio = '"
					+ curso.getDataInicio() + "', id_professor = " + aux + " WHERE id_curso =" + curso.getID();
			UtilBD.alterarBd(queryUpdateCurso);
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao realizar o update de Curso");
		}

	}

	@Override
	public void remover(Curso curso) {
		try {
			String queryDeleteCurso = "DELETE FROM curso WHERE id_curso = " + curso.getID();
			UtilBD.alterarBd(queryDeleteCurso);
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao remover Curso do banco de dados");
		}
	}

	public void adicinarConteudo(Conteudo conteudo, int idCurso) {
		// id_conteudo, id_curso, titulo, descricao, data_publicao, prazo, data_inicio,
		// data_termino
		try {
			String queryConteudo = "INSERT INTO conteudo VALUES (" + conteudo.getId() + "," + idCurso + ", '"
					+ conteudo.getTitulo() + "','" + conteudo.getDescricao() + "','" + conteudo.getDataPublicacao()
					+ "' )";

			UtilBD.alterarBd(queryConteudo);

		} catch (SQLException e) {
			AlertaFX.erro("Falaha ao inserir Conteudo no banco de dados");
		}
	}

	public Curso get(String nomeCurso) {
		Curso curso = null;
		ProfessorDAO professorDAO = new ProfessorDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String querySelectProfessor = "SELECT * FROM curso WHERE nome = '" + nomeCurso + "'";
			ResultSet resultSet = UtilBD.consultarBD(querySelectProfessor);
			while (resultSet.next()) {
				int idCurso = resultSet.getInt("id_curso");
				String nome = resultSet.getString("nome");
				String dataInicio = resultSet.getString("data_inicio");
				int idProfessor = resultSet.getInt("id_professor");

				curso = new Curso(professorDAO.get(idProfessor), nome, sdf.parse(dataInicio));
				curso.setID(idCurso);
			}
			resultSet.getStatement().close();
		} catch (SQLException e) {
			AlertaFX.erro("Falha ao buscar Curso no banco de dados");
		} catch (ParseException e) {
			AlertaFX.erro("Falha ao converter String para Data CursoDAO");
		}
		return curso;
	}

}
