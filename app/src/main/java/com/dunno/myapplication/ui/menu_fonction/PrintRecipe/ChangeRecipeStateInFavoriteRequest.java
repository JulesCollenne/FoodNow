package com.dunno.myapplication.ui.menu_fonction.PrintRecipe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
    Permet d'ajouter ou retirer un favoris de la base de donnée pour l'utilisateur connecté
 */
public class ChangeRecipeStateInFavoriteRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/ChangeRecipeStateInFavorite.php";
    private Map<String, String> params;

    ChangeRecipeStateInFavoriteRequest(String userID, String recipeID, String isFavorite, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idUser", userID);
        params.put("idRecette", recipeID);
        params.put("isFavorite", isFavorite);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
