package com.dunno.myapplication.ui.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Params: email, username, password
 *  Requête: Essaye de créer un nouveau compte avec les informations en paramètres, vérifie que le pseudo n'est pas déjà utilisé
 *  Return: Succès, pseudo déjà utilisé ou erreur
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/Register.php";
    private Map<String, String> params;

    RegisterRequest(String email, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
