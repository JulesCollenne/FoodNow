package com.dunno.myapplication.ui.menu_fonction.PrintRecipe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu_fonction.LesRecettes.ListeRecetteFromType;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.RecipeFromIngredient;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.getRecipeFromIDRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PrintRecipe extends AppCompatActivity {

    private ImageView btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_recipe);

        int recipeID = getIntent().getExtras().getInt("id_recipe");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        TextView tv_name = (TextView) findViewById(R.id.tv_recipeName);
                        TextView tv_tmp = (TextView) findViewById(R.id.tv_tmp_prep);
                        TextView tv_nb = (TextView) findViewById(R.id.tv_nb_pers);
                        TextView tv_desc = (TextView) findViewById(R.id.tv_recipe_description);
                        tv_desc.setMovementMethod(new ScrollingMovementMethod());

                        ImageView iv_recipe = (ImageView) findViewById(R.id.recipe_image_big);

                        tv_name.setText(jsonResponse.getString("name"));
                        tv_tmp.setText("Temps: "+jsonResponse.getInt("time")+" min");
                        tv_nb.setText(jsonResponse.getInt("nb")+" personnes");
                        iv_recipe.setImageResource(R.drawable.ic_menu_camera);
                        tv_desc.setText(jsonResponse.getString("description"));

                        tv_desc.setMovementMethod(new ScrollingMovementMethod());

                        Button bRetour = (Button) findViewById(R.id.btn_retour_7);

                        bRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                PrintRecipe.this.finish();

                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(PrintRecipe.this);
                    builder2.setMessage("RIP " +e.getMessage())
                            .setNegativeButton("Réessayer", null)
                            .create()
                            .show();
                }
            }
        };


        getRecipeFromIDRequest getRecipeFromID = new getRecipeFromIDRequest(recipeID+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(PrintRecipe.this);
        queue.add(getRecipeFromID);

        /**
         * Bouton favoris
         */

        this.btnFavorite = findViewById(R.id.btn_favorite);

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

