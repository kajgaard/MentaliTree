package com.example.mentalitree.ui.profile.submenus.workbook;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.MyApplication;
import com.example.mentalitree.R;
import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;

public class WorkbookDayTaskAdapter extends RecyclerView.Adapter<WorkbookDayTaskAdapter.MyViewHolder> {

    ArrayList<TaskModel> completedTasks;
    SelectWorkbookNoteListener listener;

    public WorkbookDayTaskAdapter(ArrayList<TaskModel> completedTasks, SelectWorkbookNoteListener listener){
        this.completedTasks = completedTasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkbookDayTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.workbook_day_task, parent,false);

        return new WorkbookDayTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkbookDayTaskAdapter.MyViewHolder holder, int position) {
        holder.titleTv.setText(completedTasks.get(position).getTaskName());
        //holder.iconIv.setImageResource(completedTasks.get(position).getImage());
        holder.iconIv.setImageResource(completedTasks.get(position).getMatchingCategoryIcon(completedTasks.get(position).getCategory()));
        int finalPosition = position;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(completedTasks.get(finalPosition));
            }
        });

    }

    @Override
    public int getItemCount() {
        return completedTasks.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv;
        ImageView iconIv;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.logTaskNameTv);
            iconIv = itemView.findViewById(R.id.logTaskIconIv);
            layout = itemView.findViewById(R.id.workbookDayTaskLayout);
        }
    }
}
