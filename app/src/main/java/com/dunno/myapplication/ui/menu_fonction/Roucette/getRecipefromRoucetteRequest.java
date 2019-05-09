package com.dunno.myapplication.ui.menu_fonction.Roucette;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getRecipefromRoucetteRequest extends StringRequest {

    private static final String REQUEST_ROUCETTE = "https://foodnowdb.000webhostapp.com/RoucetteV2.php";

    private Map<String, String> params;

    public getRecipefromRoucetteRequest(Response.Listener<String> listener) {
        super(Method.POST, REQUEST_ROUCETTE, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}