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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.ListAdaptater.RecipeAdapter;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ListeRecetteFromType extends AppCompatActivity {

    ArrayList<Integer> recipeID = new ArrayList<>();
    ArrayList<String> recipeName = new ArrayList<>();
    ArrayList<Integer> recipeIDOutput = new ArrayList<>();
    ArrayList<String> recipeNameOutput = new ArrayList<>();
    String type;
    String restriction = "Aucune";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recettes);


        type = Objects.requireNonNull(getIntent().getExtras()).getString("type");

        final ListView lv_recipe = findViewById(R.id.lv_all_recipe);
        final ImageButton retourBtn = findViewById(R.id.btn_retour_8);
        TextView tvTitle = findViewById(R.id.tv_list_all_recipe);

        final CheckBox vegetarien = findViewById(R.id.cb_vege);
        final CheckBox vegan = findViewById(R.id.cb_vegan);

        if(vegetarien.isChecked()){
            restriction = "Végétarien";
        }
        if(vegan.isChecked()){
            restriction = "Végan";
        }

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

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        lv_recipe.setAdapter(null);
                        recipeID.clear();
                        recipeName.clear();

                        for(int i = 0; i < jsonResponse.length()/2; i++) {
                            recipeName.add(jsonResponse.getString("name"+i));
                            recipeID.add(jsonResponse.getInt("ID"+i));
                        }


                        final RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeID, recipeName);
                        lv_recipe.setAdapter(recipeAdapter);

                        SearchView sv_recipe = findViewById(R.id.searchRecipe);
                        sv_recipe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                getRecipe(newText);
                                final RecipeAdapter filteredRecipeAdapter = new RecipeAdapter(getApplicationContext(), recipeIDOutput, recipeNameOutput);
                                lv_recipe.setAdapter(filteredRecipeAdapter);
                                return false;
                            }
                        });

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

        if(getIntent().hasExtra("liste_recipe_id")){

            recipeID = getIntent().getExtras().getIntegerArrayList("liste_recipe_id");
            recipeName = getIntent().getExtras().getStringArrayList("liste_recipe_name");

        }
        else {

            getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type,restriction, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
            queue.add(getRecipeRequest);
        }

        vegetarien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                lv_recipe.setAdapter(null);
                recipeID.clear();
                recipeName.clear();
                if(vegan.isChecked()) {
                    restriction = "Végan";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }
                else if(vegetarien.isChecked()){
                    restriction = "Végétarien";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }
                else{
                    restriction = "Aucune";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }

            }
        });

        vegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                lv_recipe.setAdapter(null);
                recipeID.clear();
                recipeName.clear();
                if(isChecked) {
                    restriction = "Végan";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }
                else if(vegetarien.isChecked()){
                    restriction = "Végétarien";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }
                else{
                    restriction = "Aucune";
                    getRecipeFromTypeRequest getRecipeRequest = new getRecipeFromTypeRequest(type, restriction, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ListeRecetteFromType.this);
                    queue.add(getRecipeRequest);
                }

            }
        });
    }

    public void getRecipe(String query) {

        recipeIDOutput.clear();
        recipeNameOutput.clear();

       /* AlertDialog.Builder builder = new AlertDialog.Builder(MonFrigoType.this);
        builder.setMessage("query = /"+query+"/")
                .setNegativeButton(R.string.alert_dialog_reesayer, null)
                .create()
                .show();*/


        for (int i = 0; i < recipeName.size(); i++) {
            if (recipeName.get(i).toLowerCase().startsWith(query.toLowerCase())) {
                if(!recipeNameOutput.contains(recipeName.get(i))) {
                    recipeNameOutput.add(recipeName.get(i));
                    recipeIDOutput.add(recipeID.get(i));
                }
            }
        }
    }
}
