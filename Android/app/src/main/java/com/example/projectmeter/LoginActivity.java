package com.example.projectmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private ProgressBar progressBar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.userName);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("username", name);
                    startActivity(i);
                }

                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
}
