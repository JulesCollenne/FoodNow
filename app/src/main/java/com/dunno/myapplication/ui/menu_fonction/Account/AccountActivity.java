package com.dunno.myapplication.ui.menu_fonction.Account;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dunno.myapplication.R;

import java.util.Objects;

/*
 *
 * Affiche les informations du compte connecté
 *
 */

public class AccountActivity extends AppCompatActivity {

    String email = null;
    String username = null;
    String password = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Récupère les informations de compte
        email = Objects.requireNonNull(getIntent().getExtras()).getString("email");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");


        // Affiche les informations de compte récupérés
        TextView tvEmail = findViewById(R.id.tv_account_email);
        TextView tvUsername = findViewById(R.id.tv_account_username);
        String tmp = getString(R.string.account_email) + "\n" + email;
        tvEmail.setText(tmp);
        tmp = getString(R.string.account_pseudo) + "\n" + username;
        tvUsername.setText(tmp);



        Button bModify = findViewById(R.id.button_account_modify);
        Button bRetour = findViewById(R.id.btn_retour_3);

        // Bouton modifier: Ouvre l'activité de modification d'information de compte
        bModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent modifyAccountIntent = new Intent(getApplicationContext(), AccountModifyActivity.class);
                modifyAccountIntent.putExtra("email", email);
                modifyAccountIntent.putExtra("username", username);
                modifyAccountIntent.putExtra("password", password);
                AccountActivity.this.finish();
                startActivity(modifyAccountIntent);

            }
        });


        // Bouton retour: Ferme l'activité en cours
        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountActivity.this.finish();

            }
        });




    }
}
