package com.example.app2.Login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.app2.HomePage;
import com.example.app2.R;

public class Login extends AppCompatActivity {
    Button login, create;
    EditText password, username;
    SharedPreferences memory;
    SharedPreferences.Editor save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        login = findViewById(R.id.Button2);
        password = findViewById(R.id.editTextNumberPassword);
        username = findViewById(R.id.editTextUsername);
        create = findViewById(R.id.Button);
        memory = getSharedPreferences("saved", MODE_PRIVATE);
        Toast alert = Toast.makeText(getApplicationContext(), "There is something wrong!", Toast.LENGTH_SHORT);
        save = memory.edit();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, Password;

                name = username.getText().toString();
                Password = password.getText().toString();

                if (valid(name, Password)) {
                    Intent intent = new Intent(Login.this, HomePage.class);
                    intent.putExtra("username", name);
                    startActivity(intent);
                } else {
                    //Toast.makeText(Login.this, "Invalid Data", Toast.LENGTH_LONG).show();
                    ErrorDialog();
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
            Intent intent = new Intent(Login.this, About.class);
            startActivity(intent);
        }
        return true;
    }

    private boolean valid(String username, String Password) {
        if (username.isEmpty() || Password.isEmpty()) {
            return false;
        } else if (username.equalsIgnoreCase(memory.getString("username", ""))
                && Password.equalsIgnoreCase(memory.getString("password", ""))) {
            return true;
        } else if (username.length() > 10 && Password.length() <= 10) {
            return false;
        } else {
            return false;
        }
    }

    public void ErrorDialog() {
        final Dialog errorDialog = new Dialog(this, R.style.errorstyle);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorDialog.setContentView(R.layout.error_dialg_layout);
        errorDialog.setCancelable(true);
        TextView title = errorDialog.findViewById(R.id.errortitle);
        TextView msg = errorDialog.findViewById(R.id.errorMessage);
        title.setText("Error");
        msg.setText("Login failed");

        errorDialog.show();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (errorDialog.isShowing()) {
                    errorDialog.dismiss();
                }
            }
        };

        errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 2000);
    }

}
