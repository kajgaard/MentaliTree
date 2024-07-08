package com.example.mentalitree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    EditText userIdEt, userPinEt, confirmPinEt;
    int avatarSelect = 1;
    SharedPreferences sharedPreferences;
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
        userIdEt = findViewById(R.id.userIdEt);
        userPinEt = findViewById(R.id.pincodeEt);
        confirmPinEt = findViewById(R.id.confirm_pincodeEt);

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

            if(areCredentialsOkay()){
                Intent intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Please make sure your information is valid", Toast.LENGTH_LONG).show();
            }

        }
    }

    public boolean areCredentialsOkay(){
        boolean flag = true;
        if (userIdEt.getText().toString().isEmpty()){
            userIdEt.setError("Cannot be empty");
            flag = false;
        }
        if (userPinEt.getText().toString().isEmpty()) {
            userPinEt.setError("Cannot be empty");
            flag = false;
        }
        if (confirmPinEt.getText().toString().isEmpty()) {
            confirmPinEt.setError("Cannot be empty");
            flag = false;
        }
        if (!userPinEt.getText().toString().equals(confirmPinEt.getText().toString())) {
            confirmPinEt.setError("Not equal to pincode above");
            flag = false;
        }

        return flag;
    }
    public void setHasUserBoolInSharedPrefs(){
        sharedPreferences = getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putBoolean("has_user", true);
        editor.apply();
    }
}