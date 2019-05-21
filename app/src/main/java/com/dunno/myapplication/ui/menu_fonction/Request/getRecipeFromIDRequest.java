package com.dunno.myapplication.ui.menu_fonction.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getRecipeFromIDRequest extends StringRequest {

    /*
     * Params: un id de recette
     *  Requête: récupère les infos lié a la recette dont l'id est donné en parametre
     *  Return: les différentes informations de la recette
     */

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getRecipeFromID.php";
    private Map<String, String> params;

    public getRecipeFromIDRequest(String recipeID, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("recipeID", recipeID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}

