package com.example.mentalitree;

import android.util.Log;

import com.example.mentalitree.ui.today.TaskModel;

import java.util.ArrayList;
import java.util.Random;

public class AdaptiveAlgorithm {
    private static AdaptiveAlgorithm single_instance = null;
    public static final String TAG = "MMALGORITHM";
    DataHandler dataHandler = DataHandler.getInstance();

    private AdaptiveAlgorithm() {
    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized AdaptiveAlgorithm getInstance() {
        if (single_instance == null) {
            single_instance = new AdaptiveAlgorithm();

        }
        return single_instance;
    }


    public ArrayList<TaskModel> offerTasks (ArrayList<TaskModel> allTasksInDatabase){
        ArrayList<Double> allProbs = new ArrayList<>();
        for (CategoryProbability category : dataHandler.getCategoryProbabilities()){
            allProbs.add(category.calculateProbability());
        }
        Double totalProb = 0.0;
        for (Double prob : allProbs) {
            totalProb += prob;
        }
        ArrayList<String> chosenCategories = new ArrayList<>();

        while(chosenCategories.size() < 5){
            double p = Math.random();
            double cumulative = 0.0;
            int counter = 0;
            for (Double prob : allProbs) {
                prob = prob / totalProb;
                cumulative += prob;
                counter += 1;
                if (p <= cumulative) {
                    break;
                }
            }
                switch (counter) {
                    case 1:
                        if (!chosenCategories.contains("reflection")) {
                            chosenCategories.add("reflection");
                        }
                        break;
                    case 2:
                        if (!chosenCategories.contains("nutrition")) {
                            chosenCategories.add("nutrition");
                        }
                        break;
                    case 3:
                        if (!chosenCategories.contains("cleaning")) {
                            chosenCategories.add("cleaning");
                        }
                        break;
                    case 4:
                        if (!chosenCategories.contains("nature")) {
                            chosenCategories.add("nature");
                        }
                        break;
                    case 5:
                        if (!chosenCategories.contains("fitness")) {
                            chosenCategories.add("fitness");
                        }
                        break;
                    case 6:
                        if (!chosenCategories.contains("social")) {
                            chosenCategories.add("social");
                        }
                        break;
                    case 7:
                        if (!chosenCategories.contains("talking")) {
                            chosenCategories.add("talking");
                        }
                        break;
                    case 8:
                        if (!chosenCategories.contains("mindfulness")) {
                            chosenCategories.add("mindfulness");
                        }
                        break;
                    case 9:
                        if (!chosenCategories.contains("thankfulness")) {
                            chosenCategories.add("thankfulness");
                        }
                        break;
                    default:
                        //Log.e(TAG,"Exception!! Counter: "+ counter);
                }
        }
        //Log.e(TAG,"chosenCatgories:"+chosenCategories);
        ArrayList<TaskModel> dataList = allTasksInDatabase;
        ArrayList<TaskModel> finalChosenTasks = new ArrayList<>();
        ArrayList<TaskModel> relevantTaskModels = new ArrayList<>();
        Random rand = new Random();
        for(String category : chosenCategories){
            for(TaskModel taskModel : dataList){
                if(category.equals(taskModel.getCategory())){
                    relevantTaskModels.add(taskModel);
                }
            }
        }
        //Log.e(TAG,"Total tasks in task pool of 5 task genres (should be 25):  "+relevantTaskModels.size());
        while(finalChosenTasks.size()<5) {
            int index = rand.nextInt(relevantTaskModels.size());
            TaskModel chosenTask = relevantTaskModels.get(index);
            if (!finalChosenTasks.contains(chosenTask)) {
                finalChosenTasks.add(chosenTask);
        }
}
        //Log.e(TAG, "Amount of chosen tasks: "+finalChosenTasks.size());
        dataHandler.setHasUserLoggedInPreviouslyToday(true);
        dataHandler.resetYesterdaysDataForCategoryProbabilitiesInDatabaseAfterCalculation();
        return finalChosenTasks;
    }
}
