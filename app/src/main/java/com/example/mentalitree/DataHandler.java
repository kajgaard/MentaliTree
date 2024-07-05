package com.example.mentalitree;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class DataHandler {

    private static DataHandler single_instance = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userPin, userId;
    String userToken;

    private DataHandler() {

    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized DataHandler getInstance() {
        if (single_instance == null) {
            single_instance = new DataHandler();

        }
        return single_instance;
    }

    public void setUpcCredentialAttempt (String userId, String userPin){
        this.userId = userId;
        this.userPin = userPin;
    }

    public void userAuthenticationSuccessful(MyFirebaseCallBack firebaseCallBack){
        CollectionReference usersRef = db.collection("users");

        Query query = usersRef.whereEqualTo("userId", this.userId).whereEqualTo("userPin", this.userPin);



        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                if(!task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("MMDATA", document.getId() + " => " + document.getData());
                        this.userToken = document.getId();
                    }
                    addTimestampToLog();
                    firebaseCallBack.onCallback(true);

                }else{
                    firebaseCallBack.onCallback(false);
                }
            }else{
                Log.d("MMDATA", "Error getting documents: ", task.getException());
            }
        });

    }

    public void addTimestampToLog(){

        String pattern = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        System.out.println(date);

        Map<String, String> timeStamp = new HashMap<>();
        timeStamp.put("timeStamp", date);

        db.collection("users").document(userToken).collection("activityLog").document(date)
                .set(timeStamp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("MMDATA", "DocumentSnapshot for timestamp successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MMDATA", "Error writing document", e);
                    }
                });

    }

    public interface MyFirebaseCallBack {

        void onCallback(boolean flag);

    }
}

