package fr.tse.thextremdevs.smartfridge_app.fridge;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import org.json.JSONObject;

public class EdamamManager
{
    public static String jsonParser(final String ingrSearch) throws IOException, JSONException {
        final JSONObject json = readJsonFromUrl(requestBuilderEdamam(ingrSearch));
        return json.toString();
    }
    
    public static String requestBuilderEdamam(final String ingredient) {
        final String app_key = "08a9e02c75f37610bac1de080b325832";
        final String app_id = "340a56d4";
        final String request = "https://api.edamam.com/api/food-database/v2/parser?ingr=" + ingredient + "&app_id=" + app_id + "&app_key=" + app_key;
        return request;
    }
    
    public static String readAll(final Reader rd) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }
        return sb.toString();
    }
    
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream myurl = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(myurl, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }
    
    public static List<Aliment> AlimentSearcher(String stringToParse) throws JSONException {
    	List<Aliment> tabAlimentParsed = new ArrayList<Aliment>();
    	JSONObject jsonToParse = new JSONObject(stringToParse);
    	
    	JSONArray jsonArrayIngr = jsonToParse.getJSONArray("hints");
    	for (int i = 0; i < jsonArrayIngr.length(); i++)
    	{
    	    JSONObject jsonFood = jsonArrayIngr.getJSONObject(i).getJSONObject("food");
    	    String foodID = jsonFood.getString("foodId");
    	    String labelFood = jsonFood.getString("label");
    	    String category = jsonFood.getString("category");
    	    String img;
    	    try {
    	    	img = jsonFood.getString("image");
			} catch (Exception e) {
	    	    img = "error";
			}
    	    
    	    JSONObject jsonNutrients = jsonFood.getJSONObject("nutrients");
    	    Double chocdf;
    	    try {
    	    	chocdf = Double.parseDouble(jsonNutrients.getString("CHOCDF"));
			} catch (Exception e) {
				chocdf =0.0;
			}
    	    Double fibtg;
    	    try {
    	    	fibtg = Double.parseDouble(jsonNutrients.getString("FIBTG"));
			} catch (Exception e) {
	    	    fibtg =0.0;
			}
    	    Double fat;
    	    try {
    	    	fat = Double.parseDouble(jsonNutrients.getString("FAT"));
			} catch (Exception e) {
				fat =0.0;
			}
    	    Double enerc_kcal;
    	    try {
    	    	enerc_kcal = Double.parseDouble(jsonNutrients.getString("ENERC_KCAL"));
			} catch (Exception e) {
				enerc_kcal =0.0;
			}
    	    Double procnt;
    	    try {
    	    	procnt = Double.parseDouble(jsonNutrients.getString("PROCNT"));
			} catch (Exception e) {
				procnt =0.0;
			}
    	    
    	    Aliment alimI= new Aliment(foodID, labelFood, enerc_kcal, procnt, fat, chocdf, fibtg, category, img, null, null, null,true);
    	    tabAlimentParsed.add(alimI);
    	}
    	
    	
    	return tabAlimentParsed;
    	 
    	
    }
    
}
