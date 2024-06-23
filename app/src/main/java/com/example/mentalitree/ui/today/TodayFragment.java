package com.example.mentalitree.ui.today;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentTodayBinding;

import java.util.ArrayList;

public class TodayFragment extends Fragment {

    private FragmentTodayBinding binding;
     ArrayList<TaskModel> workbookTasks = new ArrayList<>();
     RecyclerView workbookTasksRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TodayViewModel todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);

        binding = FragmentTodayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpWorkbookTasks();
        workbookTasksRv = binding.workbookTasksRv;

        WorkbookTaskAdapter adapter = new WorkbookTaskAdapter(workbookTasks);

        workbookTasksRv.setAdapter(adapter);
        workbookTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpWorkbookTasks(){
        workbookTasks.add(new TaskModel("Affirmations about yourself", "What is a positive affirmation, that reminds\n" +
                "you of your worth and your strength? ", R.drawable.reflection_task_icon));
        workbookTasks.add(new TaskModel("Eat a healthy meal", "Try to incoorporate at least 1 healthy meal today", R.drawable.healthy_food_task_icon));
        workbookTasks.add(new TaskModel("Go for a walk outside", "Enjoy some fresh air with a trip outside", R.drawable.nature_task_icon));
        workbookTasks.add(new TaskModel("Give your kitchen a clean ", "Make it really nice in your kitchen with clean counters", R.drawable.clean_task_icon));
        workbookTasks.add(new TaskModel("Reach out to a friend or loved one ", "Message or call a friend or family member and have a chat", R.drawable.hand_with_heart_task_icon));
    }
}