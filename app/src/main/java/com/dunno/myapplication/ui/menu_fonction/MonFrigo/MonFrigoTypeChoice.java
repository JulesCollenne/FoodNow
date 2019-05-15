package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dunno.myapplication.R;

import java.util.ArrayList;
import java.util.Objects;

public class MonFrigoTypeChoice extends AppCompatActivity {

    ArrayList<String> ingredientAdded = new ArrayList<>();
    ArrayList<String> ingredientAddedID = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type_choice);

        Button meatBtn = findViewById(R.id.btn_viande);
        Button fruitBtn = findViewById(R.id.btn_fruit);
        Button vegeBtn = findViewById(R.id.btn_legume);
        Button eggBtn = findViewById(R.id.btn_oeuf);
        Button cerealBtn = findViewById(R.id.btn_cereal);
        Button condimBtn = findViewById(R.id.btn_condiment);



        Button retourBtn = findViewById(R.id.btn_retour_9);

        if(getIntent().hasExtra("liste_ingredient")) {
            ingredientAdded = Objects.requireNonNull(getIntent().getExtras()).getStringArrayList("liste_ingredient");
            ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");
        }

        meatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Viande");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });


        fruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Fruit");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });


        vegeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Légume");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });


        eggBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Oeufs et fromages");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });


        cerealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Céréales");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });


        condimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Condiment");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(monFrigoTypeIntent);

            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();
                Intent AddIngredientIntent = new Intent(getApplicationContext(), AddIngredient.class);
                AddIngredientIntent.putExtra("liste_ingredient", ingredientAdded);
                AddIngredientIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(AddIngredientIntent);

            }
        });


    }
}

