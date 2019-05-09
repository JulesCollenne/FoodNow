package com.dunno.myapplication.ui.menu_fonction.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu.MainActivity;

public class AccountActivity extends AppCompatActivity {

    String email = null;
    String username = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        email = getIntent().getExtras().getString("email");
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");

        TextView tvEmail = (TextView) findViewById(R.id.tv_account_email);
        TextView tvUsername = (TextView) findViewById(R.id.tv_account_username);

        tvEmail.setText("Votre adresse mail:\n"+email);
        tvUsername.setText("Votre pseudonyme:\n"+username);

        Button bModify = (Button) findViewById(R.id.button_account_modify);
        Button bRetour = (Button) findViewById(R.id.btn_retour_3);


        bModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent modifyAccountIntent = new Intent(getApplicationContext(), AccountModifyActivity.class);
                modifyAccountIntent.putExtra("email", email);
                modifyAccountIntent.putExtra("username", username);
                modifyAccountIntent.putExtra("password", password);
                startActivity(modifyAccountIntent);

            }
        });

        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retour = new Intent(getApplicationContext(), MainActivity.class);
                retour.putExtra("email", email);
                retour.putExtra("username", username);
                retour.putExtra("password", password);
                startActivity(retour);

            }
        });




    }
}