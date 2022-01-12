package fr.tse.thextremdevs.smartfridge_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import org.json.JSONException;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_utilisateur;
import fr.tse.thextremdevs.smartfridge_app.database_manager.Creat_new_BDD;

/**
* Classe App héritée de Application
* Lancée au démarage de l'application c'est le main de notre programme
*/
public class App extends Application {

	private static Scene scene;

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
		scene = new Scene(loadFXML("home"), 1200, 700);
		stage.setScene(scene);
		stage.setTitle("The Smart Fridge App");
		stage.show();        
	}

	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) throws IOException, JSONException, ParseException {
		launch();
	}
}
