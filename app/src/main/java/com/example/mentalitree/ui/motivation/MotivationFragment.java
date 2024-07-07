package com.example.mentalitree.ui.motivation;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.DataHandler;
import com.example.mentalitree.R;
import com.example.mentalitree.databinding.FragmentMotivationBinding;
import com.example.mentalitree.ui.today.WorkbookTaskAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MotivationFragment extends Fragment {
    private static final String TAG = "MMMOTIVATION";
    DataHandler datahandler = DataHandler.getInstance();
    private FragmentMotivationBinding binding;
    private TextView todaysQuoteTv, todaysDateTv;
    RecyclerView previousQuotesRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MotivationViewModel motivationViewModel =
                new ViewModelProvider(this).get(MotivationViewModel.class);

        binding = FragmentMotivationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        previousQuotesRecyclerView = binding.previousQuotesListView;




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todaysQuoteTv = view.findViewById(R.id.todaysQuoteTv);
        todaysDateTv = view.findViewById(R.id.todaysDateTv);
        getPreviousQuotesFromDB();
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
    public void getPreviousQuotesFromDB(){

        datahandler.getQuoteListFromDB(list -> {
            ArrayList<QuoteModel> newQuoteList = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Collections.reverse(list);
            for (QuoteModel quote : list) {
                LocalDate date = LocalDate.parse(quote.getDate(), formatter);
                if (date.isBefore(currentDate)){
                    quote.setDate(convertDate(quote.getDate()));
                    newQuoteList.add(new QuoteModel(quote.getQuote(), quote.getDate()));
                }
                if (date.equals(currentDate)){
                    todaysQuoteTv.setText(quote.getQuote());
                    todaysDateTv.setText(convertDate(quote.getDate()));
                }
            }

            QuoteAdapter adapter = new QuoteAdapter(newQuoteList);

            previousQuotesRecyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            previousQuotesRecyclerView.setLayoutManager(linearLayoutManager);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public String convertDate(String dateString){
        // Parse the date string into a LocalDate object
        LocalDate date = LocalDate.parse(dateString);

        // Get the day of the week, month, and day of the month
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int dayOfMonth = date.getDayOfMonth();

        // Format the day of the month with the appropriate suffix (st, nd, rd, th)
        String dayOfMonthSuffix = getDayOfMonthSuffix(dayOfMonth);

        // Create the final formatted date string
        String formattedDate = String.format("%s %s %d%s", dayOfWeek, month, dayOfMonth, dayOfMonthSuffix);
        return formattedDate;
    }
    // Method to get the appropriate suffix for the day of the month
    private static String getDayOfMonthSuffix(int n) {
        Log.d(TAG,"I am trying to get a suffix for date"+ n );
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}