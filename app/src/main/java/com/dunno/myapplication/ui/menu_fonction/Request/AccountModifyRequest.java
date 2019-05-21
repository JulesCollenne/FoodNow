package com.dunno.myapplication.ui.menu_fonction.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
 * Params: l'ancien pseudo, ainsi que les nouveaux pseudo, email et mot de passe
 *  Requête: Modifie la ligne correspondants au compte dans la base de données pour changer les informations, vérifie que le pseudo n'est pas déjà utilisé
 *  Return: Réussite ou non
 */

public class AccountModifyRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/ModifyAccount.php";
    private Map<String, String> params;

    public AccountModifyRequest(String username, String email, String newEmail, String newUsername, String newPassword, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("newEmail", newEmail);
        params.put("newUsername", newUsername);
        params.put("newPassword", newPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
