package fr.tse.thextremdevs.smartfridge_app.fridge;


import java.util.List;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class Aliment{
	private String foodId;
	private String nom;
    private Double ernerc_kcal;
    private Double procnt;
    private Double fat;
    private Double chocdf;
    private Double fibtg;
    private String category;
    private String image;
    private String peremption;
    private Double quantite;
    private String unite;
    private Boolean remark;    

    
    public Aliment(String foodId, String nom, Double ernerc_kcal, Double procnt, Double fat, Double chocdf,
			Double fibtg, String category, String image, String peremption, Double quantite, String unite,Boolean remark) {
		super();
		this.foodId = foodId;
		this.nom = nom;
		this.ernerc_kcal = ernerc_kcal;
		this.procnt = procnt;
		this.fat = fat;
		this.chocdf = chocdf;
		this.fibtg = fibtg;
		this.category = category;
		this.image = image;
		this.peremption = peremption;
		this.quantite = quantite;
		this.unite = unite;
        this.remark = remark;         
	}



    public Aliment() {
		// TODO Auto-generated constructor stub
	}


	public static List<Aliment> triAlphabetique (List<Aliment> monfrigo){

        int size=monfrigo.size();
        
//      Collections.sort(monFrigo);  
        for (int i=0; i < size; i++) 
        {
            for (int j=i+1; j < size; j++) 
            {
            	
                if ((monfrigo.get(i).getNom()).compareTo(monfrigo.get(j).getNom()) > 0) 
                {
                    String tmp = monfrigo.get(i).getNom();
                    
                    monfrigo.get(i).setNom((monfrigo.get(j)).getNom());
                    monfrigo.get(j).setNom(tmp);
                }
            }
        }
        return monfrigo;
     }
	

    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public Double getErnerc_kcal() {
		return ernerc_kcal;
	}

	public void setErnerc_kcal(Double ernerc_kcal) {
		this.ernerc_kcal = ernerc_kcal;
	}

	public Double getProcnt() {
		return procnt;
	}

	public void setProcnt(Double procnt) {
		this.procnt = procnt;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public Double getChocdf() {
		return chocdf;
	}

	public void setChocdf(Double chocdf) {
		this.chocdf = chocdf;
	}

	public Double getFibtg() {
		return fibtg;
	}

	public void setFibtg(Double fibtg) {
		this.fibtg = fibtg;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public String getPeremption() {
		return peremption;
	}


	public void setPeremption(String peremption) {
		this.peremption = peremption;
	}


	public Double getQuantite() {
		return quantite;
	}


	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}



	public String getUnite() {
		return unite;
	}



	public void setUnite(String unite) {
		this.unite = unite;
	}
	
    public boolean getRemark() {
        return remark;
    }
 
    public void setRemark(Boolean newValue) {
        this.remark = newValue;
    }
	
	
    public SimpleStringProperty  getPropertyNom() {
    	SimpleStringProperty nomProperty = new SimpleStringProperty((String)this.nom);
        return nomProperty;
    } 
    
    public SimpleDoubleProperty  getPropertyQuantity() {
    	SimpleDoubleProperty quantityProperty = new SimpleDoubleProperty((Double)this.quantite);
        return quantityProperty;
    } 
    
    public SimpleStringProperty  getPropertyUnit() {
    	SimpleStringProperty unitProperty = new SimpleStringProperty((String)this.unite);
        return unitProperty;
    } 
    
    public SimpleStringProperty  getPropertyExpiration() {
    	SimpleStringProperty expirationProperty = new SimpleStringProperty((String)this.peremption);
        return expirationProperty;
    }

    
    public LocalDate getPeremptionDate() throws ParseException {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    	Date expirationDate= format.parse(peremption);
    	ZoneId defaultZoneId = ZoneId.systemDefault();
    	LocalDate expirationLocalDate =expirationDate.toInstant().atZone(defaultZoneId).toLocalDate();
    	return expirationLocalDate;
    }


   
}
