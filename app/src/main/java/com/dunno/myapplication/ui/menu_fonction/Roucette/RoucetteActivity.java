package com.dunno.myapplication.ui.menu_fonction.Roucette;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;
import com.dunno.myapplication.ui.menu_fonction.Request.getRecipefromRoucetteRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
    Page de la roucette, on peut la tourner pour avoir une recette aléatoire.
 */
public class RoucetteActivity extends AppCompatActivity {

    ImageButton button;
    ImageView roucette;

    int IDrecipe;
    double random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roucette);


        /*on récupère les différents éléments (image, bouton) grâce à leur identifiant et on les stocke dans des variables pour
        * pouvoir ensuite les "manipuler"*/

        button = findViewById(R.id.bouton_roucette);
        roucette = findViewById(R.id.roucette);

        ImageButton bRetour = findViewById(R.id.btn_retour_11);

        bRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoucetteActivity.this.finish();

            }
        });


        /* L'animation de la roue*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = Math.random() * ( 3600 );
                ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(roucette,"rotation",0f, (float)random);
                rotateAnimation.setDuration(3000);

                final AnimatorSet animatorSet = new AnimatorSet();
                Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        /*on fait rien*/
                    }


                    /* à la fin de l'animation, on affiche la recette trouvée au hasard */

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    IDrecipe = jsonResponse.getInt("id");


                                    if (success) {
                                            Intent printRecipeIntent = new Intent(getApplicationContext(), PrintRecipe.class);
                                            if(getIntent().hasExtra("username"))
                                                printRecipeIntent.putExtra("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                                            printRecipeIntent.putExtra("id_recipe", IDrecipe);
                                            startActivity(printRecipeIntent);

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RoucetteActivity.this);
                                        builder.setMessage(R.string.alert_dialog_erreur_base_de_donnée)
                                                .setNegativeButton(R.string.alert_dialog_reesayer, null)
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

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };

                animatorSet.addListener(animatorListener);
                animatorSet.playTogether(rotateAnimation);
                animatorSet.start();

            }
        });


    }

}