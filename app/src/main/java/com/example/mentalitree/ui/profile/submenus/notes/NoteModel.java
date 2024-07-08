package com.example.mentalitree.ui.profile.submenus.notes;

public class NoteModel {

    String date;
    String note;
    int rating;

    public NoteModel(String date, String note) {
        this.date = date;
        this.note = note;
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

    public int getRating(){return rating;}
}
