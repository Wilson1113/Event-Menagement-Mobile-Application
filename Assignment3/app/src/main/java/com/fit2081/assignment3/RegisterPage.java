package com.fit2081.assignment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        intent = new Intent(this, LoginPage.class);

    }

    public void registerButtonOnClick(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText tvUsername = findViewById(R.id.editUsername);
        EditText tvPassword = findViewById(R.id.editPassword);
        EditText tvConfirm = findViewById(R.id.editConfirm);

        String username = tvUsername.getText().toString();
        String password = tvPassword.getText().toString();
        String confirm = tvConfirm.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Please fill in all blank space", Toast.LENGTH_SHORT).show();
        } else if (sharedPreferences.getAll().containsKey(username)) {
            Toast.makeText(this, username + " has been registered!", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirm)) {
            Toast.makeText(this, "Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Registration successfully!", Toast.LENGTH_SHORT).show();
            editor.putString("LAST",username);
            editor.putString(username, password);
            editor.apply();
            startActivity(intent);
        }
    }

    public void loginButtonOnClick(View view) {
        startActivity(intent);
    }
}