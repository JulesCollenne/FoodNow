package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.dunno.myapplication.R;

public class MonFrigoType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type);

        int type = getIntent().getExtras().getInt("type");
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

        ListView listIngredient = (ListView) findViewById(R.id.lv_ingredients);

        /* A recuperer via la Database quand elle est faite */
        String[] ingredientName = new String[]{"Bouillabaisse", "Pistou", "Oui"};

        IngredientAdapter ingredientAdapter = new IngredientAdapter(this, ingredientName);
        listIngredient.setAdapter(ingredientAdapter);


    }
}
