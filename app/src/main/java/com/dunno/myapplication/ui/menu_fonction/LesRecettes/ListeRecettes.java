package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu.MainActivity;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.AddIngredient;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import java.util.ArrayList;

public class ListeRecettes extends AppCompatActivity {

    ArrayList<Integer> recipeID;
    ArrayList<String> recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recettes);

        final ListView lv_recipe = (ListView) findViewById(R.id.lv_recipe);
        Button retourBtn = (Button) findViewById(R.id.btn_retour_2);

        if(getIntent().hasExtra("liste_recipe_id")){

            recipeID = getIntent().getExtras().getIntegerArrayList("liste_recipe_id");
            recipeName = getIntent().getExtras().getStringArrayList("liste_recipe_name");

        }
        else {
            //_______________________________________________________________________
            //TODO: Recuperer depuis le serveur
            recipeID = new ArrayList<>();
            recipeID.add(1);
            recipeID.add(2);

            recipeName = new ArrayList<>();
            recipeName.add("Pate bolognaise");
            recipeName.add("Boeuf bourguignon");

            //_______________________________________________________________________
        }

        final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeID, recipeName);
        lv_recipe.setAdapter(recipeAdapter);

        lv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);
                if(getIntent().hasExtra("username")){
                    printRecipeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    printRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                    printRecipeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                }
                printRecipeIntent.putExtra("liste_recipe_id", recipeID);
                printRecipeIntent.putExtra("liste_recipe_name", recipeName);
                printRecipeIntent.putExtra("id_recipe", (int) lv_recipe.getAdapter().getItem(position));
                startActivity(printRecipeIntent);
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
