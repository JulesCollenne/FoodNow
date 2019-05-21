package com.dunno.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.ui.menu_fonction.Favoris.FavoriteActivity;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;
import com.dunno.myapplication.ui.menu_fonction.Request.getRecipeFromIDRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}