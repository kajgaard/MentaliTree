package com.example.mentalitree.ui.profile.submenus.workbook;

import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;

public class WorkbookDayModel {
    String timeStamp;
    ArrayList<TaskModel> chosenTasks;

    public WorkbookDayModel(String date, ArrayList<TaskModel> tasksCompleted) {
        this.timeStamp = date;
        this.chosenTasks = tasksCompleted;
    }

    public WorkbookDayModel(){}

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ArrayList<TaskModel> getChosenTasks() {
        ArrayList<TaskModel> completedChosenTasks = new ArrayList<>();
        for(TaskModel task : chosenTasks){
            if(task.isCompleted()){
                completedChosenTasks.add(task);
            }
        }
        return completedChosenTasks;
    }

    public void setChosenTasks(ArrayList<TaskModel> chosenTasks) {
        this.chosenTasks = chosenTasks;
    }
}
