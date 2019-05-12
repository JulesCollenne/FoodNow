package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dunno.myapplication.R;

import java.util.ArrayList;

public class MonFrigoTypeChoice extends AppCompatActivity {

    ArrayList<String> ingredientAdded = new ArrayList<>();
    ArrayList<String> ingredientAddedID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type_choice);

        Button meatBtn = (Button) findViewById(R.id.btn_viande);
        Button fruitBtn = (Button) findViewById(R.id.btn_fruit);
        Button vegeBtn = (Button) findViewById(R.id.btn_legume);
        Button eggBtn = (Button) findViewById(R.id.btn_oeuf);
        Button cerealBtn = (Button) findViewById(R.id.btn_cereal);
        Button condimBtn = (Button) findViewById(R.id.btn_condiment);



        Button retourBtn = (Button) findViewById(R.id.btn_retour_9);

        if(getIntent().hasExtra("liste_ingredient")) {
            ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
            ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");
        }

        meatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Viande");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });


        fruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Fruit");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });


        vegeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Légume");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });


        eggBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Oeufs et fromages");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });


        cerealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Céréales");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });


        condimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", "Condiment");
                monFrigoTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                monFrigoTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    monFrigoTypeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    monFrigoTypeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    monFrigoTypeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(monFrigoTypeIntent);

            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonFrigoTypeChoice.this.finish();

            }
        });


    }
}

