package com.example.mentalitree.ui.profile.submenus.workbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentProfileNotesBinding;
import com.example.mentalitree.databinding.FragmentProfileWorkbookBinding;
import com.example.mentalitree.ui.today.TaskModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class WorkbookFragment extends Fragment {

    RecyclerView workbookDaysRecyclerView;
    FragmentProfileWorkbookBinding binding;
    DataHandler dataHandler = DataHandler.getInstance();

    public WorkbookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProfileWorkbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        workbookDaysRecyclerView = binding.workbookDaysRecyclerView;

        getWorkbookLog();


        return root;
    }

    public void getWorkbookLog(){
       /* ArrayList<WorkbookDayModel> workbookDays = new ArrayList<>();
        ArrayList<TaskModel> arr1 = new ArrayList<>();
        arr1.add(new TaskModel("Affirmations about yourself", R.drawable.reflection_task_icon));
        arr1.add(new TaskModel("Get social", R.drawable.social_task_icon));
        workbookDays.add(new WorkbookDayModel("Wednesday June 12th", arr1));

        ArrayList<TaskModel> arr2 = new ArrayList<>();
        arr2.add(new TaskModel("Thankfullness", R.drawable.butterfly_task_icon));
        workbookDays.add(new WorkbookDayModel("Tuesday June 11th", arr2));

        ArrayList<TaskModel> emptyArr = new ArrayList<>();
        workbookDays.add(new WorkbookDayModel("Monday June 10th", emptyArr));
        workbookDays.add(new WorkbookDayModel("Sunday June 9th", emptyArr));

        ArrayList<TaskModel> arr3 = new ArrayList<>();
        arr3.add(new TaskModel("Reach out", R.drawable.hand_with_heart_task_icon));
        arr3.add(new TaskModel("Eat a healthy meal", R.drawable.healthy_food_task_icon));
        arr3.add(new TaskModel("Listing relaxing activity", R.drawable.talking_task_icon));
        workbookDays.add(new WorkbookDayModel("Saturday June 8th", arr3));

        return workbookDays;*/

        dataHandler.getLogOfCompletedTasks(list -> {
            Collections.reverse(list);
            WorkbookDayAdapter adapter = new WorkbookDayAdapter(list);
            workbookDaysRecyclerView.setAdapter(adapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            workbookDaysRecyclerView.setLayoutManager(linearLayoutManager);
        });
    }
}