package com.example.bmi.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bmi.Homepage;
import com.example.bmi.R;

public class Splash extends AppCompatActivity {
    ImageView imgshow;
    final int[] index = {0};
    int[] images = {R.drawable.unnamed,R.drawable.shdgx};
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgshow = findViewById(R.id.Showimg);

        EdgeToEdge.enable(this);

        // Change images automatically
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgshow.setImageResource(images[index[0]]);
                index[0]++;
                if (index[0] == images.length) {
                    index[0] = 0;
                }
                handler.postDelayed(this, 2600);
            }
        }, 500);

        // Start new activity after 2 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Homepage.class);
                startActivity(intent);
                finish(); // Finish the splash activity
            }
        }, 5000);
    }
}
