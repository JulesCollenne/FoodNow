package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getRecipeFromIDRequest extends StringRequest {

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
