package com.example.mentalitree.ui.motivation;

public class QuoteModel {
    String quote;
    String date;

    public QuoteModel(String quote, String date) {
        this.quote = quote;
        this.date = date;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
