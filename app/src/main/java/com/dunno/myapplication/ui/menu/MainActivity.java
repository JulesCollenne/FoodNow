
package com.dunno.myapplication.ui.menu;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dunno.myapplication.R;
import com.dunno.myapplication.ui.loginregister.LoginActivity;
import com.dunno.myapplication.ui.loginregister.LoginRequest;
import com.dunno.myapplication.ui.menu_fonction.Account.AccountActivity;
import com.dunno.myapplication.ui.menu_fonction.LesRecettes.ChoixTypeRecette;
import com.dunno.myapplication.ui.menu_fonction.MonFrigo.AddIngredient;
import com.dunno.myapplication.ui.menu_fonction.PrintRecipe.PrintRecipe;
import com.dunno.myapplication.ui.menu_fonction.Roucette.RoucetteActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean loggedin = false; //ajout
    String email = null;
    String username = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("username")){

            loggedin = true;

            email = getIntent().getExtras().getString("email");
            username = getIntent().getExtras().getString("username");
            password = getIntent().getExtras().getString("password");

        }

        if(loggedin){
            setContentView(R.layout.activity_logged_in_main);
        }else {
            setContentView(R.layout.activity_main);
        }




        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    String nameRecipe = jsonResponse.getString("name");
                    final int idRecipe = jsonResponse.getInt("id");


                    if (success) {

                        TextView tvName = findViewById(R.id.tv_recettedujour);
                        tvName.setText(nameRecipe);

                        ImageButton ibrecette = findViewById(R.id.iv_recettedujour);
                        ibrecette.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent printIntent = new Intent(getApplicationContext(), PrintRecipe.class);

                                if (loggedin) {
                                    printIntent.putExtra("email", email);
                                    printIntent.putExtra("username", username);
                                    printIntent.putExtra("password", password);
                                }

                                printIntent.putExtra("id_recipe", idRecipe);

                                startActivity(printIntent);


                            }
                        });


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

        getRecettedujourRequest getRecetteRequest = new getRecettedujourRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(getRecetteRequest);

























        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer;
        if(loggedin){
            drawer = findViewById(R.id.drawer_layout_logged_in);
        }else{
            drawer = findViewById(R.id.drawer_layout);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer;
        if(loggedin){
            drawer = findViewById(R.id.drawer_layout_logged_in);
        }else{
            drawer = findViewById(R.id.drawer_layout);
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(loggedin){
            getMenuInflater().inflate(R.menu.logged_in_main, menu);
            String username = getIntent().getExtras().getString("username");
            TextView pseudotv = (TextView) findViewById(R.id.pseudoView);
            pseudotv.setText(username);
        }else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_login){
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(startIntent);
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Intent chosenIntent;

        switch(item.getItemId()) {

            case R.id.nav_accueil:

                break;

            case R.id.nav_frigo:
                chosenIntent = new Intent(getApplicationContext(), AddIngredient.class);
                if (loggedin) {
                    chosenIntent.putExtra("email", email);
                    chosenIntent.putExtra("username", username);
                    chosenIntent.putExtra("password", password);
                }
                startActivity(chosenIntent);
                break;

            case R.id.nav_gallery:
                chosenIntent = new Intent(getApplicationContext(), ChoixTypeRecette.class);
                if (loggedin) {
                    chosenIntent.putExtra("email", email);
                    chosenIntent.putExtra("username", username);
                    chosenIntent.putExtra("password", password);
                }
                startActivity(chosenIntent);
                break;

            case R.id.nav_roucette:
                chosenIntent = new Intent(getApplicationContext(), RoucetteActivity.class);
                if (loggedin) {
                    chosenIntent.putExtra("email", email);
                    chosenIntent.putExtra("username", username);
                    chosenIntent.putExtra("password", password);
                }
                startActivity(chosenIntent);
                break;

            case R.id.nav_account:
                chosenIntent = new Intent(getApplicationContext(), AccountActivity.class);
                chosenIntent.putExtra("email", email);
                chosenIntent.putExtra("username", username);
                chosenIntent.putExtra("password", password);
                startActivity(chosenIntent);
                break;

            case R.id.nav_contact:

                break;

            case R.id.nav_favoris:

                break;

            case R.id.nav_logout:
                chosenIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(chosenIntent);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Disconnected")
                        .create()
                        .show();
                break;


        }

        DrawerLayout drawer;
        if(loggedin){
            drawer = findViewById(R.id.drawer_layout_logged_in);
        }else{
            drawer = findViewById(R.id.drawer_layout);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
