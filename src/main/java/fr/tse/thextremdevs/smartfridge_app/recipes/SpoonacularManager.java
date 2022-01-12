package fr.tse.thextremdevs.smartfridge_app.recipes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.tse.thextremdevs.smartfridge_app.database_manager.BDD_frigo;
import fr.tse.thextremdevs.smartfridge_app.fridge.Aliment;
import fr.tse.thextremdevs.smartfridge_app.fridge.EdamamManager;

//voici la classe permettant de récupérer le fichier json provenant de l'api spoonacular

//-------------------------------------------------------------------
//recherche des recettes par ingrédient
public class SpoonacularManager {

	//"549b2080336e4275b15eaf7c5e196773";
	//2d671451020d4db0a3be0c4d098d7802
	//2cc51196909342c6beebcb02ef63401b
	//8b15a2eff87b4d108b38166d940ba1f9
	
	public static String spoonacular(String ingredient) {
		//String apiKey = "8b15a2eff87b4d108b38166d940ba1f9";
		String apiKey = "e8a8f66a94b24a16aa58d7b4832f80a4";
		 /**
		*  Retourne la requete afin de requêter l'api
		* @param  une liste d'ingrédient sous la forme ingr1,ingr2, etc..
		* @return une requête String
		*/
		String requestBuilderSpoonacular;
		requestBuilderSpoonacular = " https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredient
				+ "&number=3&apiKey=" + apiKey;
		// System.out.println(requestBuilderSpoonacular);
		return (requestBuilderSpoonacular);

	}

	// cette méthode renvoie une liste de string contenant toutes les informations
	// sur les recettes
	public static ArrayList<String> returnJsonToString(final String ingrSearch) throws IOException, JSONException {
		 /**
		*  Renvoie ce que la requête renvoie (donnée par l'api).
		* @param  String ingrédient sous la forme ingr1,ingr2, etc...
		* @return Renvoie une liste de string contenant toutes les infos sur les recettes
		*/
		final JSONArray json = readArrayJsonFromUrl(spoonacular(ingrSearch));
		ArrayList<String> list_string = new ArrayList<String>();
		for (int k = 0; k < json.length(); k++) {
			list_string.add((json.get(k)).toString());
		}
		json.toString();
		return list_string;
	}

	// cette liste renvoie une liste json contenant toutes les informations
	// concernant la recette recherhée
	public static JSONArray readArrayJsonFromUrl(String url) throws IOException, JSONException {
		 /**
		*  Renvoie une liste json contenant toutes les informations sur la recette recherchée
		* @param  un url, une requête à envoyer
		* @return Renvoie une liste json contenant toutes les informations sur la recette recherchée
		*/
		try (InputStream myurl = new URL(url).openStream()) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(myurl, Charset.forName("UTF-8")));
			String jsonText = EdamamManager.readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		}
	}

	// Méthode pour récupérer la liste des recettes
	public static ArrayList<String> recupTitres(ArrayList<String> liste_entiere) {
		/**
	    *  Récupère la liste des titres d'une recherche
	    * @param  ce que renvoie la requête api.
	    * @return la liste des titres d'une recherche
	    */
		ArrayList<String> liste_titre = new ArrayList<String>();
		String titre = "";
		for (int k = 0; k < (liste_entiere.size()); k++) {
			String kieme = (liste_entiere.get(k));
			int index = kieme.indexOf("title"); // on cherche où se trouve le titre de la recette

			if (index != -1) {
				int virgule = index + 1; // maintenant on cherche où se trouve la virgule pour ne récupérer que le titre
											// de la recette
				while (kieme.charAt(virgule) != ',') {
					virgule += 1;
				}
				titre = kieme.substring(index + 8, virgule - 1);

			}

			liste_titre.add(titre);
		}

		return liste_titre;
	}

	// méthode afin de récupérer les images des recettes:
	public static ArrayList<String> recupImagesRecettes(ArrayList<String> liste_entiere) {
		/**
	    *  Méthode qui permet de récupérer les images des recettes
	    * @param  toutes les infos d'une requête sur la recette
	    * @return liste contenant toutes les images
	    */
		ArrayList<String> liste_recettes = new ArrayList<String>();
		String titre = "";
		for (int k = 0; k < (liste_entiere.size()); k++) {
			String kieme = (liste_entiere.get(k));
			int index = kieme.indexOf("image"); // on cherche où se trouve le titre de la recette

			if (index != -1) {
				int virgule = index + 1; // maintenant on cherche où se trouve la virgule pour ne récupérer que le titre
											// de la recette
				while (kieme.charAt(virgule) != ',') {
					virgule += 1;
				}
				titre = kieme.substring(index + 7, virgule);

			}
			// System.out.println(titre);
			liste_recettes.add(titre);
		}
		return liste_recettes;
	}

//méthode afin de récupérer les ingrédients manquants
	public static ArrayList<String> recupMissedIngredients(String recette) {
		/**
		*  Renvoie les ingrédients manquants d'une recette
		* @param  une recette
		* @return la liste des aliments manquants
		*/
		ArrayList<String> liste_MissedIngredients = new ArrayList<String>();
		if (recette != "Aucun") {
			int indexMissed = recette.indexOf("missedIngredients"); // on cherche où se trouve les missed ingredeints
			if (indexMissed != -1) {
				recette = recette.substring(indexMissed + 17, recette.length()); // ici on obtient que la partie de
																					// missed
				// ingredients
				int indexName = recette.indexOf("name");
				while (indexName != -1) {
					int virgule = indexName + 1; // maintenant on cherche où se trouve la virgule pour ne récupérer que
													// le titre de la recette
					while (recette.charAt(virgule) != ',') {
						virgule += 1;
					}
					liste_MissedIngredients.add(recette.substring(indexName + 7, virgule - 1));
					// System.out.println(kieme.substring(indexName+6, virgule));
					recette = recette.substring(virgule, recette.length());
					indexName = recette.indexOf("name");
				}

			}
		} else {
			liste_MissedIngredients.add("Aucun");
		}

		return liste_MissedIngredients;

	}

	public static ArrayList<String> recupID(ArrayList<String> liste_entiere) {
		/**
		*  retourne les id des recettes
		* @param  liste des recettes
		* @return une liste contenant tous les id des recettes recherchées
		*/
		ArrayList<String> listeID = new ArrayList<String>();
		String id = "";
		for (int k = 0; k < (liste_entiere.size()); k++) {
			String kieme = (liste_entiere.get(k));
			int indexTitre = kieme.indexOf("title");

			while (kieme.charAt(indexTitre) != 'd') {
				indexTitre -= 1;
			}
			int index = indexTitre;

			if (index != -1) {
				int virgule = index + 1; // maintenant on cherche où se trouve la virgule pour ne récupérer que l'id des
											// recettes
											// de la recette
				while (kieme.charAt(virgule) != ',') {
					virgule += 1;
				}
				id = kieme.substring(index + 3, virgule);

			}

			listeID.add(id);
		}

		return listeID;
	}

	public static ArrayList<String> concateneIDtitre(ArrayList<String> liste_entiere) {
		/**
		*  Permet de concaténer les id et les titres des recettes comme suit : NameRecepe (Id) lors d'une recherche par name
		* @param  la liste complète des infos des recettes
		* @return  la concaténation des id et des titres des recettes comme suit : NameRecepe (Id)
		*/
		ArrayList<String> listeIDtitre = new ArrayList<String>();
		ArrayList<String> listeID = new ArrayList<String>();
		ArrayList<String> liste_titre = new ArrayList<String>();
		listeID = recupID(liste_entiere);
		// System.out.println(listeID);
		liste_titre = recupTitres(liste_entiere);
		for (int k = 0; k < liste_titre.size(); k++) {
			listeIDtitre.add(liste_titre.get(k) + " (" + listeID.get(k) + ')');
		}
		return (listeIDtitre);
	}

	public static ArrayList<String> concateneIDtitreByIngr(ArrayList<String> liste_entiere) {
		/**
		*  Permet de concaténer les id et les titres des recettes comme suit : NameRecepe (Id) lors d'une recherche par ingrédient
		* @param  la liste complète des infos des recettes
		* @return  la concaténation des id et des titres des recettes comme suit : NameRecepe (Id)
		*/
		ArrayList<String> listeIDtitre = new ArrayList<String>();
		ArrayList<String> listeID = new ArrayList<String>();
		ArrayList<String> liste_titre = new ArrayList<String>();
		String listeString = "";
		for (int j = 0; j < liste_entiere.size(); j++) {
			listeString = listeString + liste_entiere.get(j);
		}
		// System.out.println(listeString);
		listeID = recupID(liste_entiere);
		// System.out.println(listeID);
		liste_titre = recupTitres(liste_entiere);
		for (int k = 0; k < liste_titre.size(); k++) {
			listeIDtitre.add(liste_titre.get(k) + " (" + listeID.get(k) + ')');
		}
		return (listeIDtitre);
	}

	public static ArrayList<Aliment> unSeulMot(List<Aliment> liste_aliment) {
		/**
		*  permet de sélectionner seulement les ingrédients qui n'ont qu'un mot
		* @param  la liste complète des aliments (du frigo en général)
		* @return  permet de sélectionner seulement les ingrédients qui n'ont qu'un mot
		*/
		ArrayList<Aliment> listeUnSeul = new ArrayList<Aliment>();
		for (int k = 0; k < liste_aliment.size(); k++) {
			if (liste_aliment.get(k).getNom().indexOf(" ") == -1) {
				listeUnSeul.add(liste_aliment.get(k));
			}
		}
		return listeUnSeul;

	}

	// --------------------------------------------------------------------------------------
	
	public static String rechercherRecette(String nom) throws IOException, JSONException {
		/**
		*  recherche des recettes par nom
		* @param  le nom d'une recette
		* @return  toutes les infos de la recette donnée
		*/
		String apiKey = "e8a8f66a94b24a16aa58d7b4832f80a4";
		String url;
		url = "https://api.spoonacular.com/recipes/complexSearch?query=" + nom + "&number=3&apiKey=" + apiKey;

		final JSONObject json = EdamamManager.readJsonFromUrl(url);
		String jsonString = json.toString();
		return jsonString;
	}

	public static ArrayList<String> recupTitresByName(String toutes_infos) {
		/**
		*  Récupère la liste des titres d'une recherche
		* @param  ce que renvoie la requête api.
		* @return la liste des titres d'une recherche
		*/
		ArrayList<String> liste_titre = new ArrayList<String>();
		String titre = "";

		int index = toutes_infos.indexOf("title"); // on cherche où se trouve le titre de la recette

		while (index != -1) {
			int virgule = index + 1; // maintenant on cherche où se trouve la virgule pour ne récupérer que le titre
										// de la recette
			while (toutes_infos.charAt(virgule) != ',') {
				virgule += 1;
			}
			titre = toutes_infos.substring(index + 8, virgule - 1);
			liste_titre.add(titre);
			toutes_infos = toutes_infos.substring(virgule, toutes_infos.length());
			index = toutes_infos.indexOf("title");
		}

		return liste_titre;
	}

	public static ArrayList<String> recupIDByName(String liste_entiere) {
		/**
		*  retourne les id des recettes
		* @param  liste des recettes
		* @return une liste contenant tous les id des recettes recherchées
		*/
		ArrayList<String> listeID = new ArrayList<String>();
		String id = "";
		int indexTitre = liste_entiere.indexOf("title");
		while (indexTitre != -1) {
			int index = indexTitre;
			// System.out.println(liste_entiere);
			while (liste_entiere.charAt(index) != 'd') {
				index -= 1;
			}
			int virgule = index + 1; // maintenant on cherche où se trouve la virgule
			while (liste_entiere.charAt(virgule) != ',') {
				virgule += 1;
			}
			id = liste_entiere.substring(index + 3, virgule);
			listeID.add(id);
			liste_entiere = liste_entiere.substring(indexTitre + 7, liste_entiere.length());
			indexTitre = liste_entiere.indexOf("title");

		}
		return (listeID);
	}

	public static ArrayList<String> concateneIDtitreNOM(String liste_entiere) {
		/**
		*  Permet de concaténer les id et les titres des recettes comme suit : NameRecepe (Id) lors d'une recherche par ingrédient
		* @param  la liste complète des infos des recettes
		* @return  la concaténation des id et des titres des recettes comme suit : NameRecepe (Id)
		*/
		ArrayList<String> listeIDtitre = new ArrayList<String>();
		ArrayList<String> listeID = new ArrayList<String>();
		ArrayList<String> liste_titre = new ArrayList<String>();
		listeID = recupIDByName(liste_entiere);
		liste_titre = recupTitresByName(liste_entiere);
		for (int k = 0; k < liste_titre.size(); k++) {
			listeIDtitre.add(liste_titre.get(k) + " (" + listeID.get(k) + ')');
		}
		return (listeIDtitre);
	}

	// --------------------------------------------------------------------------------------
	
	// Outils permettant l'affichage des fiches recettes
	
	// requete récupérant les informations d'une recette via son id
	public static String instructionRecette(String id) throws IOException, JSONException {
		/**
		* méthode récupérant les informations d'une recette via son id
		* @param  l'id de la recette concernée
		* @return  les informations d'une recette via son id
		*/
		if (id != "Recette inexistante") {
			String url = "https://api.spoonacular.com/recipes/";
			String apiKey = "e8a8f66a94b24a16aa58d7b4832f80a4";
			String requete = url + id + "/analyzedInstructions?apiKey=" + apiKey;
			// System.out.println(requete);
			final JSONArray json = readArrayJsonFromUrl(requete);
			ArrayList<String> list_string = new ArrayList<String>();
			for (int k = 0; k < json.length(); k++) {
				list_string.add((json.get(k)).toString());
			}
			json.toString();
			if (list_string.size() != 0) {
				return list_string.get(0);
			} else {
				return ("Liste Vide");
			}

		} else {
			ArrayList<String> list_string = new ArrayList<String>();
			list_string.add("Problème d'id");
			return (list_string.get(0));
		}
	}

	public static ArrayList<String> instructionsPasApas(String id) throws IOException, JSONException {
		/**
		* méthode récupérant les étapes de réalisation d'une recette
		* @param  l'id de la recette concernée
		* @return  les étapes de réalisation d'une recette
		*/
		String infosCompletes = instructionRecette(id);
		ArrayList<String> instructions = new ArrayList<String>();
		if (infosCompletes == "Problème d'id" || infosCompletes == "Liste Vide") {
			instructions.add("Aucune instuction pour cette recette !");
			return (instructions);

		} else {
			int premier = infosCompletes.indexOf("step");
			// On supprime le premier step trouvé car il ne correspond à aucune information
			// importante.
			infosCompletes = infosCompletes.substring(premier + 5, infosCompletes.length());
			int index = infosCompletes.indexOf("step");
			while (index != -1) {
				infosCompletes = infosCompletes.substring(index + 7, infosCompletes.length());
				int guillemet = infosCompletes.indexOf('"');
				instructions.add((infosCompletes).substring(0, guillemet));
				infosCompletes = infosCompletes.substring(guillemet + 2, infosCompletes.length());
				index = infosCompletes.indexOf("step");
			}
			return (instructions);

		}
	}

	public static String recupImage(String id) throws IOException, JSONException {
		/**
		* méthode récupérant l'image d'une recette donnée
		* @param  l'id de la recette concernée
		* @return l'url d'image d'une recette donnée
		*/
		String url = "https://api.spoonacular.com/recipes/";
		String apiKey = "e8a8f66a94b24a16aa58d7b4832f80a4";
		String requete = url + id + "/information?apiKey=" + apiKey;
		// System.out.println(requete);
		final JSONObject json = EdamamManager.readJsonFromUrl(requete);
		String complement = json.toString();
		// System.out.println(complement);
		int indexHttp = complement.indexOf("https://spoonacular.com/recipeImages");
		if (indexHttp != -1) {
			String image = complement.substring(indexHttp, complement.length());
			int virgule = image.indexOf(',');
			image = image.substring(0, virgule - 1);
			return image;
		} else {
			return ("https://www.batirama.com/scaled/983/755/1/2017/08/31/125459/images/article/15082-_00erreur.jpg");
		}
	}

	public static ArrayList<String> recupRecipesIngredient(String id) throws IOException, JSONException {
		/**
		* méthode récupérant les ingrédients utilisés dans une recette
		* @param  l'id de la recette concernée
		* @return une liste contenant les ingrédients utilisés dans une recette
		*/
		String apiKey = "e8a8f66a94b24a16aa58d7b4832f80a4";
		String request;
		request = "https://api.spoonacular.com/recipes/" + id + "/ingredientWidget.json?apiKey=" + apiKey;
		final JSONObject ingredient = EdamamManager.readJsonFromUrl(request);
		ArrayList<String> list_nom = new ArrayList<String>();
		String ing = ingredient.toString();
		int index = 0;

		int nbrename = 0;
		Pattern p = Pattern.compile("name");
		Matcher m = p.matcher(ing);
		while (m.find()) {
			nbrename++;
		}
		System.out.println(ing);
		System.out.println("ehjehfzjnjkdqcnfrkgjznfdkj");
		for (int k = 1; k < nbrename + 1; k++) {
			index = ing.indexOf("name", index + 1);
			String complement = ing.substring(index + 6, ing.length());
			int accolade = complement.indexOf('}');
			String name = complement.substring(1, accolade - 1);
			list_nom.add(name);

		}
		System.out.println(list_nom);
		return list_nom;

	}

	public static ArrayList<String> recupIngredientQuantity(String id) throws IOException, JSONException {
		/**
		* méthode récupérant la quantité des ingrédients utilisés dans une recette
		* @param  l'id de la recette concernée
		* @return une liste contenant la quantité des ingrédients utilisés dans une recette
		*/
		String apiKey = "2cc51196909342c6beebcb02ef63401b";
		String request;
		request = "https://api.spoonacular.com/recipes/" + id + "/ingredientWidget.json?apiKey=" + apiKey;
		final JSONObject ingredient = EdamamManager.readJsonFromUrl(request);
		ArrayList<String> list_value = new ArrayList<String>();
		String ing = ingredient.toString();
		int index = 0;
		int count = 0;

		int nbrename = 0;
		Pattern p = Pattern.compile("value");
		Matcher m = p.matcher(ing);
		while (m.find()) {
			nbrename++;
		}
		for (int k = 1; k < nbrename + 1; k++) {
			count++;
			index = ing.indexOf("value", index + 1);
			String complement = ing.substring(index + 6, ing.length());
			int virgule = complement.indexOf(',');
			String value = complement.substring(1, virgule - 1);
			if (count % 2 == 1) {
				list_value.add(value);
			}

		}

		return list_value;

	}

	public static ArrayList<String> recupUnit(String id) throws IOException, JSONException {
		/**
		* méthode récupérant l'unité des ingrédients utilisés dans une recette
		* @param  l'id de la recette concernée
		* @return une liste contenant l'unité des ingrédients utilisés dans une recette
		*/
		String apiKey = "2cc51196909342c6beebcb02ef63401b";
		String request;
		request = "https://api.spoonacular.com/recipes/" + id + "/ingredientWidget.json?apiKey=" + apiKey;
		final JSONObject ingredient = EdamamManager.readJsonFromUrl(request);
		ArrayList<String> list_unit = new ArrayList<String>();
		String ing = ingredient.toString();
		int index = 0;
		int count = 0;

		int nbrename = 0;
		Pattern p = Pattern.compile("unit");
		Matcher m = p.matcher(ing);
		while (m.find()) {
			nbrename++;
		}
		for (int k = 1; k < nbrename + 1; k++) {
			count++;
			index = ing.indexOf("unit", index + 1);
			String complement = ing.substring(index + 6, ing.length());
			int virgule = complement.indexOf(',');
			String unit = complement.substring(1, virgule - 1);
			if (count % 2 == 1) {
				list_unit.add(unit);
			}

		}

		return list_unit;

	}

	public static ArrayList<String> recupIngcomp(String id) throws IOException, JSONException {
		/**
		* méthode concaténant les informations nom, quantité et unité
		* @param  l'id de la recette concernée
		* @return une liste concaténant les informations nom, quantité et unité
		*/
		ArrayList<String> unit = recupUnit(id);
		ArrayList<String> name = recupRecipesIngredient(id);
		ArrayList<String> value = recupIngredientQuantity(id);
		ArrayList<String> ingcomp = new ArrayList<String>();
		for (int i = 0; i < value.size(); i++) {
			ingcomp.add(name.get(i) + " " + value.get(i) + " " + unit.get(i));
		}
		return ingcomp;

	}

	public static String recupIDrecetteSelectionnee(String recette) {
		/**
		* méthode récupérant l'id d'une recette sélectionnée
		* @param  le nom de la recette et l'id sous la forme : RecepeName (id)
		* @return l'id de la recette entre parenthèse
		*/
		int par = recette.indexOf('(');
		String id = recette.substring(par + 1, recette.length() - 1);
		return (id);
	}

	public static Nutriments recupNutritionWidget(String id) throws IOException, JSONException {
		/**
		* méthode créant un objet de type nutriment contenant toutes les infos nutritives d'une recette
		* @param  l'id d'une recette donnée
		* @return un objet de type nutriment contenant toutes les infos nutritives d'une recette
		*/
		String apiKey = "2cc51196909342c6beebcb02ef63401b";
		String request;
		request = "https://api.spoonacular.com/recipes/" + id + "/nutritionWidget.json?apiKey=" + apiKey;
		final JSONObject recup = EdamamManager.readJsonFromUrl(request);
		JSONArray bad = recup.getJSONArray("bad");
		JSONArray good = recup.getJSONArray("good");

		List<String> listeGoodBad = Arrays.asList("Calories", "Fat", "Saturated Fat", "Carbohydrates", "Sugar",
				"Cholesterol", "Sodium", "Protein", "Vitamin K", "Manganese", "Vitamin B1", "Fiber", "Folate", "Iron",
				"Vitamin B3", "Vitamin B2", "Vitamin C", "Selenium", "Potassium", "Calcium", "Phosphorus", "Magnesium",
				"Vitamin E", "Copper", "Vitamin B5", "Vitamin B6", "Zinc", "Vitamin A", "Vitamin B12", "Vitamin D");
		List<String> listeNutriment = new ArrayList<String>();
		List<String> listeName = new ArrayList<String>();
		for (int j = 0; j < bad.length(); j++) {
			listeNutriment.add(bad.getJSONObject(j).getString("amount"));
			listeName.add(bad.getJSONObject(j).getString("title"));

		}
		for (int j = 0; j < good.length(); j++) {
			listeNutriment.add(good.getJSONObject(j).getString("amount"));
			listeName.add(good.getJSONObject(j).getString("title"));
		}

		ArrayList<String> liste = new ArrayList<String>();
		ArrayList<String> listeName2 = new ArrayList<String>();
		for (int k = 0; k < listeGoodBad.size(); k++) {
			if (listeName.indexOf(listeGoodBad.get(k)) != -1) {
				liste.add(listeNutriment.get(listeName.indexOf(listeGoodBad.get(k))));
				listeName2.add(listeName.get(listeName.indexOf(listeGoodBad.get(k))));
			} else {
				listeName2.add(listeGoodBad.get(k));
				liste.add("0");
			}
		}
		System.out.println(listeName2);
		System.out.println(liste);
		Nutriments nut = new Nutriments(liste);
		return (nut);
	}

	public static ArrayList<String> dishTypesRequest(String id) throws IOException, JSONException {
		/**
		* méthode permettant de récupérer ce que nous renvoie l'api (information recette)
		* @param  l'id d'une recette donnée
		* @return une liste avec les infos de la recette du type lunch, breakfast, dinner, dessert
		*/
		String url = "https://api.spoonacular.com/recipes/";
		String apiKey = "2cc51196909342c6beebcb02ef63401b";
		String requete = url + id + "/information?apiKey=" + apiKey;
		// System.out.println(requete);
		final JSONObject json = EdamamManager.readJsonFromUrl(requete);
		JSONArray dishTypes = json.getJSONArray("dishTypes");
		System.out.println(dishTypes);

		ArrayList<String> listTypes = new ArrayList<String>();
		for (int k = 0; k < dishTypes.length(); k++) {
			listTypes.add(dishTypes.getString(k));
		}
		return (listTypes);
	}

	public static ArrayList<Boolean> dishTypes(ArrayList<String> listInfos) {
		/**
		* méthode permettant de savoir si la recette sélectionnée est un lunch, breakfast, dinner, dessert
		* @param  l'id d'une recette donnée
		* @return une liste avec les infos du type lunch, breakfast, dinner, dessert
		*/
		boolean dessert = false;
		boolean lunch = false;
		boolean breakfast = false;
		boolean dinner = false;

		for (int k = 0; k < listInfos.size(); k++) {
			if (listInfos.get(k).compareTo("dessert") == 0 && !dessert) {
				dessert = true;
			}
			if (listInfos.get(k).compareTo("lunch") == 0 && !lunch) {
				lunch = true;
			}
			if (listInfos.get(k).compareTo("breakfast") == 0 && !breakfast) {
				breakfast = true;
			}
			if (listInfos.get(k).compareTo("dinner") == 0 && !dinner) {
				dinner = true;
			}
		}
		ArrayList<Boolean> listTypes = new ArrayList<Boolean>();
		listTypes.add(breakfast);
		listTypes.add(lunch);
		listTypes.add(dinner);
		listTypes.add(dessert);
		return (listTypes);
	}

	public static ArrayList<String> IdRecepe(ArrayList<String> listId) throws IOException, JSONException {
		/**
		* méthode récupérant toutes les recettes qui doivent afficher et qui filtre en fonction de la volonté de l'user
		* @param  une liste avec tous les id des recettes à afficher avant filtrage
		* @return une liste avec tous les id des recettes à aficher après filtrage
		*/
		ArrayList<String> IdRecepe = new ArrayList<String>();
		int index = Integer.parseInt(listId.get(0)); // Voulons-nous breakfast ? lunch ? dinner ? ou dessert ?
		// index est compris entre 0 et 3 inclu.

		for (int k = 1; k < listId.size(); k++) {
			ArrayList<String> listeInfos = new ArrayList<String>();
			listeInfos = dishTypesRequest(listId.get(k));
			ArrayList<Boolean> listTypes = new ArrayList<Boolean>();
			listTypes = dishTypes(listeInfos);
			if (listTypes.get(index)) {
				IdRecepe.add(listId.get(k));
			}
		}
		return (IdRecepe);
	}

	public static ArrayList<String> justId(ArrayList<String> liste_titre, ArrayList<Boolean> listeBLDD)
			throws IOException, JSONException {
		/**
		* méthode permettant de savoir si on filtre par breakfast, lunch, dinner ou dessert 
		* @param  une liste contant les titres et les id comme suit : NameRecepe (id)
		* @param une liste booléan dans l'ordre Breakfast Lunch Dinner Dessert
		* @return une liste avec tous les id des recettes à aficher après filtrage
		*/
		ArrayList<String> listeIdToutesRecettes = new ArrayList<String>();
		ArrayList<String> listeId = new ArrayList<String>();
		if (listeBLDD.get(0)) {
			listeIdToutesRecettes.add("0");
			System.out.println("0");
		}
		if (listeBLDD.get(1)) {
			listeIdToutesRecettes.add("1");
			System.out.println("1");
		}
		if (listeBLDD.get(2)) {
			listeIdToutesRecettes.add("2");
			System.out.println("2");
		}
		if (listeBLDD.get(3)) {
			listeIdToutesRecettes.add("3");
			System.out.println("3");
		}
		for (int k = 0; k < liste_titre.size(); k++) {
			listeIdToutesRecettes.add(
					liste_titre.get(k).substring(liste_titre.get(k).indexOf("(") + 1, liste_titre.get(k).length() - 1));
		}
		listeId = IdRecepe(listeIdToutesRecettes);
		return (listeId);
	}

	public static ArrayList<String> IdAfficher(ArrayList<String> liste_titre, ArrayList<String> listeId) {
		/**
		* méthode permettant d'afficher les recettes à afficher après filtrage 
		* @param  une liste contant les titres et les id comme suit : NameRecepe (id)
		* @param une liste contenant les id des recettes à afficher
		* @return une liste complète avec le nom de la recette et son id entre parenthèse
		*/
		ArrayList<String> listeARenvoyer = new ArrayList<String>();
		if (listeId.size() == liste_titre.size()) {
			return (liste_titre);
		}
		if (listeId.size() != liste_titre.size()) {
			for (int k = 0; k < listeId.size(); k++) {
				int index = -1;
				int j = 0;
				while (j < liste_titre.size() && index == -1) {
					if (liste_titre.get(j).indexOf(listeId.get(k)) != -1) {
						index = j;
					}
					j += 1;
				}
				if (index != -1) {
					listeARenvoyer.add(liste_titre.get(index));
				}
			}
		}
		return (listeARenvoyer);
	}
	
	public static String readyIn(String id) throws IOException, JSONException {
		/**
		* méthode permettant de savoir le temps de préapation en minutes de la recette donnée
		* @param  l'id d'une recette
		* @return une information temporelle en minute quant au temps de préparation de la recette donnée
		*/
		String url = "https://api.spoonacular.com/recipes/";
		String apiKey = "2cc51196909342c6beebcb02ef63401b";
		String requete = url + id + "/information?apiKey=" + apiKey;
		// System.out.println(requete);
		final JSONObject json = EdamamManager.readJsonFromUrl(requete);
		return(json.get("readyInMinutes").toString()+" min");
	}

}
