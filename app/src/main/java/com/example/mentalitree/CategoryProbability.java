package com.example.mentalitree;

public class CategoryProbability {
    String category;
    int pickAmountCounter;
    Boolean pickedLastTime;
    String pickrateAmountEligibleModifierString;


    public CategoryProbability(){

    }

    public CategoryProbability(String category){
        this.category = category;
        this.pickAmountCounter = 0;
        this.pickedLastTime = false;
    }

    public CategoryProbability(String category, Boolean pickedLastTime, String pickrateAmountEligibleModifierString){
        this.category = category;
        this.pickAmountCounter = 0;
        this.pickedLastTime = pickedLastTime;
        this.pickrateAmountEligibleModifierString = pickrateAmountEligibleModifierString;
    }

    public Double calculateProbability(){
        Double finalProbability = 0.0;
        Double baseProbability = 0.5;
        Double pickedYesterdayModifier = -0.2;
        Double goodRatingModifier = 0.01;
        Double okayRatingModifier = 0.02;
        int habitStartInt = 30;
        int habitEndInt = 60;

        finalProbability = finalProbability + baseProbability;
        if (this.pickedLastTime){
            finalProbability = finalProbability + pickedYesterdayModifier;
        }
        if(this.pickAmountCounter < habitStartInt){
            if(this.pickrateAmountEligibleModifierString.equals("Okay") ){
                finalProbability = finalProbability + (okayRatingModifier *pickAmountCounter);
            } else if (this.pickrateAmountEligibleModifierString.equals("Good")) {
                finalProbability = finalProbability + (goodRatingModifier *pickAmountCounter);
            }
        }
        else if (this.pickAmountCounter >= habitStartInt && this.pickAmountCounter < habitEndInt) {
            if(this.pickrateAmountEligibleModifierString.equals("Okay")){
                finalProbability = finalProbability + (okayRatingModifier *habitStartInt);
            } else if (this.pickrateAmountEligibleModifierString.equals("Very good")) {
                finalProbability = finalProbability + (goodRatingModifier *habitStartInt);
            }
        } else {
            this.setPickAmountCounter(0);
        }
        return finalProbability;
    }

    public String getCategory() {return category;}
    public int getPickAmountCounter() {return pickAmountCounter;}


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
}
