package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Loginpage extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the following with your authentication logic
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                Intent intent=new Intent(Loginpage.this,MainActivity.class);
                startActivity(intent);
                if (username.equals("admin") && password.equals("admin123")) {
                    // Authentication successful


                } else {
                    // Authentication failed
                    Toast.makeText(Loginpage.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
