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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *
 *  Gère tout ce qui est lié à l'inscription d'un nouveau compte
 *
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword2);
        final EditText etPasswordVerif = findViewById(R.id.etPasswordVerif);

        final Button bRegister = findViewById(R.id.bRegister);
        final Button bRetour = findViewById(R.id.btn_retour_6);



        // Bouton retour: Termine l'activité courante et crée une nouvelle activité Login
        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterActivity.this.finish();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);

            }
        });



        // Bouton d'inscription: Essaye d'inscrire le nouveau compte entré par l'utilisateur
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String passwordVerif = etPasswordVerif.getText().toString();

                //Fonctions détaillées plus bas
                if(!verifPasswordEquals(password, passwordVerif))
                    return;
                if(!verifPassword(password))
                    return;
                if(!verifUsername(username))
                    return;
                if(!verifEmail(email))
                    return;


                // Attente de la réponse du serveur
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) { //Si succes, crée une activité Login

                                RegisterActivity.this.finish();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("Registered", username);
                                RegisterActivity.this.startActivity(intent);

                            } else { // Sinon, si l'erreur vient du fait que le pseudo est déjà utilisé, previent l'utilisateur
                                if(jsonResponse.has("usernameAvailability")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage(R.string.inscription_alert_dialog_pseudo_deja_utilise)
                                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                            .create()
                                            .show();
                                }
                                else {
                                    if(jsonResponse.has("emailAvailability")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage(R.string.inscription_alert_dialog_email_deja_utilise)
                                                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                                .create()
                                                .show();
                                    }
                                    else {
                                        // Sinon erreur anormale
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setMessage(R.string.alert_dialog_erreur_base_de_donnée)
                                                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                                .create()
                                                .show();
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Création et envoie de la requête
                RegisterRequest registerRequest = new RegisterRequest(email, username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }



    /*
     *  Params: Mot de passe et confirmation de mot de passe entrés par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que les 2 mots de passes sont bien identiques
     */
    public boolean verifPasswordEquals(String password, String passwordVerif) {

        if (!password.equals(passwordVerif)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(R.string.inscription_alert_dialog_mdp_different)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;
    }


    /*
     *  Params: Mot de passe entré par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que le mot de passe fait plus de 3 caractères
     */
    public boolean verifPassword(String password) {
        if(password.length() < 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(R.string.inscription_alert_dialog_mdp_non_valide)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;
    }


    /*
     *  Params: Pseudo entré par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que le pseudo fait plus de 3 caractères
     */
    public boolean verifUsername(String username) {

        if (username.length() < 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(R.string.inscription_alert_dialog_pseudo_non_valide)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;
    }


    /*
     *  Params: email entré par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que l'email est sous le bon format
     */
    public boolean verifEmail(String email){

        String masque = "^[a-zA-Z]+[a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9_-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);
        Matcher controler = pattern.matcher(email);

        if (!controler.matches()){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(R.string.inscription_alert_dialog_email_non_valide)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;

    }
}
