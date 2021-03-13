package poo2.doodle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import poo2.doodle.db.TeacherDAO;
import poo2.doodle.entities.Teacher;

public class App extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) {

		// Teste API
		List<String> users = consultaAPI();
		consumeAPI(users);

		this.stage = stage;
		stage.setScene(FXMLUtil.loadScene("login"));
		stage.setResizable(true);
		stage.show();
	}

	private void consumeAPI(List<String> users) {
		String username = null;
		String password = null;

		// forma mais simples e não recomendavel de se consumir um JSON

		for (String line : users) {
			if (line.contains("username")) {
				username = processJSONLine(line);
				System.out.println(username);
			}

			if (line.contains("password")) {
				password = processJSONLine(line);
				System.out.println(password);
				
				Teacher teacher = new Teacher();
				teacher.setName(username);
				teacher.setPassword(password);
				teacher.setId(10);
				new TeacherDAO().persist(teacher);
			}
		}
	}

	private String processJSONLine(String line) {
		String[] divideLine = line.split(":");
		String content = divideLine[1];
		content = content.replace(",", " ");
		content = content.replace("\"", " ");
		content = content.trim();
		return content;
	}

	private List<String> consultaAPI() {
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL("http://lucasbueno.com.br/steam.json");
			URLConnection uc = url.openConnection();
			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				result.add(inputLine);

			in.close();
		} catch (Exception e) {
			System.err.println("Erro ao comsumir API!");
		}

		return result;
	}
	
	public static void setRoot(String fxml) {
		stage.setScene(FXMLUtil.loadScene(fxml));
	}

	public static void setResizable(Boolean value) {
		stage.setResizable(value);
	}
}