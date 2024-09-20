package com.example.calculator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity_l extends AppCompatActivity {
    Button zero,one,two,three,four,five,six,seven,eight,nine,equal,C_button,divid,multiply,plus,minus,draw,del,moduls,dot;
    EditText calcarea;
    TextView history;
    private BroadcastReceiver myReceiver;
    double num1,num2;
    boolean add, sub, mul, div, mod;
    RadioButton  light,dark;
    Button submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_l);
        submit=findViewById(R.id.Submit_l);
        light=findViewById(R.id.lightb);
        dark=findViewById(R.id.darkb);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        if (radioGroup == null) {
            Log.e("MainActivity", "RadioGroup not found");

            return;
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("MainActivity", "Checked ID: " + checkedId);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int checkedId=R.id.darkb;
                if (checkedId == R.id.darkb) {
                    Intent intent;
                    intent = new Intent(MainActivity_l.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });

        zero = findViewById(R.id.Zero);
        one = findViewById(R.id.One);
        two = findViewById(R.id.Two);
        three = findViewById(R.id.Three);
        four = findViewById(R.id.Four);
        five = findViewById(R.id.Five);
        six = findViewById(R.id.Six);
        seven = findViewById(R.id.Seven);
        eight = findViewById(R.id.Eight);
        nine = findViewById(R.id.Nine);
        plus = findViewById(R.id.Plus);
        C_button = findViewById(R.id.C);
        minus = findViewById(R.id.Minus);
        multiply = findViewById(R.id.Multi);
        divid = findViewById(R.id.Divide);
        moduls = findViewById(R.id.Moduls);
        draw = findViewById(R.id.Draw);
        dot = findViewById(R.id.Dot);
        del = findViewById(R.id.Del);
        equal = findViewById(R.id.Equal);
        calcarea = findViewById(R.id.CalcArea);
        history = findViewById(R.id.History);

        // Set tags for buttons
        zero.setTag(0);
        one.setTag(1);
        two.setTag(2);
        three.setTag(3);
        four.setTag(4);
        five.setTag(5);
        six.setTag(6);
        seven.setTag(7);
        eight.setTag(8);
        nine.setTag(9);
        multiply.setTag("x");
        plus.setTag("+");
        equal.setTag("=");
        moduls.setTag("%");
        minus.setTag("-");
        divid.setTag("÷");

// Clear button functionality
        C_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1 = 0;
                calcarea.setText("");
            }
        });

// Number button click listener
        View.OnClickListener numClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = (int) view.getTag();
                calcarea.append(String.valueOf(value));
            }
        };



// Set listeners for number buttons
        zero.setOnClickListener(numClickListener);
        one.setOnClickListener(numClickListener);
        two.setOnClickListener(numClickListener);
        three.setOnClickListener(numClickListener);
        four.setOnClickListener(numClickListener);
        five.setOnClickListener(numClickListener);
        six.setOnClickListener(numClickListener);
        seven.setOnClickListener(numClickListener);
        eight.setOnClickListener(numClickListener);
        nine.setOnClickListener(numClickListener);


        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcarea.append(".");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcarea.getText().length() != 0) {
                    num1 = Double.parseDouble(calcarea.getText().toString());
                    add = true;
                    calcarea.setText(calcarea.getText().toString() + " + ");                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcarea.getText().length() != 0) {
                    num1 = Double.parseDouble(calcarea.getText().toString());
                    sub = true;
                    calcarea.setText(calcarea.getText().toString() + " - ");    }
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcarea.getText().length() != 0) {
                    num1 = Double.parseDouble(calcarea.getText().toString());
                    mul = true;
                    calcarea.setText(calcarea.getText().toString() + " x ");    }
            }
        });

        divid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcarea.getText().length() != 0) {
                    num1 = Double.parseDouble(calcarea.getText().toString());
                    div = true;
                    calcarea.setText(calcarea.getText().toString() + " ÷ ");     }
            }
        });

        moduls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcarea.getText().length() != 0) {
                    num1 = Double.parseDouble(calcarea.getText().toString());
                    mod = true;
                    calcarea.setText(calcarea.getText().toString() + " % ");
                }
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add || sub || mul || div || mod) {
                    String[] parts = calcarea.getText().toString().split(" ");
                    if (parts.length == 3) {
                        num2 = Double.parseDouble(parts[2]);
                        double result = 0;
                        if (add) {
                            calcarea.setText(String.valueOf(num1 + num2));                                                     add = false;

                        } else if (sub) {
                            calcarea.setText(String.valueOf(num1 - num2));                                                     sub = false;
                        } else if (mul) {
                            calcarea.setText(String.valueOf(num1 * num2));                                                     mul = false;
                        } else if (div) {
                            calcarea.setText(String.valueOf(num1 / num2));                                                     div = false;
                        } else if (mod) {
                            calcarea.setText(String.valueOf(num1 % num2));
                            history.setText(String.valueOf(num1 % num2));

                        }
                    }
                }

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = calcarea.getText().toString();
                if (currentText.length() > 0) {
                    calcarea.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });
        // Initialize the BroadcastReceiver
        myReceiver = new BroadCast.MyReceiver();

        // Register the receiver for airplane mode changes
        IntentFilter filter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
        registerReceiver(myReceiver, filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the receiver
        unregisterReceiver(myReceiver);
    }

}

