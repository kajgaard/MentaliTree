package com.example.mentalitree.ui.motivation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.databinding.FragmentMotivationBinding;
import com.example.mentalitree.ui.today.WorkbookTaskAdapter;

import java.util.ArrayList;

public class MotivationFragment extends Fragment {

    private FragmentMotivationBinding binding;
    RecyclerView previousQuotesRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MotivationViewModel motivationViewModel =
                new ViewModelProvider(this).get(MotivationViewModel.class);

        binding = FragmentMotivationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        previousQuotesRecyclerView = binding.previousQuotesListView;

        QuoteAdapter adapter = new QuoteAdapter(getPreviousQuotes());

        previousQuotesRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        previousQuotesRecyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    public ArrayList<QuoteModel> getPreviousQuotes(){
        ArrayList<QuoteModel> mQuotes = new ArrayList<>();
        mQuotes.add(new QuoteModel("Just one small positive thought in the morning can change your whole day", "Wednesday June 12th"));
        mQuotes.add(new QuoteModel("Opportunities don’t happen, you create them", "Tuesday June 11th"));
        mQuotes.add(new QuoteModel("If you believe it will work, you'll see opportunities. If you believe it won't, you will see obstacles.", "Monday June 10th"));
        mQuotes.add(new QuoteModel("Believe you can and you're halfway there.", "Sunday June 9th"));
        mQuotes.add(new QuoteModel("I didn't get there by wishing for it or hoping for it, but by working for it.", "Saturday June 8th"));
        mQuotes.add(new QuoteModel("Success is not final, failure is not fatal: it is the courage to continue that counts.", "Friday June 7th"));
        mQuotes.add(new QuoteModel("Strength does not come from physical capacity. It comes from an indomitable will.", "Thursday June 6th"));

        return mQuotes;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}