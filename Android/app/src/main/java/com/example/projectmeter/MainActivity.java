package com.example.projectmeter;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.gesture.Prediction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;

public class MainActivity extends AppCompatActivity {

    private static MainActivity inst;

    String emailid;

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

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            return;
        }
        do {
            if (smsInboxCursor.getString(indexAddress).equals("+919724927731")) {
                Log.i("*****", smsInboxCursor.getString(indexBody));
                Log.i("******", "Message to aaya");

                // Watt, Voltage, Energy, Ampere
                String line[] = smsInboxCursor.getString(indexBody).split("\\r?\\n");
                power.setText(line[0]);
                voltage.setText(line[1]);
                currentReadings.setText(line[2]);
                ampere.setText(line[3]);
            }
        } while (smsInboxCursor.moveToNext());
    }

    public void updateReadings(final String smsMessage) {
        currentReadings.setText(smsMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//        previousReadings.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
//            @Override
//            public void onExpandChanged(View v, boolean isExpanded) {
//                Toast.makeText(MainActivity.this, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
//            }
//        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("emailid", emailid);
                startActivity(i);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PredictionActivity.class);
                startActivity(i);
            }
        });

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BillingActivity.class);
                startActivity(i);
            }
        });
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        refreshSmsInbox();
                    }
                }, 5000
        );
    }
}
