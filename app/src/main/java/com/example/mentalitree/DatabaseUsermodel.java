package com.example.mentalitree;

import java.util.ArrayList;

public class DatabaseUsermodel {
    int avatar;
    ArrayList<CategoryProbability> categoryProbabilities;
    String creationDate;
    int currentStreak;
    int totalEffortStreak;
    String userId;
    String userPin;

    public DatabaseUsermodel(){

    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public ArrayList<CategoryProbability> getCategoryProbabilities() {
        return categoryProbabilities;
    }

    public void setCategoryProbabilities(ArrayList<CategoryProbability> categoryProbabilities) {
        this.categoryProbabilities = categoryProbabilities;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getTotalEffortStreak() {
        return totalEffortStreak;
    }

    public void setTotalEffortStreak(int totalEffortStreak) {
        this.totalEffortStreak = totalEffortStreak;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }
}

