package com.dunno.myapplication.ui.menu_fonction.Account;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AccountModifyRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://foodnowdb.000webhostapp.com/ModifyAccount.php";
    private Map<String, String> params;

    AccountModifyRequest(String username, String newEmail, String newUsername, String newPassword, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("newEmail", newEmail);
        params.put("newUsername", newUsername);
        params.put("newPassword", newPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
