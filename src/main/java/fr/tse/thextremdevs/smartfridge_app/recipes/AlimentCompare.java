package fr.tse.thextremdevs.smartfridge_app.recipes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class AlimentCompare {
	private String nom;
	private Double quantiteNeeded;
	private Double quantiteOwn;
    private String uniteNeeded;
    private String uniteOwn;
    
    
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Double getQuantiteNeeded() {
		return quantiteNeeded;
	}
	public void setQuantiteNeeded(Double quantiteNeeded) {
		this.quantiteNeeded = quantiteNeeded;
	}
	public Double getQuantiteOwn() {
		return quantiteOwn;
	}
	public void setQuantiteOwn(Double quantiteOwn) {
		this.quantiteOwn = quantiteOwn;
	}
	public String getUniteNeeded() {
		return uniteNeeded;
	}
	public void setUniteNeeded(String uniteNeeded) {
		this.uniteNeeded = uniteNeeded;
	}
	public String getUniteOwn() {
		return uniteOwn;
	}
	public void setUniteOwn(String uniteOwn) {
		this.uniteOwn = uniteOwn;
	}
	
	public AlimentCompare(String nom, Double quantiteNeeded, String uniteNeeded, Double quantiteOwn, String uniteOwn) {
		super();
		this.nom = nom;
		this.quantiteNeeded = quantiteNeeded;
		this.quantiteOwn = quantiteOwn;
		this.uniteNeeded = uniteNeeded;
		this.uniteOwn = uniteOwn;
	}
	
	public AlimentCompare() {
		super();
	}
    
	
    public SimpleStringProperty  getPropertyName() {
    	SimpleStringProperty stringProperty = new SimpleStringProperty((String)this.nom);
        return stringProperty;
    }
    public SimpleStringProperty  getPropertyUniteNeeded() {
    	SimpleStringProperty stringProperty = new SimpleStringProperty((String)this.uniteNeeded);
        return stringProperty;
    }
    public SimpleStringProperty  getPropertyUniteOwn() {
    	SimpleStringProperty stringProperty = new SimpleStringProperty((String)this.uniteOwn);
        return stringProperty;
    }
    
    public SimpleDoubleProperty  getPropertyQuantityNeeded() {
    	SimpleDoubleProperty quantityProperty = new SimpleDoubleProperty((Double)this.quantiteNeeded);
        return quantityProperty;
    } 
	
    public SimpleDoubleProperty  getPropertyQuantityOwn() {
    	SimpleDoubleProperty quantityProperty = new SimpleDoubleProperty((Double)this.quantiteOwn);
        return quantityProperty;
    } 
    
    public AlimentCourse  alimCourseFromAlimComp() {
    	AlimentCourse alimentCourse = new AlimentCourse(nom, quantiteNeeded, uniteNeeded);
    	return alimentCourse; 	
    } 
    
}
