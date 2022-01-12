package fr.tse.thextremdevs.smartfridge_app.view_manager;

import org.json.JSONException;

import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.fridge.EdamamManager;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;


public class AddIngrPopuController implements Initializable{
    @FXML
    private Button addAlimentAction;
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
    
    Aliment alimentChoose = new Aliment();
    List<Aliment> tabAlimentParsed = new ArrayList<Aliment>();
	private String table_actuelle=BDD_frigo.Table_actuelle();
    
	 /**
	* Fonction d'initialisation du à l'implementation de l'interface Initializable.
	* Elle est excutee à l'affichage de la vue
	* Ici on set les valeurs de la combobox de choix unite 
	*/ 
    public void initialize(final URL location, final ResourceBundle resources) {
    	ObservableList<String> optionsComboBox = FXCollections.observableArrayList("g","ml","piece(s)");
    	unitChoice.setItems(optionsComboBox);
    }
    
    
    /**
    * Fonction appelee lors de l'appui sur le bouton search a food
    * Recherche via la classe Edamam manager les aliments correspondants a l'aliment recherché
    * Puis set la list de ces aliments et les affiche
    */ 
    @FXML
    public void addAlimentAction() throws IOException, JSONException {
        final String searchAlimStr = this.searchAlimentTxtField.getText();
        
        tabAlimentParsed=EdamamManager.AlimentSearcher(EdamamManager.jsonParser(searchAlimStr));
        
        List<String> tabNomAliment = new ArrayList<String>();
        for (int i = 0; i < tabAlimentParsed.size(); i++){
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
    		alimentChoose= tabAlimentParsed.get(selectedIndex);  		
    	}
    
    /**
    * Appelee lors de l'appui sur le bouton "confirm"
    * Ajoute a la BDD du frigo en cours l'aliment complet avec les informations de quantités/unité et date de péremption renseignée par l'utilisateur
    */ 
    @FXML
	public void validateChooseAliment() {
    	//if null ou pas chiffre popup
    	Double quantityChoose= Double.valueOf(quantitySelection.getText());
    	String dateChoose = expirationSelection.getValue().toString();
    	String unitChoose = unitChoice.getValue().toString();

    	
    	System.out.println(unitChoose);
    	
    	alimentChoose.setQuantite(quantityChoose);
    	alimentChoose.setPeremption(dateChoose);
    	alimentChoose.setUnite(unitChoose);
    	BDD_frigo.insert(alimentChoose,table_actuelle);
    	
    	searchAlimentTxtField.setText(null);
    	listIngrView.setItems(null);
    	choiceDisplay.setText(null);
    	quantitySelection.setText(null);
    	expirationSelection.setValue(null);
    	
    }
	
}


