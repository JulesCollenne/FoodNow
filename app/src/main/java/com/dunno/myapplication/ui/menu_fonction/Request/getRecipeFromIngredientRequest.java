package com.dunno.myapplication.ui.menu_fonction.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class getRecipeFromIngredientRequest extends StringRequest {

    /*
     * Params: liste d'ingrédient et sa taille
     *  Requête: Récupère la liste de recette utilisant la liste d'ingrédient donnée en paramètre
     *  Return: liste de recette
     */

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getRecipeFromIngredient.php";
    private Map<String, String> params;

    public getRecipeFromIngredientRequest(ArrayList<String> ingredientID, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        for(int i=0; i<ingredientID.size(); i++) {
            params.put("ingredient"+i, ingredientID.get(i));
        }
        params.put("size", ingredientID.size()+"");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
