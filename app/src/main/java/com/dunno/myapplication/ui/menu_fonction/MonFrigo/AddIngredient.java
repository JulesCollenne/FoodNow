package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.menu.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddIngredient extends AppCompatActivity {

    ArrayList<String> ingredientAdded = new ArrayList<>();
    ArrayList<String> ingredientAddedID = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        if(getIntent().hasExtra("liste_ingredient")){

            ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
            ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");

            final ListView listIngredient = (ListView) findViewById(R.id.lv_ingredient_chosen);

            ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientAdded, ingredientAddedID);

            listIngredient.setAdapter(ingredientAdapter);

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



        Button addBtn = (Button) findViewById(R.id.btn_add_ingredient);
        Button searchBtn = (Button) findViewById(R.id.button_search_recipee);
        Button retourBtn = (Button) findViewById(R.id.btn_retour_2);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addIngredient = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                addIngredient.putExtra("liste_ingredient", ingredientAdded);
                addIngredient.putExtra("liste_ingredient_id", ingredientAddedID);
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


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                ArrayList<Integer> recipeID = new ArrayList<>();
                                ArrayList<String> recipeName = new ArrayList<>();

                                for(int i = 0; i<jsonResponse.length()/2; i++){

                                    recipeID.add(jsonResponse.getInt("id"+i));
                                    recipeName.add(jsonResponse.getString("name"+i));

                                }

                                Intent searchRecipeIntent = new Intent(getApplicationContext(), RecipeFromIngredient.class);
                                if(getIntent().hasExtra("username")){
                                    searchRecipeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    searchRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    searchRecipeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }
                                searchRecipeIntent.putExtra("liste_recipe_id", recipeID);
                                searchRecipeIntent.putExtra("liste_recipe_name", recipeName);
                                searchRecipeIntent.putExtra("liste_ingredient", ingredientAdded);
                                searchRecipeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                startActivity(searchRecipeIntent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddIngredient.this);
                                builder.setMessage("Erreur lors de l'ajout")
                                        .setNegativeButton("Réessayer", null)
                                        .create()
                                        .show();

                                //En attendant que le .php fonctionne
                                Intent searchRecipeIntent = new Intent(getApplicationContext(), RecipeFromIngredient.class);
                                if(getIntent().hasExtra("username")){
                                    searchRecipeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    searchRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    searchRecipeIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }
                                searchRecipeIntent.putExtra("liste_ingredient", ingredientAdded);
                                searchRecipeIntent.putExtra("liste_ingredient", ingredientAddedID);
                                startActivity(searchRecipeIntent);
                                //

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(AddIngredient.this);
                            builder2.setMessage("RIP " +e.getMessage())
                                    .setNegativeButton("Réessayer", null)
                                    .create()
                                    .show();
                        }
                    }
                };

                getRecipeFromIngredientRequest getRecipeRequest = new getRecipeFromIngredientRequest(ingredientAddedID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddIngredient.this);
                queue.add(getRecipeRequest);


            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddIngredient.this.finish();
            }
        });


    }
}
