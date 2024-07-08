package com.example.mentalitree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    PrivateDataHandler privateDataHandler = PrivateDataHandler.getInstance();
    private static final String TAG  ="MMLAUNCHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        sharedPreferences = getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);



        if(hasExistingUser()){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            //privateDataHandler.createKeyForSharedPreferences();
            privateDataHandler.getUserKeyFromSharedPreferences();

        }else{
            privateDataHandler.createKeyForSharedPreferences();
            setHasUserBoolInSharedPrefs();
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
        }

    }

    public boolean hasExistingUser(){
        return sharedPreferences.getBoolean("has_user",false);
    }

    public void setHasUserBoolInSharedPrefs(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean("has_user", true);
        editor.apply();
        Log.e(TAG, "Just created the has_user value in SharedPrefs");
    }

    public void fakeValueAsTrue(){
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean("has_user", true);
        editor.apply();
    }
}