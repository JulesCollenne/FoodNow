package com.dunno.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.ui.menu_fonction.Request.getRecipeFromIDRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dunno.myapplication", appContext.getPackageName());
    }

    @Test
    public void getRecipeFromID_Test() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String recipeName = jsonResponse.getString("name");
                    String recipeType = jsonResponse.getString("type");
                    int recipeTime = jsonResponse.getInt("time");

                    assertThat(success, equalTo(true));
                    assertThat(recipeName, equalTo("aaaaaaaa"));
                    assertThat(recipeType, equalTo("Plat"));
                    assertThat(recipeTime, equalTo(30));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        getRecipeFromIDRequest getRecipeFromID = new getRecipeFromIDRequest(0 + "", responseListener);
        RequestQueue queue = Volley.newRequestQueue(InstrumentationRegistry.getTargetContext());
        queue.add(getRecipeFromID);
    }
}

