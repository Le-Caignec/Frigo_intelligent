package fr.tse.thextremdevs.smartfridge_app.database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.tse.thextremdevs.smartfridge_app.recipes.Recipe;

public class BDD_favoris {
	/**
	 * Créé une nouvelle table dans la base de données.
	 * 
	 * @param fileName nom de la table à créer
	 * @return
	 */
	public static void createNewTable(String fileName) {
		// SQLite connection string
		String url = "jdbc:sqlite:BDD_favoris.db";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (\n" + "	id_recette integer PRIMARY KEY,\n"
				+ "	nom_recette text NOT NULL,\n" + "	fiche_recette text\n" + ");";

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
		String url = "jdbc:sqlite:BDD_favoris.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Permet de récupérer le contenu de la table dans une liste.
	 * 
	 * @param fileName nom de la table à récupérer
	 * @return
	 */
	public static List<Recipe> selectAll(String fileName) {
		String sql = "SELECT id_recette, nom_recette, fiche_recette FROM " + fileName;

		List<Recipe> list_recettes = new ArrayList<>();
		try (Connection conn = BDD_favoris.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				Recipe recipe = new Recipe(rs.getInt("id_recette"), rs.getString("nom_recette"));
				list_recettes.add(recipe);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return list_recettes;
	}

	/**
	 * Permet d'insérer un élément dans la table indiquée.
	 * 
	 * @param fileName      nom de la table à modifier.
	 * @param id_recette    id de la recette favoris à insérer.
	 * @param nom_recette   nom de la recette favoris à insérer.
	 * @param fiche_recette fiche recette de la recette favoris à insérer.
	 * @return
	 */
	public static void insert(int id_recette, String nom_recette, String fiche_recette, String fileName) {
		String sql = "INSERT INTO " + fileName + " (id_recette, nom_recette, fiche_recette) VALUES(?,?,?)";
		Connection conn1 = connect();
		try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id_recette);
			pstmt.setString(2, nom_recette);
			pstmt.setString(3, fiche_recette);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet de supprimer un élement de la table
	 * 
	 * @param table_name nom de la table à modifier.
	 * @param nom        nom de l'élément à supprimer.
	 * @return
	 */
	public static void delete_element(String table_name, String nom) {

		String sql = "DELETE FROM " + table_name + " WHERE nom_recette = ?";

		Connection conn1 = connect();
		try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nom);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
