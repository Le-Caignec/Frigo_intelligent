package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.recipes.Recipe;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_favoris;
import fr.tse.thextremdevs.smartfridge_app.App;

public class ListController implements Initializable{
	
    @FXML
    private ListView<String> listTableView;
    @FXML
    private TextField newTable;
    @FXML
    private TableView<Aliment> tableFridge;
    @FXML
    private TableColumn<Aliment, String> foodColumn;
    @FXML
    private TableColumn<Aliment, Double> quantityColumn;
    @FXML
    private TableColumn<Aliment, String> unitColumn;
    @FXML
    private TableColumn<Aliment, String> expirationColumn;
	//private ObservableList<Aliment> alimentObservableList;
    @FXML
    private TableColumn<Aliment, Boolean> Col_checkbox;
	//private ObservableList<Aliment> alimentObservableList;
    @FXML
	private VBox vboxListOverview;
	@FXML
	private VBox vboxRecipesFavourites;
	@FXML
	private ListView<String> listRecipesFavourites;
    
	

	private List<String> listRecettesFavs= new ArrayList();
	private String table_actuelle=BDD_frigo.Table_actuelle();
	List<Recipe> tableauRecipeFav = new ArrayList<Recipe>();
	
	private void initialize_table() {
		tableauRecipeFav = BDD_favoris.selectAll("Favoris");
	}
	private void initialize_listRecette() {
		for (int i = 0; i < tableauRecipeFav.size(); i++) {
			listRecettesFavs.add(tableauRecipeFav.get(i).getNom());
		}
		ObservableList<String> listfavsView = FXCollections.observableArrayList(listRecettesFavs);
		listRecipesFavourites.setItems(listfavsView);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		//Initialisation des cellules du tableau
		diplayTables();	
		vboxRecipesFavourites.setVisible(false);
		initialize_table();
		initialize_listRecette();
	}
	
	@FXML
    private void switchToHome() throws IOException {
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
	public void diplayTables() {
		BDD_frigo.Table_actuelle();
		List<String> nom_tables=BDD_frigo.Nom_tables_BDD();
		ObservableList<String> list = FXCollections.observableArrayList(nom_tables);
		listTableView.setItems(list);
	}
	
	@FXML
    public String handle_List_frigo_Clicked() {
            String selectedItem = listTableView.getSelectionModel().getSelectedItem().toString();
            displayfridgelist (selectedItem);
            vboxRecipesFavourites.setVisible(false);
			vboxListOverview.setVisible(true);
			return selectedItem;
			
    }
	
	@FXML
	public void displayfridgelist (String tablename) {
		List<Aliment> tableaualiment= new ArrayList<Aliment>();
		tableaualiment= BDD_frigo.afficher_table(tablename);
		
        List<String> tabNomAliment = new ArrayList<String>();
        for (int i = 0; i < tableaualiment.size(); i++){
        	tabNomAliment.add(tableaualiment.get(i).getNom()); 
    	}
        ObservableList<Aliment> table_list = FXCollections.observableArrayList(tableaualiment);
        
        tableFridge.setItems(table_list);
        
        foodColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyNom());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyQuantity().asObject());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyUnit());
        expirationColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyExpiration());

	}
	
	@FXML
	public void deleteTable() {
		String nom_delete=handle_List_frigo_Clicked();
		BDD_frigo.delete_table(nom_delete);
		diplayTables();
	}
	
	@FXML
	public void createTable() {
		if (newTable.getText()!=null) {
			BDD_frigo.createNewTable(newTable.getText());
		}
		diplayTables();
	}
	
	@FXML
	public void viewRecipesFavourites() {
		vboxRecipesFavourites.setVisible(true);
		vboxListOverview.setVisible(false);
		
	}
	
	@FXML 
	public void show_nutritional_formulary() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("nutritional_formulary.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Ingredient Adder");
		stage.setScene(new Scene(root, 450, 700));
		stage.show();
    }
	
	@FXML 
	public void show_nutritional_calandar() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("graph_calandar.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Ingredient Adder");
		stage.setScene(new Scene(root, 1000, 450));
		stage.show();
    }
	
}