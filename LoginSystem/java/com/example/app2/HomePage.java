package com.example.app2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView arrow_right, arrow_left,show_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar);
        drawerLayout=findViewById(R.id.drawerLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        arrow_right=findViewById(R.id.arrow_right);
        arrow_left=findViewById(R.id.arrow_left);
        show_img = findViewById(R.id.show_img);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toggle.syncState();
            TextView welcomeMessage = findViewById(R.id.welcomeMessage);
            String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeMessage.setText("Hello, " + username + "!");
        } else {
            welcomeMessage.setText("Hello, Guest!");
        }
        final int[] index = {0};
        int [] images={R.drawable.img_one,R.drawable.img_two,R.drawable.img_three,R.drawable.img_four,R.drawable.img_five,R.drawable.img_six};
        arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_img.setImageResource(images[index[0]]);
                index[0]++;
                if(index[0] ==images.length){
                    index[0] =0;
                }
            }
        });
        arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_img.setImageResource(images[index[0]]);
                index[0]++;
                if(index[0] ==images.length){
                    index[0] =0;
                }

            }
        });
    }
}

