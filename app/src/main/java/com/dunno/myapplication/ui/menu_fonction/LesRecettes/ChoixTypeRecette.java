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

        ImageButton entreeBtn = findViewById(R.id.entreeButton);
        ImageButton platBtn = findViewById(R.id.platButton);
        ImageButton dessertBtn = findViewById(R.id.dessertButton);

        Button retourBtn = findViewById(R.id.btn_retour_9);


        entreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                listeRecetteIntent.putExtra("type", "Entrées");
                startActivity(listeRecetteIntent);

            }
        });


        platBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                listeRecetteIntent.putExtra("type", "Plats");
                startActivity(listeRecetteIntent);

            }
        });


        dessertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
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
