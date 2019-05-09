package com.dunno.myapplication.ui.menu_fonction.ChefSashimi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChefSashimi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_sashimi);
        ImageButton sashimiBtn = findViewById(R.id.talkingSashimi);

        sashimiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                TextView tv_advice = findViewById(R.id.tv_advice);

                                tv_advice.setText(jsonResponse.getString("conseil"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(ChefSashimi.this);
                            builder2.setMessage("RIP " +e.getMessage())
                                    .setNegativeButton("Réessayer", null)
                                    .create()
                                    .show();
                        }
                    }
                };


                getAdviceRequest getAdviceRequest = new getAdviceRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChefSashimi.this);
                queue.add(getAdviceRequest);
            }
        });

        Button retourBtn = findViewById(R.id.btn_retour_10);

        retourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChefSashimi.this.finish();
            }
        });

    }
}