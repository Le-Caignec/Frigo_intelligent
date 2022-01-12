package fr.tse.thextremdevs.smartfridge_app.recipes;

public class AlimentCourse {
	private String nom;
	private Double quantite;
    private String unite;
	public AlimentCourse(String nom, Double quantite, String unite) {
		super();
		this.nom = nom;
		this.quantite = quantite;
		this.unite = unite;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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

}
