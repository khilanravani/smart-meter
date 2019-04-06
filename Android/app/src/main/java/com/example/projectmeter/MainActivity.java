package com.example.projectmeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String BASE_URL = "http://smart-meter-guj.herokuapp.com/rest/user/record/";
    String name;

    ExpandableCardView previousReadings;
    CardView predict, profile;
    TextView currentReadings, voltage, ampere, power;
    Button billingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        name = i.getStringExtra("username");

        String url = BASE_URL + name + "/?format=json";

        loadData(url);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        currentReadings = findViewById(R.id.current_reading);
        voltage = findViewById(R.id.voltage);
        power = findViewById(R.id.power);
        ampere = findViewById(R.id.ampere);
        predict = findViewById(R.id.predict);
        profile = findViewById(R.id.profile);
        billingButton = findViewById(R.id.billing_button);

        previousReadings = findViewById(R.id.previous_readings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout, profileFragment)
                        .addToBackStack(PredictionFragment.class.getSimpleName())
                        .commit();
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PredictionFragment predictionFragment = new PredictionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                predictionFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout, predictionFragment)
                        .addToBackStack(PredictionFragment.class.getSimpleName())
                        .commit();
            }
        });

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillingFragment billingFragment = new BillingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                billingFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout, billingFragment)
                        .addToBackStack(ProfileFragment.class.getSimpleName())
                        .commit();
            }
        });
    }

    public void loadData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject userRecord = null;
                try {
                    userRecord = response.getJSONObject(response.length()-1);
                    currentReadings.setText(String.valueOf(userRecord.getDouble("energy")));
                    voltage.setText(String.valueOf(userRecord.getDouble("volt")));
                    ampere.setText(String.valueOf(userRecord.getDouble("current")));
                    power.setText(String.valueOf(userRecord.getDouble("watt")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("******", "Error" + error.toString());
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            homeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout, homeFragment)
                    .addToBackStack(HomeFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_predict) {
            PredictionFragment predictionFragment = new PredictionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            predictionFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout, predictionFragment)
                    .addToBackStack(PredictionFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            profileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout, profileFragment)
                    .addToBackStack(ProfileFragment.class.getSimpleName())
                    .commit();
        } else if (id == R.id.nav_bill) {
            BillingFragment billingFragment = new BillingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", name);
            billingFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout, billingFragment)
                    .addToBackStack(ProfileFragment.class.getSimpleName())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
