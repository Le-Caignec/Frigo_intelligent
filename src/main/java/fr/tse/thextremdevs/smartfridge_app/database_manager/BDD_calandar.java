package fr.tse.thextremdevs.smartfridge_app.database_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BDD_calandar {
		
		public String date;
		public String calories;
		public String fat;
		public String sugar;
		public String carbohydrates;
		public String magnesium;
		public String cholesterol;
		public String satured_fat;
		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
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

		public String getSugar() {
			return sugar;
		}

		public void setSugar(String sugar) {
			this.sugar = sugar;
		}

		public String getCarbohydrates() {
			return carbohydrates;
		}

		public void setCarbohydrates(String carbohydrates) {
			this.carbohydrates = carbohydrates;
		}

		public String getMagnesium() {
			return magnesium;
		}

		public void setMagnesium(String magnesium) {
			this.magnesium = magnesium;
		}

		public String getCholesterol() {
			return cholesterol;
		}

		public void setCholesterol(String cholesterol) {
			this.cholesterol = cholesterol;
		}

		public String getSatured_fat() {
			return satured_fat;
		}

		public void setSatured_fat(String satured_fat) {
			this.satured_fat = satured_fat;
		}

		public BDD_calandar(String date, String calories, String fat, String sugar, String carbohydrates, String magnesium,
				String cholesterol, String satured_fat) {
			super();
			this.date = date;
			this.calories = calories;
			this.fat = fat;
			this.sugar = sugar;
			this.carbohydrates = carbohydrates;
			this.magnesium = magnesium;
			this.cholesterol = cholesterol;
			this.satured_fat = satured_fat;
		}

	public BDD_calandar() {
			// TODO Auto-generated constructor stub
		}

	/**
	 * Créé une nouvelle table dans la base de données.
	 * 
	 * @param fileName nom de la table à créer
	 * @return
	 */
	public static void createNewTable(String fileName) {
        // SQLite connection string
        String url = "jdbc:sqlite:BDD_calandar.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+ fileName +" (\n"
                + "	date PRIMARY KEY,\n"
                + "	calories text,\n"
                + "	fat text,\n"
                + "	sugar text,\n"
                + "	carbohydrates text,\n"
                + "	magnesium text,\n"
                + "	cholesterol text,\n"
                + "	satured_fat text\n"
                + ");";
     
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
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
		public static Connection connect() {
		        // SQLite connection string
		        String url = "jdbc:sqlite:BDD_calandar.db";
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
	 	public static List<BDD_calandar> selectAll(String fileName){
	        String sql = "SELECT date, calories, fat, sugar, carbohydrates, magnesium, cholesterol, satured_fat FROM " + fileName;
        	List<BDD_calandar> calandar_list = new ArrayList<>();

	        try (Connection conn = BDD_calandar.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	            while (rs.next()) {
	            	BDD_calandar calandar =new BDD_calandar(rs.getString("date"), rs.getString("calories"), rs.getString("fat"), rs.getString("sugar"), rs.getString("carbohydrates"), rs.getString("magnesium"),
	            			rs.getString("cholesterol"), rs.getString("satured_fat"));
	            	calandar_list.add(calandar);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return calandar_list;
	    }
	 
	 	/**
		 * Permet d'insérer un élément dans la table indiquée.
		 * 
		 * @param fileName      nom de la table à modifier.
		 * @param calandar    prend en argument un objet calandar pour l'insérer ensuite dans la table.
		 * @return
		 */
	   public static void insert(BDD_calandar calandar, String fileName) throws ParseException {
	        String sql = "INSERT INTO " + fileName + " (date,calories,fat,sugar,carbohydrates,magnesium,cholesterol,satured_fat) VALUES(?,?,?,?,?,?,?,?)";
	        Connection conn1 = connect();
	        try (Connection conn = conn1;
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	String date=calandar.getDate();
	        	String calories=calandar.getCalories();
	        	String fat=calandar.getFat();
	        	String sugar=calandar.getSugar();
	        	String carbohydrates=calandar.getCarbohydrates();
	        	String magnesium=calandar.getMagnesium();
	        	String cholesterol=calandar.getCholesterol();
	        	String satured_fat=calandar.getSatured_fat();
	        	
	        	List<BDD_calandar> my_list=BDD_calandar.selectAll("calandar_nutrionnal");
	        	if (my_list.size()>0) {
		    		String dernière_date_enregiste=my_list.get(my_list.size()-1).getDate();
		    		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd" );
		    		Date date2 = formatter.parse(dernière_date_enregiste);
		            Date date1 = formatter.parse(date);
		            
		    		if (date1.compareTo(date2) > 0) {
			        	pstmt.setString(1, date);
			            pstmt.setString(2, calories);
			            pstmt.setString(3, fat);
			            pstmt.setString(4, sugar);
			            pstmt.setString(5, carbohydrates);
			            pstmt.setString(6, magnesium);
			            pstmt.setString(7, cholesterol);
			            pstmt.setString(8, satured_fat);
			            pstmt.executeUpdate();
		    		}else {
		    			System.out.println("test insert");
		    			modif_dernier_ligne_ajout("calandar_nutrionnal",calandar);
		    		}
	        	}else {
	        		pstmt.setString(1, date);
		            pstmt.setString(2, calories);
		            pstmt.setString(3, fat);
		            pstmt.setString(4, sugar);
		            pstmt.setString(5, carbohydrates);
		            pstmt.setString(6, magnesium);
		            pstmt.setString(7, cholesterol);
		            pstmt.setString(8, satured_fat);
		            pstmt.executeUpdate();
	        	}
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	   /**
		 * Permet de générer une date au format yyyy.MM.dd
		 * 
		 * @param 
		 * @return dateString retourne une date au format String de la forme "yyyy.MM.dd" 
		 */
	   public static String ma_date() {
		   SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd" );
		   Date currentTime_1 = new Date();
		   String dateString = formatter.format(currentTime_1);
		   return dateString;
	   }
	   
	   	/**
		 * Permet de supprimer un élement de la table
		 * 
		 * @param table_name nom de la table à modifier.
		 * @param calandar  Objet du type calandar .
		 * @return
		 */
	   public static void delete_element(String table_name,BDD_calandar calandar) {

			String sql = "DELETE FROM " + table_name + " WHERE date = ?";

			Connection conn1 = connect();
			try (Connection conn = conn1; PreparedStatement pstmt = conn.prepareStatement(sql)) {

				String foodId = calandar.getDate();
				pstmt.setString(1, foodId); 
				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	   
	   /**
		 * Permet de sommer à la derniére ligne de la table les nouveaux nutriments que 
		 * l'utilisateur vient de consommer en mangeant sa recette. 
		 * 
		 * @param table_name nom de la table à modifier.
		 * @param calandar  Objet du type calandar .
		 * @return
		 */
	   public static void modif_dernier_ligne_ajout(String table_name,BDD_calandar calandar) throws ParseException{
		   
	
			List<BDD_calandar> list=selectAll( table_name);
			
			BDD_calandar calandar_tab = list.get(list.size()-1);
			
			String calories_BDD=calandar_tab.getCalories().replaceAll("[^\\d.]", "");
			String fat_BDD=calandar_tab.getFat().replaceAll("[^\\d.]", "");
			String sugar_BDD=calandar_tab.getSugar().replaceAll("[^\\d.]", "");
			String carbohydrates_BDD=calandar_tab.getCarbohydrates().replaceAll("[^\\d.]", "");
			String magnesium_BDD=calandar_tab.getMagnesium().replaceAll("[^\\d.]", "");
			String cholesterol_BDD=calandar_tab.getCholesterol().replaceAll("[^\\d.]", "");
			String satured_fat_BDD=calandar_tab.getSatured_fat().replaceAll("[^\\d.]", "");
			
			delete_element( table_name, calandar);
			BDD_calandar NEW_calandar=new BDD_calandar();
			
			NEW_calandar.setDate(calandar.getDate());
			NEW_calandar.setCalories(Double.toString(Double.parseDouble(calandar.getCalories().replaceAll("[^\\d.]", ""))+Double.parseDouble(calories_BDD))); 
			NEW_calandar.setFat(Double.toString(Double.parseDouble(calandar.getFat().replaceAll("[^\\d.]", ""))+Double.parseDouble(fat_BDD))+"g"); 
			NEW_calandar.setSugar(Double.toString(Double.parseDouble(calandar.getSugar().replaceAll("[^\\d.]", ""))+Double.parseDouble(sugar_BDD))+"g"); 
			NEW_calandar.setCarbohydrates(Double.toString(Double.parseDouble(calandar.getCarbohydrates().replaceAll("[^\\d.]", ""))+Double.parseDouble(carbohydrates_BDD))+"g"); 
			NEW_calandar.setMagnesium(Double.toString(Double.parseDouble(calandar.getMagnesium().replaceAll("[^\\d.]", ""))+Double.parseDouble(magnesium_BDD))+"mg"); 
			NEW_calandar.setCholesterol(Double.toString(Double.parseDouble(calandar.getCholesterol().replaceAll("[^\\d.]", ""))+Double.parseDouble(cholesterol_BDD))+"mg"); 
			NEW_calandar.setSatured_fat(Double.toString(Double.parseDouble(calandar.getSatured_fat().replaceAll("[^\\d.]", ""))+Double.parseDouble(satured_fat_BDD))+"g"); 
			
			insert( NEW_calandar,  table_name);
	   }
	   
		
}
