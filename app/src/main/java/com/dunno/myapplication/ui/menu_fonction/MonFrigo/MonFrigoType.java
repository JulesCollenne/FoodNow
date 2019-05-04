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

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.loginregister.LoginActivity;

import java.util.ArrayList;


public class MonFrigoType extends AppCompatActivity {

    ArrayList<String> ingredientAdded;

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

        Button btnRetour = (Button) findViewById(R.id.btn_retour_1);

        final ListView listIngredient = (ListView) findViewById(R.id.lv_ingredients);

        /* A recuperer via la Database quand elle est faite */
        ArrayList<String> ingredientName = new ArrayList<>();
        ingredientName.add("Carotte");
        ingredientName.add("Saumon");
        ingredientName.add("Salade");

        final IngredientAdapter ingredientAdapter = new IngredientAdapter(this, ingredientName);
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

                String newIngredient = (String) listIngredient.getAdapter().getItem(position);

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



    }
}
