package poo2.doodle.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.scene.control.Alert;
import poo2.doodle.AlertUtil;
import poo2.doodle.entities.Activity;
import poo2.doodle.entities.Content;
import poo2.doodle.entities.Course;
import poo2.doodle.entities.Teacher;

public class UtilDB {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	private static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null)
			entityManagerFactory = Persistence.createEntityManagerFactory("teste");
		return entityManagerFactory;
	}

	public static EntityManager getEntityManager() {
		if (entityManager == null)
			entityManager = getEntityManagerFactory().createEntityManager();
		return entityManager;
	}

	public static void closeConn() {
		if (entityManager != null)
			entityManager.close();
		if (entityManagerFactory != null)
			entityManagerFactory.close();
	}

	public static void initDB() {
		for (Teacher teacher : consumeAPI(consultAPI())) {
			new TeacherDAO().persist(teacher);
		}

		Teacher teacher1 = new Teacher("lucas", "1234", "Lucas", "lucas@email.com");
		new TeacherDAO().persist(teacher1);

		Course course1 = new Course("Programação Orientada a Objetos 2", "Disciplina do quarto semestre de 2020");
		new CourseDAO().persist(course1);

		Content content1 = new Content("Polimorfismo");
		new ContentDAO().persist(content1);
		course1.setContent(content1);

		Activity activity1 = new Activity("Primeira Prova");
		new ActivityDAO().persist(activity1);
		course1.setActivitie(activity1);

		teacher1.setCourse(course1);
		new TeacherDAO().persist(teacher1);
	}

	public static List<Teacher> consumeAPI(List<String> users) {
		List<Teacher> result = new ArrayList<Teacher>();
		String username = null;
		String password = null;

		for (String line : users) {
			if (line.contains("username")) {
				username = processJSONLine(line);
			}

			if (line.contains("password")) {
				password = processJSONLine(line);
				Teacher teacher = new Teacher();
				teacher.setUsername(username);
				teacher.setPassword(password);
				result.add(teacher);
			}
		}

		return result;
	}

	private static String processJSONLine(String line) {
		String[] divideLine = line.split(":");
		String content = divideLine[1];
		content = content.replace(",", " ");
		content = content.replace("\"", " ");
		content = content.trim();
		return content;
	}

	private static List<String> consultAPI() {
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL("http://www.lucasbueno.com.br/steam.json");
			URLConnection uc = url.openConnection();
			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				result.add(inputLine);

			in.close();
		} catch (Exception e) {
			Alert alert = AlertUtil.error("Error", "Error when consuming API!", "Error when consuming API!", e);
			alert.showAndWait();
		}

		return result;
	}
}