package com.example.mentalitree.ui.profile.submenus.workbook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class WorkbookDayAdapter extends RecyclerView.Adapter<WorkbookDayAdapter.MyViewHolder> {

    ArrayList<WorkbookDayModel> workbookDays;
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    SelectWorkbookNoteListener listener;

    public WorkbookDayAdapter(ArrayList<WorkbookDayModel> workbookDays, SelectWorkbookNoteListener listener) {
        this.workbookDays = workbookDays;
        this.listener = listener;

    }

    @NonNull
    @Override
    public WorkbookDayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.workbook_day_entry, parent, false);

        return new WorkbookDayAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkbookDayAdapter.MyViewHolder holder, int position) {
        holder.dateTv.setText(convertDate(workbookDays.get(position).getTimeStamp()));
        WorkbookDayTaskAdapter taskAdapter = new WorkbookDayTaskAdapter(workbookDays.get(position).getChosenTasks(), this.listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.completedTasksRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(workbookDays.get(position).getChosenTasks().size());

        holder.completedTasksRecyclerView.setAdapter(taskAdapter);
        holder.completedTasksRecyclerView.setLayoutManager(layoutManager);
        holder.completedTasksRecyclerView.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return workbookDays.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dateTv;
        RecyclerView completedTasksRecyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.dateOfWorkbookLogTv);
            completedTasksRecyclerView = itemView.findViewById(R.id.completedTasksRv);
        }
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
