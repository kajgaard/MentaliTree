package com.example.mentalitree.ui.profile.submenus.notes;

public class NoteModel {

    String date;
    String note;
    int rating;

    public NoteModel(String date, String note, int rating) {
        this.date = date;
        this.note = note;
        this.rating = rating;
    }

    public NoteModel(){};

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRating(String ratingAsString){
        this.rating = Integer.parseInt(ratingAsString);
    }

    public String getRatingAsString(){
        switch (rating){
            case 1:
                return "Good";

            case 2:
                return "Okay";

            case 3:
                return "Not sure";

            case 4:
                return "Poorly";

            case 5:
                return "Very bad";

            default:
                return "No rating";
        }

    }

    public int getRating(){return rating;}
}
