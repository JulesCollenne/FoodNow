package com.dunno.myapplication.ui.menu_fonction.Favoris;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Page des favoris de l'utilisateur connecté
 */
public class FavoriteActivity extends AppCompatActivity {

    ArrayList<String> recipesName = new ArrayList<>();
    ArrayList<Integer> recipesID = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        ImageButton btnRetour = findViewById(R.id.btn_retour_12);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FavoriteActivity.this.finish();

            }
        });

        /*
         * Requete a serveur
         */
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        for (int i = 0; i < jsonResponse.length() / 2; i++) {
                            recipesName.add(jsonResponse.getString("name" + i));
                            recipesID.add(jsonResponse.getInt("ID" + i));
                        }

                        final ListView listFavorite = findViewById(R.id.lv_favorite);


                        final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipesID, recipesName);
                        listFavorite.setAdapter(recipeAdapter);


                        listFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                int recipeID = (int) listFavorite.getAdapter().getItem(position);

                                Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);
                                printRecipeIntent.putExtra("favoris", "salut");
                                if (getIntent().hasExtra("username"))
                                    printRecipeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                printRecipeIntent.putExtra("id_recipe", recipeID);
                                FavoriteActivity.this.finish();
                                startActivity(printRecipeIntent);

                            }
                        });

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
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

        getFavoriteRequest getfavoriteRequest = new getFavoriteRequest(Objects.requireNonNull(getIntent().getExtras()).getString("username"), responseListener);
        RequestQueue queue = Volley.newRequestQueue(FavoriteActivity.this);
        queue.add(getfavoriteRequest);
    }


}
