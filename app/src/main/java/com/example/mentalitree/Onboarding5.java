package com.example.mentalitree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Onboarding5 extends AppCompatActivity implements View.OnClickListener  {
    Button nextBtn;
    ImageView avatar1;
    ImageView avatar2;
    ImageView avatar3;
    int avatarSelect = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding5);
        nextBtn = findViewById(R.id.goToApp);
        nextBtn.setOnClickListener(this);
        avatar1 = findViewById(R.id.BearCheckedIv);
        avatar1.setOnClickListener(this);
        avatar2 = findViewById(R.id.PandaIv);
        avatar2.setOnClickListener(this);
        avatar3 = findViewById(R.id.BunnyIv);
        avatar3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == avatar1 && avatarSelect != 1){
            avatar1.setImageResource(R.drawable.bearchecked);
            avatar2.setImageResource(R.drawable.panda);
            avatar3.setImageResource(R.drawable.bunny);
            avatarSelect = 1;
        } else if (view == avatar2 && avatarSelect != 2) {
            avatar1.setImageResource(R.drawable.bear);
            avatar2.setImageResource(R.drawable.pandachecked);
            avatar3.setImageResource(R.drawable.bunny);
            avatarSelect = 2;
        } else if (view == avatar3 && avatarSelect != 3) {
            avatar1.setImageResource(R.drawable.bear);
            avatar2.setImageResource(R.drawable.panda);
            avatar3.setImageResource(R.drawable.bunnychecked);
            avatarSelect = 3;
        }
        if(view == nextBtn){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
    }
}