package com.fit2081.assignment3.categories;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.assignment3.R;
import com.fit2081.assignment3.Util;
import com.fit2081.assignment3.provider.ViewModel;

import java.util.regex.Pattern;


public class NewEventCategory extends AppCompatActivity {

    private EditText tvId;
    private EditText tvName;
    private EditText tvCount;
    private EditText tvLocation;
    private Switch switchActive;
    private ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        tvId = findViewById(R.id.editId);
        tvName = findViewById(R.id.editName);
        tvCount = findViewById(R.id.editCount);
        tvLocation = findViewById(R.id.editLocation);
        switchActive = findViewById(R.id.switchActive);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS}, 0);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);


    }


    public void saveCategoryOnClick(View view) {
        String name = tvName.getText().toString();

        String countString = tvCount.getText().toString();
        int count = countString.isEmpty() ? 0 : Integer.parseInt(countString);

        boolean isActive = switchActive.isChecked();
        String location = tvLocation.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Category Name must be filled!", Toast.LENGTH_SHORT).show();
        } else if (!Pattern.matches("^[a-zA-z]([a-zA-Z0-9]|\\s)*", name)) {
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
        } else {
            if (count < 0) {
                Toast.makeText(this, "Invalid Event Count", Toast.LENGTH_SHORT).show();
                count = 0;
            }
            String id = Util.generateCategoryId();

            tvId.setText(id);
            Toast.makeText(this, "Category saved successfully: " + id, Toast.LENGTH_SHORT).show();

            viewModel.insert(new Category(id, name, count, isActive, location));
            finish();
        }

    }

}

