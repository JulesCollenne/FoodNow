package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MonFrigoType extends AppCompatActivity {

    ArrayList<String> ingredientAdded;
    ArrayList<String> ingredientAddedID;
    ArrayList<String> ingredientName = new ArrayList<>();
    ArrayList<String> ingredientID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type);

        String type = getIntent().getExtras().getString("type");
        ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
        ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");

        String typeName;

        switch(type) {

            case "Viande":
                typeName = "Viandes";
                break;

            case "Légume":
                typeName = "Légumes";
                break;

            case "Fruit":
                typeName = "Fruits";
                break;

            case "Condiment":
                typeName = "Condiments";
                break;

            case "Oeufs et fromages":
                typeName = "Oeufs et fromages";
                break;

            case "Céréales":
                typeName = "Céréales";
                break;

            default:
                typeName = "TYPE";

        }

        TextView tv_type = (TextView) findViewById(R.id.tv_ingredientType);
        tv_type.setText(typeName);






        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        for(int i = 0; i < jsonResponse.length()/2; i++) {
                            ingredientName.add(jsonResponse.getString("name"+i));
                            ingredientID.add(jsonResponse.getString("ID"+i));
                        }

                        Button btnRetour = (Button) findViewById(R.id.btn_retour_1);

                        final ListView listIngredient = (ListView) findViewById(R.id.lv_ingredients);


                        final IngredientAdapter ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientName, ingredientID);
                        listIngredient.setAdapter(ingredientAdapter);


                        btnRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MonFrigoType.this.finish();

                            }
                        });


                        listIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                IngredientAdapter IA = (IngredientAdapter) listIngredient.getAdapter();
                                String newIngredient = IA.getName(position);
                                String newIngredientID = IA.getID(position);

                                if(!ingredientAdded.contains(newIngredient)) {
                                    ingredientAdded.add(newIngredient);
                                    ingredientAddedID.add(newIngredientID);
                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MonFrigoType.this);
                                    builder.setMessage("Vous avez déjà ajouté cet ingrédient")
                                            .setNegativeButton("Ok", null)
                                            .create()
                                            .show();

                                    return;
                                }

                                Intent addedIngredientIntent = new Intent(getApplicationContext(), AddIngredient.class);
                                addedIngredientIntent.putExtra("liste_ingredient", ingredientAdded);
                                addedIngredientIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                if(getIntent().hasExtra("username")){
                                    addedIngredientIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    addedIngredientIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    addedIngredientIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }
                                startActivity(addedIngredientIntent);

                            }
                        });



                    } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MonFrigoType.this);
                            builder.setMessage("Erreur lors de l'ajout")
                                    .setNegativeButton("Réessayer", null)
                                    .create()
                                    .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MonFrigoType.this);
                    builder2.setMessage("RIP " +e.getMessage())
                            .setNegativeButton("Réessayer", null)
                            .create()
                            .show();
                }
            }
        };

        getIngredientPerTypeRequest getIngredientRequest = new getIngredientPerTypeRequest(type, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MonFrigoType.this);
        queue.add(getIngredientRequest);

    }
}

