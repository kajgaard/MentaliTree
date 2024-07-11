package com.example.mentalitree.ui.profile.submenus.statistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.BarchartStatWidgetBinding;
import com.example.mentalitree.databinding.CurrentStatWidgetBinding;
import com.example.mentalitree.databinding.FragmentProfileStatisticsBinding;
import com.example.mentalitree.databinding.LongestStatWidgetBinding;

public class StatisticsFragment extends Fragment {
    FragmentProfileStatisticsBinding binding;
    BarchartStatWidgetBinding barchartStatWidgetBinding;
    LongestStatWidgetBinding longestStatWidgetBinding;
    CurrentStatWidgetBinding currentStatWidgetBinding;
    TextView currentStreakTv, totalEffortStreakTv;
    DataHandler dataHandler = DataHandler.getInstance();
    public StatisticsFragment() {
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

        binding = FragmentProfileStatisticsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        totalEffortStreakTv = root.findViewById(R.id.longestNumberTv);
        totalEffortStreakTv.setText(""+dataHandler.getTotalEffortStreak());

        currentStreakTv = root.findViewById(R.id.currentNumberTv);
        currentStreakTv.setText(""+dataHandler.getUsersCurrentStreak());


        return root;
    }
}