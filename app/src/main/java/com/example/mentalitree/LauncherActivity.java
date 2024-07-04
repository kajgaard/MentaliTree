package com.example.mentalitree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        sharedPreferences = getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);

        fakeValueAsTrue();

        if(hasExistingUser()){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
        }


    }

    public boolean hasExistingUser(){
        return sharedPreferences.getBoolean("has_user",false);
    }

    public void fakeValueAsTrue(){
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean("has_user", true);
        editor.apply();
    }
}