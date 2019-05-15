package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

public class ListeRecetteFromType extends AppCompatActivity {

    ArrayList<Integer> recipeID = new ArrayList<>();
    ArrayList<String> recipeName = new ArrayList<>();
    String type;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recettes);


        type = Objects.requireNonNull(getIntent().getExtras()).getString("type");

        final ListView lv_recipe = findViewById(R.id.lv_all_recipe);
        final Button retourBtn = findViewById(R.id.btn_retour_8);
        TextView tvTitle = findViewById(R.id.tv_list_all_recipe);





        String typeTitle;

        switch(type) {

            case "Plats":
                type = "Plat";
                typeTitle = getString(R.string.type_recette_plat);
                break;

            case "Entrées":
                type = "Entree";
                typeTitle = getString(R.string.type_recette_entrée);
                break;

            case "Desserts":
                type = "Dessert";
                typeTitle = getString(R.string.type_recette_dessert);
                break;


            default:
                type = "TYPE";
                typeTitle = "TYPE";

        }

        tvTitle.setText(typeTitle);

        if(getIntent().hasExtra("liste_recipe_id")){

            recipeID = getIntent().getExtras().getIntegerArrayList("liste_recipe_id");
            recipeName = getIntent().getExtras().getStringArrayList("liste_recipe_name");

        }
        else {


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {


                            for(int i = 0; i < jsonResponse.length()/2; i++) {
                                recipeName.add(jsonResponse.getString("name"+i));
                                recipeID.add(jsonResponse.getInt("ID"+i));
                            }


                            final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeID, recipeName);
                            lv_recipe.setAdapter(recipeAdapter);

                            lv_recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);
                                    if(getIntent().hasExtra("username"))
                                        printRecipeIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    printRecipeIntent.putExtra("id_recipe", (int) lv_recipe.getAdapter().getItem(position));
                                    startActivity(printRecipeIntent);

                                }

                            });


                            retourBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    ListeRecetteFromType.this.finish();

                                }
                            });
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListeRecetteFromType.this);
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

            getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
            queue.add(getRecipeRequest);

        }

    }
}
