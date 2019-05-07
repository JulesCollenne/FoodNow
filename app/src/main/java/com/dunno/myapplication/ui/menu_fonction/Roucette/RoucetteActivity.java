package com.dunno.myapplication.ui.menu_fonction.Roucette;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    double random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roucette);

        button = (Button) findViewById(R.id.boutton_roucette);
        roucette = (ImageView) findViewById(R.id.roucette);
        textView = (TextView) findViewById(R.id.textView);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random = Math.random() * ( 3600 );
                ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(roucette,"rotation",0f, (float)random);
                rotateAnimation.setDuration(3000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(rotateAnimation);
                animatorSet.start();
            }
        });

    }

}