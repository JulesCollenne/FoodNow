package com.dunno.myapplication.ui.menu_fonction.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.dunno.myapplication.ui.menu.MainActivity;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.AddIngredient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountModifyActivity extends AppCompatActivity {

    String email = null;
    String username = null;
    String password = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify);

        email = getIntent().getExtras().getString("email");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");


        final EditText etActualPassword = (EditText) findViewById(R.id.et_password_confirmation);
        final EditText etNewEmail = (EditText) findViewById(R.id.et_new_email);
        final EditText etNewUsername = (EditText) findViewById(R.id.et_new_username);
        final EditText etNewPassword = (EditText) findViewById(R.id.et_new_password);
        final EditText etNewPasswordConfirmation = (EditText) findViewById(R.id.et_new_password_confirmation);

        Button bModify = (Button) findViewById(R.id.bModify);
        Button bRetourModify = (Button) findViewById(R.id.btn_retour_4);

        bRetourModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retourIntent = new Intent(getApplicationContext(), AddIngredient.class);
                retourIntent.putExtra("email", email);
                retourIntent.putExtra("username", username);
                retourIntent.putExtra("password", password);
                startActivity(retourIntent);

            }
        });

        bModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String actualPassword = etActualPassword.getText().toString();
                final String newEmail = etNewEmail.getText().toString();
                final String newUsername = etNewUsername.getText().toString();
                final String newPassword = etNewPassword.getText().toString();
                final String newPasswordConfirmation = etNewPasswordConfirmation.getText().toString();


                if(!actualPassword.equals(password)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage("Le mot de passe actuel que vous avez entré est incorrect, veuillez entrer votre mot de passe actuel")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                if(!newPassword.equals(newPasswordConfirmation)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage("Votre nouveau mot de passe est différent de celui entré dans la confirmation, veuillez entrer le même mot de passe")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                        + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
                Pattern pattern = Pattern.compile(masque);
                Matcher controler = pattern.matcher(newEmail);
                if (!controler.matches()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage("Veuillez entrer une adresse eMail valide")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                if(newPassword.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage("Le nouveau mot de passe n'est pas valide (au moins 3 caractères)")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                if(newUsername.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage("Le nouveau pseudo n'est pas valide (au moins 3 caractères")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                builder.setMessage("Voulez vous vraiment modifier votre compte ainsi:\nemail: "+newEmail+"\nPseudo: "+newUsername+"\nMot de passe: "+newPassword)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                builder.setMessage("Modification réussie")
                                                        .create()
                                                        .show();

                                                Intent intent = new Intent(AccountModifyActivity.this, MainActivity.class);
                                                intent.putExtra("email", newEmail);
                                                intent.putExtra("username", newUsername);
                                                intent.putExtra("password", newPassword);
                                                AccountModifyActivity.this.startActivity(intent);

                                            } else {
                                                if(jsonResponse.has("usernameAvailability")){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                    builder.setMessage("Le pseudo est déjà utilisé, essayez en un autre")
                                                            .setNegativeButton("Réessayer", null)
                                                            .create()
                                                            .show();
                                                }
                                                else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                    builder.setMessage("Modification échouée, verifiez que les informations sont correctement entrées")
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

                                AccountModifyRequest accountModifyRequest = new AccountModifyRequest(username, newEmail, newUsername, newPassword, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(AccountModifyActivity.this);
                                queue.add(accountModifyRequest);

                            }
                        })
                        .setNegativeButton("non", null)
                        .create()
                        .show();




            }
        });


    }
}
