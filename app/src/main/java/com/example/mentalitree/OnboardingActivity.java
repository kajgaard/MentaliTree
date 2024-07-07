package com.example.mentalitree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {
Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        nextBtn=findViewById(R.id.goToOnboarding2btn);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextBtn){
            Intent intent = new Intent(this, Onboarding2.class);
            startActivity(intent);
        }
    }
}