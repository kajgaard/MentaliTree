package com.example.mentalitree.ui.today;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentTodayBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class TodayFragment extends Fragment {

    private FragmentTodayBinding binding;
     ArrayList<TaskModel> workbookTasks = new ArrayList<>();
     RecyclerView workbookTasksRv;
     ImageView mondayIv, tuesdayIv, wednesdayIv, thursdayIv, fridayIv, saturdayIv, sundayIv;
     TextView totalDaysCounter, currentStreakCounter;
    DataHandler datahandler = DataHandler.getInstance();
    private static final String TAG = "MMTODAYFRAG";
    public static String DATE_FORMAT_INPUT = "yyyy-MM-dd-HH:mm:ss";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TodayViewModel todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);

        binding = FragmentTodayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpWorkbookTasks();
        workbookTasksRv = binding.workbookTasksRv;



        mondayIv = binding.mondayStreakIv;
        tuesdayIv = binding.tuesdayStreakIv;
        wednesdayIv = binding.wednesdayStreakIv;
        thursdayIv = binding.thursdayStreakIv;
        fridayIv = binding.fridayStreakIv;
        saturdayIv = binding.saturdayStreakIv;
        sundayIv = binding.sundayStreakIv;

        totalDaysCounter = binding.highestStreakTv;
        currentStreakCounter = binding.currentStreakTv;

        updateStreak();
        updateWorkbookTasks();

        return root;
    }

    public void updateWorkbookTasks(){
        datahandler.getWorkbookTaskFromDatabase(list -> {
            this.workbookTasks = list;
            WorkbookTaskAdapter adapter = new WorkbookTaskAdapter(workbookTasks);
            workbookTasksRv.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            workbookTasksRv.setLayoutManager(linearLayoutManager);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateStreak(){

        ArrayList<LocalDate> listOfDates = new ArrayList<>();
        ArrayList<String> listOfDateStrings = new ArrayList<>();
        datahandler.getActivityLog(list -> {
            for (String date : list) {
                listOfDates.add(convert(date));
                listOfDateStrings.add(convert(date).toString());
            }
            Log.d(TAG, "The list is now of LocalDates: "+ listOfDates.toString());
            updateStreakUI(listOfDates);
            updateTotalDayCounter(listOfDateStrings);
            updateCurrentStreak();
        });

    }

    public LocalDate convert(String dateStr) {
        return (LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_INPUT)));
    }

    public void updateStreakUI(ArrayList<LocalDate> activityDates){
        LocalDate today = LocalDate.now();

        LocalDate lastSaturday = today.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
        LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate lastThursday = today.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
        LocalDate lastWednesday = today.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
        LocalDate lastTuesday = today.with(TemporalAdjusters.previous(DayOfWeek.TUESDAY));
        LocalDate lastMonday = today.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        switch(today.getDayOfWeek()){
            case SUNDAY:
                streakHit(today, activityDates, sundayIv);
                streakHit(lastSaturday, activityDates, saturdayIv);
                streakHit(lastFriday,activityDates, fridayIv);
                streakHit(lastThursday, activityDates, thursdayIv);
                streakHit(lastWednesday, activityDates, wednesdayIv);
                streakHit(lastTuesday, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;

            case SATURDAY:
                streakHit(today, activityDates, saturdayIv);
                streakHit(lastFriday,activityDates, fridayIv);
                streakHit(lastThursday, activityDates, thursdayIv);
                streakHit(lastWednesday, activityDates, wednesdayIv);
                streakHit(lastTuesday, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;

            case FRIDAY:
                streakHit(today, activityDates, fridayIv);
                streakHit(lastThursday, activityDates, thursdayIv);
                streakHit(lastWednesday, activityDates, wednesdayIv);
                streakHit(lastTuesday, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;

            case THURSDAY:
                streakHit(today, activityDates, thursdayIv);
                streakHit(lastWednesday, activityDates, wednesdayIv);
                streakHit(lastTuesday, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;
            case WEDNESDAY:
                streakHit(today, activityDates, wednesdayIv);
                streakHit(lastTuesday, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;
            case TUESDAY:
                streakHit(today, activityDates, tuesdayIv);
                streakHit(lastMonday,activityDates, mondayIv);
                break;
            case MONDAY:
                streakHit(today,activityDates, mondayIv);
                break;


        }

        Log.d(TAG, "Finished updating streak");
    }

    public void streakHit(LocalDate hitDate, ArrayList<LocalDate> activityDates, ImageView imageView){
        if(activityDates.contains(hitDate)){
            imageView.setImageResource(R.drawable.completed_streak);
        }else{
            if(hitDate == LocalDate.now()){
                imageView.setImageResource(R.drawable.today_pending_streak);
            }else {
                imageView.setImageResource(R.drawable.missed_streak);
            }
        }
    }

    public void updateTotalDayCounter(ArrayList<String> list){
        long n = list.stream().distinct().count();
        String value = ""+n;
        totalDaysCounter.setText(value);
    }

    public void updateCurrentStreak(){
        String value = String.valueOf(datahandler.getUsersCurrentStreak());
        Log.d(TAG, "I am setting value for current streak: " + value);
       currentStreakCounter.setText(value);

    }

    private void setUpWorkbookTasks(){
        workbookTasks.clear();
        workbookTasks.add(new TaskModel("Affirmations about yourself", "What is a positive affirmation, that reminds you of your worth and your strength? ", R.drawable.reflection_task_icon));
        workbookTasks.add(new TaskModel("Eat a healthy meal", "Try to incorporate at least 1 healthy meal today", R.drawable.healthy_food_task_icon));
        workbookTasks.add(new TaskModel("Go for a walk outside", "Enjoy some fresh air with a trip outside", R.drawable.nature_task_icon));
        workbookTasks.add(new TaskModel("Give your kitchen a clean ", "Make it really nice in your kitchen with clean counters", R.drawable.clean_task_icon));
        workbookTasks.add(new TaskModel("Reach out to a friend or loved one ", "Message or call a friend or family member and have a chat", R.drawable.hand_with_heart_task_icon));
    }
}