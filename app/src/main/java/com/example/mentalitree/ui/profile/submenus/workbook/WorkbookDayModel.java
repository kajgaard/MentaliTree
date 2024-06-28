package com.example.mentalitree.ui.profile.submenus.workbook;

import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;

public class WorkbookDayModel {
    String date;
    ArrayList<TaskModel> tasksCompleted;

    public WorkbookDayModel(String date, ArrayList<TaskModel> tasksCompleted) {
        this.date = date;
        this.tasksCompleted = tasksCompleted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<TaskModel> getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(ArrayList<TaskModel> tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }
}
