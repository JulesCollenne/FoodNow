package com.dunno.myapplication.ui.menu_fonction.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.loginregister.RegisterActivity;
import com.dunno.myapplication.ui.menu.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 *
 * Permet à l'utilisateur de modifier ses informations de compte
 *
 */

public class AccountModifyActivity extends AppCompatActivity {

    String email = null;
    String username = null;
    String password = null;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify);

        //Récupère les informations de compte actuel
        email = Objects.requireNonNull(getIntent().getExtras()).getString("email");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");


        final EditText etActualPassword = findViewById(R.id.et_password_confirmation);
        final EditText etNewEmail = findViewById(R.id.et_new_email);
        final EditText etNewUsername = findViewById(R.id.et_new_username);
        final EditText etNewPassword = findViewById(R.id.et_new_password);
        final EditText etNewPasswordConfirmation = findViewById(R.id.et_new_password_confirmation);

        Button bModify = findViewById(R.id.bModify);
        ImageButton bRetourModify = findViewById(R.id.btn_retour_4);


        // Bouton retour: Ferme l'activité actuel et ouvre une activité AccountActivity avec les informations du compte inchangées
        bRetourModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                accountIntent.putExtra("email", email);
                accountIntent.putExtra("username", username);
                accountIntent.putExtra("password", password);
                AccountModifyActivity.this.finish();
                startActivity(accountIntent);

            }
        });


        // Bouton modifier: Envoie la requete au serveur pour modifier les informations du comptes (si les données entrées par l'utilisateur sont correct
        bModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String actualPassword = etActualPassword.getText().toString();
                final String newEmail = etNewEmail.getText().toString();
                final String newUsername = etNewUsername.getText().toString();
                final String newPassword = etNewPassword.getText().toString();
                final String newPasswordConfirmation = etNewPasswordConfirmation.getText().toString();


                //Fonctions détaillées plus bas
                if(!verifPasswordActualEquals(password, actualPassword))
                    return;
                if(!verifPasswordEqualsNew(newPassword, newPasswordConfirmation))
                    return;
                if(!verifPassword(newPassword))
                    return;
                if(!verifUsername(newUsername))
                    return;
                if(!verifEmail(newEmail))
                    return;


                // Création du message de confirmation avec les constantes dans String.xml
                String modifyConfirmation = getConfirmationText(newEmail, newUsername, newPassword);

                // Message de confirmation: Oui -> Modifie les informations de compte, Non -> Ne fait rien
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                builder.setMessage(modifyConfirmation)
                        //Bouton oui
                        .setPositiveButton(R.string.alert_dialog_oui, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Attend la réponse du serveur
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            // Si modification réussie, préviens l'utilisateur et ouvre une nouvelle AccountActivity avec les nouvelles informations
                                            if (success) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                builder.setMessage(R.string.account_modify_success)
                                                        .create()
                                                        .show();

                                                Intent intent = new Intent(AccountModifyActivity.this, AccountActivity.class);
                                                intent.putExtra("email", newEmail);
                                                intent.putExtra("username", newUsername);
                                                intent.putExtra("password", newPassword);
                                                AccountModifyActivity.this.finish();
                                                AccountModifyActivity.this.startActivity(intent);

                                            } else {
                                                // Sinon, si l'erreur vient du fait que le pseudo est déjà utilisé, previent l'utilisateur
                                                if(jsonResponse.has("usernameAvailability")){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                    builder.setMessage(R.string.inscription_alert_dialog_pseudo_deja_utilise)
                                                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                                            .create()
                                                            .show();
                                                }
                                                //sinon, si l'erreur vient du fait que l'email est déjà utilisé, previent l'utilisateur
                                                else {
                                                    if(jsonResponse.has("emailAvailability")){
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                        builder.setMessage(R.string.inscription_alert_dialog_email_deja_utilise)
                                                                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                                                .create()
                                                                .show();
                                                    }
                                                    // Sinon erreur anormale
                                                    else {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
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
                                AccountModifyRequest accountModifyRequest = new AccountModifyRequest(username, email, newEmail, newUsername, newPassword, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(AccountModifyActivity.this);
                                queue.add(accountModifyRequest);

                            }
                        })
                        //Bouton non
                        .setNegativeButton(R.string.alert_dialog_non, null)
                        .create()
                        .show();
            }
        });
    }


    /*
     *  Params: Mot de passe actuel et mot de passe entrés par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que les 2 mots de passes sont bien identiques
     */
    public boolean verifPasswordActualEquals(String password, String actualPassword) {

        if(!actualPassword.equals(password)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
            builder.setMessage(R.string.account_modify_mdp_incorrect)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;
    }


    /*
     *  Params: nouveau mot de passe et confirmation du nouveau mot de passe entrés par l'utilisateur
     *  Returns: Boolean
     *  Vérifie que les 2 mots de passes sont bien identiques
     */
    public boolean verifPasswordEqualsNew(String newPassword, String newPasswordConfirmation) {
        if (!newPassword.equals(newPasswordConfirmation)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
            builder.setMessage(R.string.account_modify_mdp_different)
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
    public boolean verifPassword(String newPassword) {
        if(newPassword.length() < 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
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
    public boolean verifUsername(String newUsername) {

        if(newUsername.length() < 3){
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
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
    public boolean verifEmail(String newEmail){

        String masque = "^[a-zA-Z]+[a-zA-Z0-9_-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9_-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(masque);
        Matcher controler = pattern.matcher(newEmail);
        if (!controler.matches()){
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
            builder.setMessage(R.string.inscription_alert_dialog_email_non_valide)
                    .setNegativeButton(R.string.alert_dialog_reesayer, null)
                    .create()
                    .show();
            return false;
        }

        return true;

    }


    /*
     * Params: Le nouvel email, pseudo et mot de passe entrés par l'utilisateur
     * Return: Un message crée à partir des constantes dans String.xml
     * Fonction: Concatène le message
     */
    public String getConfirmationText(String newEmail, String newUsername, String newPassword){

        String modifyConfirmation = getString(R.string.account_modify_confirmation_part_1);
        modifyConfirmation += " " + newEmail + "\n";
        modifyConfirmation += getString(R.string.account_modify_confirmation_part_2);
        modifyConfirmation += " " + newUsername + "\n";
        modifyConfirmation += getString(R.string.account_modify_confirmation_part_3);
        modifyConfirmation += " " + newPassword;

        return modifyConfirmation;

    }

}
