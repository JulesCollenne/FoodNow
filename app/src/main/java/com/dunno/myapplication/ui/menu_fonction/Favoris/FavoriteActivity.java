package com.dunno.myapplication.ui.menu_fonction.Favoris;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.AddIngredient;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.MonFrigoType;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.MonFrigoTypeChoice;
import com.dunno.myapplication.ui.menu_fonction.Favoris.getFavoriteRequest;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    ArrayList<String> recipesName = new ArrayList<>();
    ArrayList<Integer> recipesID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        /**
         * Requete a serveur
         */

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        for(int i = 0; i < jsonResponse.length()/2; i++) {
                            recipesName.add(jsonResponse.getString("name"+i));
                            recipesID.add(jsonResponse.getInt("ID"+i));
                        }

                        //Button btnRetour = (Button) findViewById(R.id.btn_retour_1);

                        final ListView listFavorite = (ListView) findViewById(R.id.lv_favorite);


                        final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipesID, recipesName);
                        listFavorite.setAdapter(recipeAdapter);


                        /*btnRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent retourIntent = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                                retourIntent.putExtra("liste_ingredient", ingredientAdded);
                                retourIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                if(getIntent().hasExtra("username")){
                                    retourIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    retourIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    retourIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }
                                startActivity(retourIntent);

                            }
                        });*/


                        listFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                int recipeID = (int) listFavorite.getAdapter().getItem(position);

                                Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);


                                printRecipeIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                printRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                printRecipeIntent.putExtra("password", getIntent().getExtras().getString("password"));

                                printRecipeIntent.putExtra("id_recipe", recipeID);

                                startActivity(printRecipeIntent);

                            }
                        });



                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
                        builder.setMessage("Erreur")
                                .setNegativeButton("Réessayer", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(FavoriteActivity.this);
                    builder2.setMessage("RIP " +e.getMessage())
                            .setNegativeButton("Réessayer", null)
                            .create()
                            .show();
                }
            }
        };

        getFavoriteRequest getfavoriteRequest = new getFavoriteRequest(getIntent().getExtras().getString("username"), responseListener);
        RequestQueue queue = Volley.newRequestQueue(FavoriteActivity.this);
        queue.add(getfavoriteRequest);
    }
}
