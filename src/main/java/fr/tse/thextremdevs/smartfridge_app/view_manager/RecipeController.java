package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import org.json.JSONException;

import fr.tse.thextremdevs.smartfridge_app.App;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_calandar;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_favoris;
import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.fridge.EdamamManager;
import fr.tse.thextremdevs.smartfridge_app.recipes.AlimentCompare;
import fr.tse.thextremdevs.smartfridge_app.recipes.AlimentCourse;
import fr.tse.thextremdevs.smartfridge_app.recipes.Recipe;
import fr.tse.thextremdevs.smartfridge_app.recipes.SpoonacularManager;
import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecipeController implements Initializable {
	@FXML
	private Button addRecepeName;
	@FXML
	private Button addRecepeActionByIngredients;
	@FXML
	private TextField searchRecepeTxtField;
	@FXML
	private TextField searchRecepeTxtFieldByIngredients;
	@FXML
	private Button button_decoche;
	@FXML
	private Button button_coche;
	@FXML
	private Button recipeDone;
	@FXML
	private Label labelRecipe;
	@FXML
	private ListView<String> ficheRecette;
	@FXML
	private ImageView imageRecette;
	@FXML
	private ListView<String> liste_recette;
	@FXML
	private ListView<String> favorites;
	@FXML
	private TableView<AlimentCompare> IngrR;
	@FXML
	private TableColumn<AlimentCompare, String> columnName;
	@FXML
	private TableColumn<AlimentCompare, Double> columnNeeded;
	@FXML
	private TableColumn<AlimentCompare, Double> columnOwned;
	@FXML
	private TableColumn<AlimentCompare, String> columnUnitNeeded;
	@FXML
	private TableColumn<AlimentCompare, String> columnUnitOwned;
	@FXML
	private Label carbs;
	@FXML
	private Label calories;
	@FXML
	private Label fat;
	@FXML
	private Label protein;
	@FXML
	private Label titleIng;
	@FXML
	private Button graph_button_plus;
	@FXML
	private Label titleStep;
	@FXML
	private Label Personns;
	@FXML
	private Button Plus;
	@FXML
	private Button Moins;
	@FXML
	private CheckBox breakfast;
	@FXML
	private CheckBox lunch;
	@FXML
	private CheckBox dinner;
	@FXML
	private CheckBox dessert;
	@FXML
	private Label minute;
	@FXML
	private Button generate_list;
	@FXML
	private Button delete_list;

	@FXML
	private void switchToHome() throws IOException {
		App.setRoot("home");
	}

	@FXML
	private void switchToFridge() throws IOException {
		App.setRoot("fridge");
	}

	@FXML
	private void switchToRecipe() throws IOException {
		App.setRoot("recipe");
	}

	@FXML
	private void switchToList() throws IOException {
		App.setRoot("list");
	}

	//Variables globales utiles pour le code qui suit
	public static String id_ma_recette;
	private List<String> listRecettesFavs = new ArrayList();
	private String table_actuelle = BDD_frigo.Table_actuelle();
	public static String value_double_clicked;
	List<Aliment> liste_aliment = new ArrayList<Aliment>();

	List<Recipe> tableauRecipeFav = new ArrayList<Recipe>();
	List<AlimentCourse> liste_Course = new ArrayList<AlimentCourse>();

	
	private void initialize_table() {
		tableauRecipeFav = BDD_favoris.selectAll("Favoris");
	}

	private void initialize_listRecette() {
		for (int i = 0; i < tableauRecipeFav.size(); i++) {
			listRecettesFavs.add(tableauRecipeFav.get(i).getNom());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		* permet d'intialiser l'onglet Recepe
		* @param  un url et une ressource
		* @return 
		*/
		// TODO Auto-generated method stub
		try {
			searchRecepeFromMyFridge();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		liste_aliment = BDD_frigo.afficher_table(BDD_frigo.Table_actuelle());
		button_coche.setVisible(false);
		button_decoche.setVisible(true);
		initialize_table();
		initialize_listRecette();
		carbs.setVisible(false);
		calories.setVisible(false);
		fat.setVisible(false);
		protein.setVisible(false);
		titleIng.setVisible(false);
		graph_button_plus.setVisible(false);
		button_decoche.setVisible(false);
		ficheRecette.setVisible(false);
		imageRecette.setVisible(false);
		IngrR.setVisible(false);
		titleIng.setText("");
		titleStep.setText("");
		Personns.setVisible(false);
		Plus.setVisible(false);
		Moins.setVisible(false);
		recipeDone.setVisible(false);
		minute.setVisible(false);
		Plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Double personns = Double.parseDouble(Personns.getText()) + 1;
				Personns.setText(personns.toString());
				String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString(); // je récupère le
																										// titre
				String id = SpoonacularManager.recupIDrecetteSelectionnee(selectedItem);
				Double diviseur = Double.parseDouble(Personns.getText());
				try {
					IngrRecette(id, changed_quantity(id, diviseur));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		Moins.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Double personns = Double.parseDouble(Personns.getText()) - 1;
				Personns.setText(personns.toString());
				String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString(); // je récupère le
																										// titre
				String id = SpoonacularManager.recupIDrecetteSelectionnee(selectedItem);
				Double diviseur = Double.parseDouble(Personns.getText());
				try {
					IngrRecette(id, changed_quantity(id, diviseur));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	@FXML
	public void open_mon_graph_recip() throws IOException {
		/**
		* permet d'ouvrir le graphe des recettes
		* @param  
		* @return 
		*/
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("graph_recip.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setOpacity(1);
		stage.setTitle("Graphe nutrionnel");
		stage.setScene(new Scene(root, 1000, 700));
		stage.show();
	}

	@FXML
	public void AddRecepeActionByIngredients() throws IOException, JSONException {
		/**
		* permet de rechercher des recettes par ingrédients
		* @param
		* @return 
		*/
		System.out.println("Passage réussi dans ingredients");
		final String searchRecepe = this.searchRecepeTxtFieldByIngredients.getText();
		System.out.println("ingredient : " + searchRecepe);
		ArrayList<String> liste_entiere = SpoonacularManager.returnJsonToString(searchRecepe);
		ArrayList<String> listeIDtitre = SpoonacularManager.concateneIDtitreByIngr(liste_entiere);
		ArrayList<String> isMeal = new ArrayList<String>();
		isMeal = isMeal(listeIDtitre);

		ObservableList<String> list = FXCollections.observableArrayList(isMeal);
		liste_recette.refresh();
		liste_recette.setItems(list);
	}

	@FXML
	public void addRecepeName() throws IOException, JSONException {
		/**
		* permet de rechercher des recettes par nom
		* @param
		* @return 
		*/
		System.out.println("Passage réussi dans recette");
		final String searchRecepe = this.searchRecepeTxtField.getText();
		System.out.println(searchRecepe);
		String liste = SpoonacularManager.rechercherRecette(searchRecepe);
		ArrayList<String> liste_titre = new ArrayList<String>();
		liste_titre = SpoonacularManager.concateneIDtitreNOM(liste);
		ArrayList<String> isMeal = new ArrayList<String>();
		isMeal = isMeal(liste_titre);

		ObservableList<String> list = FXCollections.observableArrayList(isMeal);
		liste_recette.refresh();
		liste_recette.setItems(list);
	}

	@FXML
	public void searchRecepeFromMyFridge() throws IOException, JSONException {
		/**
		* permet de rechercher des recettes en fonction des ingrédients présents dans le frigo
		* @param
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
			ArrayList<String> listeIDtitre = SpoonacularManager.concateneIDtitre(liste_entiere);
			ArrayList<String> isMeal = new ArrayList<String>();
			isMeal = isMeal(listeIDtitre);

			ObservableList<String> list = FXCollections.observableArrayList(isMeal);
			liste_recette.refresh();
			liste_recette.setItems(list);
		}

	}

	@FXML
	public void recepeChoosen() throws IOException, JSONException {
		/**
		* permet réafficher toutes les informations de la recette sélectionnée
		* @param
		* @return 
		*/
		String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString(); // je récupère le titre

		String id = SpoonacularManager.recupIDrecetteSelectionnee(selectedItem);
		id_ma_recette = id;
		System.out.println(id);
		ArrayList<String> instructions = new ArrayList<String>();
		ArrayList<String> liste = new ArrayList<String>();
		liste = SpoonacularManager.instructionsPasApas(id);
		if (liste.get(0) != ("Aucune instuction pour cette recette !")) {
			for (int k = 0; k < liste.size(); k++) {
				instructions.add("Etape " + (k + 1) + " : " + liste.get(k));
			}
		} else {
			instructions = liste;
		}
		ObservableList<String> list = FXCollections.observableArrayList(instructions);
		ficheRecette.refresh();
		ficheRecette.setItems(list);
		int parenthese = selectedItem.indexOf('(');
		labelRecipe.setText(selectedItem.substring(0, parenthese));

		String url = (SpoonacularManager.recupImage(id));
		// System.out.println(url);
		boolean backgroundLoading = true;
		final Image image = new Image(url, backgroundLoading);
		imageRecette.setImage(image);
		button_decoche.setVisible(true);
		ficheRecette.setVisible(true);
		imageRecette.setVisible(true);
		IngrR.setVisible(true);
		carbs.setVisible(true);
		calories.setVisible(true);
		fat.setVisible(true);
		protein.setVisible(true);
		titleIng.setVisible(true);
		graph_button_plus.setVisible(true);
		Personns.setVisible(true);
		Plus.setVisible(true);
		Moins.setVisible(true);
		recipeDone.setVisible(true);
		titleIng.setText("Ingredients");
		titleStep.setText("Prepation steps");
		Nutriments(id);
		minute.setVisible(true);
		minute.setText(SpoonacularManager.readyIn(id));

		IngrRecette(id, SpoonacularManager.recupIngredientQuantity(id));

		if (listRecettesFavs.contains(selectedItem.substring(0, parenthese))) {
			button_coche.setVisible(true);
			button_decoche.setVisible(false);
		} else {
			button_coche.setVisible(false);
			button_decoche.setVisible(true);
		}

	}

	@FXML
	private void coche_clicked() {
		/**
		* permet d'ajouter une recette aux favoris
		* @param
		* @return 
		*/
		button_coche.setVisible(false);
		button_decoche.setVisible(true);

		String selectedItem = labelRecipe.getText(); // je récupère le titre
		listRecettesFavs.remove(selectedItem);
		System.out.println(listRecettesFavs);
		BDD_favoris.delete_element("Favoris", selectedItem);

	}

	@FXML
	private void decoche_clicked() {
		/**
		* permet d'enlever une recette des favoris
		* @param
		* @return 
		*/
		button_coche.setVisible(true);
		button_decoche.setVisible(false);
		String nomRecette = labelRecipe.getText(); // je récupère le titre
		String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString();
		int id = Integer.parseInt(SpoonacularManager.recupIDrecetteSelectionnee(selectedItem));
		BDD_favoris.insert(id, nomRecette, "Pas de fiche", "Favoris");

	}

	public ArrayList<String> IngrManquant() throws IOException, JSONException {
		/**
		* permet de récupérer les ingrédients manquants d'une recette
		* @param
		* @return Liste contenant les infos
		*/
		ArrayList<String> liste_entiere = new ArrayList<String>();
		String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString(); // je récupère le titre
		String id = SpoonacularManager.recupIDrecetteSelectionnee(selectedItem) + ',';
		System.out.println(id);
		List<Aliment> liste_aliment = new ArrayList<Aliment>();
		liste_aliment = BDD_frigo.afficher_table(table_actuelle);

		liste_aliment = SpoonacularManager.unSeulMot(liste_aliment);
		if (liste_aliment.size() != 0) {
			String aliment = liste_aliment.get(0).getNom();
			for (int k = 1; k < liste_aliment.size(); k++) {
				aliment = aliment + "," + (liste_aliment.get(k).getNom().toLowerCase());
			}
			liste_entiere = SpoonacularManager.returnJsonToString(aliment);
		}

		int indexID = -1;
		int k = 0;
		do {
			indexID = liste_entiere.get(k).indexOf(id);
			k += 1;
		} while ((k < liste_entiere.size() && indexID == -1));
		String recette = "";
		System.out.println(indexID);
		if (indexID != -1) {
			recette = liste_entiere.get(k - 1);
		} else {
			recette = "Aucun";
		}

		System.out.println(recette);
		ArrayList<String> MissedIngr = SpoonacularManager.recupMissedIngredients(recette);
		System.out.println(MissedIngr);

		return (MissedIngr);
//		IngrMq.refresh();
//		IngrMq.setItems(list);
	}
	
	ArrayList<AlimentCompare> tableauCompare = new ArrayList<AlimentCompare>();
	
	public void IngrRecette(String id, ArrayList<String> value) throws IOException, JSONException {
		/**
		* permet d'afficher les informations nutritives d'une recette
		* @param id d'une recette
		* @param liste avec les quantités des ingrédients
		* @return 
		*/
		ArrayList<String> unit = SpoonacularManager.recupUnit(id);
		ArrayList<String> val = value;
		ArrayList<String> name = SpoonacularManager.recupRecipesIngredient(id);
		
		tableauCompare.clear();
		
		for (int k = 0; k < value.size(); k++) {
			String unite = unit.get(k);
			Double valeur = Double.parseDouble(value.get(k));
			String nom = name.get(k);
			AlimentCompare variable = new AlimentCompare(nom, valeur, unite, null, null);
			tableauCompare.add(variable);

		}
		tableauCompare = AddIngrOwned(tableauCompare,false);
		ObservableList<AlimentCompare> table_list = FXCollections.observableArrayList(tableauCompare);
		IngrR.setItems(table_list);

		columnName.setCellValueFactory(cellData -> cellData.getValue().getPropertyName());
		columnNeeded.setCellValueFactory(cellData -> cellData.getValue().getPropertyQuantityNeeded().asObject());
		columnUnitNeeded.setCellValueFactory(cellData -> cellData.getValue().getPropertyUniteNeeded());
		columnUnitOwned.setCellValueFactory(cellData -> cellData.getValue().getPropertyUniteOwn());
		columnOwned.setCellValueFactory(cellData -> cellData.getValue().getPropertyQuantityOwn().asObject());
		
		IngrR.setRowFactory(tv -> new TableRow<AlimentCompare>() {
			@Override
			protected void updateItem(AlimentCompare aliment, boolean empty) {
				super.updateItem(aliment, empty);
				if (aliment == null)
					setStyle("");
				else if (aliment.getUniteOwn().equals("-"))
					setStyle("-fx-background-color: #EAA09C;");
				else {
					setStyle("-fx-background-color: #80E0A7;");
				}
			}});	
	}
	public void recepeDone() throws JSONException, IOException{
		/**
		* Permet de cliquer sur le bouton recepe done
		* @param
		* @return 
		*/
		AddIngrOwned(tableauCompare,true);
	}

	// AlimentCourse alimCourse = new AlimentCourse;}

	private ArrayList<AlimentCompare> AddIngrOwned(ArrayList<AlimentCompare> tableauCompare, boolean recepeDone)
			throws JSONException, IOException {
		/**
		* permet de comparer les aliments présents dans le frigo avec ceux présents dans la recette et de retenir ceux manquants
		* @param une liste d'AlimentCompare
		* @param booléan pour savoir si la recepe a été faite ou pas
		* @return liste de type AlimentCompare contenant les aliments manquants et des infos sur ces derniers.
		*/

		for (AlimentCompare alimComp : tableauCompare) {
			boolean find = false;
			List<Aliment> listCoresp;
			try {
				listCoresp = EdamamManager.AlimentSearcher(EdamamManager.jsonParser(alimComp.getNom()));
				System.out.println("Recherche reussi pour " + alimComp.getNom());

				Integer i = 0;
				while (find == false && i < listCoresp.size() - 1) {
					Integer j = 0;
					while (find == false && j < liste_aliment.size() - 1) {
						if (liste_aliment.get(j).getNom().equals(listCoresp.get(i).getNom())) {
							System.out.println("Correspondance pour l'aliment recette : " + alimComp.getNom()
									+ " avec l'aliment frigo " + liste_aliment.get(j).getNom());
							alimComp.setQuantiteOwn(liste_aliment.get(j).getQuantite());
							alimComp.setUniteOwn(liste_aliment.get(j).getUnite());
							if (recepeDone == true) {
								Double newQte =recepeDoneQteCalc(alimComp,false);
								if(newQte.equals(0.0))
								{
									BDD_frigo.delete_element(table_actuelle, liste_aliment.get(j));
								}
								else {

									BDD_frigo.delete_element(table_actuelle, liste_aliment.get(j));
									Aliment alimModif= liste_aliment.get(j);
									alimModif.setQuantite(newQte);						
									BDD_frigo.insert(alimModif,table_actuelle);
								}
							}
							Double DoubleQteRest = recepeDoneQteCalc(alimComp,true);
							if (DoubleQteRest<0.0) {
								
								DoubleQteRest = - DoubleQteRest;
								AlimentCourse alimentCourse = new AlimentCourse(alimComp.getNom(), DoubleQteRest, alimComp.getUniteOwn());
								liste_Course.add(alimentCourse);
							}

							// Fcton Qte
							find = true;
						}
						j++;
					}
					i++;
				}

			} catch (java.io.IOException e) {
				System.out.println("Erreur 400 Edamam pour " + alimComp.getNom());
			} finally {
				if (find == false) {
					System.out.println("Pas de correspondance pour l'aliment recette : " + alimComp.getNom());
					alimComp.setQuantiteOwn(0.0);
					alimComp.setUniteOwn("-");
					AlimentCourse alimentCourse = alimComp.alimCourseFromAlimComp();
					liste_Course.add(alimentCourse);
				}
			}

		}
		for (AlimentCourse alimcourse : liste_Course) {
			System.out.println(alimcourse.getNom());
			System.out.println(alimcourse.getQuantite());
		}

		return tableauCompare;

	}

	private Double recepeDoneQteCalc(AlimentCompare alimComp, boolean forCourse) {
		/**
		* met à jour le frigo en fonction si la recette a été faite ou non
		* @param un aliment du type AlimentCompare
		* @param booléan pour savoir si la recepe a été faite ou pas
		* @return liste de type AlimentCompare contenant les aliments manquants et des infos sur ces derniers.
		*/
		String unitRecette = alimComp.getUniteNeeded();
		String unitFridge = alimComp.getUniteOwn();
		Double qteRecette = alimComp.getQuantiteNeeded();
		Double qteFridge = alimComp.getQuantiteOwn();
		Double newQte;
		newQte = qteFridge;
		if (unitFridge.equals("g")) {
			if (unitRecette.equals("g") || unitRecette.equals("grams")) {
				newQte = qteFridge-qteRecette;
			} else if (unitRecette.equals("kilo")) {
				newQte = qteFridge-qteRecette*1000;
			} else if (unitRecette.equals("Tbsp") || unitRecette.equals("Tbsps")) {
				newQte = qteFridge-qteRecette*9;
			} else if (unitRecette.equals("tsps") || unitRecette.equals("tsp")) {
				newQte = qteFridge-qteRecette*3;
			} else if (unitRecette.equals("cup")) {
				newQte = qteFridge-qteRecette*140;
			}
		} else if (unitFridge.equals("ml")) {
			if (unitRecette.equals("ml")) {
				newQte = qteFridge-qteRecette;
			} else if (unitRecette.equals("liters")) {
				newQte = qteFridge-qteRecette*1000;
			} else if (unitRecette.equals("Tbsp") || unitRecette.equals("Tbsps")) {
				newQte = qteFridge-qteRecette*15;
			} else if (unitRecette.equals("tsps") || unitRecette.equals("tsp")) {
				newQte = qteFridge-qteRecette*15;
			} else if (unitRecette.equals("cup")) {
				newQte = qteFridge-qteRecette*236;
			}
		} else if (unitFridge.equals("piece(s)")) {
			if (unitRecette.equals("large") || unitRecette.equals("servings") || unitRecette.equals("leaf")
					|| unitRecette.equals("cloves") || unitRecette.equals("clove")
					|| unitRecette.equals("medium pieces") || unitRecette.equals("pinch")
					|| unitRecette.equals("sticks") || unitRecette.equals("pinch") || unitRecette.equals("links")
					|| unitRecette.equals("cubes") || unitRecette.equals("can")||unitRecette.equals("small"))  {
				newQte = qteFridge-qteRecette;
			}
		}
		if(newQte<0.0 && forCourse==false)
		{
			newQte=0.0;
		}
		return newQte;
	}

	public void Nutriments(String id) throws IOException, JSONException {
		/**
		* permet l'affichage des informations nutritives d'une recette
		* @param l'id d'une recette
		* @return un objet du type Nutriment
		*/
		Nutriments nutriment = SpoonacularManager.recupNutritionWidget(id);
		carbs.setText("Carbohydrates : " + nutriment.getCarbohydrates());
		calories.setText("Calories : " + nutriment.getCalories());
		fat.setText("Fat : " + nutriment.getFat());
		protein.setText("Proteins : " + nutriment.getProtein());

	}

	public ArrayList<String> changed_quantity(String id, double d) throws IOException, JSONException {
		/**
		* permet d'adapter la recette au nombre de personne
		* @param l'id d'une recette
		* @param une quantité
		* @return une liste où les changements ont étés effectués
		*/
		ArrayList<String> value = SpoonacularManager.recupIngredientQuantity(id);
		ArrayList<String> valueModified = new ArrayList<String>();
		for (int k = 0; k < value.size(); k++) {
			Double newvalue = (Double.parseDouble(value.get(k)) * d / 4);
			String nValue = newvalue.toString();
			valueModified.add(nValue);
		}
		return valueModified;

	}

	@FXML
	public ArrayList<String> isMeal(ArrayList<String> liste_titre) throws IOException, JSONException {
		/**
		* permet l'affichage des recettes filtrés en fonction de Breakfast, Lunch, Dinner, Dessert
		* @param une liste des recettes non filtrées
		* @return une liste des recettes filtrées
		*/
		ArrayList<String> listeARenvoyer = new ArrayList<String>();
		ArrayList<Boolean> listeBLDD = new ArrayList<Boolean>();
		ArrayList<String> listeId = new ArrayList<String>();
		if (breakfast.isSelected()) {
			System.out.println("breafast selected");
			listeBLDD.add(true);
		} else {
			listeBLDD.add(false);
		}
		if (lunch.isSelected()) {
			System.out.println("lunch selected");
			listeBLDD.add(true);
		} else {
			listeBLDD.add(false);
		}
		if (dinner.isSelected()) {
			System.out.println("dinner selected");
			listeBLDD.add(true);
		} else {
			listeBLDD.add(false);
		}
		if (dessert.isSelected()) {
			System.out.println("dessert selected");
			listeBLDD.add(true);
		} else {
			listeBLDD.add(false);
		}
		if (!listeBLDD.get(0) && !listeBLDD.get(1) && !listeBLDD.get(2) && !listeBLDD.get(3)) {
			return (liste_titre);
		}
		listeId = SpoonacularManager.justId(liste_titre, listeBLDD);
		listeARenvoyer = SpoonacularManager.IdAfficher(liste_titre, listeId);
		System.out.println(listeARenvoyer);
		return (listeARenvoyer);
	}

	@FXML
	public void testCheckBoxBreakfast() {
		/**
		* permet de décaucher les autres filtres
		* @param
		* @return 
		*/
		lunch.setSelected(false);
		dessert.setSelected(false);
		dinner.setSelected(false);
	}

	@FXML
	public void testCheckBoxLunch() {
		/**
		* permet de décaucher les autres filtres
		* @param
		* @return 
		*/
		breakfast.setSelected(false);
		dessert.setSelected(false);
		dinner.setSelected(false);
	}

	@FXML
	public void testCheckBoxDinner() {
		/**
		* permet de décaucher les autres filtres
		* @param
		* @return 
		*/
		lunch.setSelected(false);
		dessert.setSelected(false);
		breakfast.setSelected(false);
	}

	@FXML
	public void testCheckBoxDessert() {
		/**
		* permet de décaucher les autres filtres
		* @param
		* @return 
		*/
		lunch.setSelected(false);
		breakfast.setSelected(false);
		dinner.setSelected(false);
	}

	@FXML
	public void RecipeDone() throws IOException, JSONException, ParseException {
		/**
		* permet de lancer les méthodes lorsqu'on appuie sur le bouton correspondant
		* @param
		* @return 
		*/
		BDD_calandar calandar =new BDD_calandar();
	    Nutriments reciptalimentTableChoose = SpoonacularManager.recupNutritionWidget(RecipeController.id_ma_recette);
	    
	    calandar.setDate(BDD_calandar.ma_date());
    	calandar.setCalories(reciptalimentTableChoose.getCalories());
    	calandar.setFat(reciptalimentTableChoose.getFat());
    	calandar.setSugar(reciptalimentTableChoose.getSugar());
    	calandar.setCarbohydrates(reciptalimentTableChoose.getCarbohydrates());
    	calandar.setMagnesium(reciptalimentTableChoose.getMagnesium());
    	calandar.setCholesterol(reciptalimentTableChoose.getCholesterol());
    	calandar.setSatured_fat(reciptalimentTableChoose.getSatured_fat());
	    BDD_calandar.insert( calandar, "calandar_nutrionnal");
	    
	    AddIngrOwned(tableauCompare,true);
		System.out.println("RecepeDone");
	}

	@FXML
	public void generateList() throws IOException {
		/**
		* permet de créer la liste de course (au format txt)
		* @param
		* @return 
		*/
		System.out.println();
		System.out.println(liste_Course);
		try {
			String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString();
			File file = new File("ma_liste_de_course.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> Arendre = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null) {
				Arendre.add(line);
			}
			Arendre.add("\n" + "------- " + selectedItem.substring(0, selectedItem.indexOf('(') - 1) + " -------");
			for (int k = 0; k < liste_Course.size(); k++) {
				Arendre.add(liste_Course.get(k).getNom() + " " + liste_Course.get(k).getQuantite().toString() + " "
						+ liste_Course.get(k).getUnite());
			}
			fr.close();

			PrintWriter writer = new PrintWriter("ma_liste_de_course.txt", "UTF-8");
			for (int k = 0; k < Arendre.size(); k++) {
				writer.println(Arendre.get(k));
			}
			System.out.println(writer);
			writer.close();

		} catch (FileNotFoundException e) {
			String selectedItem = liste_recette.getSelectionModel().getSelectedItem().toString();
			PrintWriter writer = new PrintWriter("ma_liste_de_course.txt", "UTF-8");
			writer.println("My Beautiful Grocery List : " + "\n");
			writer.println();

			writer.println("------- " + selectedItem.substring(0, selectedItem.indexOf('(') - 1) + " -------");
			for (int k = 0; k < liste_Course.size(); k++) {
				writer.print(liste_Course.get(k).getNom());
				writer.print(" " + liste_Course.get(k).getQuantite());
				writer.println(" " + liste_Course.get(k).getUnite());
			}
			System.out.println(writer);
			writer.close();
		} catch (NullPointerException e2) {
			System.out.println("nope");
		}
	}

	@FXML
	public void deleteList() throws IOException {
		/**
		* permet de supprimer la liste de course (au format txt)
		* @param
		* @return 
		*/
		File file = new File("ma_liste_de_course.txt");
		file.delete();
	}
}
