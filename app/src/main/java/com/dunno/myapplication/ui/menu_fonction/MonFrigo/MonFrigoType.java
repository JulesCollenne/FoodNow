package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.ListAdaptater.IngredientAdapter;
import com.dunno.myapplication.ui.menu_fonction.Request.getIngredientPerTypeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 *
 * Liste les ingrédients selon le type choisit, et les ajoutes lorsqu'on clique dessus
 *
 */

public class MonFrigoType extends AppCompatActivity {

    ArrayList<String> ingredientAdded;
    ArrayList<String> ingredientAddedID;
    ArrayList<String> ingredientName = new ArrayList<>();
    ArrayList<String> ingredientID = new ArrayList<>();
    ArrayList<String> ingredientNameOutput = new ArrayList<>();
    ArrayList<String> ingredientIDOutput = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type);

        String type = Objects.requireNonNull(getIntent().getExtras()).getString("type");
        ingredientAdded = getIntent().getExtras().getStringArrayList("liste_ingredient");
        ingredientAddedID = getIntent().getExtras().getStringArrayList("liste_ingredient_id");

        ImageView iv_type = findViewById(R.id.iv_ingredientType);

        assert type != null;
        switch(type) {

            case "Viande":
                iv_type.setImageResource(R.drawable.buton_viandes);
                break;

            case "Légume":
                iv_type.setImageResource(R.drawable.button_leg);
                break;

            case "Fruit":
                iv_type.setImageResource(R.drawable.buton_fruits);
                break;

            case "Condiment":
                iv_type.setImageResource(R.drawable.buton_condi);
                break;

            case "Oeufs et fromages":
                iv_type.setImageResource(R.drawable.buton_fromage);
                break;

            case "Céréales":
                iv_type.setImageResource(R.drawable.button_cere);
                break;

            case "Autres":
                iv_type.setImageResource(R.drawable.buton_autre);
                break;

            case "Pains":
                iv_type.setImageResource(R.drawable.buton_pains);
                break;

            case "Poisson et fruit de mer":
                iv_type.setImageResource(R.drawable.buton_fish);
                break;

            default:


        }

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

                        ImageButton btnRetour = findViewById(R.id.btn_retour_1);

                        final ListView listIngredient = findViewById(R.id.lv_ingredients);


                        final IngredientAdapter ingredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientName, ingredientID);
                        listIngredient.setAdapter(ingredientAdapter);


                        btnRetour.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent choiceIngredientTypeIntent = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                                choiceIngredientTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                                choiceIngredientTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                if(getIntent().hasExtra("username"))
                                    choiceIngredientTypeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                MonFrigoType.this.finish();
                                startActivity(choiceIngredientTypeIntent);

                            }
                        });

                        SearchView sv_ingredient = findViewById(R.id.searchIngredient);

                        sv_ingredient.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                getIngredient(newText);
                                final IngredientAdapter filteredIngredientAdapter = new IngredientAdapter(getApplicationContext(), ingredientNameOutput, ingredientIDOutput);
                                listIngredient.setAdapter(filteredIngredientAdapter);
                                return false;
                            }
                        });



                        listIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                IngredientAdapter IA = (IngredientAdapter) listIngredient.getAdapter();
                                String newIngredient = IA.getName(position);
                                String newIngredientID = IA.getID(position);


                                // Vérifie que l'ingrédient choisit n'a pas déjà été ajouté
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
                                Intent choiceIngredientTypeIntent = new Intent(getApplicationContext(), MonFrigoTypeChoice.class);
                                choiceIngredientTypeIntent.putExtra("liste_ingredient", ingredientAdded);
                                choiceIngredientTypeIntent.putExtra("liste_ingredient_id", ingredientAddedID);
                                if(getIntent().hasExtra("username"))
                                    choiceIngredientTypeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                startActivity(choiceIngredientTypeIntent);

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


    public void getIngredient(String query) {

        ingredientIDOutput.clear();
        ingredientNameOutput.clear();

        for (int i = 0; i < ingredientName.size(); i++) {
            if (ingredientName.get(i).toLowerCase().contains(query.toLowerCase())) {
                if(!ingredientNameOutput.contains(ingredientName.get(i))) {
                    ingredientNameOutput.add(ingredientName.get(i));
                    ingredientIDOutput.add(ingredientID.get(i));
                }
            }
        }
    }




}

