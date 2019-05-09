package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dunno.myapplication.R;


public class ChoixTypeRecette extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_type_recette);

        ImageButton entreeBtn = (ImageButton) findViewById(R.id.entreeButton);
        ImageButton platBtn = (ImageButton) findViewById(R.id.platButton);
        ImageButton dessertBtn = (ImageButton) findViewById(R.id.dessertButton);

        Button retourBtn = (Button) findViewById(R.id.btn_retour_9);


        entreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                if(getIntent().hasExtra("username")){
                    listeRecetteIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    listeRecetteIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    listeRecetteIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                listeRecetteIntent.putExtra("type", "Entrées");
                startActivity(listeRecetteIntent);

            }
        });


        platBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                if(getIntent().hasExtra("username")){
                    listeRecetteIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    listeRecetteIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    listeRecetteIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                listeRecetteIntent.putExtra("type", "Plats");
                startActivity(listeRecetteIntent);

            }
        });


        dessertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                if(getIntent().hasExtra("username")){
                    listeRecetteIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    listeRecetteIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    listeRecetteIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                listeRecetteIntent.putExtra("type", "Desserts");
                startActivity(listeRecetteIntent);

            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChoixTypeRecette.this.finish();

            }
        });
    }
}
