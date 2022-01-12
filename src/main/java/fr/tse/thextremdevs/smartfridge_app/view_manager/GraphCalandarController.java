package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_calandar;
import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_utilisateur;
import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;


public class GraphCalandarController implements Initializable  {
		@FXML
		private ListView<String> liste_recette;
		@FXML
	    private CategoryAxis xAxis;
		@FXML
	    private NumberAxis yAxis;
		@FXML
	    private LineChart<String,Number> lineChart;
		@FXML
		private CheckBox calories;
		@FXML
		private CheckBox fat;
		@FXML
		private CheckBox sugar;
		@FXML
		private CheckBox carbohydrates;
		@FXML
		private CheckBox magnesium;
		@FXML
		private CheckBox cholesterol;
		@FXML
		private CheckBox satured_fat;
		
		private XYChart.Series calories_curb;
		private XYChart.Series fat_curb;
		private XYChart.Series sugar_curb;
		private XYChart.Series carbohydrates_curb;
		private XYChart.Series magnesium_curb;
		private XYChart.Series cholesterol_curb;
		private XYChart.Series satured_fat_curb;

		/**
	    * Fonction d'initialisation du à l'implementation de l'interface Initializable.
	    * Elle est excutee à l'affichage de la vue
	    * Ici on set les valeurs de la combobox de choix unite 
	    */ 
		@Override
		public void initialize(URL location, ResourceBundle resources) {
		}	
		
		/**
		* Trace un calandrier nutrionnel pour l'utilisateur en fonction des recettes qu'il a effectuée. 
		* @param  
		* @return  
		*/  
		@FXML
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void mon_calander() {
				

				calories_curb = new XYChart.Series();
				fat_curb = new XYChart.Series();
				sugar_curb = new XYChart.Series();
				carbohydrates_curb = new XYChart.Series();
				magnesium_curb = new XYChart.Series();
				cholesterol_curb = new XYChart.Series();
				satured_fat_curb = new XYChart.Series();
		        lineChart.setTitle("Calandar nutritionnal");

		        calories_curb.setName("Calories");
		        fat_curb.setName("Fat");
		        sugar_curb.setName("Sugar");
		        carbohydrates_curb.setName("Carbohydrates");
		        magnesium_curb.setName("Magnesium");
		        cholesterol_curb.setName("Cholesterol");	
		        satured_fat_curb.setName("Satured");


	        	List<BDD_calandar> my_list=BDD_calandar.selectAll("calandar_nutrionnal");
				Nutriments nutriment_BDD=BDD_utilisateur.afficher_table("formulary").get(0);

	        	
	        	for (int k=0;k<my_list.size();k++) {
	        		
			        calories_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(),100*Double.parseDouble(my_list.get(k).getCalories().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCalories().replaceAll("[^\\d.]", ""))));
			        fat_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(), 100*Double.parseDouble(my_list.get(k).getFat().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getFat().replaceAll("[^\\d.]", ""))));        
			        sugar_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(),100*Double.parseDouble( my_list.get(k).getSugar().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSugar().replaceAll("[^\\d.]", ""))));
			        carbohydrates_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(), 100*Double.parseDouble(my_list.get(k).getCarbohydrates().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCarbohydrates().replaceAll("[^\\d.]", ""))));
			        magnesium_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(),100*Double.parseDouble( my_list.get(k).getMagnesium().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getMagnesium().replaceAll("[^\\d.]", ""))));
			        cholesterol_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(),100*Double.parseDouble( my_list.get(k).getCholesterol().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getCholesterol().replaceAll("[^\\d.]", ""))));
			        satured_fat_curb.getData().add(new XYChart.Data(my_list.get(k).getDate(),100*Double.parseDouble( my_list.get(k).getSatured_fat().replaceAll("[^\\d.]", ""))/Double.parseDouble(nutriment_BDD.getSatured_fat().replaceAll("[^\\d.]", ""))));
			        
	        	} 
	        	lineChart.getData().clear();
	        	
	        	if (calories.isSelected()) {
			        lineChart.getData().add(calories_curb);
	        	}
		        if (fat.isSelected()) {
			        lineChart.getData().add(fat_curb);
			    }
		        if (sugar.isSelected()) {
			        lineChart.getData().add(sugar_curb);
			    }
		        if (carbohydrates.isSelected()) {
			        lineChart.getData().add(carbohydrates_curb);
				}
		        if (magnesium.isSelected()) {
			        lineChart.getData().add(magnesium_curb);
		        }
		        if (cholesterol.isSelected()) {
			        lineChart.getData().add(cholesterol_curb);
		        }
		        if (satured_fat.isSelected()) {
			        lineChart.getData().add(satured_fat_curb);
		        }
				
		    }	
}