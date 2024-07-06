package com.example.mentalitree;

import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;

public class WorkDayLogEntry {

    String timeStamp;
    ArrayList<TaskModel> chosenTasks;

    public WorkDayLogEntry() {
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ArrayList<TaskModel> getChosenTasks() {
        return chosenTasks;
    }

    public void setChosenTasks(ArrayList<TaskModel> chosenTasks) {
        this.chosenTasks = chosenTasks;
    }
}
