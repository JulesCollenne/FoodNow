package com.dunno.myapplication.ui.menu;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Params: none
 *  Requête: Récupère une recette au hasard (pour le moment) de la base de donnée
 *  Return: une recette aléatoire
 */

public class getRecettedujourRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getDayRecipe.php";
    private Map<String, String> params;

    getRecettedujourRequest(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}