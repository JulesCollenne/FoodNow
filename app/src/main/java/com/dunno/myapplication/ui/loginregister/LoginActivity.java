
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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(getIntent().hasExtra("Registered")){
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


        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);
        final Button bLogin = findViewById(R.id.bSignIn);
        final Button bRetour = findViewById(R.id.btn_retour_5);


        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retour = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(retour);

            }
        });

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
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


                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(R.string.connection_reussit_alert_dialog)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoginActivity.this.finish();
                                                startActivity(loggedIn);
                                            }
                                        })
                                        .create()
                                        .show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(R.string.connection_échouée_alert_dialog)
                                        .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) { //JSON
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
