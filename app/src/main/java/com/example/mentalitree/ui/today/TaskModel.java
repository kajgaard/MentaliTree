package com.example.mentalitree.ui.today;

import com.example.mentalitree.R;


public class TaskModel {
    String taskName;
    String taskShortDescription;
    int image;
    String category;
    String helpText;
    String taskId;
    boolean completed = false;



    public TaskModel(String taskName, String taskShortDescription, String category, String helpText, String taskId) {
        this.taskName = taskName;
        this.taskShortDescription = taskShortDescription;
        this.category = category;
        this.helpText = helpText;
        this.taskId = taskId;
        this.image = getMatchingCategoryIcon(category);
        this.completed = false;
    }

    public TaskModel(){
    }

    public int getMatchingCategoryIcon(String category){
        switch (category){
            case "nutrition":
                this.image = R.drawable.healthy_food_task_icon;
                break;

            case "reflection":
                this.image = R.drawable.reflection_task_icon;
                break;

            case "nature":
                this.image = R.drawable.nature_task_icon;
                break;

            case "cleaning":
                this.image = R.drawable.clean_task_icon;
                break;

            case "fitness":
                this.image = R.drawable.fitness_task_icon;
                break;

            case "social":
                this.image = R.drawable.social_task_icon;
                break;

            case "talking":
                this.image =  R.drawable.talking_task_icon;
                break;

            case "mindfulness":
                this.image = R.drawable.hand_with_heart_task_icon;
                break;

            case "thankfulness":
                this.image = R.drawable.butterfly_task_icon;
                break;
        }

        return this.image;

    }

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
    public String getCategory() {
        return category;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getHelpText() {
        return helpText;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
