package com.example.mentalitree.ui.today;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;

import java.util.ArrayList;

public class WorkbookTaskAdapter  extends RecyclerView.Adapter<WorkbookTaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<TaskModel> workbookTasks;

    public WorkbookTaskAdapter(ArrayList<TaskModel> workbookTasks) {
        this.workbookTasks = workbookTasks;
    }

    @NonNull
    @Override
    public WorkbookTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating the layout (giving a look to each row)

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.workbook_task, parent, false);

        return new WorkbookTaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkbookTaskAdapter.MyViewHolder holder, int position) {
        //assigning the values to the views in the layout file, based on the position of the recyclerview

        holder.titleTv.setText(workbookTasks.get(position).getTaskName());
        holder.shortdescriptionTv.setText(workbookTasks.get(position).getTaskShortDescription());
        holder.iconIv.setImageResource(workbookTasks.get(position).getImage());
        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        //the number of views we want displayed
        return workbookTasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //grabbing the views from the layout file

        TextView titleTv, shortdescriptionTv;
        ImageView iconIv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.workbookTaskTitleTv);
            shortdescriptionTv = itemView.findViewById(R.id.workbookTaskShortDescriptionTv);
            iconIv = itemView.findViewById(R.id.workbookTaskIconIv);

        }
    }
}
