package com.dunno.myapplication.ui.menu_fonction.PrintRecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu_fonction.Favoris.FavoriteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
    Page qui affiche une recette en particulier ( image, temps de préparations, étaps, nb de personnes... )
 */
public class PrintRecipe extends AppCompatActivity {

    private ImageView btnFavorite;
    private int recipeID;
    private int userID;
    private boolean isFavorite;
    String recipeName;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_recipe);

        recipeID = Objects.requireNonNull(getIntent().getExtras()).getInt("id_recipe");

        /*
         * Affiche la recette en prenant les infos depuis la bdd
         */

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        TextView tv_name = findViewById(R.id.tv_recipeName);
                        TextView tv_tmp = findViewById(R.id.tv_tmp_prep);
                        TextView tv_nb = findViewById(R.id.tv_nb_pers);
                        TextView tv_desc = findViewById(R.id.tv_recipe_description);
                        tv_desc.setMovementMethod(new ScrollingMovementMethod());

                        ImageView iv_recipe = findViewById(R.id.recipe_image_big);

                        recipeName = jsonResponse.getString("name");
                        tv_name.setText(recipeName);
                        String tmp = jsonResponse.getInt("time")+" min";
                        tv_tmp.setText(tmp);
                        tmp = jsonResponse.getInt("nb") + "";
                        tv_nb.setText(tmp);

                        String imageName = "recid"+recipeID;
                        Context context = iv_recipe.getContext();
                        int id = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                        iv_recipe.setImageResource(id);

                        tv_desc.setText(jsonResponse.getString("description"));

                        tv_desc.setMovementMethod(new ScrollingMovementMethod());

                        ImageButton bRetour = findViewById(R.id.btn_retour_7);


                        if(getIntent().hasExtra("favoris")){

                            bRetour.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent favorisIntent = new Intent(getApplicationContext(), FavoriteActivity.class);
                                    if (getIntent().hasExtra("username"))
                                        favorisIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                    PrintRecipe.this.finish();
                                    startActivity(favorisIntent);


                                }
                            });

                        }else {
                            bRetour.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    PrintRecipe.this.finish();

                                }
                            });
                        }

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PrintRecipe.this);
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

        getRecipeFromIDRequest getRecipeFromID = new getRecipeFromIDRequest(recipeID+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(PrintRecipe.this);
        queue.add(getRecipeFromID);

        /*
         * Bouton favoris
         */

        this.btnFavorite = findViewById(R.id.btn_favorite);
        btnFavorite.setVisibility(View.INVISIBLE);

        /* On vérifie si l'utilisateur est connecté */
        if(getIntent().hasExtra("username")) {
            String pseudo = getIntent().getExtras().getString("username");
            btnFavorite.setVisibility(View.VISIBLE);
            btnFavorite.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            /*
             * Récupération de l'ID de l'utilisateur dans la bdd
             */
            /*
             * On check si la recette est déjà en favoris pour cette utilisateur
             */

            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            isFavorite = jsonResponse.getBoolean("isFavorite");
                            userID = jsonResponse.getInt("idUser");

                            if(!isFavorite) {
                                btnFavorite.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                            }
                            else{
                                btnFavorite.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                            }




                            btnFavorite.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onClick(View view) {

                                    /* le click ne fonctionne que si l'utilisateur est connecté */

                                    /*
                                     * Si la recette est déjà en favoris on l'enleve des favoris, sinon on l'ajoute
                                     */
                                    Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");

                                                if (success) {
                                                    if(isFavorite) {
                                                        isFavorite = false;
                                                        btnFavorite.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                                                    }
                                                    else {
                                                        isFavorite = true;
                                                        btnFavorite.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                                                    }
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(PrintRecipe.this);
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

                                    String paramIsFavorite;
                                    if(isFavorite)
                                        paramIsFavorite = "true";
                                    else
                                        paramIsFavorite = "false";

                                    ChangeRecipeStateInFavoriteRequest changeRecipeStateInFavorite = new ChangeRecipeStateInFavoriteRequest(userID+"", recipeID+"", paramIsFavorite, responseListener3);
                                    RequestQueue queue3 = Volley.newRequestQueue(PrintRecipe.this);
                                    queue3.add(changeRecipeStateInFavorite);

                                }
                            });




                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PrintRecipe.this);
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

            isRecipeFavoriteRequest isRecipeFavorite = new isRecipeFavoriteRequest(pseudo, recipeID+"", responseListener2);
            RequestQueue queue2 = Volley.newRequestQueue(PrintRecipe.this);
            queue2.add(isRecipeFavorite);
        }
    }
}

