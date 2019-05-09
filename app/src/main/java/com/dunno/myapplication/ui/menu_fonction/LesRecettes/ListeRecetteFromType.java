package com.dunno.myapplication.ui.menu_fonction.LesRecettes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.AddIngredient;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.MonFrigoType;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.MonFrigoTypeChoice;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.getIngredientPerTypeRequest;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListeRecetteFromType extends AppCompatActivity {

    ArrayList<Integer> recipeID = new ArrayList<>();
    ArrayList<String> recipeName = new ArrayList<>();
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recettes);



        type = getIntent().getExtras().getString("type");

        final ListView lv_recipe = (ListView) findViewById(R.id.lv_all_recipe);
        final Button retourBtn = (Button) findViewById(R.id.btn_retour_8);
        TextView tvTitle = (TextView) findViewById(R.id.tv_list_all_recipe);




        tvTitle.setText(type);


        switch(type) {

            case "Plats":
                type = "Plat";
                break;

            case "Entrées":
                type = "Entree";
                break;

            case "Desserts":
                type = "Dessert";
                break;


            default:
                type = "TYPE";

        }



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

                                    ListeRecetteFromType.this.finish();

                                }
                            });


                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListeRecetteFromType.this);
                            builder.setMessage("Erreur lors de l'ajout")
                                    .setNegativeButton("Réessayer", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ListeRecetteFromType.this);
                        builder2.setMessage("RIP " +e.getMessage())
                                .setNegativeButton("Réessayer", null)
                                .create()
                                .show();
                    }
                }
            };

            getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
            queue.add(getRecipeRequest);






            /*____________________________________________________________________
            //TODO: Recuperer depuis le serveur: Crée une classe java getAllRecipeRequest qui envoie une demande a un .php, le .php lui recupere toute les recettes et les transmets.
            recipeID = new ArrayList<>();
            recipeID.add(1);
            recipeID.add(2);

            recipeName = new ArrayList<>();
            recipeName.add("Pate bolognaise");
            recipeName.add("Boeuf bourguignon");

            //_______________________________________________________________________*/
        }





    }
}
