package fr.tse.thextremdevs.smartfridge_app.view_manager;

import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.fridge.EdamamManager;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.scene.input.*;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;

import fr.tse.thextremdevs.smartfridge_app.App;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FridgeController implements Initializable {
	
	@FXML
	private Button add_food_button;
	@FXML
	private BorderPane borderPaneAddFood;
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
	@FXML
	private TableColumn<Aliment, Boolean> actionCol;
	@FXML
	private CheckBox cliqued_general;
	@FXML
	private AnchorPane anchorLabelSearch;
	@FXML
	private VBox vboxModifier;
	@FXML
	private VBox vboxFinder;
	@FXML
	private TextField searchAlimentTxtField;
	@FXML
	private ListView<String> listIngrView;
	@FXML
	private TextField choiceDisplay;
	@FXML
	private TextField quantitySelection;
	@FXML
	private DatePicker expirationSelection;
	@FXML
	private ComboBox<String> unitChoice;
	@FXML
	private TextField choiceDisplayModifier;
	@FXML
	private TextField quantitySelectionModifier;
	@FXML
	private DatePicker expirationSelectionModifier;
	@FXML
	private ComboBox<String> unitChoiceModifier;
	@FXML
	private ListView<String> viewFrigo;
	
	 /**
	* Fonction appelee lors de l'appui sur le bouton home
	* Change la vue principale de la vue Fridge à la vue home
	*/ 
	@FXML
	private void switchToHome() throws IOException {
		App.setRoot("home");
	}
	
	/**
	* Fonction appelee lors de l'appui sur le bouton recipe
	* Change la vue principale de la vue Fridge à la vue recipe
	*/
	@FXML
	private void switchToRecipe() throws IOException {
		App.setRoot("recipe");
	}

	/**
	* Fonction appelee lors de l'appui sur le bouton fridge
	* Change la vue principale de la vue Fridge à la vue fridge (recharge la page)
	*/
	@FXML
	private void switchToFridge() throws IOException {
		App.setRoot("fridge");
	}
	
	/**
	* Fonction appelee lors de l'appui sur le bouton profil
	* Change la vue principale de la vue Fridge à la vue list
	*/
	@FXML
	private void switchToList() throws IOException {
		App.setRoot("list");
	}
	

	private List<Aliment> tableaualiment = new ArrayList<Aliment>();
	private String table_actuelle = BDD_frigo.Table_actuelle();

	public static Aliment value_double_clicked;
	Aliment alimentChoose = new Aliment();
	private Aliment alimentTableChoose = new Aliment();
	List<Aliment> tabAlimentParsed = new ArrayList<Aliment>();
	ObservableList<String> optionsComboBox = FXCollections.observableArrayList("g", "ml", "piece(s)");

	 /**
	    * Fonction d'initialisation du contenu du frigo
	    * Fait appel a la classe BDD_frigo pour remplir la liste tableaualiment déclarée en local et appelée dans les autres fonctions
	    */
	private void initialize_table(String tablename) {
		tableaualiment = BDD_frigo.afficher_table(tablename);
	}


	@SuppressWarnings({ "exports"})
	@Override
	 /**
	    * Fonction d'initialisation du à l'implementation de l'interface Initializable.
	    * Elle est excutee à l'affichage de la vue
	    * Ici on initialise la table en cours, le contenu de la ComboBox et on affiche le contenu du frigo
	    * On affiche de base la demi page de recherche d'aliment et cache celle de modification
	    * Cette fonction déclare aussi une écoute sur le click sur la liste du frigo
	    * Si l'utilisateur double click on affiche le graph aliment
	    * Si il click une fois on affiche la demi page de modification d'aliment
	    */ 
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		initialize_table(table_actuelle);
		displayfridgelist();
		ObservableList<Aliment> alimentList = FXCollections.observableArrayList();
		unitChoice.setItems(optionsComboBox);
		unitChoiceModifier.setItems(optionsComboBox);
		vboxFinder.setVisible(true);
		vboxModifier.setVisible(false);
		select_all();
		
		tableFridge.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if (t.getClickCount() == 2 && tableFridge.getSelectionModel().getSelectedItem() != null) {
                value_double_clicked=tableFridge.getSelectionModel().getSelectedItem();
                try {
                    open_mon_graph_aliment();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();}
            }else if (t.getClickCount() == 1 && tableFridge.getSelectionModel().getSelectedItem() != null) {
                alimentTableChoose = tableFridge.getSelectionModel().getSelectedItem();

                choiceDisplayModifier.setText(alimentTableChoose.getNom());
                quantitySelectionModifier.setText(alimentTableChoose.getQuantite().toString());
                try {
                    expirationSelectionModifier.setValue(alimentTableChoose.getPeremptionDate());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                unitChoiceModifier.setValue(alimentTableChoose.getUnite());

                vboxFinder.setVisible(false);
                vboxModifier.setVisible(true);
            	}
          	}
    	});
		
	}
	
	
	 /**
	* Remplis la tableView pour afficher nos aliments et leurs propriétés
	* On déclare aussi une écoute sur le tableau pour checker les checkbox et récupérer cette information quand l'utilisateur click dessus
	* @return      ObservableList<Aliment> une liste des aliments du frigo sous forme d'observable list pour être affichée dans la tableView
	*/ 
	@FXML
	public ObservableList<Aliment> displayfridgelist() {
		tableFridge.setEditable(true);

		ObservableList<Aliment> table_list = FXCollections.observableArrayList(tableaualiment);

		foodColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyNom());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyQuantity().asObject());
		unitColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyUnit());
		expirationColumn.setCellValueFactory(cellData -> cellData.getValue().getPropertyExpiration());
		actionCol.setCellValueFactory(new Callback<CellDataFeatures<Aliment, Boolean>, ObservableValue<Boolean>>() {
			public ObservableValue<Boolean> call(CellDataFeatures<Aliment, Boolean> param) {
				Aliment aliment = param.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(aliment.getRemark());
				booleanProp.addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						aliment.setRemark(newValue);
					}
				});
				return booleanProp;
			}
		});

		actionCol.setCellFactory(new Callback<TableColumn<Aliment, Boolean>, TableCell<Aliment, Boolean>>() {
			public TableCell<Aliment, Boolean> call(TableColumn<Aliment, Boolean> p) {
				CheckBoxTableCell<Aliment, Boolean> cell = new CheckBoxTableCell<Aliment, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
		tableFridge.setItems(table_list);

		return table_list;
	}
	
	 /**
	* Appelée lors de l'appui sur le bouton Delete
	* Supprime tous les aliment dont la checkbox est cochée de la BDD et actualise l'affichage
	*/ 
	@FXML
	public void supressed() {
		ObservableList<Aliment> table_list = displayfridgelist();
		for (int i = 0; i < table_list.size(); i++) {
			if (table_list.get(i).getRemark() == true) {
				BDD_frigo.delete_element(table_actuelle, table_list.get(i));
			}
		}
		initialize_table(table_actuelle);
		displayfridgelist();
	}
	
	 /**
	* Appelée lors du coche de la checkbox générale
	* Elle coche toutes les check box pour permettre la supression de tous les aliments du frigo
	*/ 
	@FXML
	public void select_all() {
		ObservableList<Aliment> table_list = displayfridgelist();
		if (cliqued_general.isSelected()) {
			for (int i = 0; i < table_list.size(); i++) {
				table_list.get(i).setRemark(true);
			}
		} else {
			for (int i = 0; i < table_list.size(); i++) {
				table_list.get(i).setRemark(false);
			}
		}	
	}

	/**
	* Appelee lors du click sur le bouton add a Food
	* Affiche l'onglet de recherche d'aliment et cache celui de modification d'aliment lors de l'appui sur un aliment de la liste
	*/ 
	@FXML
    private void show_add_ingr() throws IOException {
        vboxFinder.setVisible(true);
        vboxModifier.setVisible(false);
    }
	
	 /**
	* Appelée lors d'un doubleClick sur un aliment
	*  Ouvre une popup avec les informations de l'aliment sélectionné
	*/ 
	public void open_mon_graph_aliment() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("graph_aliments.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Graphe nutrionnel");
		stage.setScene(new Scene(root, 1000, 600));
		stage.show();
	}
	
	
	 /**
     * Appelee lors de l'appui du bouton Modify
     * Actualise les informations de l'aliment avec celles modifiées (ou non) par l'utilisateur
     */
	@FXML
	public void modifyChooseAliment() {
		// if null ou pas chiffre popup
		Double quantityChoose = Double.valueOf(quantitySelectionModifier.getText());
		String dateChoose = expirationSelectionModifier.getValue().toString();
		String unitChoose = unitChoiceModifier.getValue().toString();
		
		BDD_frigo.delete_element(table_actuelle, alimentTableChoose);
		
		alimentTableChoose.setQuantite(quantityChoose);
		alimentTableChoose.setPeremption(dateChoose);
		alimentTableChoose.setUnite(unitChoose);
		
		BDD_frigo.insert(alimentTableChoose,table_actuelle);
		initialize_table(table_actuelle);
		displayfridgelist();
		unitChoiceModifier.setItems(optionsComboBox);

		choiceDisplayModifier.setText(null);
		quantitySelectionModifier.setText(null);
		expirationSelection.setValue(null);

	}
	
	 
	/**
     * Appelee lors de l'appui du bouton Search a food
     * Recherche les aliments renvoyés par Edamam correspondant à l'aliment recherché et affiche cette liste
     */
	@FXML
	public void addAlimentAction() throws IOException, JSONException {
		final String searchAlimStr = this.searchAlimentTxtField.getText();

		tabAlimentParsed = EdamamManager.AlimentSearcher(EdamamManager.jsonParser(searchAlimStr));

		List<String> tabNomAliment = new ArrayList<String>();
		for (int i = 0; i < tabAlimentParsed.size(); i++) {
			tabNomAliment.add(tabAlimentParsed.get(i).getNom());
		}

		ObservableList<String> list = FXCollections.observableArrayList(tabNomAliment);

		listIngrView.setItems(list);

	}
	
	/**
	 * Ecoute l'evenement de selection d'un item dans la list des aliments proposés
	 * Lors du click sur un de ces éléments il est affiché après "you have choosen" et on enregistre l'aliment en mémoire via alimentChoose
	 */
	@FXML
	public void handleItemListClicked() {
		String selectedItem = listIngrView.getSelectionModel().getSelectedItem().toString();
		int selectedIndex = listIngrView.getSelectionModel().getSelectedIndex();
		choiceDisplay.setText(selectedItem);
		alimentChoose = tabAlimentParsed.get(selectedIndex);
	}
	
	/**
	 * Appelee lors de l'appui du bouton Confirm
	 * Complete les information de l'aliment sélectionné alimentChoose via les champs saisis par l'utilisateur
	 * Affiche celui ci dans le frigo et remet les champs utilisateurs à null
	 */ 
	@FXML
	public void validateChooseAliment() {
		// if null ou pas chiffre popup
		Double quantityChoose = Double.valueOf(quantitySelection.getText());
		String dateChoose = expirationSelection.getValue().toString();
		String unitChoose = unitChoice.getValue().toString();

		alimentChoose.setQuantite(quantityChoose);
		alimentChoose.setPeremption(dateChoose);
		alimentChoose.setUnite(unitChoose);
		BDD_frigo.insert(alimentChoose, table_actuelle);

		initialize_table(table_actuelle);
		displayfridgelist();
		unitChoice.setItems(optionsComboBox);

		searchAlimentTxtField.setText(null);
		listIngrView.setItems(null);
		choiceDisplay.setText(null);
		quantitySelection.setText(null);
		expirationSelection.setValue(null);

	}
}
