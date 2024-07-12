package com.example.mentalitree;

public class CategoryProbability {
    String category;
    int pickAmountCounter;
    Boolean pickedLastTime;
    String pickrateAmountEligibleModifierString;
    Double currentCalculatedProbability;


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
    }

    public Double calculateProbability(){
        Double finalProbability = this.currentCalculatedProbability;
        Double pickedYesterdayModifier = -0.2;
        Double goodRatingModifier = 0.01;
        Double okayRatingModifier = 0.02;
        int habitStartInt = 30;
        int habitEndInt = 60;


        if (this.pickedLastTime){
            finalProbability = this.currentCalculatedProbability + pickedYesterdayModifier;
        }else {

        }
        if(this.pickAmountCounter < habitStartInt){
            if(this.pickrateAmountEligibleModifierString.equals("Okay") ){
                finalProbability = finalProbability + (okayRatingModifier *pickAmountCounter);
                this.currentCalculatedProbability = currentCalculatedProbability+(okayRatingModifier *pickAmountCounter);
            } else if (this.pickrateAmountEligibleModifierString.equals("Good")) {
                finalProbability = finalProbability + (goodRatingModifier *pickAmountCounter);
                this.currentCalculatedProbability = currentCalculatedProbability + (goodRatingModifier * pickAmountCounter);
            }
        }
        else if (this.pickAmountCounter >= habitStartInt && this.pickAmountCounter < habitEndInt) {
            if(this.pickrateAmountEligibleModifierString.equals("Okay")){
                finalProbability = finalProbability + (okayRatingModifier *habitStartInt);
                this.currentCalculatedProbability = currentCalculatedProbability + (okayRatingModifier *habitStartInt);
            } else if (this.pickrateAmountEligibleModifierString.equals("Very good")) {
                finalProbability = finalProbability + (goodRatingModifier *habitStartInt);
                this.currentCalculatedProbability = currentCalculatedProbability + (goodRatingModifier *habitStartInt);
            }
        } else {
            this.setPickAmountCounter(0);
        }
        return  finalProbability;
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

    public Double getCurrentCalculatedProbability() {
        return currentCalculatedProbability;
    }

    public void setCurrentCalculatedProbability(Double currentCalculatedProbability) {
        this.currentCalculatedProbability = currentCalculatedProbability;
    }
}

