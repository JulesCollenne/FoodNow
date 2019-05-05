package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.menu.MainActivity;

import java.util.ArrayList;

public class AddIngredient extends AppCompatActivity {

    ArrayList<String> ingredientAdded = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        if(getIntent().hasExtra("liste_ingredient")){

            ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");

            final ListView listIngredient = (ListView) findViewById(R.id.lv_ingredient_chosen);

            ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientAdded);

            listIngredient.setAdapter(ingredientAdapter);

            listIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String removedIngredient = (String) listIngredient.getAdapter().getItem(position);
                    ingredientAdded.remove(removedIngredient);


                    ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientAdded);
                    listIngredient.setAdapter(ingredientAdapter);

                }
            });

        }



        Button addBtn = (Button) findViewById(R.id.btn_add_ingredient);
        Button searchBtn = (Button) findViewById(R.id.button_search_recipee);
        Button retourBtn = (Button) findViewById(R.id.btn_retour_2);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addIngredient = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                addIngredient.putExtra("liste_ingredient", ingredientAdded);
                if(getIntent().hasExtra("username")){
                    addIngredient.putExtra("email", getIntent().getExtras().getString("email"));
                    addIngredient.putExtra("username", getIntent().getExtras().getString("username"));
                    addIngredient.putExtra("password", getIntent().getExtras().getString("password"));
                }
                startActivity(addIngredient);

            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ingredientAdded.size() == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddIngredient.this);
                    builder.setMessage("Vous devez ajouter au moins 1 ingrédient")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();

                    return;
                }

                Intent searchRecipeIntent = new Intent(getApplicationContext(), RecipeFromIngredient.class);
                if(getIntent().hasExtra("username")){
                    searchRecipeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    searchRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    searchRecipeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                searchRecipeIntent.putExtra("liste_ingredient", ingredientAdded);
                startActivity(searchRecipeIntent);

            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent retourIntent = new Intent(getApplicationContext(), MainActivity.class);
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
