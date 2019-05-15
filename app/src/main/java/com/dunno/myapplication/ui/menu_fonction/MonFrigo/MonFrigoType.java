package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.util.Objects;


public class MonFrigoType extends AppCompatActivity {

    ArrayList<String> ingredientAdded;
    ArrayList<String> ingredientAddedID;
    ArrayList<String> ingredientName = new ArrayList<>();
    ArrayList<String> ingredientID = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type);

        String type = Objects.requireNonNull(getIntent().getExtras()).getString("type");
        ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
        ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");

        String typeName;

        assert type != null;
        switch(type) {

            case "Viande":
                typeName = getString(R.string.choix_ingredient_type_viandes);
                break;

            case "Légume":
                typeName = getString(R.string.choix_ingredient_type_légumes);
                break;

            case "Fruit":
                typeName = getString(R.string.choix_ingredient_type_fruits);
                break;

            case "Condiment":
                typeName = getString(R.string.choix_ingredient_type_condiments);
                break;

            case "Oeufs et fromages":
                typeName = getString(R.string.choix_ingredient_type_oeufsetfromage);
                break;

            case "Céréales":
                typeName = getString(R.string.choix_ingredient_type_céréales);
                break;

            default:
                typeName = "TYPE";

        }

        TextView tv_type = findViewById(R.id.tv_ingredientType);
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

                        Button btnRetour = findViewById(R.id.btn_retour_1);

                        final ListView listIngredient = findViewById(R.id.lv_ingredients);


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
                                    builder.setMessage(R.string.mon_frigo_type_deja_ajouté)
                                            .setNegativeButton(R.string.alert_dialog_ok, null)
                                            .create()
                                            .show();

                                    return;
                                }

                                MonFrigoType.this.finish();
                                Intent addedIngredientIntent = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                                addedIngredientIntent.putExtra("liste_ingredient", ingredientAdded);
                                addedIngredientIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                startActivity(addedIngredientIntent);

                            }
                        });



                    } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MonFrigoType.this);
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

        getIngredientPerTypeRequest getIngredientRequest = new getIngredientPerTypeRequest(type, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MonFrigoType.this);
        queue.add(getIngredientRequest);

    }
}

