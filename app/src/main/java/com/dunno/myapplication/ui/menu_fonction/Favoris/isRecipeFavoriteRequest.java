package com.dunno.myapplication.ui.menu_fonction.Favoris;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class isRecipeFavoriteRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/isRecipeFavorite.php";
    private Map<String, String> params;

    public isRecipeFavoriteRequest(String username, String recipeID, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("idRecipe", recipeID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
