package com.example.mentalitree;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button enterBtn;
    EditText userId, userPin;
    Intent intent;
    DataHandler datahandler = DataHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        enterBtn = findViewById(R.id.logonBtn);
        enterBtn.setOnClickListener(this);

        userId = findViewById(R.id.userIdEt);
        userPin = findViewById(R.id.pincodeEt);

        intent = new Intent(getApplicationContext(), MainActivity.class);

    }

    @Override
    public void onClick(View view) {
        if(view == enterBtn){
            String inputId = String.valueOf(userId.getText());
            String inputPin = String.valueOf(userPin.getText());

            datahandler.setUpcCredentialAttempt(inputId, inputPin);

            datahandler.userAuthenticationSuccessful(flag -> {
                if(flag){
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Credentials are incorrect", Toast.LENGTH_SHORT).show();
                }
            });



        }
    }


}