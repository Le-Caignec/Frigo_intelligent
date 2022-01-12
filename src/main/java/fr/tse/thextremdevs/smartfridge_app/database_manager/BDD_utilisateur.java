package fr.tse.thextremdevs.smartfridge_app.database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.tse.thextremdevs.smartfridge_app.recipes.Nutriments;

public class BDD_utilisateur {
	/**
	 * Créé une nouvelle table dans la base de données.
	 * 
	 * @param fileName nom de la table à créer
	 * @return
	 */
	public static void createNewTable(String fileName) {
		// SQLite connection string
		String url = "jdbc:sqlite:BDD_utilisateur.db";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + fileName + " (calories text,\n" + " fat text,\n"
				+ "	satured_fat text,\n" + " carbohydrates text,\n" + " sugar text,\n" + " cholesterol text,\n"
				+ "	sodium text,\n" + "	protein text,\n" + " vitamine_K text,\n" + " manganese text,\n"
				+ " vitamine_B1 text,\n" + " fiber text,\n" + " folate text,\n" + " iron text,\n"
				+ " vitamine_B3 text,\n" + " vitamine_B2 text,\n" + " vitamine_C text,\n" + " selenium text,\n"
				+ "	potassium text,\n" + " calcium text,\n" + " phosphorus text,\n" + "	magnesium text,\n"
				+ "	vitamine_E text,\n" + " copper text,\n" + " vitamine_B5 text,\n" + "	vitamine_B6 text,\n"
				+ " zinc text,\n" + "	vitamine_A text,\n" + "	vitamine_B12 text,\n" + " vitamine_D text\n" + ");";

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
	static Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:BDD_utilisateur.db";
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
	 * @param fileName   nom de la table à modifier.
	 * @param nutriments objet à insérer dans la table.
	 * @return
	 */
	public static void insert(Nutriments nutriments, String fileName) {
		String calories = nutriments.getCalories();
		String fat = nutriments.getFat();
		String satured_fat = nutriments.getSatured_fat();
		String carbohydrates = nutriments.getCarbohydrates();
		String sugar = nutriments.getSugar();
		String cholesterol = nutriments.getCholesterol();
		String sodium = nutriments.getSodium();
		String protein = nutriments.getProtein();
		String manganese = nutriments.getManganese();
		String selenium = nutriments.getSelenium();
		String fiber = nutriments.getFiber();
		String magnesium = nutriments.getMagnesium();
		String phosphorus = nutriments.getPhosphorus();
		String vitamine_B1 = nutriments.getVitamine_B1();
		String vitamine_E = nutriments.getVitamine_E();
		String potassium = nutriments.getPotassium();
		String iron = nutriments.getIron();
		String vitamine_B6 = nutriments.getVitamine_B6();
		String zinc = nutriments.getZinc();
		String folate = nutriments.getFolate();
		String vitamine_K = nutriments.getVitamine_K();
		String vitamine_B2 = nutriments.getVitamine_B2();
		String calcium = nutriments.getCalcium();
		String vitamine_B3 = nutriments.getVitamine_B3();
		String vitamine_B5 = nutriments.getVitamine_B5();
		String vitamine_A = nutriments.getVitamine_A();
		String vitamine_C = nutriments.getVitamine_C();
		String vitamine_B12 = nutriments.getVitamine_B12();
		String vitamine_D = nutriments.getVitamine_D();
		String copper = nutriments.getCopper();
		String sql = "INSERT INTO " + fileName + " (calories, fat, satured_fat, carbohydrates, sugar,\r\n"
				+ "	cholesterol, sodium, protein, vitamine_K, manganese, vitamine_B1, fiber, folate, iron, vitamine_B3, vitamine_B2, vitamine_C, selenium,\r\n"
				+ "	potassium, calcium, phosphorus, magnesium, vitamine_E, copper, vitamine_B5, vitamine_B6, zinc, vitamine_A, vitamine_B12, vitamine_D) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn1 = connect();
		try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, calories);
			pstmt.setString(2, fat);
			pstmt.setString(3, satured_fat);
			pstmt.setString(4, carbohydrates);
			pstmt.setString(5, sugar);
			pstmt.setString(6, cholesterol);
			pstmt.setString(7, sodium);
			pstmt.setString(8, protein);
			pstmt.setString(9, vitamine_K);
			pstmt.setString(10, manganese);
			pstmt.setString(11, vitamine_B1);
			pstmt.setString(12, fiber);
			pstmt.setString(13, folate);
			pstmt.setString(14, iron);
			pstmt.setString(15, vitamine_B3);
			pstmt.setString(16, vitamine_B2);
			pstmt.setString(17, vitamine_C);
			pstmt.setString(18, selenium);
			pstmt.setString(19, potassium);
			pstmt.setString(20, calcium);
			pstmt.setString(21, phosphorus);
			pstmt.setString(22, magnesium);
			pstmt.setString(23, vitamine_E);
			pstmt.setString(24, copper);
			pstmt.setString(25, vitamine_B5);
			pstmt.setString(26, vitamine_B6);
			pstmt.setString(27, zinc);
			pstmt.setString(28, vitamine_A);
			pstmt.setString(29, vitamine_B12);
			pstmt.setString(30, vitamine_D);
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
	public static List<Nutriments> afficher_table(String table_name) {
		String sql = "SELECT calories, fat, satured_fat, carbohydrates, sugar, cholesterol, sodium, protein, vitamine_K, manganese, vitamine_B1, fiber, folate, iron, vitamine_B3, vitamine_B2, vitamine_C, selenium, potassium, calcium, phosphorus, magnesium, vitamine_E, copper, vitamine_B5, vitamine_B6, zinc, vitamine_A, vitamine_B12, vitamine_D FROM "
				+ table_name;
		List<Nutriments> list_nutriments = new ArrayList<>();

		try (Connection conn = BDD_utilisateur.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Nutriments nutriments = new Nutriments(rs.getString("calories"), rs.getString("fat"),
						rs.getString("satured_fat"), rs.getString("carbohydrates"), rs.getString("sugar"),
						rs.getString("cholesterol"), rs.getString("sodium"), rs.getString("protein"),
						rs.getString("vitamine_K"), rs.getString("manganese"), rs.getString("vitamine_B1"),
						rs.getString("fiber"), rs.getString("folate"), rs.getString("iron"),
						rs.getString("vitamine_B3"), rs.getString("vitamine_B2"), rs.getString("vitamine_C"),
						rs.getString("selenium"), rs.getString("potassium"), rs.getString("calcium"),
						rs.getString("phosphorus"), rs.getString("magnesium"), rs.getString("vitamine_E"),
						rs.getString("copper"), rs.getString("vitamine_B5"), rs.getString("vitamine_B6"),
						rs.getString("zinc"), rs.getString("vitamine_A"), rs.getString("vitamine_B12"),
						rs.getString("vitamine_D"));
				list_nutriments.add(nutriments);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return list_nutriments;
	}

	/**
	 * Permet de supprimer une table
	 * 
	 * @param table_name nom de la table à supprimer
	 * @return
	 */
	public static void delete_table(String table_name) {
		String sql = "DROP TABLE " + table_name;
		try (Connection conn = BDD_utilisateur.connect()) {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permet d'enregistrer dans la base de données le formulaire de nutriments.
	 * 
	 * @param table_name nom de la table à sauvegarder
	 * @param nutriments nutriments à sauvegarder
	 * @return
	 */
	public static void save(String table_name, Nutriments nutriments) {
		delete_table(table_name);
		createNewTable(table_name);
		insert(nutriments, table_name);
	}

}
