package com.dunno.myapplication.ui.menu_fonction.ChefSashimi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Requete qui permet de récuperer un conseil aléatoire
 */
public class getAdviceRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/getAdvice.php";
    private Map<String, String> params;

    getAdviceRequest(Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}