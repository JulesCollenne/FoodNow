package com.dunno.myapplication.ui.menu_fonction.Roucette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunno.myapplication.R;

import java.util.Random;

public class RoucetteActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    ImageView roucette;

    Random random;

    int degree = 0;
    int old_degree=0;

    private static final float factor = 45.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roucette);

        button = (Button) findViewById(R.id.button2);
        roucette = (ImageView) findViewById(R.id.roucette);
        textView = (TextView) findViewById(R.id.textView);

        random = new Random();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_degree = degree % 360;
                degree = random.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(old_degree, degree,RotateAnimation.RELATIVE_TO_SELF,
                        0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                roucette.startAnimation(rotate);
            }
        });

    }
}