package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Requete permettant de recevoir les recettes en fonction du type (entrée, plat ou dessert )
 */
public class getRecipeFromTypeRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getRecipeFromType.php";
    private Map<String, String> params;

    getRecipeFromTypeRequest(String type,String restriction, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("type", type);
        params.put("restriction", restriction);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
