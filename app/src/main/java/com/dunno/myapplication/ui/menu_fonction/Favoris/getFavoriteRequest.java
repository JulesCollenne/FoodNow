package com.dunno.myapplication.ui.menu_fonction.Favoris;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getFavoriteRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getFavoriteByID.php";
    private Map<String, String> params;

    getFavoriteRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

