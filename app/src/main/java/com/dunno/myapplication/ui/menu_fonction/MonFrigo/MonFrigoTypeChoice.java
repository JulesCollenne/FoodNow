package com.dunno.myapplication.ui.menu_fonction.MonFrigo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dunno.myapplication.R;

public class MonFrigoTypeChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_frigo_type_choice);

        ImageButton meatBtn = (ImageButton) findViewById(R.id.meatButton);
        ImageButton vegetableBtn = (ImageButton) findViewById(R.id.vegetablesButton);
        ImageButton feculentBtn = (ImageButton) findViewById(R.id.feculentButton);
        ImageButton diversBtn = (ImageButton) findViewById(R.id.diversButton);


        meatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 0);
                startActivity(monFrigoTypeIntent);

            }
        });


        vegetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 1);
                startActivity(monFrigoTypeIntent);

            }
        });


        feculentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 2);
                startActivity(monFrigoTypeIntent);

            }
        });


        diversBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent monFrigoTypeIntent = new Intent(getApplicationContext(), MonFrigoType.class);
                monFrigoTypeIntent.putExtra("type", 3);
                startActivity(monFrigoTypeIntent);

            }
        });


    }
}
