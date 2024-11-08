package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dunno.myapplication.R;

import java.util.Objects;

/**
 * Page du choix du type de la recette ( Entrée, plat, dessert )
 */
public class ChoixTypeRecette extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_type_recette);

        ImageButton entreeBtn = findViewById(R.id.entreeButton);
        ImageButton platBtn = findViewById(R.id.platButton);
        ImageButton dessertBtn = findViewById(R.id.dessertButton);

        ImageButton retourBtn = findViewById(R.id.btn_retour_13);

        entreeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                listeRecetteIntent.putExtra("type", "Entrées");
                if(getIntent().hasExtra("username"))
                    listeRecetteIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                startActivity(listeRecetteIntent);

            }
        });


        platBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                listeRecetteIntent.putExtra("type", "Plats");
                if(getIntent().hasExtra("username"))
                    listeRecetteIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                startActivity(listeRecetteIntent);

            }
        });


        dessertBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Intent listeRecetteIntent = new Intent(getApplicationContext(), ListeRecetteFromType.class);
                listeRecetteIntent.putExtra("type", "Desserts");
                if(getIntent().hasExtra("username"))
                    listeRecetteIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
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
