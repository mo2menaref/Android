package com.example.bmi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.bmi.MenuPages.ContactUs;
import com.example.bmi.MenuPages.Guide;

public class Homepage extends AppCompatActivity {
    EditText gender, age, weight, height;
    Button submit;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        gender = findViewById(R.id.Gender);
        age = findViewById(R.id.Age);
        weight = findViewById(R.id.Weight);
        height = findViewById(R.id.Height);
        submit = findViewById(R.id.Submit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double ag = 0, we = 0, he = 0;
                String gen = "";
                boolean isValid = true;

                gen = gender.getText().toString();
                if (gen.isEmpty() || (!gen.equalsIgnoreCase("male") && !gen.equalsIgnoreCase("female"))) {
                    Toast.makeText(Homepage.this, "Invalid Gender", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                try {
                    ag = Double.parseDouble(age.getText().toString());
                    if (ag <= 12) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(Homepage.this, "Invalid Age", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                try {
                    he = Double.parseDouble(height.getText().toString()) * 0.01;
                    if (he <= 0.8) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(Homepage.this, "Invalid Height", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                try {
                    we = Double.parseDouble(weight.getText().toString());
                    if (we <= 10) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(Homepage.this, "Invalid Weight", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                if (isValid) {
                    double bmi = we / (he * he);
                    Intent intent = new Intent(Homepage.this, Result.class);
                    intent.putExtra("BMI_RESULT", bmi);
                    intent.putExtra("Gender", gen);
                    startActivity(intent);
                }
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(Homepage.this, ContactUs.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(Homepage.this, Guide.class);
            startActivity(intent);
        }
        return true;
    }
}
