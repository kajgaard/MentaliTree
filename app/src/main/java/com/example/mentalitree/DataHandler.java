package com.example.mentalitree;



import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mentalitree.ui.motivation.QuoteModel;
import com.example.mentalitree.ui.profile.submenus.notes.NoteModel;
import com.example.mentalitree.ui.today.TaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    boolean firstLogonToday;
    boolean firstLoginEver;
    boolean hasReviewedToday;
    PrivateDataHandler privateDataHandler = PrivateDataHandler.getInstance();


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
                            try {
                                LocalDate lastEntry = convert(list.get(list.size() - 2));
                                Log.d(TAG, "LastEntry is: " + lastEntry);
                                increaseCurrentStreakIfNessercary(lastEntry);
                                firebaseCallBack.onCallback(list);
                            }catch (Exception e){
                                //LocalDate lastEntry = LocalDate.now().plusDays(1);
                                LocalDate lastEntry = convert(list.get(list.size() - 1));
                                firstLoginEver = true;
                                Log.d(TAG, "LastEntry is: " + lastEntry);
                                increaseCurrentStreakIfNessercary(lastEntry);
                                firebaseCallBack.onCallback(list);
                            }

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

    public boolean isFirstLogonToday() {
        return firstLogonToday;
    }

    public void increaseCurrentStreakIfNessercary(LocalDate lastEntry){
        LocalDate today = LocalDate.now();

        Log.d(TAG, "Hello from increasing streak method");

        Log.d(TAG, "Todays date for comp is: " + today+"\nlastEntry is: " + lastEntry + "\ntoday.minusDays(1): " + today.minusDays(1));
        if(!(lastEntry.isEqual(today.minusDays(1))) && !(lastEntry.isEqual(today))){
            Log.d(TAG, "Ooops i dont think you hit the streak");
            usersCurrentStreak = 1;
            firstLogonToday = true;
            updateStreakInDatabase();
            getWorkbookTaskFromDatabase(list -> {
                Log.d(TAG, "(1)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                todaysTasks = list;
                Log.d(TAG, "(2)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                writeTodaysChosenTasksToLog(flag -> {
                    Log.d(TAG, "I am done updating the db with chosen tasks");
                });
            });


        }else if(lastEntry.isEqual(today.minusDays(1))){
            Log.d(TAG, "Yes you hit the streak yay!");
            firstLogonToday = true;
            usersCurrentStreak++;
            updateStreakInDatabase();
            getWorkbookTaskFromDatabase(list -> {
                Log.d(TAG, "(1)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                todaysTasks = list;
                Log.d(TAG, "(2)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                writeTodaysChosenTasksToLog(flag -> {
                    Log.d(TAG, "I am done updating the db with chosen tasks");
                });
            });

        }else if((lastEntry.isEqual(today)) && firstLoginEver){

            Log.d(TAG, "Seems like this is your first login ever! I will create new tasks ");
            firstLogonToday = true;
            usersCurrentStreak++;
            updateStreakInDatabase();
            getWorkbookTaskFromDatabase(list -> {
                Log.d(TAG, "(1)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                todaysTasks = list;
                Log.d(TAG, "(2)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                writeTodaysChosenTasksToLog(flag -> {
                    Log.d(TAG, "I am done updating the db with chosen tasks");
                });
            });
            /*Log.d(TAG, "Today is normal same day...");
            usersCurrentStreak++;
            updateStreakInDatabase();
            getWorkbookTaskFromDatabase(list -> {
                Log.d(TAG, "(1)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                todaysTasks = list;
                Log.d(TAG, "(2)Hello from shitty method: list is: "+ list + "\ntodays tasks are: "+todaysTasks);
                writeTodaysChosenTasksToLog();
            });*/
        }
    }

    public void getChosenTasksFromDatabase(MyFirebaseTaskModelListCallback firebaseCallback){

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        db.collection("users").document(userToken).collection("taskLog").document(date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {

                                WorkDayLogEntry entry = document.toObject(WorkDayLogEntry.class);
                                Log.d(TAG,"FINALLY THE OBJECT IS: "+entry);
                                if(entry != null) {
                                    todaysTasks = entry.getChosenTasks();
                                    Log.d(TAG,"SEEEE WHAT TODAYS TASKS ARE: "+todaysTasks);
                                    firebaseCallback.onCallback(todaysTasks);

                                }

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
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


        db.collection("users").document(userToken).collection("completionLog").document(date)
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

    public void updateChosenTasksInDatabase(){

        String simplePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(simplePattern);

        String specificPattern = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat specificDateFormat = new SimpleDateFormat(specificPattern);

        String simpleDate = simpleDateFormat.format(new Date());
        String specificDate = specificDateFormat.format(new Date());


        Map<String, Object> dayEntry = new HashMap<>();
        dayEntry.put("timeStamp", simpleDate);
        dayEntry.put("chosenTasks", this.todaysTasks);

        db.collection("users").document(userToken).collection("taskLog").document(simpleDate)
                .set(dayEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot for dayentry successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }

    public void getWorkbookTaskFromDatabase(MyFirebaseTaskModelListCallback firebaseCallback){
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
                            Log.d(TAG, "Sending callback with list: "+ chooseTodaysTasks(taskList));
                            firebaseCallback.onCallback(chooseTodaysTasks(taskList));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public ArrayList<TaskModel> chooseTodaysTasks(ArrayList<TaskModel> list){
        ArrayList<TaskModel> chosen = new ArrayList<>();
                chosen = (ArrayList<TaskModel>) list.stream().limit(5).collect(Collectors.toList());
        return chosen;
    }

    public void writeTodaysChosenTasksToLog(MyFirebaseBooleanCallBack firebaseBooleanCallBack){

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String date = simpleDateFormat.format(new Date());
            System.out.println(date);

            Map<String, Object> taskLog = new HashMap<>();
            taskLog.put("timeStamp", date);
            taskLog.put("chosenTasks", this.todaysTasks);

            db.collection("users").document(userToken).collection("taskLog").document(date)
                    .set(taskLog)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot for taskLog successfully written!");
                            firebaseBooleanCallBack.onCallback(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });

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
        updateChosenTasksInDatabase();
    }

    public void getQuoteListFromDB(MyFirebaseQuoteListCallback firebaseCallback){
        db.collection("quotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<QuoteModel> quoteList  = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> dataObject;
                                dataObject = document.getData();
                                QuoteModel quote = new QuoteModel(dataObject.get("motivational_quote").toString(),
                                        dataObject.get("date").toString());
                                quoteList.add(quote);

                                Log.d(TAG, document.getId() + " => " + document.getData()+"\nI made a quote object like: "+quote.toString());
                            }
                            Log.d(TAG, "Sending callback with list: "+ quoteList);
                            firebaseCallback.onCallback(quoteList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveEncryptedNoteToDatabase(String encryptedString, String encryptedRating){
        String pattern = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String shortPattern = "yyyy-MM-dd";
        SimpleDateFormat shortSimpleDateFormat = new SimpleDateFormat(shortPattern);

        String shortDate = shortSimpleDateFormat.format(new Date());
        String date = simpleDateFormat.format(new Date());

        Map<String, String> noteEntry = new HashMap<>();
        noteEntry.put("timeStamp", date);
        noteEntry.put("date",shortDate);
        noteEntry.put("note",encryptedString);
        noteEntry.put("rating", encryptedRating);

        db.collection("users").document(userToken).collection("notesLog").document(shortDate)
                .set(noteEntry)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot for NoteEntry successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void setHasReviewedToday(boolean hasReviewedToday) {
        this.hasReviewedToday = hasReviewedToday;
    }

    public void getNotesDecryptedFromDatabase(MyFirebaseNoteListCallback firebaseCallback){
        db.collection("users").document(userToken).collection("notesLog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<NoteModel> noteList  = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> dataObject;
                                dataObject = document.getData();
                                String decryptedNote = privateDataHandler.decryptString(dataObject.get("note").toString());
                                String decryptedRating = privateDataHandler.decryptString(dataObject.get("rating").toString());

                                NoteModel note = new NoteModel(dataObject.get("date").toString(),decryptedNote,Integer.parseInt(decryptedRating));
                                noteList.add(note);


                                Log.d(TAG, document.getId() + " => " + document.getData()+"\nI made a note object like: "+note.toString());
                            }
                            Log.d(TAG, "Sending callback with list: "+ noteList);
                            firebaseCallback.onCallback(noteList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void hasUserReviewedToday(MyFirebaseBooleanCallBack firebaseCallBack){
        CollectionReference usersRef = db.collection("users").document(userToken).collection("notesLog");

        String shortPattern = "yyyy-MM-dd";
        SimpleDateFormat shortSimpleDateFormat = new SimpleDateFormat(shortPattern);

        String shortDate = shortSimpleDateFormat.format(new Date());
        Query query = usersRef.whereEqualTo("date", shortDate);

        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                if(!task.getResult().isEmpty()) {
                    this.hasReviewedToday = true;
                    firebaseCallBack.onCallback(true);

                }else{
                    this.hasReviewedToday = false;
                    firebaseCallBack.onCallback(false);
                }
            }else{
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

    }

    public boolean isHasReviewedToday() {
        return hasReviewedToday;
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
    public interface MyFirebaseQuoteListCallback {
        void onCallback(ArrayList<QuoteModel> list);
    }

    public interface  MyFirebaseNoteListCallback{
        void onCallback(ArrayList<NoteModel> list);
    }

}

