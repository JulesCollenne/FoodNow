package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

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
