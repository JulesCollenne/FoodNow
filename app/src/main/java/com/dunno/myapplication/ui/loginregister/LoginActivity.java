
package com.dunno.myapplication.ui.loginregister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


/**
 *  Gère tout ce qui est lié à la connection à un compte existant
 */

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvRegisterLink;
    private Button bLogin;
    private ImageButton bRetour;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /** Appel la fonction lorsqu'on arrive d'une inscription réussie */
        if(getIntent().hasExtra("Registered")){
            promptRegisterSuccessful();
        }


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        bLogin = findViewById(R.id.bSignIn);
        bRetour = findViewById(R.id.btn_retour_5);



        /** Bouton retour: Supprime l'activité courante (retour au menu) */
        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { LoginActivity.this.finish(); }
        });



        /** Bouton vers l'inscription: Crée une activité Register */
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });



        /**
         * Bouton de tentative de connection: Vérifie via la requête LoginRequest que le pseudo et le mdp sont bon.
         * Si oui: Affiche un alertDialog indiquant la réussite, puis crée une nouvelle activité main avec les informations de comptes.
         * Sinon affiche un message d'erreur et attent une nouvelle demande
         */
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                if(username.length() < 3){
                    usernameFailAlert();

                    return;
                }

                if(password.length() < 3){
                    passwordFailAlert();

                    return;
                }

                /** Fonction qui reçoit la réponse du serveur */
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                final Intent loggedIn = new Intent(getApplicationContext(), MainActivity.class);

                                String email = jsonResponse.getString("email");

                                loggedIn.putExtra("username", username);
                                loggedIn.putExtra("password", password);
                                loggedIn.putExtra("email", email);

                                loggedIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                LoginActivity.this.finish();
                                startActivity(loggedIn);

                            } else {
                                connectionFailAlert();
                            }

                        } catch (JSONException e) { //JSON
                            e.printStackTrace();
                        }
                    }
                };

                /** Création et envoie de la requête */
                loginRequest(username, password, responseListener);
            }
        });
    }

    /** Message d'alerte en cas de non conformité du nom d'utilisateur */
    public void usernameFailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.pseudo_non_conforme_alert_dialog)
                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                .create()
                .show();
    }

    /** Message d'alerte en cas de non conformité du mot de passe */
    public void passwordFailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.mot_de_passe_non_conforme_alert_dialog)
                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                .create()
                .show();
    }

    /** Message d'alerte en cas d'erreur lors de la connexion */
    public void connectionFailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.connection_échouée_alert_dialog)
                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                .create()
                .show();
    }

    /** Requête de vérification et connexion de l'utilisateur */
    public void loginRequest(String username, String password, Response.Listener<String> listener) {
        LoginRequest loginRequest = new LoginRequest(username, password, listener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }


    /**
        Params: None
        Return: None
        Fonction: Affiche un message via AlertDialog indiquant que l'inscription a été réussit
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void promptRegisterSuccessful(){

        String pseudo = Objects.requireNonNull(getIntent().getExtras()).getString("Registered");

        String connectionReussitTexte = getString(R.string.connection_inscription_reussit_alert_dialog_part_1);
        connectionReussitTexte += " "+pseudo+" ";
        connectionReussitTexte += getString(R.string.connection_inscription_reussit_alert_dialog_part_2);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(connectionReussitTexte)
                .setNegativeButton(R.string.alert_dialog_ok, null)
                .create()
                .show();

    }

}
