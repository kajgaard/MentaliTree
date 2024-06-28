package com.example.mentalitree.ui.profile.submenus.workbook;

import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;
import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;

public class WorkbookDayAdapter extends RecyclerView.Adapter<WorkbookDayAdapter.MyViewHolder> {

    ArrayList<WorkbookDayModel> workbookDays;
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public WorkbookDayAdapter(ArrayList<WorkbookDayModel> workbookDays) {
        this.workbookDays = workbookDays;
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
        holder.dateTv.setText(workbookDays.get(position).getDate());
        WorkbookDayTaskAdapter taskAdapter = new WorkbookDayTaskAdapter(workbookDays.get(position).getTasksCompleted());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.completedTasksRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(workbookDays.get(position).getTasksCompleted().size());

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
}
