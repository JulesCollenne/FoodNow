package com.dunno.myapplication.ui.menu_fonction.Roucette;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.loginregister.LoginActivity;
import com.dunno.myapplication.ui.loginregister.LoginRequest;
import com.dunno.myapplication.ui.menu.MainActivity;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class RoucetteActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    ImageView roucette;
    boolean loggedin = false;
    String email;
    String username;
    String password;

    double random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roucette);

        button = (Button) findViewById(R.id.boutton_roucette);
        roucette = (ImageView) findViewById(R.id.roucette);
        textView = (TextView) findViewById(R.id.textView);




        if(getIntent().hasExtra("username")){

            loggedin = true;

            email = getIntent().getExtras().getString("email");
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = Math.random() * ( 3600 );
                ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(roucette,"rotation",0f, (float)random);
                rotateAnimation.setDuration(3000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(rotateAnimation);
                animatorSet.start();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int IDrecipe = jsonResponse.getInt("id");

                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(RoucetteActivity.this);
                                builder.setMessage("Connection réussie")
                                        .create()
                                        .show();

                                Intent loggedIn = new Intent(getApplicationContext(), PrintRecipe.class);

                                String email = jsonResponse.getString("email");

                                if(loggedin) {
                                    loggedIn.putExtra("username", username);
                                    loggedIn.putExtra("password", password);
                                    loggedIn.putExtra("email", email);
                                }

                                loggedIn.putExtra("id_recipe",IDrecipe);
                                startActivity(loggedIn);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RoucetteActivity.this);
                                builder.setMessage("Connection échouée")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) { //JSON
                            e.printStackTrace();
                        }
                    }
                };

                getRecipefromRoucetteRequest roucetteRequest = new getRecipefromRoucetteRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(RoucetteActivity.this);
                queue.add(roucetteRequest);
            }
        });

    }

}