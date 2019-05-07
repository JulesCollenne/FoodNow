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
import com.dunno.myapplication.ui.menu_fonction.LesRecettes.ListeRecettes;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.RecipeFromIngredient;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.getRecipeFromIDRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PrintRecipe extends AppCompatActivity {

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
                        iv_recipe.setImageResource(R.drawable.viandepoisson);
                        tv_desc.setText(jsonResponse.getString("description"));

                        tv_desc.setMovementMethod(new ScrollingMovementMethod());

                        Button bRetour = (Button) findViewById(R.id.btn_retour_7);

                        bRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent retourIntent;

                                if(getIntent().hasExtra("liste_ingredient")){
                                    retourIntent = new Intent(getApplicationContext(), RecipeFromIngredient.class);
                                    retourIntent.putExtra("liste_ingredient", getIntent().getExtras().getStringArrayList("liste_ingredient"));
                                    retourIntent.putExtra("liste_ingredient_id", getIntent().getExtras().getStringArrayList("liste_ingredient_id"));
                                }
                                else{
                                    retourIntent = new Intent(getApplicationContext(), ListeRecettes.class);
                                }


                                retourIntent.putExtra("liste_recipe_id", getIntent().getExtras().getIntegerArrayList("liste_recipe_id"));
                                retourIntent.putExtra("liste_recipe_name", getIntent().getExtras().getStringArrayList("liste_recipe_name"));


                                if(getIntent().hasExtra("username")){
                                    retourIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    retourIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    retourIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }





                                startActivity(retourIntent);

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


    }
}

