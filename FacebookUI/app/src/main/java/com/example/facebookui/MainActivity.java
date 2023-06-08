package com.example.facebookui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
    }

        public void onClick(View view) {
            EditText etUsername = findViewById(R.id.txtEmail);
            EditText etPassword = findViewById(R.id.txtPassword);
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.equals("admin") && password.equals("123456")) {
                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,HomeActivity.class);
                i.putExtra("username", etUsername.getText().toString());
                startActivity(i);
                MainActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        }


}

