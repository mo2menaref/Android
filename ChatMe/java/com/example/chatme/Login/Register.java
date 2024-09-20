package com.example.chatme.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatme.UserModel;
import com.example.chatme.HomePage;
import com.example.chatme.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText email, pass, repass, user;
    Button confirm1;
    SharedPreferences memory;
    String emaill, username, password, repassword;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.Email);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repass);
        confirm1 = findViewById(R.id.confirm);
        user = findViewById(R.id.username);
        Toast alert = Toast.makeText(getApplicationContext(), "There is something wrong!", Toast.LENGTH_SHORT);


        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emaill = email.getText().toString();
                username = user.getText().toString();
                password = pass.getText().toString();
                repassword = repass.getText().toString();
                if (password.length() > 10 && password.equalsIgnoreCase(repassword) && (emaill.contains("@gmail.com") || emaill.contains("@yahoo.com"))) {
                    sign_up();
                } else if(password.isEmpty()||repassword.isEmpty()||emaill.isEmpty()||username.isEmpty()) {
                    alert.show();
                }
            }
        });

    }
    public void sign_up() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emaill.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(), username, emaill, password);
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel);
                        Intent intent = new Intent(Register.this, Login.class);
                        intent.putExtra("User ", username);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("SignUpError", e.getMessage());
                        Toast.makeText(Register.this, "SignUp failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
