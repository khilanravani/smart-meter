package com.example.projectmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;

public class MainActivity extends AppCompatActivity {

    private static MainActivity inst;

    String name;

    ExpandableCardView previousReadings;
    CardView predict, profile;
    TextView currentReadings, voltage, ampere, power;
    Button billingButton;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public void updateReadings(final String smsMessage) {
        currentReadings.setText(smsMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        name = intent.getStringExtra("username");

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

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
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PredictionActivity.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BillingActivity.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });
    }
}
