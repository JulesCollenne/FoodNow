package com.dunno.myapplication.ui.menu_fonction.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
 * Params: type d'ingredient
 *  Requête: Récupère la liste des ingrédients selon le type en paramètres
 *  Return: Une liste d'ingrédient
 */

public class getIngredientPerTypeRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getIngredientPerType.php";
    private Map<String, String> params;

    public getIngredientPerTypeRequest(String type, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("type", type);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
