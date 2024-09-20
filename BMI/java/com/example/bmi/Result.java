package com.example.bmi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    TextView result, resultname, guide;
    ImageView showimg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.Result);
        showimg = findViewById(R.id.Imgresult);
        resultname = findViewById(R.id.Resultname);
        guide = findViewById(R.id.Guide);

        double bmi = getIntent().getDoubleExtra("BMI_RESULT", 0.0);
        String gend = getIntent().getStringExtra("Gender");

        if (result != null) {
            result.setText(String.format("%.2f", bmi));
        } else {
            Log.e("ResultActivity", "result TextView is null");
        }

        if (resultname != null && showimg != null) {
            if (bmi > 18.5 && bmi <= 24 && gend.equalsIgnoreCase("male")) {
                resultname.setText("Normal");
                guide.setText("Balanced Diet: Maintain a balanced diet with a variety of fruits, vegetables, lean proteins, and whole grains.\n Regular Exercise: Aim for at least 150 minutes of moderate aerobic activity or 75 minutes of vigorous activity each week.\n Stay Hydrated: Drink plenty of water throughout the day to stay hydrated.");
                showimg.setImageResource(R.drawable.bmiclassification3);
            } else if (bmi > 18.5 && bmi <= 24 && gend.equalsIgnoreCase("female")) {
                resultname.setText("Normal");
                guide.setText("Balanced Diet: Maintain a balanced diet with a variety of fruits, vegetables, lean proteins, and whole grains.\n Regular Exercise: Aim for at least 150 minutes of moderate aerobic activity or 75 minutes of vigorous activity each week.\n Stay Hydrated: Drink plenty of water throughout the day to stay hydrated.");
                showimg.setImageResource(R.drawable.bmiclassification5);
            } else if (bmi < 18.5 && gend.equalsIgnoreCase("male")) {
                resultname.setText("Under Weight");
                guide.setText("Eat Nutrient-Rich Foods: Focus on high-calorie, nutrient-dense foods like nuts, seeds, avocados, and whole grains.\n Frequent Meals: Eat small, frequent meals throughout the day to increase calorie intake.\nStrength Training: Incorporate strength training exercises to build muscle mass.");
                showimg.setImageResource(R.drawable.bmiclassification4);
            } else if (bmi < 18.5 && gend.equalsIgnoreCase("female")) {
                resultname.setText("Under Weight");
                guide.setText("Eat Nutrient-Rich Foods: Focus on high-calorie, nutrient-dense foods like nuts, seeds, avocados, and whole grains.\n Frequent Meals: Eat small, frequent meals throughout the day to increase calorie intake.\nStrength Training: Incorporate strength training exercises to build muscle mass.");
                showimg.setImageResource(R.drawable.bmiclassification8);
            } else if (bmi > 24 && bmi < 30 && gend.equalsIgnoreCase("male")) {
                resultname.setText("Over Weight");
                guide.setText("Portion Control: Be mindful of portion sizes to avoid overeating.\nHealthy Snacks: Choose healthy snacks like fruits, vegetables, and nuts instead of processed foods.\n Increase Physical Activity: Incorporate more physical activity into your daily routine, such as walking, cycling, or swimming.");
                showimg.setImageResource(R.drawable.bmiclassification2);
            } else if (bmi > 24 && bmi <= 30 && gend.equalsIgnoreCase("female")) {
                resultname.setText("Over Weight");
                guide.setText("Portion Control: Be mindful of portion sizes to avoid overeating.\nHealthy Snacks: Choose healthy snacks like fruits, vegetables, and nuts instead of processed foods.\n Increase Physical Activity: Incorporate more physical activity into your daily routine, such as walking, cycling, or swimming.");
                showimg.setImageResource(R.drawable.bmiclassification6);
            } else if (bmi > 30 && gend.equalsIgnoreCase("male")) {
                resultname.setText("Obesity");
                guide.setText("Consult a Healthcare Provider: Seek advice from a healthcare provider for a personalized weight loss plan.\nHealthy Eating Habits: Focus on eating whole, unprocessed foods and reducing sugar and refined carbs.\nRegular Exercise: Engage in regular physical activity, aiming for at least 300 minutes of moderate-intensity exercise per week.");
                showimg.setImageResource(R.drawable.bmiclassification1);
            } else if (bmi > 30 && gend.equalsIgnoreCase("female")) {
                resultname.setText("Obesity");
                guide.setText("Consult a Healthcare Provider: Seek advice from a healthcare provider for a personalized weight loss plan.\nHealthy Eating Habits: Focus on eating whole, unprocessed foods and reducing sugar and refined carbs.\nRegular Exercise: Engage in regular physical activity, aiming for at least 300 minutes of moderate-intensity exercise per week.");
                showimg.setImageResource(R.drawable.bmiclassification7);
            } else {
                Log.e("ResultActivity", "Unhandled BMI and gender combination");
            }
        } else {
            Log.e("ResultActivity", "resultname or showimg is null");
        }
    }
}
