package com.example.app2.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app2.R;

public class Register extends AppCompatActivity {
    EditText user,pass,repass;
    Button confirm1;
    SharedPreferences memory;
    SharedPreferences.Editor save;
    String name,password,repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        user= findViewById(R.id.username);
        pass= findViewById(R.id.password);
        repass= findViewById(R.id.repass);
        confirm1= findViewById(R.id.confirm);

        Toast alert= Toast.makeText(getApplicationContext(), "There is something wrong!", Toast.LENGTH_SHORT);

        memory=getSharedPreferences("saved",MODE_PRIVATE);
        save=memory.edit();
        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=user.getText().toString();
                password=pass.getText().toString();
                repassword=repass.getText().toString();
                if(password.length()>10&&password.equalsIgnoreCase(repassword)) {

                    save.putString("username",name);
                    save.putString("password",password);
                    save.apply();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
                else{
                  alert.show();
                }
            }
        });
    }
}