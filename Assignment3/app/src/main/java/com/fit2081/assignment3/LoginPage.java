package com.fit2081.assignment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fit2081.assignment3.events.Dashboard;

import java.util.Map;
import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Intent intentToRegisterPage;
    private Intent intentToDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        intentToRegisterPage = new Intent(this, RegisterPage.class);
        intentToDashBoard = new Intent(this, Dashboard.class);

        EditText tvUsername = findViewById(R.id.editUsername2);
        tvUsername.setText(sharedPreferences.getString("LAST", ""));
    }

    public void registerButtonOnClick(View view){
        startActivity(intentToRegisterPage);
    }

    public void loginButtonOnClick(View view) {

        EditText tvUsername = findViewById(R.id.editUsername2);
        EditText tvPassword = findViewById(R.id.editPassword2);

        String username = tvUsername.getText().toString();
        String password = tvPassword.getText().toString();

        Map<String, ?> registeredUser = sharedPreferences.getAll();

        if (Objects.equals(registeredUser.get(username), password)) {
            Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(intentToDashBoard);
        } else {
            Toast.makeText(this, "Authentication failure: Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText tvPassword = findViewById(R.id.editPassword2);
        tvPassword.setText("");
    }
}