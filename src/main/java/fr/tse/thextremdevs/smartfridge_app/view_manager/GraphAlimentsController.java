package fr.tse.thextremdevs.smartfridge_app.view_manager;

import java.net.URL;
import java.util.ResourceBundle;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_utilisateur;
import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class GraphAlimentsController implements Initializable  {
		
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
			mon_graph();
		}
		
		/**
		* Trace un graphe nutrionnel pour tous les éléments d'un aliment. 
		* @param  
		* @return
		*/ 
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void mon_graph() {
			
			Aliment alimentTableChoose = FridgeController.value_double_clicked;
			Nutriments nutriment_BDD=BDD_utilisateur.afficher_table("formulary").get(0);

			Double ernerc_kcal=100*alimentTableChoose.getErnerc_kcal()/Double.parseDouble(nutriment_BDD.getCalories().replaceAll("[^\\d.]", ""));
			Double procnt=100*alimentTableChoose.getProcnt()/Double.parseDouble(nutriment_BDD.getProtein().replaceAll("[^\\d.]", ""));
			Double fat=100*alimentTableChoose.getFat()/Double.parseDouble(nutriment_BDD.getFat().replaceAll("[^\\d.]", ""));
			Double fibtg=100*alimentTableChoose.getFibtg()/Double.parseDouble(nutriment_BDD.getFiber().replaceAll("[^\\d.]", ""));
			
			
	        bc.setTitle("Nutriment Summary");
	        xAxis.setLabel("Value");  
	        xAxis.setTickLabelRotation(90);
	        xAxis.setAutoRanging(false);
	        xAxis.setMaxHeight(100);
	        yAxis.setLabel("Nutiment");        
	 
			XYChart.Series series1 = new XYChart.Series();
			series1.setName("Nutrients");
			final XYChart.Data<Double,String> dataEnergy = new XYChart.Data(ernerc_kcal, "Energy");
			final XYChart.Data<Double,String> dataProtein = new XYChart.Data(procnt,"Protein");
			final XYChart.Data<Double,String> dataFat = new XYChart.Data(fat,"Fat");
			final XYChart.Data<Double,String> dataFiber = new XYChart.Data(fibtg,"Fiber");
			
			series1.getData().add(dataEnergy);
			series1.getData().add(dataProtein);
			series1.getData().add(dataFat);
			series1.getData().add(dataFiber); 
	        bc.getData().addAll(series1);
	        
	        bc.setLegendVisible(false);
	        dataEnergy.getNode().setStyle("-fx-bar-fill: CHART_COLOR_1;");
			dataProtein.getNode().setStyle("-fx-bar-fill: CHART_COLOR_2;");
			dataFat.getNode().setStyle("-fx-bar-fill: CHART_COLOR_3;");
			dataFiber.getNode().setStyle("-fx-bar-fill: CHART_COLOR_4;");
	        
	    }
 
}
