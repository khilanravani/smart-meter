package com.example.projectmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    Button editDetails, saveButton;
    EditText userName, emailId, phoneNumber, address, pin;
    TextView meterId;


    int meter_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("Profile");



        meterId = findViewById(R.id.user_id);

        editDetails = findViewById(R.id.edit_details);
        saveButton = findViewById(R.id.save_button);
        userName = findViewById(R.id.user_name);
        emailId = findViewById(R.id.email_id);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        pin = findViewById(R.id.pin);

        String eid = "harshendrashah@abc.com";
        int position = 0;
        for (int x = 0; x < UserData.emailId.length; x++) {
            if(UserData.emailId[x].equals(eid)) {
                Log.i("----", "" + x);
                position = x;
                break;
            }
        }

        meterId.setText(String.valueOf(UserData.meterId[position]));
        emailId.setText(UserData.emailId[position]);
        userName.setText(UserData.userName[position]);
        phoneNumber.setText(UserData.phoneNumber[position]);
        pin.setText(String.valueOf(UserData.pin[position]));

        address.setText(UserData.address[position]);
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setEnabled(true);
                userName.setFocusable(true);
                emailId.setEnabled(true);
                phoneNumber.setEnabled(true);
                address.setEnabled(true);
                pin.setEnabled(true);
                editDetails.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setEnabled(false);
                emailId.setEnabled(false);
                phoneNumber.setEnabled(false);
                address.setEnabled(false);
                pin.setEnabled(false);
                editDetails.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
            }
        });

    }


}
