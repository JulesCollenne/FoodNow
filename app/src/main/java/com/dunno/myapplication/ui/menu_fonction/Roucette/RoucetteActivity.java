package com.dunno.myapplication.ui.menu_fonction.Roucette;
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

    Random random;

    int degree = 0;
    int old_degree=0;

    final double  demi_part_roue= 22.5;
    final double part_roue = 45;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roucette);

        button = (Button) findViewById(R.id.boutton_roucette);
        roucette = (ImageView) findViewById(R.id.roucette);
        textView = (TextView) findViewById(R.id.textView);

        random = new Random();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_degree = degree % 360;
                degree = random.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(old_degree, degree, RotateAnimation.RELATIVE_TO_SELF,
                        0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        textView.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setText(currentNumber(360 - (degree % 360)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                roucette.startAnimation(rotate);
            }
        });

    }

    private String currentNumber(int degree){
        String text = "";

        if(degree >= demi_part_roue + part_roue * 3 && degree < demi_part_roue + part_roue * 4){
            text = "Recette 1";
        }
        if(degree >= demi_part_roue + part_roue * 4 && degree < demi_part_roue + part_roue * 5){
            text = "Recette 2";
        }
        if(degree >= demi_part_roue + part_roue * 5 && degree < demi_part_roue + part_roue * 6){
            text = "Recette 3";
        }
        if(degree >= demi_part_roue + part_roue * 6 && degree < demi_part_roue + part_roue * 7){
            text = "Recette 4";
        }
        if((degree >= demi_part_roue + part_roue * 7 && degree < demi_part_roue )||(degree==0)){
            text = "Recette 5";
        }
        if(degree >= demi_part_roue && degree < demi_part_roue + part_roue * 1){
            text = "Recette 6";
        }
        if(degree >= demi_part_roue + part_roue * 1 && degree < demi_part_roue + part_roue * 2){
            text = "Recette 7";
        }
        if(degree >= demi_part_roue + part_roue * 2 && degree < demi_part_roue + part_roue * 3){
            text = "Recette 8";
        }

        return  text;
    }
}