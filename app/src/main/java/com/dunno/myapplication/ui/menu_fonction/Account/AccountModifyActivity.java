package com.dunno.myapplication.ui.menu_fonction.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountModifyActivity extends AppCompatActivity {

    String email = null;
    String username = null;
    String password = null;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_modify);

        email = Objects.requireNonNull(getIntent().getExtras()).getString("email");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");


        final EditText etActualPassword = findViewById(R.id.et_password_confirmation);
        final EditText etNewEmail = findViewById(R.id.et_new_email);
        final EditText etNewUsername = findViewById(R.id.et_new_username);
        final EditText etNewPassword = findViewById(R.id.et_new_password);
        final EditText etNewPasswordConfirmation = findViewById(R.id.et_new_password_confirmation);

        Button bModify = findViewById(R.id.bModify);
        Button bRetourModify = findViewById(R.id.btn_retour_4);

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
                    builder.setMessage(R.string.account_modify_mdp_incorrect)
                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                            .create()
                            .show();
                    return;
                }

                if(!newPassword.equals(newPasswordConfirmation)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage(R.string.account_modify_mdp_different)
                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                            .create()
                            .show();
                    return;
                }

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
                    return;
                }

                if(newPassword.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage(R.string.inscription_alert_dialog_mdp_non_valide)
                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                            .create()
                            .show();
                    return;
                }

                if(newUsername.length() < 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                    builder.setMessage(R.string.inscription_alert_dialog_pseudo_non_valide)
                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                            .create()
                            .show();
                    return;
                }

                String modifyConfirmation = getString(R.string.account_modify_confirmation_part_1);
                modifyConfirmation += " " + newEmail + "\n";
                modifyConfirmation += getString(R.string.account_modify_confirmation_part_2);
                modifyConfirmation += " " + newUsername + "\n";
                modifyConfirmation += getString(R.string.account_modify_confirmation_part_3);
                modifyConfirmation += " " + newPassword;

                AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                builder.setMessage(modifyConfirmation)
                        .setPositiveButton(R.string.alert_dialog_oui, new DialogInterface.OnClickListener() {
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
                                                if(jsonResponse.has("usernameAvailability")){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                    builder.setMessage(R.string.inscription_alert_dialog_pseudo_deja_utilise)
                                                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                                            .create()
                                                            .show();
                                                }
                                                else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AccountModifyActivity.this);
                                                    builder.setMessage(R.string.alert_dialog_erreur_base_de_donnée)
                                                            .setNegativeButton(R.string.alert_dialog_reesayer, null)
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
                        .setNegativeButton(R.string.alert_dialog_non, null)
                        .create()
                        .show();




            }
        });


    }
}
