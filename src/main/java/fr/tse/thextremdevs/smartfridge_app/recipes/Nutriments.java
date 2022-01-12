package fr.tse.thextremdevs.smartfridge_app.recipes;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class Nutriments {

	private String calories;
	private String fat;
	private String satured_fat;
	private String carbohydrates;
	private String sugar;
	private String Cholesterol;
	private String sodium;

	private String protein;
	private String manganese;
	private String selenium;
	private String Fiber;
	private String Magnesium;
	private String Phosphorus;
	private String vitamine_B1;
	private String vitamine_E;
	private String Potassium;
	private String Iron;
	private String vitamine_B6;
	private String zinc;
	private String folate;
	private String vitamine_K;
	private String vitamine_B2;
	private String calcium;
	private String vitamine_B3;
	private String vitamine_B5;
	private String vitamine_A;
	private String vitamine_C;
	private String vitamine_B12;
	private String vitamine_D;
	private String copper;

	public Nutriments(ArrayList<String> liste) {
		super();
		this.calories = liste.get(0);
		this.fat = liste.get(1);
		this.satured_fat = liste.get(2);
		this.carbohydrates = liste.get(3);
		this.sugar = liste.get(4);
		Cholesterol = liste.get(5);
		this.sodium = liste.get(6);
		
		this.protein=liste.get(7);
		this.vitamine_K = liste.get(8);
		this.manganese = liste.get(9);
		this.vitamine_B1 = liste.get(10);
		Fiber = liste.get(11);
		this.folate=liste.get(12);
		Iron = liste.get(13);
		this.vitamine_B3 = liste.get(14);
		this.vitamine_B2 = liste.get(15);
		this.vitamine_C = liste.get(16);
		this.selenium = liste.get(17);
		Potassium = liste.get(18);
		this.calcium = liste.get(19);
		Phosphorus = liste.get(20);
		Magnesium = liste.get(21);
		this.vitamine_E = liste.get(22);
		this.copper = liste.get(23);
		this.vitamine_B5 = liste.get(24);
		this.vitamine_B6 = liste.get(25);
		this.zinc = liste.get(26);
		this.vitamine_A = liste.get(27);
		this.vitamine_B12=liste.get(28);
		this.vitamine_D=liste.get(29);
		}
	
	
	public Nutriments() {
		super();
	}


	public Nutriments(String calories, String fat, String satured_fat, String carbohydrates, String sugar,
			String cholesterol, String sodium, String protein, String vitamine_K, String manganese, String vitamine_B1, String fiber, 
			String folate, String iron, String vitamine_B3, String vitamine_B2, String vitamine_C, String selenium, String potassium, String calcium,
			String phosphorus,String magnesium, String vitamine_E, String copper, String vitamine_B5, String vitamin_B6, String zinc,
			  String vitamine_A, String vitamine_B12, String vitamine_D) {
		super();
		this.calories = calories;
		this.fat = fat;
		this.satured_fat = satured_fat;
		this.carbohydrates = carbohydrates;
		this.sugar = sugar;
		Cholesterol = cholesterol;
		this.sodium = sodium;
		
		this.protein=protein;
		this.vitamine_K = vitamine_K;
		this.manganese = manganese;
		this.vitamine_B1 = vitamine_B1;
		Fiber = fiber;
		this.folate=folate;
		Iron = iron;
		this.vitamine_B3 = vitamine_B3;
		this.vitamine_B2 = vitamine_B2;
		this.vitamine_C = vitamine_C;
		this.selenium = selenium;
		Potassium = potassium;
		this.calcium = calcium;
		Phosphorus = phosphorus;
		Magnesium = magnesium;
		this.vitamine_E = vitamine_E;
		this.copper = copper;
		this.vitamine_B5 = vitamine_B5;
		this.vitamine_B6 = vitamin_B6;
		this.zinc = zinc;
		this.vitamine_A = vitamine_A;
		this.vitamine_B12=vitamine_B12;
		this.vitamine_D=vitamine_D;
	}
	
	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getFat() {
		return fat;
	}
	public void setFat(String fat) {
		this.fat = fat;
	}
	public String getSatured_fat() {
		return satured_fat;
	}
	public void setSatured_fat(String satured_fat) {
		this.satured_fat = satured_fat;
	}
	public String getCarbohydrates() {
		return carbohydrates;
	}
	public void setCarbohydrates(String carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public String getCholesterol() {
		return Cholesterol;
	}
	public void setCholesterol(String cholesterol) {
		Cholesterol = cholesterol;
	}
	public String getSodium() {
		return sodium;
	}
	public void setSodium(String sodium) {
		this.sodium = sodium;
	}
	public String getProtein() {
		return protein;
	}
	public void setProtein(String protein) {
		this.protein = protein;
	}
	public String getManganese() {
		return manganese;
	}
	public void setManganese(String manganese) {
		this.manganese = manganese;
	}
	public String getSelenium() {
		return selenium;
	}
	public void setSelenium(String selenium) {
		this.selenium = selenium;
	}
	public String getFiber() {
		return Fiber;
	}
	public void setFiber(String fiber) {
		Fiber = fiber;
	}
	public String getMagnesium() {
		return Magnesium;
	}
	public void setMagnesium(String magnesium) {
		Magnesium = magnesium;
	}
	public String getPhosphorus() {
		return Phosphorus;
	}
	public void setPhosphorus(String phosphorus) {
		Phosphorus = phosphorus;
	}
	public String getVitamine_B1() {
		return vitamine_B1;
	}
	public void setVitamine_B1(String vitamine_B1) {
		this.vitamine_B1 = vitamine_B1;
	}
	public String getVitamine_E() {
		return vitamine_E;
	}
	public void setVitamine_E(String vitamine_E) {
		this.vitamine_E = vitamine_E;
	}
	public String getPotassium() {
		return Potassium;
	}
	public void setPotassium(String potassium) {
		Potassium = potassium;
	}
	public String getIron() {
		return Iron;
	}
	public void setIron(String iron) {
		Iron = iron;
	}
	public String getVitamine_B6() {
		return vitamine_B6;
	}
	public void setVitamine_B6(String vitamin_B6) {
		this.vitamine_B6 = vitamin_B6;
	}
	public String getZinc() {
		return zinc;
	}
	public void setZinc(String zinc) {
		this.zinc = zinc;
	}
	public String getFolate() {
		return folate;
	}
	public void setFolate(String folate) {
		this.folate = folate;
	}
	public String getVitamine_K() {
		return vitamine_K;
	}
	public void setVitamine_K(String vitamine_K) {
		this.vitamine_K = vitamine_K;
	}
	public String getVitamine_B2() {
		return vitamine_B2;
	}
	public void setVitamine_B2(String vitamine_B2) {
		this.vitamine_B2 = vitamine_B2;
	}
	public String getCalcium() {
		return calcium;
	}
	public void setCalcium(String calcium) {
		this.calcium = calcium;
	}
	public String getVitamine_B3() {
		return vitamine_B3;
	}
	public void setVitamine_B3(String vitamine_B3) {
		this.vitamine_B3 = vitamine_B3;
	}
	public String getVitamine_B5() {
		return vitamine_B5;
	}
	public void setVitamine_B5(String vitamine_B5) {
		this.vitamine_B5 = vitamine_B5;
	}
	public String getVitamine_A() {
		return vitamine_A;
	}
	public void setVitamine_A(String vitamine_A) {
		this.vitamine_A = vitamine_A;
	}
	public String getVitamine_C() {
		return vitamine_C;
	}

	public void setVitamine_C(String vitamine_C) {
		this.vitamine_C = vitamine_C;
	}

	public String getCopper() {
		return copper;
	}
	public void setCopper(String copper) {
		this.copper = copper;
	}
	
	
	
	public String getVitamine_B12() {
		return vitamine_B12;
	}


	public void setVitamine_B12(String vitamine_B12) {
		this.vitamine_B12 = vitamine_B12;
	}


	public String getVitamine_D() {
		return vitamine_D;
	}


	public void setVitamine_D(String vitamine_D) {
		this.vitamine_D = vitamine_D;
	}


	public SimpleStringProperty  getPropertyCalories() {
    	SimpleStringProperty expirationProperty = new SimpleStringProperty((String)this.calories);
        return expirationProperty;
    }
    public SimpleStringProperty  getPropertyCarbs() {
    	SimpleStringProperty expirationProperty = new SimpleStringProperty((String)this.carbohydrates);
        return expirationProperty;
    }
    public SimpleStringProperty  getPropertyFat() {
    	SimpleStringProperty expirationProperty = new SimpleStringProperty((String)this.fat);
        return expirationProperty;
    }
    public SimpleStringProperty  getPropertyProtein() {
    	SimpleStringProperty expirationProperty = new SimpleStringProperty((String)this.protein);
        return expirationProperty;
    }



}
