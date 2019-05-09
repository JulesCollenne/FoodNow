package com.dunno.myapplication.ui.menu_fonction.ChefSashimi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu_fonction.LesRecettes.ChoixTypeRecette;

public class ChefSashimi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_sashimi);

        ImageButton sashimiBtn = findViewById(R.id.talkingSashimi);

        Button retourBtn = findViewById(R.id.btn_retour_10);


        sashimiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //On prend un conseil au hasard TODO

            }
        });

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChefSashimi.this.finish();
            }
        });

    }
}
