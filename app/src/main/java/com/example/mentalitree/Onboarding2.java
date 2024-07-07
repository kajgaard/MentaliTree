package com.example.mentalitree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Onboarding2 extends AppCompatActivity implements View.OnClickListener {
    boolean checkedBool = false;
    Button nextBtn;
    LinearLayout checkboxLl;
    ImageView checkboxIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);
        nextBtn = findViewById(R.id.goToOnboarding3btn);
        nextBtn.setOnClickListener(this);
        checkboxLl = findViewById(R.id.understandCheck);
        checkboxLl.setOnClickListener(this);
        checkboxIv = findViewById(R.id.uncheckedBoxIv);


    }

    @Override
    public void onClick(View view) {
        if(view == checkboxLl && !checkedBool){
            checkboxIv.setImageResource(R.drawable.checked_box);
            checkedBool = true;
        } else if (view == checkboxLl && checkedBool) {
            checkboxIv.setImageResource(R.drawable.unchecked_box);
            checkedBool = false;
        }
        if(view == nextBtn && checkedBool){
            Intent intent = new Intent(this, Onboarding3.class);
            startActivity(intent);
        } else if (view == nextBtn && !checkedBool) {
            Toast.makeText(this, "Sorry! We cannot proceed if you do not accept the privacy terms", Toast.LENGTH_SHORT).show();
        }
    }
}