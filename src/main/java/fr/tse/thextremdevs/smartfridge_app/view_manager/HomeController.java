package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;

import fr.tse.thextremdevs.smartfridge_app.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.recipes.SpoonacularManager;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;

public class HomeController implements Initializable {

	@FXML
	private ListView<String> liste_recette;
	@FXML
	private TableView<Aliment> fridgeOverviewTable;
	@FXML
	private TableColumn<Aliment, String> foodColumn;
	@FXML
	private TableColumn<Aliment, Double> quantityColumn;
	@FXML
	private TableColumn<Aliment, String> unitColumn;
	@FXML
	private TableColumn<Aliment, String> expirationColumn;
	@FXML
	private ComboBox<String> table_name_comboBox;

	private static String table_actuelle = BDD_frigo.Table_actuelle();
	private List<Aliment> tableaualiment = new ArrayList<Aliment>();
	public static String nouvelle_table_selectionnee=table_actuelle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		* Permet d'initialiser les variables de départ
		* @param  un url et une ressources
		* @return
		*/
		my_lists_comboBox();
		displayfridgetable(nouvelle_table_selectionnee);
		try {
			searchRecepeFromMyFridge(nouvelle_table_selectionnee);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void switchToHome() throws IOException {
		/**
		* permet de changer de fenêtre
		* @param 
		* @return
		*/
		App.setRoot("home");
	}

	@FXML
	private void switchToRecipe() throws IOException {
		App.setRoot("recipe");
	}

	@FXML
	private void switchToFridge() throws IOException {
		App.setRoot("fridge");
	}

	@FXML
	private void switchToList() throws IOException {
		App.setRoot("list");
	}

	@FXML
	public void searchRecepeFromMyFridge(String table_actuelle) throws IOException, JSONException {
		/**
		* permet de rechercher les recettes en fonction de ce qu'on a dans le frigo.
		* @param le nom de la table sur laquelle on travaille
		* @return 
		*/
		List<Aliment> liste_aliment = new ArrayList<Aliment>();
		liste_aliment = BDD_frigo.afficher_table(table_actuelle);

		liste_aliment = SpoonacularManager.unSeulMot(liste_aliment);
		if (liste_aliment.size() != 0) {
			String aliment = liste_aliment.get(0).getNom();
			for (int k = 1; k < liste_aliment.size(); k++) {
				aliment = aliment + "," + (liste_aliment.get(k).getNom().toLowerCase());
			}

			ArrayList<String> liste_entiere = SpoonacularManager.returnJsonToString(aliment);
			ArrayList<String> liste_titre = SpoonacularManager.recupTitres(liste_entiere);
			ObservableList<String> list = FXCollections.observableArrayList(liste_titre);
			liste_recette.setItems(list);
		}
	}

	@FXML
	public void displayfridgetable(String table_actuelle) {
		/**
		* permet d'afficher les aliments du frigo.
		* @param le nom de la table sur laquelle on travaille
		* @return 
		*/
		tableaualiment = BDD_frigo.afficher_table(table_actuelle);

		ObservableList<Aliment> table_list = FXCollections.observableArrayList(tableaualiment);

		foodColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyNom());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyQuantity().asObject());
		unitColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyUnit());
		expirationColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyExpiration());

		//Pour coloration en fonction de la péremption
		
		fridgeOverviewTable.setRowFactory(tv -> new TableRow<Aliment>() {
			@Override
			protected void updateItem(Aliment aliment, boolean empty) {
				super.updateItem(aliment, empty);
				LocalDate dateVeryClose = LocalDate.now().plusDays(2);
				LocalDate dateClose = LocalDate.now().plusDays(5);
				LocalDate datePass = LocalDate.now();
				try {
					if (aliment == null || aliment.getPeremption() == null)
						setStyle("");
					else if (aliment.getPeremptionDate().isBefore(datePass))
						setStyle("-fx-background-color: #797D7F;");
					else if (aliment.getPeremptionDate().isBefore(dateVeryClose)
							|| aliment.getPeremptionDate().isEqual(dateVeryClose))
						setStyle("-fx-background-color: #FF5733;");
					else if (aliment.getPeremptionDate().isBefore(dateClose)
							|| aliment.getPeremptionDate().isEqual(dateClose))
						setStyle("-fx-background-color: #FFC300;");
					else
						setStyle("");
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		fridgeOverviewTable.setItems(table_list);
		fridgeOverviewTable.getSortOrder().add(expirationColumn);
	};

	@FXML
	private void show_add_ingr() throws IOException {
		/**
		* permet d'afficher la vue afin d'ajouter un aliment ou de modifier un aliment existant
		* @param 
		* @return 
		*/
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("add_ing_popup.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Ingredient Adder");
		stage.setScene(new Scene(root, 450, 700));
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent paramT) {
				displayfridgetable(nouvelle_table_selectionnee);
			}
		});

	}
	
	
	public void ma_nouvelle_list_actuelle(String table_actuelle ) {
		nouvelle_table_selectionnee=table_actuelle;		
	}
	
	public void my_lists_comboBox() {
		/**
		* 
		* @param 
		* @return 
		*/
		table_name_comboBox.setValue(nouvelle_table_selectionnee);
		List<String> mes_lists=BDD_frigo.Nom_tables_BDD();
		table_name_comboBox.getItems().setAll(mes_lists);
		
		table_name_comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
				ma_nouvelle_list_actuelle(newValue);
				displayfridgetable(newValue);
				try {
					searchRecepeFromMyFridge(newValue);
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}
}
