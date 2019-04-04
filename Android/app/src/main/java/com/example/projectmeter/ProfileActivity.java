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

    TextView userName, emailId, phoneNumber, address;
    TextView meterId;

    String name;

    int meter_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle("Profile");

        Intent intent = getIntent();
        name = intent.getStringExtra("username");


        meterId = findViewById(R.id.user_id);
        userName = findViewById(R.id.user_name);
        emailId = findViewById(R.id.email_id);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);

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

        address.setText(UserData.address[position]);


    }


}
