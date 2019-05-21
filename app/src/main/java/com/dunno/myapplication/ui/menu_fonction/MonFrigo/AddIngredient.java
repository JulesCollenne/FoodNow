package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.menu_fonction.Request.getRecipeFromIngredientRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 *
 *  Affiche la liste d'ingrédient qui a été ajouté
 *
 */


public class AddIngredient extends AppCompatActivity {

    ArrayList<String> ingredientAdded = new ArrayList<>();
    ArrayList<String> ingredientAddedID = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);


        // Récupère les listes d'ingrédients ajoutés à jour
        if(getIntent().hasExtra("liste_ingredient")){

            ingredientAdded = Objects.requireNonNull(getIntent().getExtras()).getStringArrayList("liste_ingredient");
            ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");

            final ListView listIngredient = findViewById(R.id.lv_ingredient_chosen);

            ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientAdded, ingredientAddedID);

            listIngredient.setAdapter(ingredientAdapter);

            // Lorsqu'on clique sur un élément de la liste
            listIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    IngredientAdapter IA = (IngredientAdapter) listIngredient.getAdapter();

                    String removedIngredient = IA.getName(position);
                    ingredientAdded.remove(removedIngredient);

                    String removedIngredientID = IA.getID(position);
                    ingredientAddedID.remove(removedIngredientID);

                    ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientAdded, ingredientAddedID);
                    listIngredient.setAdapter(ingredientAdapter);

                }
            });

        }


        Button addBtn = findViewById(R.id.btn_add_ingredient);
        Button searchBtn = findViewById(R.id.button_search_recipee);
        ImageButton retourBtn = findViewById(R.id.btn_retour_2);


        // Bouton ajout d'ingrédient: Mène a l'acitivité MonFrigoTypeChoice
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddIngredient.this.finish();
                Intent addIngredient = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                if(getIntent().hasExtra("username"))
                    addIngredient.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                addIngredient.putExtra("liste_ingredient", ingredientAdded);
                addIngredient.putExtra("liste_ingredient_id", ingredientAddedID);
                startActivity(addIngredient);

            }
        });


        // Bouton de recherche: Lance la requete getIngredientPerType au serveur et ouvre une nouvelle activité RecipeFromIngredient
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Uniquement s'il y a au moins un ingrédient dans la liste
                if(ingredientAdded.size() == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddIngredient.this);
                    builder.setMessage(R.string.add_ingredient_0)
                            .setNegativeButton(R.string.alert_dialog_ok, null)
                            .create()
                            .show();

                    return;
                }



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                ArrayList<Integer> recipeID = new ArrayList<>();
                                ArrayList<String> recipeName = new ArrayList<>();


                                //Récupère la liste de recettes envoyée par le serveur
                                for(int i = 0; i<jsonResponse.length()/2; i++){

                                    recipeID.add(jsonResponse.getInt("id"+i));
                                    recipeName.add(jsonResponse.getString("name"+i));

                                }

                                Intent searchRecipeIntent = new Intent(getApplicationContext(), RecipeFromIngredient.class);
                                if(getIntent().hasExtra("username"))
                                    searchRecipeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                searchRecipeIntent.putExtra("liste_recipe_id", recipeID);
                                searchRecipeIntent.putExtra("liste_recipe_name", recipeName);
                                startActivity(searchRecipeIntent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddIngredient.this);
                                builder.setMessage(R.string.alert_dialog_erreur_base_de_donnée)
                                        .setNegativeButton(R.string.alert_dialog_reesayer, null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                getRecipeFromIngredientRequest getRecipeRequest = new getRecipeFromIngredientRequest(ingredientAddedID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddIngredient.this);
                queue.add(getRecipeRequest);


            }
        });


        // Bouton retour
        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddIngredient.this.finish();
            }
        });


    }
}
