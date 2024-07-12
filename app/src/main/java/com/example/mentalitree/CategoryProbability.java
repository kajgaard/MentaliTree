package com.example.mentalitree;

import androidx.annotation.NonNull;

public class CategoryProbability{
    String category;
    int pickAmountCounter;
    Boolean pickedLastTime;
    String pickrateAmountEligibleModifierString;
    Double currentCalculatedProbability;
    Double cumulativeReviewStringModifier;
    int image;


    public CategoryProbability(){

    }

    public CategoryProbability(String category){
        this.category = category;
        this.pickAmountCounter = 0;
        this.pickedLastTime = false;
        this.currentCalculatedProbability = 0.0;

    }

    public CategoryProbability(String category, Boolean pickedLastTime, String pickrateAmountEligibleModifierString){
        this.category = category;
        this.pickAmountCounter = 0;
        this.pickedLastTime = pickedLastTime;
        this.pickrateAmountEligibleModifierString = pickrateAmountEligibleModifierString;
        this.currentCalculatedProbability = 0.5;
        this.cumulativeReviewStringModifier = 0.0;
    }

    public Double calculateProbability(){
        Double finalProbability = this.currentCalculatedProbability;
        Double pickedYesterdayModifier = -0.2;
        Double goodRatingModifier = 0.02;
        Double okayRatingModifier = 0.01;
        int habitStartInt = 30;
        int habitEndInt = 60;


        if (this.pickedLastTime){
            finalProbability = this.currentCalculatedProbability + pickedYesterdayModifier;
        }else {

        }
        if(this.pickAmountCounter < habitStartInt) {
            if(this.pickrateAmountEligibleModifierString.equals("Okay")) {
                this.cumulativeReviewStringModifier = this.cumulativeReviewStringModifier + okayRatingModifier;
                finalProbability = finalProbability + this.cumulativeReviewStringModifier;
                this.currentCalculatedProbability = 0.5 + this.cumulativeReviewStringModifier;
            } else if (this.pickrateAmountEligibleModifierString.equals("Good")) {
                this.cumulativeReviewStringModifier = this.cumulativeReviewStringModifier + goodRatingModifier;
                finalProbability = finalProbability + this.cumulativeReviewStringModifier;
                this.currentCalculatedProbability = 0.5 + this.cumulativeReviewStringModifier;
            }
        }
        else if (this.pickAmountCounter >= habitStartInt && this.pickAmountCounter < habitEndInt) {
            if(this.pickrateAmountEligibleModifierString.equals("Okay")){
                finalProbability = finalProbability + this.cumulativeReviewStringModifier;
            } else if (this.pickrateAmountEligibleModifierString.equals("Good")) {
                finalProbability = finalProbability + this.cumulativeReviewStringModifier;
            }
        } else {
            this.setCumulativeReviewStringModifier(0.0);
            this.setPickAmountCounter(0);
        }
        return  finalProbability;
    }

    public String getCategory() {return category;}
    public int getPickAmountCounter() {return pickAmountCounter;}

    public Double getCumulativeReviewStringModifier() {
        return cumulativeReviewStringModifier;
    }

    public void setCumulativeReviewStringModifier(Double cumulativeReviewStringModifier) {
        this.cumulativeReviewStringModifier = cumulativeReviewStringModifier;
    }

    public Boolean getPickedLastTime() {
        return pickedLastTime;
    }

    public void setPickedLastTime(Boolean pickedLastTime) {
        this.pickedLastTime = pickedLastTime;
    }

    public String getPickrateAmountEligibleModifierString() {
        return pickrateAmountEligibleModifierString;
    }

    public void setPickrateAmountEligibleModifierString(String pickrateAmountEligibleModifierString) {
        this.pickrateAmountEligibleModifierString = pickrateAmountEligibleModifierString;
    }

    public void setCategory(String category) {this.category = category;}
    public void setPickAmountCounter(int PickAmountCounter) {this.pickAmountCounter = PickAmountCounter;}

    public Double getCurrentCalculatedProbability() {
        return currentCalculatedProbability;
    }

    public void setCurrentCalculatedProbability(Double currentCalculatedProbability) {
        this.currentCalculatedProbability = currentCalculatedProbability;
    }

    @NonNull
    @Override
    public String toString() {
        return "CategoryProbability " + this.getCategory() + " Probaility: " + this.getCurrentCalculatedProbability();
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
}

