package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_utilisateur;
import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;
import fr.tse.thextremdevs.smartfridge_app.recipes.SpoonacularManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;


public class GraphRecipController implements Initializable  {
		@FXML
		private ListView<String> liste_recette;
		@FXML
	    private NumberAxis xAxis;
		@FXML
	    private CategoryAxis yAxis;
		@FXML
	    private BarChart<Number,String> bc;
		
		/**
	    * Fonction d'initialisation du à l'implementation de l'interface Initializable.
	    * Elle est excutee à l'affichage de la vue
	    * Ici on set les valeurs de la combobox de choix unite 
	    */ 
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			try {
				mon_graph();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		* Trace un graphe nutrionnel pour tous les éléments d'une recette.
		* Cette fonction permet aussi de de mettre une légende.  
		* @param  
		* @return
		*/ 
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void mon_graph() throws IOException, JSONException {
			bc.setTitle("Nutriment Summary");
	        xAxis.setLabel("Value");  
	        xAxis.setAutoRanging(false);
	        xAxis.setMaxHeight(100);
	        xAxis.setTickLabelRotation(90);
	        yAxis.setLabel("Nutriment");        
			
			Nutriments reciptalimentTableChoose = SpoonacularManager.recupNutritionWidget(RecipeController.id_ma_recette);
			Nutriments nutriment_BDD=BDD_utilisateur.afficher_table("formulary").get(0);
			
			Double calories=100*Double.parseDouble(reciptalimentTableChoose.getCalories().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCalories().replaceAll("[^\\d.]", ""));
			Double fat=100*Double.parseDouble(reciptalimentTableChoose.getFat().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getFat().replaceAll("[^\\d.]", ""));
			Double satured_fat=100*Double.parseDouble(reciptalimentTableChoose.getSatured_fat().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSatured_fat().replaceAll("[^\\d.]", ""));
			Double carbohydrates=100*Double.parseDouble(reciptalimentTableChoose.getCarbohydrates().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCarbohydrates().replaceAll("[^\\d.]", ""));
			Double sugar=100*Double.parseDouble(reciptalimentTableChoose.getSugar().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSugar().replaceAll("[^\\d.]", ""));
			Double Cholesterol=100*Double.parseDouble(reciptalimentTableChoose.getCholesterol().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCholesterol().replaceAll("[^\\d.]", ""));
			Double sodium=100*Double.parseDouble(reciptalimentTableChoose.getSodium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSodium().replaceAll("[^\\d.]", ""));

			Double protein=100*Double.parseDouble(reciptalimentTableChoose.getProtein().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getProtein().replaceAll("[^\\d.]", ""));
			Double manganese=100*Double.parseDouble(reciptalimentTableChoose.getManganese().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getManganese().replaceAll("[^\\d.]", ""));
			Double selenium=100*Double.parseDouble(reciptalimentTableChoose.getSelenium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSelenium().replaceAll("[^\\d.]", ""));
			Double Fiber=100*Double.parseDouble(reciptalimentTableChoose.getFiber().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getFiber().replaceAll("[^\\d.]", ""));
			Double Magnesium=100*Double.parseDouble(reciptalimentTableChoose.getMagnesium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getMagnesium().replaceAll("[^\\d.]", ""));
			Double Phosphorus=100*Double.parseDouble(reciptalimentTableChoose.getPhosphorus().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getPhosphorus().replaceAll("[^\\d.]", ""));
			Double vitamine_B1=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B1().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B1().replaceAll("[^\\d.]", ""));
			Double vitamine_E=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_E().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_E().replaceAll("[^\\d.]", ""));
			Double Potassium=100*Double.parseDouble(reciptalimentTableChoose.getPotassium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getPotassium().replaceAll("[^\\d.]", ""));
			Double Iron=100*Double.parseDouble(reciptalimentTableChoose.getIron().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getIron().replaceAll("[^\\d.]", ""));
			Double vitamine_B6=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B6().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B6().replaceAll("[^\\d.]", ""));
			Double zinc=100*Double.parseDouble(reciptalimentTableChoose.getZinc().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getZinc().replaceAll("[^\\d.]", ""));
			Double folate=100*Double.parseDouble(reciptalimentTableChoose.getFolate().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getFolate().replaceAll("[^\\d.]", ""));
			Double vitamine_K=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_K().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_K().replaceAll("[^\\d.]", ""));
			Double vitamine_B2=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B2().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B2().replaceAll("[^\\d.]", ""));
			Double calcium=100*Double.parseDouble(reciptalimentTableChoose.getCalcium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCalcium().replaceAll("[^\\d.]", ""));
			Double vitamine_B3=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B3().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B3().replaceAll("[^\\d.]", ""));
			Double vitamine_B5=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B5().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B5().replaceAll("[^\\d.]", ""));
			Double vitamine_A=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_A().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_A().replaceAll("[^\\d.]", ""));
			Double vitamine_C=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_C().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_C().replaceAll("[^\\d.]", ""));
			Double vitamine_D=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_D().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_D().replaceAll("[^\\d.]", ""));
			Double vitamine_B12=100*Double.parseDouble(reciptalimentTableChoose.getVitamine_B12().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getVitamine_B12().replaceAll("[^\\d.]", ""));
			Double copper=100*Double.parseDouble(reciptalimentTableChoose.getCopper().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCopper().replaceAll("[^\\d.]", ""));
		
			
			XYChart.Series series1 = new XYChart.Series();
	        series1.setName("Limit These");       
	        series1.getData().add(new XYChart.Data(calories, "Calories"));
	        series1.getData().add(new XYChart.Data(fat, "Fat"));
	        series1.getData().add(new XYChart.Data(satured_fat, "Satured Fat"));
	        series1.getData().add(new XYChart.Data(carbohydrates, "Carbohydrates"));
	        series1.getData().add(new XYChart.Data(sugar, "Sugar"));
	        series1.getData().add(new XYChart.Data(Cholesterol, "Cholesterol"));
	        series1.getData().add(new XYChart.Data(sodium, "sodium"));
    
	        
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("Get Enough of These");
	        series2.getData().add(new XYChart.Data(protein, "Protein"));
	        series2.getData().add(new XYChart.Data(manganese, "Manganese"));
	        series2.getData().add(new XYChart.Data(selenium, "Selenium"));
	        series2.getData().add(new XYChart.Data(Fiber, "Fiber"));
	        series2.getData().add(new XYChart.Data(Magnesium, "Magnesium"));
	        series2.getData().add(new XYChart.Data(Phosphorus, "Phosphorus"));
	        series2.getData().add(new XYChart.Data(vitamine_B1, "Vitamine_B1"));
	        series2.getData().add(new XYChart.Data(vitamine_E, "Vitamine_E"));
	        series2.getData().add(new XYChart.Data(Potassium, "Potassium"));
	        series2.getData().add(new XYChart.Data(Iron, "Iron"));
	        series2.getData().add(new XYChart.Data(vitamine_B6, "Vitamine_B6"));
	        series2.getData().add(new XYChart.Data(zinc, "Zinc"));
	        series2.getData().add(new XYChart.Data(folate, "Folate"));
	        series2.getData().add(new XYChart.Data(vitamine_K, "Vitamine_K"));
	        series2.getData().add(new XYChart.Data(vitamine_B2, "Vitamine_B2"));
	        series2.getData().add(new XYChart.Data(calcium, "Calcium"));
	        series2.getData().add(new XYChart.Data(vitamine_B3, "Vitamine_B3"));
	        series2.getData().add(new XYChart.Data(vitamine_B5, "Vitamine_B5"));
	        series2.getData().add(new XYChart.Data(vitamine_A, "Vitamine_A"));
	        series2.getData().add(new XYChart.Data(vitamine_C, "Vitamine_C"));
	        series2.getData().add(new XYChart.Data(vitamine_D, "Vitamine_D"));
	        series2.getData().add(new XYChart.Data(vitamine_B12, "Vitamine_B12"));
	        series2.getData().add(new XYChart.Data(copper, "Copper"));
	        
	        bc.getData().addAll(series1, series2);
	        
	        
	    }
 
}
