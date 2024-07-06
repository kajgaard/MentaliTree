package com.example.mentalitree;



import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mentalitree.ui.today.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataHandler {

    public int getUsersCurrentStreak() {
        return usersCurrentStreak;
    }

    private static DataHandler single_instance = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userPin, userId;
    String userToken;
    private static final String TAG = "MMDATAHANDLER";
    public static String DATE_FORMAT_INPUT = "yyyy-MM-dd-HH:mm:ss";
    int usersCurrentStreak;
    ArrayList<TaskModel> todaysTasks = new ArrayList<>();


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

    public void userAuthenticationSuccessful(MyFirebaseBooleanCallBack firebaseCallBack){
        CollectionReference usersRef = db.collection("users");

        Query query = usersRef.whereEqualTo("userId", this.userId).whereEqualTo("userPin", this.userPin);

        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                if(!task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        this.userToken = document.getId();
                        Map<String, Object> data = document.getData();
                        this.usersCurrentStreak = ((Long) data.get("currentStreak")).intValue();
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

    public void getActivityLog(MyFirebaseStringListCallback firebaseCallBack){
        db.collection("users").document(userToken).collection("activityLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String timeStamp = data.get("timeStamp").toString();
                                list.add(timeStamp);
                                Log.d(TAG, "Found document: "+ document.getId() + " => " + document.getData());
                            }
                            LocalDate lastEntry = convert(list.get(list.size()-2));
                            Log.d(TAG, "LastEntry is: " + lastEntry);
                            increaseCurrentStreakIfNessercary(lastEntry);
                            firebaseCallBack.onCallback(list);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public LocalDate convert(String dateStr) {
        return (LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_INPUT)));
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
                        Log.d(TAG, "DocumentSnapshot for timestamp successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void increaseCurrentStreakIfNessercary(LocalDate lastEntry){
        LocalDate today = LocalDate.now();

        Log.d(TAG, "Hello from increasing streak method");

        Log.d(TAG, "Todays date for comp is: " + today+"\nlastEntry is: " + lastEntry + "\ntoday.minusDays(1): " + today.minusDays(1));
        if(!(lastEntry.isEqual(today.minusDays(1))) && !(lastEntry.isEqual(today))){
            Log.d(TAG, "Ooops i dont think you hit the streak");
            usersCurrentStreak = 1;
            updateStreakInDatabase();

        }else if(lastEntry.isEqual(today.minusDays(1))){
            Log.d(TAG, "Yes you hit the streak yay!");
            usersCurrentStreak++;
            updateStreakInDatabase();
        }
    }

    public void addCompletedTaskToLog(TaskModel taskModel){

        String pattern = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());


        Map<String, String> logEntry = new HashMap<>();
        logEntry.put("timeStamp", date);
        logEntry.put("category", taskModel.getCategory());
        logEntry.put("taskId", taskModel.getTaskId());
        logEntry.put("taskName", taskModel.getTaskName());


        db.collection("users").document(userToken).collection("taskLog").document(date)
                .set(logEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot for logentry successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }

    public void getWorkbookTaskFromDatabase(MyFirebaseTaskModelListCallback myFirebaseCallback){
        db.collection("workbook-tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<TaskModel> taskList  =new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> dataObject;
                                dataObject = document.getData();
                                TaskModel workbookTask = new TaskModel(dataObject.get("taskName").toString(),
                                        dataObject.get("shortDescription").toString(),
                                        dataObject.get("category").toString(),
                                        dataObject.get("additionalText").toString(),
                                        dataObject.get("taskId").toString());
                                taskList.add(workbookTask);

                                Log.d(TAG, document.getId() + " => " + document.getData()+"\nI made a taskModel object like: "+workbookTask.toString());
                            }
                            chooseTodaysTasks(taskList);
                            myFirebaseCallback.onCallback(todaysTasks);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void chooseTodaysTasks(ArrayList<TaskModel> list){
        this.todaysTasks = (ArrayList<TaskModel>) list.stream().limit(5).collect(Collectors.toList());

    }

    public void updateStreakInDatabase(){
        Map<String, Object> streak = new HashMap<>();
        streak.put("currentStreak", usersCurrentStreak);

        db.collection("users").document(userToken)
                .update(streak)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot for streak successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public ArrayList<TaskModel> getTodaysTasks() {
        return todaysTasks;
    }

    public void setTodaysTasks(ArrayList<TaskModel> todaysTasks) {
        this.todaysTasks = todaysTasks;
    }

    public interface MyFirebaseBooleanCallBack {

        void onCallback(boolean flag);

    }

    public interface MyFirebaseStringListCallback {
        void onCallback(ArrayList<String> list);
    }

    public interface MyFirebaseTaskModelListCallback{
        void onCallback(ArrayList<TaskModel> list);
    }


}

