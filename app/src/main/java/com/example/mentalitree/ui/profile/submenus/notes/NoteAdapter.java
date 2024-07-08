package com.example.mentalitree.ui.profile.submenus.notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    ArrayList<NoteModel> notes;
    public static final String TAG = "MMNOTEADAPTER";

    public NoteAdapter(ArrayList<NoteModel> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);

        return new NoteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, int position) {
        holder.dateTv.setText(convertDate(notes.get(position).getDate()));
        holder.ratingTv.setText(notes.get(position).getRatingAsString());

        if(notes.get(position).getNote().isEmpty()){
            holder.noteTv.setVisibility(View.GONE);
        }else{
            holder.noteTv.setText(notes.get(position).getNote());
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dateTv;
        TextView noteTv;
        TextView ratingTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.noteDateTv);
            noteTv = itemView.findViewById(R.id.noteTextTv);
            ratingTv = itemView.findViewById(R.id.dayRatingOnNoteTv);
        }
    }

}
