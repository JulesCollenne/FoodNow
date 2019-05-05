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
    ArrayList<String> ingredientName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type);

        int type = getIntent().getExtras().getInt("type");
        ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");

        String typeName;

        switch(type) {

            case 0:
                typeName = "Viandes et Poissons";
                break;

            case 1:
                typeName = "Fruits et Légumes";
                break;

            case 2:
                typeName = "Féculents";
                break;

            case 3:
                typeName = "Divers";
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

                        for(int i = 0; i < jsonResponse.length()-1; i++) {
                            ingredientName.add(jsonResponse.getString(i+""));
                        }

                        Button btnRetour = (Button) findViewById(R.id.btn_retour_1);

                        final ListView listIngredient = (ListView) findViewById(R.id.lv_ingredients);


                        final IngredientAdapter ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientName);
                        listIngredient.setAdapter(ingredientAdapter);


                        btnRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent retourIntent = new Intent(getApplicationContext(), AddIngredient.class);
                                retourIntent.putExtra("liste_ingredient", ingredientAdded);
                                if(getIntent().hasExtra("username")){
                                    retourIntent.putExtra("email", getIntent().getExtras().getString("email"));
                                    retourIntent.putExtra("username", getIntent().getExtras().getString("username"));
                                    retourIntent.putExtra("password", getIntent().getExtras().getString("password"));
                                }
                                startActivity(retourIntent);

                            }
                        });


                        listIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                IngredientAdapter IA = (IngredientAdapter) listIngredient.getAdapter();
                                String newIngredient = IA.getName(position);

                                if(!ingredientAdded.contains(newIngredient)) {
                                    ingredientAdded.add(newIngredient);
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

        getIngredientPerTypeRequest getIngredientRequest = new getIngredientPerTypeRequest(type+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(MonFrigoType.this);
        queue.add(getIngredientRequest);

    }
}
