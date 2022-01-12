package fr.tse.thextremdevs.smartfridge_app.view_manager;


import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_utilisateur;
import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;

public class NutrionnalFormularyController implements Initializable{

	@FXML
	private TextField calories;
	@FXML
	private TextField fat;
	@FXML
	private TextField saturated_fat;
	@FXML
	private TextField sugar;
	@FXML
	private TextField carbohydrates;
	@FXML
	private TextField cholesterol;
	@FXML
	private TextField sodium;
	@FXML
	private TextField protein;
	@FXML
	private TextField selenium;
	@FXML
	private TextField magnesium;
	@FXML
	private TextField Potassium;
	@FXML
	private TextField folate;
	@FXML
	private TextField manganese;
	@FXML
	private TextField fiber;
	@FXML
	private TextField phosphorus;
	@FXML
	private TextField iron;
	@FXML
	private TextField zinc;
	@FXML
	private TextField calcium;
	@FXML
	private TextField vitamin_A;
	@FXML
	private TextField vitamin_B1;
	@FXML
	private TextField vitamin_B2;
	@FXML
	private TextField vitamin_B3;
	@FXML
	private TextField vitamin_B5;
	@FXML
	private TextField vitamin_B6;
	@FXML
	private TextField vitamin_B12;
	@FXML
	private TextField vitamin_E;
	@FXML
	private TextField vitamin_K;
	@FXML
	private TextField vitamin_C;
	@FXML
	private TextField vitamin_D;
	@FXML
	private TextField copper;
	@FXML
	private Button save_formulary;
	
    public void initialize(final URL location, final ResourceBundle resources) {
    	List<Nutriments> list_nutriment= BDD_utilisateur.afficher_table("formulary");

    	Nutriments nutriment=new Nutriments("2000"  ,"70g" ,"20g"         ,"260g"        ,"36g" ,"300mg"     ,"2300mg" ,"60g"   ,"65µg"    ,"2.3mg"   ,"1.2mg"    ,"25g"  ,"400µg" ,"10mg" ,"16mg"     ,"1.3mg"    ,"70mg"    ,"55µg"   ,"3500mg"  ,"2500mg","700mg"    ,"400mg"   ,"15mg"    ,"1mg"   ,"5mg"      ,"1.3mg"    ,"10mg" ,"25000IU" ,"2.4µg"     ,"10µg");
    	if (list_nutriment.size()!=0) {
    		nutriment=list_nutriment.get(0);
    	}
    	
    	calories.setText(nutriment.getCalories());
    	fat.setText(nutriment.getFat());
    	saturated_fat.setText(nutriment.getSatured_fat());
    	sugar.setText(nutriment.getSugar());
    	carbohydrates.setText(nutriment.getCarbohydrates());
    	cholesterol.setText(nutriment.getCholesterol());
    	sodium.setText(nutriment.getSodium());
    	protein.setText(nutriment.getProtein());
    	selenium.setText(nutriment.getSelenium());
    	magnesium.setText(nutriment.getMagnesium());
    	Potassium.setText(nutriment.getPotassium());
    	folate.setText(nutriment.getFolate());
    	manganese.setText(nutriment.getManganese());
    	fiber.setText(nutriment.getFiber());
    	phosphorus.setText(nutriment.getPhosphorus());
    	iron.setText(nutriment.getIron());
    	zinc.setText(nutriment.getZinc());
    	calcium.setText(nutriment.getCalcium());
    	vitamin_A.setText(nutriment.getVitamine_A());
    	vitamin_B1.setText(nutriment.getVitamine_B1());
    	vitamin_B2.setText(nutriment.getVitamine_B2());
    	vitamin_B3.setText(nutriment.getVitamine_B3());
    	vitamin_B5.setText(nutriment.getVitamine_B5());
    	vitamin_B6.setText(nutriment.getVitamine_B6());
    	vitamin_E.setText(nutriment.getVitamine_E());
    	vitamin_K.setText(nutriment.getVitamine_K());
    	vitamin_C.setText(nutriment.getVitamine_C());
    	vitamin_B12.setText(nutriment.getVitamine_B12());
    	vitamin_D.setText(nutriment.getVitamine_D());
    	copper.setText(nutriment.getCopper());

    }

    @FXML
    public void save_in_BDD(ActionEvent event) throws IOException {
    	Nutriments nutriment = new Nutriments();
    	
    	nutriment.setCalories(calories.getText());
    	nutriment.setFat(fat.getText());
    	nutriment.setSatured_fat(saturated_fat.getText());
    	nutriment.setSugar(sugar.getText());
    	nutriment.setCarbohydrates(carbohydrates.getText());
    	nutriment.setCholesterol(cholesterol.getText());
    	nutriment.setSodium(sodium.getText());
    	nutriment.setProtein(protein.getText());
    	nutriment.setSelenium(selenium.getText());
    	nutriment.setMagnesium(magnesium.getText());
    	nutriment.setPotassium(Potassium.getText());
    	nutriment.setFolate(folate.getText());
    	nutriment.setManganese(manganese.getText());
    	nutriment.setFiber(fiber.getText());
    	nutriment.setPhosphorus(phosphorus.getText());
    	nutriment.setIron(iron.getText());
    	nutriment.setZinc(zinc.getText());
    	nutriment.setCalcium(calcium.getText());
    	nutriment.setVitamine_A(vitamin_A.getText());
    	nutriment.setVitamine_B1(vitamin_B1.getText());
    	nutriment.setVitamine_B2(vitamin_B2.getText());
    	nutriment.setVitamine_B3(vitamin_B3.getText());
    	nutriment.setVitamine_B5(vitamin_B5.getText());
    	nutriment.setVitamine_B6(vitamin_B6.getText());
    	nutriment.setVitamine_B12(vitamin_B12.getText());
    	nutriment.setVitamine_E(vitamin_E.getText());
    	nutriment.setVitamine_K(vitamin_K.getText());
    	nutriment.setVitamine_C(vitamin_C.getText());
    	nutriment.setVitamine_D(vitamin_D.getText());
    	nutriment.setCopper(copper.getText());

		BDD_utilisateur.save("formulary", nutriment);
		Stage stage = (Stage) save_formulary.getScene().getWindow();
	    stage.close();
    }
    
}


