package com.dunno.myapplication.ui.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import java.util.regex.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
TODO: Verifiez que l'email n'est pas déjà utilisé
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword2);
        final EditText etPasswordVerif = (EditText) findViewById(R.id.etPasswordVerif);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String passwordVerif = etPasswordVerif.getText().toString();

                if(!password.equals(passwordVerif)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Les mots de passes sont différents")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                if(password.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Le mot de passe n'est pas valide (au moins 3 caractères)")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                if(username.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Le pseudo n'est pas valide (au moins 3 caractères")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }


                String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                        + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
                Pattern pattern = Pattern.compile(masque);
                Matcher controler = pattern.matcher(email);
                if (!controler.matches()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Veuillez entrer une adresse eMail valide")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("Registered", username);
                                RegisterActivity.this.startActivity(intent);

                            } else {
                                if(jsonResponse.has("usernameAvailability")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Le pseudo est déjà utilisé, essayez en un autre")
                                            .setNegativeButton("Réessayer", null)
                                            .create()
                                            .show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Création échoué, verifiez que les informations sont correctement entrées")
                                            .setNegativeButton("Réessayer", null)
                                            .create()
                                            .show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(email, username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
