package com.example.mentalitree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Onboarding4 extends AppCompatActivity implements View.OnClickListener {
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding4);
        nextBtn=findViewById(R.id.goToOnboarding5btn);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextBtn){
            Intent intent = new Intent(this, Onboarding5.class);
            startActivity(intent);
        }
    }
}