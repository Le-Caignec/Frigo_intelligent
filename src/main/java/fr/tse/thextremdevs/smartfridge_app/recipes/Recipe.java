package fr.tse.thextremdevs.smartfridge_app.recipes;

public class Recipe {
	private int Id;
	private String Nom;
	
	public Recipe(int id, String nom) {
		super();
		Id = id;
		Nom = nom;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
}
