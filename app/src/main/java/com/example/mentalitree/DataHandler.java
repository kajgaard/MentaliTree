package com.example.mentalitree;



import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String TAG = "MMDATAHANDLER";


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
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        this.userToken = document.getId();
                    }
                    addTimestampToLog();
                    firebaseCallBack.onCallback(true);

                }else{
                    firebaseCallBack.onCallback(false);
                }
            }else{
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

    }

    public void getActivityLog(MyFirebaseListCallback firebaseCallBack){
        db.collection("users").document(userToken).collection("activityLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String timeStamp = data.get("timeStamp").toString();
                                list.add(timeStamp);
                                Log.d(TAG, "Found document: "+ document.getId() + " => " + document.getData());
                            }
                            firebaseCallBack.onCallback(list);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
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

    public interface MyFirebaseListCallback{
        void onCallback(ArrayList<String> list);
    }
}

