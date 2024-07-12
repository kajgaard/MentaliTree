package com.example.mentalitree.ui.profile.submenus.statistics;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mentalitree.CategoryProbability;
import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.BarchartStatWidgetBinding;
import com.example.mentalitree.databinding.CurrentStatWidgetBinding;
import com.example.mentalitree.databinding.FragmentProfileStatisticsBinding;
import com.example.mentalitree.databinding.LongestStatWidgetBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatisticsFragment extends Fragment {
    FragmentProfileStatisticsBinding binding;
    BarchartStatWidgetBinding barchartStatWidgetBinding;
    LongestStatWidgetBinding longestStatWidgetBinding;
    CurrentStatWidgetBinding currentStatWidgetBinding;
    ImageView bar1st, bar2nd, bar3rd, bar4th, bar5th;
    ImageView icon1st, icon2nd, icon3rd, icon4th, icon5th;
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

        ArrayList<ImageView> barsInChart = new ArrayList<>();

        bar1st = root.findViewById(R.id.bar1stIv);
        barsInChart.add(bar1st);
        bar2nd = root.findViewById(R.id.bar2ndIv);
        barsInChart.add(bar2nd);
        bar3rd = root.findViewById(R.id.bar3rdIv);
        barsInChart.add(bar3rd);
        bar4th = root.findViewById(R.id.bar4thIv);
        barsInChart.add(bar4th);
        bar5th = root.findViewById(R.id.bar5thIv);
        barsInChart.add(bar5th);

        ArrayList<ImageView> iconsInChart = new ArrayList<>();
        icon1st = root.findViewById(R.id.icon1stIv);
        iconsInChart.add(icon1st);
        icon2nd = root.findViewById(R.id.icon2ndIv);
        iconsInChart.add(icon2nd);
        icon3rd = root.findViewById(R.id.icon3rdIv);
        iconsInChart.add(icon3rd);
        icon4th = root.findViewById(R.id.icon4thIv);
        iconsInChart.add(icon4th);
        icon5th = root.findViewById(R.id.icon5thIv);
        iconsInChart.add(icon5th);



        ArrayList<CategoryProbability> categoryProbabilities = dataHandler.getCategoryProbabilities();

        Log.d("STATFRAG", "The unsorted catgoryprobabs are: "+ categoryProbabilities.toString());

        Collections.sort(categoryProbabilities, new Comparator<CategoryProbability>() {
            @Override
            public int compare(CategoryProbability catProb1, CategoryProbability catProb2) {
                return catProb2.getCurrentCalculatedProbability().compareTo(catProb1.getCurrentCalculatedProbability());
            }
        });
        Log.d("STATFRAG", "I would say that the sorted categoryProbabilities are now "+ categoryProbabilities.toString());

        Double uiModifierFactor = 1 - categoryProbabilities.get(0).getCurrentCalculatedProbability();
        Double ratio = uiModifierFactor / categoryProbabilities.get(0).getCurrentCalculatedProbability();
        for(int i = 0; i < 5; i++){
            Log.e("STATFRAG", "Float vlaue of " + categoryProbabilities.get(i).toString() + " is " + categoryProbabilities.get(1).getCurrentCalculatedProbability().floatValue());


            Double calculatedRatio = (1+ratio) * categoryProbabilities.get(i).getCurrentCalculatedProbability();

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    calculatedRatio.floatValue()
            );
            barsInChart.get(i).setLayoutParams(param);
            iconsInChart.get(i).setImageResource(categoryProbabilities.get(i).getMatchingCategoryIcon(categoryProbabilities.get(i).getCategory()));
        }


        return root;
    }
}