package fr.tse.thextremdevs.smartfridge_app.database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.view_manager.HomeController;

import java.sql.ResultSet;

public class BDD_frigo {
	/**
	 * Créé une nouvelle table dans la base de données.
	 * 
	 * @param fileName nom de la table à créer
	 * @return
	 */
	public static void createNewTable(String fileName) {
		// SQLite connection string
		String url = "jdbc:sqlite:BDD_frigo.db";
		List<String> Nomtable = Nom_tables_BDD();
		String taille = String.valueOf(Nomtable.size() + 1);
		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + "_" + taille + "_" + fileName + " (\n"
				+ "	foodId text PRIMARY KEY,\n" + "	nom text,\n" + "	unite text,\n" + "	quantite real,\n"
				+ "	ernerc_kcal real,\n" + "	procnt real,\n" + "	fat real,\n" + "	chocdf real,\n"
				+ "	fibtg real,\n" + "	category text,\n" + "	peremption text,\n" + " image text\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet de se connecter à la base de données.
	 * 
	 * @param
	 * @return
	 */
	private static Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:BDD_frigo.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Permet d'insérer un élément dans la table indiquée.
	 * 
	 * @param fileName nom de la table à modifier.
	 * @param aliment  objet à insérer dans la table.
	 * @return
	 */
	public static void insert(Aliment aliment, String fileName) {
		String foodId = genFoodId(afficher_table(fileName), aliment);
		String nom = aliment.getNom();
		String unite = aliment.getUnite();
		Double quantite = aliment.getQuantite();
		Double ernerc_kcal = aliment.getErnerc_kcal();
		Double procnt = aliment.getProcnt();
		Double fat = aliment.getFat();
		Double chocdf = aliment.getChocdf();
		Double fibtg = aliment.getFibtg();
		String category = aliment.getCategory();
		String peremption = aliment.getPeremption();
		String image = aliment.getImage();
		String sql = "INSERT INTO " + fileName
				+ " (foodId, nom, unite,quantite, ernerc_kcal, procnt, fat, chocdf, fibtg, category, peremption, image) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		Connection conn1 = connect();
		try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, foodId);
			pstmt.setString(2, nom);
			pstmt.setString(3, unite);
			pstmt.setDouble(4, quantite);
			pstmt.setDouble(5, ernerc_kcal);
			pstmt.setDouble(6, procnt);
			pstmt.setDouble(7, fat);
			pstmt.setDouble(8, chocdf);
			pstmt.setDouble(9, fibtg);
			pstmt.setString(10, category);
			pstmt.setString(11, peremption);
			pstmt.setString(12, image);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet de récupérer le contenu de la table dans une liste.
	 * 
	 * @param table_name nom de la table à récupérer
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<Aliment> afficher_table(String table_name) {
		String sql = "SELECT foodId, nom, unite, quantite, ernerc_kcal, procnt, fat, chocdf, fibtg, category, peremption, image FROM "
				+ table_name;
		List<Aliment> list_aliment = new ArrayList<>();

		try (Connection conn = BDD_frigo.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Aliment aliments = new Aliment(rs.getString("foodId"), rs.getString("nom").toLowerCase(),
						rs.getDouble("ernerc_kcal"), rs.getDouble("procnt"), rs.getDouble("fat"),
						rs.getDouble("chocdf"), rs.getDouble("fibtg"), rs.getString("category"), rs.getString("image"),
						rs.getString("peremption"), rs.getDouble("quantite"), rs.getString("unite"), false);
				list_aliment.add(aliments);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return list_aliment;
	}

	/**
	 * Permet de récupérer le nom des aliments de la table dans une liste.
	 * 
	 * @param table_name nom de la table contenant les aliments à récupérer
	 * @return
	 */

	public static List<String> nom_aliments_frigo(String table_name) {
		List<Aliment> list_aliment = afficher_table(table_name);
		List<String> nom_aliments = new ArrayList<>();

		for (int k = 0; k < list_aliment.size(); k++) {
			String nom = list_aliment.get(k).getNom();
			nom = nom.toLowerCase();
			nom_aliments.add(nom);
		}
		return nom_aliments;
	}

	/**
	 * Permet de supprimer un élement de la table
	 * 
	 * @param table_name nom de la table à modifier.
	 * @param aliment  nom de l'élément à supprimer.
	 * @return
	 */
	public static void delete_element(String table_name, Aliment aliment) {

		String sql = "DELETE FROM " + table_name + " WHERE foodId = ?";

		Connection conn1 = connect();
		try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {

			String foodId = aliment.getFoodId();
			pstmt.setString(1, foodId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet de supprimer une table
	 * 
	 * @param table_name nom de la table à supprimer
	 * @return
	 */
	public static void delete_table(String table_name) {
		String sql = "DROP TABLE " + table_name;
		try (Connection conn = BDD_frigo.connect()) {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet de récupérer le nom des tables dans une liste
	 * 
	 * @param
	 * @return
	 */
	public static List<String> Nom_tables_BDD() {
		List<String> nom_tables = new ArrayList<>();
		try (Connection conn = BDD_frigo.connect()) {
			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
			while (rs.next()) {
				nom_tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nom_tables;
	}

	/**
	 * Permet de récupérer le nom de la table sélectionnée ou d'initialiser la base
	 * de données en créant une table prédéterminée.
	 * 
	 * @return
	 */
	public static String Table_actuelle() {
		List<String> nom_tables = Nom_tables_BDD();
		if (HomeController.nouvelle_table_selectionnee != null) {
			return HomeController.nouvelle_table_selectionnee;

		} else if (nom_tables.size() != 0) {
			String table_actuelle = nom_tables.get(nom_tables.size() - 1);
			return table_actuelle;

		} else {
			createNewTable("Mon_frigo");
			return "_1_Mon_frigo";
		}
	}

	/**
	 * Permet générer un id unique pour chaque élément à insérer dans la table.
	 * 
	 * @param bdd     liste des aliments présents dans la table
	 * @param aliment aliment pour lequel on génère un id
	 * @return
	 */
	public static String genFoodId(List<Aliment> bdd, Aliment aliment) {
		int i = -1;
		String uniqueID = UUID.randomUUID().toString();
		while (i < bdd.size() - 1) {
			i = i + 1;
			System.out.println("1: " + i);
			while (bdd.get(i).getFoodId() == uniqueID) {
				uniqueID = UUID.randomUUID().toString();
				i = -1;

			}
		}
		return uniqueID;
	}

}
