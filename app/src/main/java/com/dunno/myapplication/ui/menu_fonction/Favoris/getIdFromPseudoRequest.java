package com.dunno.myapplication.ui.menu_fonction.Favoris;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getIdFromPseudoRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getIdFromPseudo.php";
    private Map<String, String> params;

    public getIdFromPseudoRequest(String pseudo, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", pseudo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

