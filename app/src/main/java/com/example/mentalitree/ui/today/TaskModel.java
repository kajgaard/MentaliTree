package com.example.mentalitree.ui.today;

public class TaskModel {
    String taskName;
    String taskShortDescription;
    int image;


    public TaskModel(String taskName, String taskShortDescription, int image) {
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
        this.image = image;
    }

    public TaskModel(String taskName, int image) {
        this.image = image;
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskShortDescription() {
        return taskShortDescription;
    }

    public int getImage() {
        return image;
    }
}
