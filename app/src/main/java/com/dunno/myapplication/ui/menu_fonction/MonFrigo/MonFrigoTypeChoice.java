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

        ImageButton meatBtn = (ImageButton) findViewById(R.id.meatButton);
        ImageButton vegetableBtn = (ImageButton) findViewById(R.id.vegetablesButton);
        ImageButton feculentBtn = (ImageButton) findViewById(R.id.feculentButton);
        ImageButton diversBtn = (ImageButton) findViewById(R.id.diversButton);

        Button retourBtn = (Button) findViewById(R.id.btn_retour_9);

        if(getIntent().hasExtra("liste_ingredient")) {
            ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
            ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");
        }

        meatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 0);
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


        vegetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 1);
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


        feculentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 2);
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


        diversBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 3);
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

                Intent retourIntent = new Intent(getApplicationContext(), AddIngredient.class);
                retourIntent.putExtra("liste_ingredient", ingredientAdded);
                retourIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                if(getIntent().hasExtra("username")){
                    retourIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    retourIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    retourIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(retourIntent);

            }
        });


    }
}
