package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import java.util.ArrayList;
import java.util.Objects;

/*
 *
 * Affiche les recettes trouvés selon la liste d'ingrédients
 *
 */

public class RecipeFromIngredient extends AppCompatActivity {

    ArrayList<Integer> recipeID;
    ArrayList<String> recipeName;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_from_ingredient);

        Button changeIngredient = findViewById(R.id.btn_recipe_change);
        final ListView lv_recipe = findViewById(R.id.lv_recipe_from_ingredients);

        changeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecipeFromIngredient.this.finish();

            }
        });


        if(getIntent().hasExtra("liste_recipe_id")){

            recipeID = Objects.requireNonNull(getIntent().getExtras()).getIntegerArrayList("liste_recipe_id");
            recipeName = getIntent().getExtras().getStringArrayList("liste_recipe_name");

        }

        final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeID, recipeName);
        lv_recipe.setAdapter(recipeAdapter);

        lv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);
                if(getIntent().hasExtra("username"))
                    printRecipeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                printRecipeIntent.putExtra("id_recipe", (int) lv_recipe.getAdapter().getItem(position));
                startActivity(printRecipeIntent);

            }

        });
    }
}

